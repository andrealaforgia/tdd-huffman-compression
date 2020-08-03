package tdd.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class HuffmanDecompressor {

    private final HuffmanTreeDeserializer huffmanTreeDeserializer;

    public HuffmanDecompressor(HuffmanTreeDeserializer huffmanTreeDeserializer) {
        this.huffmanTreeDeserializer = huffmanTreeDeserializer;
    }

    public void decompress(InputStream inputStream, OutputStream outputStream) throws IOException {
        BitInputStream bitInputStream = new BitInputStream(inputStream);

        Bit singleSymbolIndicator = bitInputStream.expectBit();
        long inputLength = bitInputStream.expectLong();

        if (singleSymbolIndicator.isZero()) {
            decompressSingleSymbolStream(bitInputStream, inputLength, outputStream);

        } else {
            decompressMultipleSymbolStream(bitInputStream, inputLength, outputStream);
        }
    }

    private void decompressMultipleSymbolStream(BitInputStream bitInputStream,
                                                long inputLength,
                                                OutputStream outputStream) throws IOException {
        HuffmanTree huffmanTree = huffmanTreeDeserializer.deserialize(bitInputStream);
        HuffmanTreeTraverser huffmanTreeTraverser = new HuffmanTreeTraverser(huffmanTree);

        long writtenByteCount = 0;

        Optional<Bit> optionalBit;
        while (writtenByteCount < inputLength
                && (optionalBit = bitInputStream.read()).isPresent()) {
            if (optionalBit.get().isZero()) {
                huffmanTreeTraverser.traverseLeftBranch();

            } else {
                huffmanTreeTraverser.traverseRightBranch();
            }

            if (huffmanTreeTraverser.isCurrentNodeTerminal()) {
                outputStream.write(huffmanTreeTraverser.getCurrentNodeValue());
                ++writtenByteCount;
                huffmanTreeTraverser.reset();
            }
        }
    }

    private void decompressSingleSymbolStream(BitInputStream bitInputStream,
                                              long inputLength,
                                              OutputStream outputStream) throws IOException {
        byte symbol = bitInputStream.expectByte();
        for (long i = 0; i < inputLength; i++) {
            outputStream.write(symbol);
        }
    }
}
