package tdd.huffman;

import lombok.Value;

@Value
public class Bit {
    byte value;

    public static Bit zero() {
        return new Bit((byte)0);
    }

    public static Bit one() {
        return new Bit((byte)1);
    }

    private Bit(byte value) {
        this.value = value;
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public boolean isOne() {
        return this.value == 1;
    }
}
