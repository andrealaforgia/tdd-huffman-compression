package tdd.huffman;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class BitTest {

    public static final Bit ZERO = zero();
    public static final Bit ONE = one();

    @Test
    void shouldBuildBitAsExpected() {
        assertThat(ZERO.getValue()).isEqualTo((byte) 0);
        assertThat(ONE.getValue()).isEqualTo((byte) 1);
    }

    @Test
    void shouldRecognizeBit() {
        assertThat(ZERO.isZero()).isTrue();
        assertThat(ZERO.isOne()).isFalse();
        assertThat(ONE.isZero()).isFalse();
        assertThat(ONE.isOne()).isTrue();
    }

    @Test
    void shouldBuildBitFromInt() {
        assertThat(Bit.valueOf(0)).isEqualTo(zero());
        assertThat(Bit.valueOf(1)).isEqualTo(one());
        assertThatThrownBy(() -> Bit.valueOf(2))
                .as("Could not convert 2 to bit")
                .isInstanceOf(IllegalArgumentException.class);
    }
}