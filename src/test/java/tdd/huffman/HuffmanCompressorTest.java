package tdd.huffman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HuffmanCompressorTest {

    private static final String SINGLE_SYMBOL_INPUT = "AAAAAAAA";
    private static final String MULTI_SYMBOL_INPUT = "AAAABBBB";

    private ByteArrayInputStream inputStream;
    private ByteArrayOutputStream outputStream;
    private HuffmanCompressor huffmanCompressor = new HuffmanCompressor(
            new SymbolWeightMapBuilder(),
            new HuffmanTreeBuilder(),
            new HuffmanTreeSerializer()
    );

    @Test
    void shouldCompressASingleSymbolInputInAConciseForm() throws IOException {
        givenASingleSymbolInput();
        whenCompressingInput();
        thenSingleSymbolInputShouldBeCompressedInAConciseForm();
    }

    @Test
    void shouldCompressAMultiSymbolInput() throws IOException {
        givenAMultiSymbolInput();
        whenCompressingInput();
        thenMultiSymbolInputShouldBeCompressed();
    }

    private void thenMultiSymbolInputShouldBeCompressed() throws IOException {
        // we only validate the compressed data prefix
        // the compressed data cannot be tested deterministically
        assertThat(outputStream.toByteArray()).startsWith(compressedDataPrefix());
    }

    private byte[] compressedDataPrefix() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
        bitOutputStream.write(Bit.one());
        bitOutputStream.writeLong((long) MULTI_SYMBOL_INPUT.length());
        bitOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        // we exclude the final byte, just in case some bits have been flushed
        return Arrays.copyOf(bytes, bytes.length-1);
    }

    private byte[] expectedBytesForCompressedSingleSymbolInput() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
        bitOutputStream.write(Bit.zero());
        bitOutputStream.writeLong((long) SINGLE_SYMBOL_INPUT.length());
        bitOutputStream.writeByte(SINGLE_SYMBOL_INPUT.getBytes()[0]);
        bitOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    private void givenAMultiSymbolInput() {
        inputStream = new ByteArrayInputStream(MULTI_SYMBOL_INPUT.getBytes());
    }

    private void givenASingleSymbolInput() {
        inputStream = new ByteArrayInputStream(SINGLE_SYMBOL_INPUT.getBytes());
    }

    private void thenSingleSymbolInputShouldBeCompressedInAConciseForm() throws IOException {
        assertThat(outputStream.toByteArray())
                .isEqualTo(expectedBytesForCompressedSingleSymbolInput());
    }

    private void whenCompressingInput() throws IOException {
        outputStream = new ByteArrayOutputStream();
        huffmanCompressor.compress(inputStream, outputStream);
    }
}