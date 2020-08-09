package tdd.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

public class BitInputStream {

    private final ByteConverter byteConverter = new ByteConverter();

    private final InputStream inputStream;
    private byte buffer;
    private byte countOfBitsReadFromBuffer;

    public BitInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Optional<Bit> read() throws IOException {
        if (countOfBitsReadFromBuffer % 8 == 0) {
            int nextInt = inputStream.read();
            if (nextInt == -1) {
                return Optional.empty();
            }
            buffer = (byte) nextInt;
            countOfBitsReadFromBuffer = 0;
        }
        byte nextBit = (byte) ((buffer >> (7 - countOfBitsReadFromBuffer++)) & 1);
        return Optional.of(Bit.valueOf(nextBit));
    }

    public Bit expectBit() throws IOException {
        return read().orElseThrow(UnexpectedEndOfInputException::new);
    }

    public Byte expectByte() throws IOException {
        byte aByte = 0;
        for (int i = 0; i < 8; i++) {
            Bit bit = expectBit();
            aByte = (byte) (aByte << 1 | bit.getValue() & 0xff);
        }
        return aByte;
    }

    public Long expectLong() throws IOException {
        byte[] longBytes = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            longBytes[i] = expectByte();
        }
        return byteConverter.bytesToLong(longBytes);
    }
}
