package tdd.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

public class HuffmanCompressor {

    private final SymbolWeightMapBuilder symbolWeightMapBuilder;
    private final HuffmanTreeBuilder huffmanTreeBuilder;
    private final HuffmanTreeSerializer huffmanTreeSerializer;

    public HuffmanCompressor(SymbolWeightMapBuilder symbolWeightMapBuilder, HuffmanTreeBuilder huffmanTreeBuilder, HuffmanTreeSerializer huffmanTreeSerializer) {
        this.symbolWeightMapBuilder = symbolWeightMapBuilder;
        this.huffmanTreeBuilder = huffmanTreeBuilder;
        this.huffmanTreeSerializer = huffmanTreeSerializer;
    }

    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        Map<Byte, Long> symbolWeightMap = symbolWeightMapBuilder.build(inputStream);

        long inputSize = calculateInputSize(symbolWeightMap);

        BitOutputStream bitOutputStream = new BitOutputStream(outputStream);

        if (symbolWeightMap.size() == 1) {
            bitOutputStream.write(zero());
            bitOutputStream.writeLong(inputSize);
            bitOutputStream.writeByte(symbolWeightMap.keySet().iterator().next());

        } else {
            bitOutputStream.write(one());
            inputStream.reset();
            HuffmanTree huffmanTree = huffmanTreeBuilder.build(symbolWeightMap);
            bitOutputStream.writeLong(inputSize);
            huffmanTreeSerializer.serialize(huffmanTree, bitOutputStream);
            compressInputSymbols(inputStream, huffmanTree, bitOutputStream);
        }
        bitOutputStream.flush();
    }

    private void compressInputSymbols(InputStream inputStream,
                                      HuffmanTree huffmanTree,
                                      BitOutputStream bitOutputStream) throws IOException {
        int readByte;
        Map<Byte, List<Bit>> huffmanCodeCache = new HashMap<>();
        while ((readByte = inputStream.read()) != -1) {
            List<Bit> symbolHuffmanCode = huffmanCodeCache.computeIfAbsent((byte)readByte, huffmanTree::findHuffmanCodeForSymbol);
            for (Bit bit : symbolHuffmanCode) {
                bitOutputStream.write(bit);
            }
        }
    }

    private Long calculateInputSize(Map<Byte, Long> symbolWeightMap) {
        return symbolWeightMap.values().stream().reduce(Long::sum).orElseThrow(IllegalStateException::new);
    }
}
