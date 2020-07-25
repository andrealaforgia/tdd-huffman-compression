package tdd.huffman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class BitInputStreamTest {

    private BitInputStream bitInputStream;

    private static final List<Bit> EXPECTED_READ_BITS = asList(
            zero(), one(), zero(), one(), zero(), one(), zero(), one(),
            one(), zero(), one(), zero(), zero(), zero(), one(), one()
    );

    private static final byte[] BYTES = new byte[]{
            (byte) 0b0101_0101,
            (byte) 0b1010_0011
    };

    private final List<Bit> readBits = new ArrayList<>();

    @Test
    public void shouldReadBitsAsExpected() throws IOException {
        givenAnBufferContainingSomeBytes();
        whenReadingTheSingleBitsInTheBuffer();
        thenTheSingleBitsShouldBeReadAsExpected();
    }

    private void thenTheSingleBitsShouldBeReadAsExpected() {
        assertThat(readBits).isEqualTo(EXPECTED_READ_BITS);
    }

    private void whenReadingTheSingleBitsInTheBuffer() throws IOException {
        Optional<Bit> optionalBit;
        while ((optionalBit = bitInputStream.read()).isPresent()) {
            readBits.add(optionalBit.get());
        }
    }

    private void givenAnBufferContainingSomeBytes() {
        bitInputStream = new BitInputStream(new ByteArrayInputStream(BYTES));
    }
}