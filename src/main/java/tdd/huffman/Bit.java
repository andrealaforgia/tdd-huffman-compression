package tdd.huffman;

import lombok.Value;

@Value
public class Bit {
    byte value;

    public static Bit zero() {
        return new Bit((byte) 0);
    }

    public static Bit one() {
        return new Bit((byte) 1);
    }

    public static Bit valueOf(int value) {
        switch (value) {
            case 0:
                return zero();
            case 1:
                return one();
        }
        throw new IllegalArgumentException(String.format("Could not convert %d to bit", value));
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

    public String toString() {
        return "" + this.value;
    }
}
