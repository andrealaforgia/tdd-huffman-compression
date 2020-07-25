package tdd.huffman;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class BitTest {

    public static final Bit ZERO = zero();
    public static final Bit ONE = one();

    @Test
    public void shouldBuildBitAsExpected() {
        assertThat(ZERO.getValue()).isEqualTo((byte)0);
        assertThat(ONE.getValue()).isEqualTo((byte)1);
    }

    @Test
    public void shouldRecognizeBit() {
        assertThat(ZERO.isZero()).isTrue();
        assertThat(ZERO.isOne()).isFalse();
        assertThat(ONE.isZero()).isFalse();
        assertThat(ONE.isOne()).isTrue();
    }
}