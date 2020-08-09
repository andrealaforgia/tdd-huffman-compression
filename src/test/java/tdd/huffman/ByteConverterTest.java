package tdd.huffman;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ByteConverterTest {

    private static final byte[] ARRAY_OF_BYTES = new byte[] {
            (byte) 0b10101010,
            (byte) 0b01010101,
            (byte) 0b00101000,
            (byte) 0b00010000,
            (byte) 0b10101010,
            (byte) 0b01010101,
            (byte) 0b00101000,
            (byte) 0b00010000
    };

    private static final long LONG = -6172983662199101424L;

    private final ByteConverter byteConverter = new ByteConverter();

    @Test
    void shouldConvertLongToBytes() {
        assertThat(byteConverter.longToBytes(LONG)).isEqualTo(ARRAY_OF_BYTES);
    }

    @Test
    void shouldConvertBytesToLong() {
        assertThat(byteConverter.bytesToLong(ARRAY_OF_BYTES)).isEqualTo(LONG);
    }
}
