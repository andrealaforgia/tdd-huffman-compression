package tdd.huffman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HuffmanCompressionIT {

    private final String TEST_DATA = "But I must explain to you how all this mistaken idea of denouncing pleasure and " +
            "praising pain was born and I will give you a complete account of the system, and expound the actual " +
            "teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, " +
            "dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to " +
            "pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone " +
            "who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally " +
            "circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, " +
            "which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who " +
            "has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, " +
            "or one who avoids a pain that produces no resultant pleasure?";

    private final HuffmanCompressor huffmanCompressor = new HuffmanCompressor(
            new SymbolWeightMapBuilder(),
            new HuffmanTreeBuilder(),
            new HuffmanTreeSerializer()
    );
    private final HuffmanDecompressor huffmanDecompressor = new HuffmanDecompressor(
            new HuffmanTreeDeserializer()
    );
    private final ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();

    @Test
    void shouldCompressAndDecompress() throws IOException {
        whenCompressingData();
        thenDataIsCompressed();
        thenCompressedDataShouldBeCorrectlyDecompressed();
        thenTheCompressionRatioIsPrintedOut();
    }

    private void thenTheCompressionRatioIsPrintedOut() {
        long inputSize = TEST_DATA.getBytes().length;
        System.out.printf("Input size: %d - Compressed size: %d - Compression ratio: %5.2f%%%n",
                inputSize,
                compressedStream.size(),
                (inputSize-compressedStream.size())*100.0/inputSize);
    }

    private void thenDataIsCompressed() {
        assertThat(compressedStream.toByteArray()).hasSizeLessThan(TEST_DATA.getBytes().length);
    }

    private void thenCompressedDataShouldBeCorrectlyDecompressed() throws IOException {
        ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream();
        huffmanDecompressor.decompress(new ByteArrayInputStream(compressedStream.toByteArray()), decompressedStream);
        assertThat(decompressedStream.toByteArray()).isEqualTo(TEST_DATA.getBytes());
    }

    private void whenCompressingData() throws IOException {
        huffmanCompressor.compress(new ByteArrayInputStream(TEST_DATA.getBytes()), compressedStream);
    }
}
