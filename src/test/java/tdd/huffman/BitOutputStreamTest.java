package tdd.huffman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class BitOutputStreamTest {

    private static final List<Bit> BITS = asList(
            zero(), one(), zero(), one(), zero(), one(), zero(), one(),
            one(), zero(), one()
    );

    private static final byte[] EXPECTED_WRITTEN_BYTES_FOR_BITS = new byte[]{
            (byte) 0b0101_0101,
            (byte) 0b1010_0000
    };

    private static final byte A_BYTE = (byte)0xAB;

    private static final byte[] EXPECTED_WRITTEN_BYTES_FOR_BYTE = new byte[]{
            (byte) 0b1010_1011
    };

    private static final long A_LONG = 0xABCDEF0990FEDCBAL;

    private static final byte[] EXPECTED_WRITTEN_BYTES_FOR_LONG = new byte[]{
            (byte)0b10101011,
            (byte)0b11001101,
            (byte)0b11101111,
            (byte)0b00001001,
            (byte)0b10010000,
            (byte)0b11111110,
            (byte)0b11011100,
            (byte)0b10111010
    };

    private ByteArrayOutputStream byteArrayOutputStream;
    private BitOutputStream bitOutputStream;

    @BeforeEach
    void setUp() {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitOutputStream = new BitOutputStream(byteArrayOutputStream);
    }

    @Test
    public void shouldWriteBitsAsExpected() throws IOException {
        whenWritingSomeBits();
        thenTheBitsAreWrittenAsExpected();
    }

    @Test
    public void shouldWriteByteAsExpected() throws IOException {
        whenWritingAByte();
        thenTheByteIsWrittenAsExpected();
    }

    @Test
    public void shouldWriteLongAsExpected() throws IOException {
        whenWritingALong();
        thenTheLongIsWrittenAsExpected();
    }

    private void thenTheBitsAreWrittenAsExpected() {
        assertThat(byteArrayOutputStream.toByteArray()).isEqualTo(EXPECTED_WRITTEN_BYTES_FOR_BITS);
    }

    private void thenTheByteIsWrittenAsExpected() {
        assertThat(byteArrayOutputStream.toByteArray()).isEqualTo(EXPECTED_WRITTEN_BYTES_FOR_BYTE);
    }

    private void thenTheLongIsWrittenAsExpected() {
        assertThat(byteArrayOutputStream.toByteArray()).isEqualTo(EXPECTED_WRITTEN_BYTES_FOR_LONG);
    }

    private void whenWritingAByte() throws IOException {
        bitOutputStream.writeByte(A_BYTE);
    }

    private void whenWritingALong() throws IOException {
        bitOutputStream.writeLong(A_LONG);
    }

    private void whenWritingSomeBits() throws IOException {
        writeTheBits();
        bitOutputStream.flush();
    }

    private void writeTheBits() throws IOException {
        for (Bit bit : BITS) {
            bitOutputStream.write(bit);
        }
    }
}