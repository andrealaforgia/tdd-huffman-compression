package tdd.huffman;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

@RequiredArgsConstructor
public class HuffmanCompressor {

    public static final Bit SINGLE_SYMBOL_INDICATOR = zero();
    public static final Bit MULTIPLE_SYMBOLS_INDICATOR = one();

    private final SymbolWeightMapBuilder symbolWeightMapBuilder;
    private final HuffmanTreeBuilder huffmanTreeBuilder;
    private final HuffmanTreeSerializer huffmanTreeSerializer;

    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        compress(inputStream, outputStream, false);
    }

    public void compress(InputStream inputStream, OutputStream outputStream, boolean verbose) throws IOException {
        Map<Byte, Long> symbolWeightMap = symbolWeightMapBuilder.build(inputStream);

        long inputSize = calculateInputSize(symbolWeightMap);

        BitOutputStream bitOutputStream = new BitOutputStream(outputStream);

        if (symbolWeightMap.size() == 1) {
            bitOutputStream.write(SINGLE_SYMBOL_INDICATOR);
            bitOutputStream.writeLong(inputSize);
            bitOutputStream.writeByte(symbolWeightMap.keySet().iterator().next());

        } else {
            bitOutputStream.write(MULTIPLE_SYMBOLS_INDICATOR);
            bitOutputStream.writeLong(inputSize);
            HuffmanTree huffmanTree = huffmanTreeBuilder.build(symbolWeightMap);
            huffmanTreeSerializer.serialize(huffmanTree, bitOutputStream);
            inputStream.reset();
            compressInputSymbols(inputStream, huffmanTree, bitOutputStream, symbolWeightMap, verbose);
        }
        bitOutputStream.flush();
    }

    private void compressInputSymbols(InputStream inputStream,
                                      HuffmanTree huffmanTree,
                                      BitOutputStream bitOutputStream,
                                      Map<Byte, Long> symbolWeightMap,
                                      boolean verbose) throws IOException {
        int readByte;
        Map<Byte, List<Bit>> huffmanCodeCache = new HashMap<>();
        while ((readByte = inputStream.read()) != -1) {
            List<Bit> symbolHuffmanCode = huffmanCodeCache.computeIfAbsent((byte)readByte, huffmanTree::findHuffmanCodeForSymbol);
            for (Bit bit : symbolHuffmanCode) {
                bitOutputStream.write(bit);
            }
        }

        if (verbose) {
            symbolWeightMap.entrySet()
                    .stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                    .forEach(entry -> System.out.printf("[ASCII 0x%2X] [freq: %5d] [code: %s]%n",
                            entry.getKey(),
                            entry.getValue(),
                            huffmanCodeCache.get(entry.getKey()).stream().map(Bit::toString).collect(joining())));
        }
    }

    private Long calculateInputSize(Map<Byte, Long> symbolWeightMap) {
        return symbolWeightMap.values().stream().reduce(Long::sum).orElseThrow(IllegalStateException::new);
    }
}
