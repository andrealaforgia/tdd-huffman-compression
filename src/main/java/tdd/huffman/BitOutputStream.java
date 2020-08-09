package tdd.huffman;

import java.io.IOException;
import java.io.OutputStream;

import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

public class BitOutputStream {

    private final OutputStream outputStream;

    private final ByteConverter byteConverter = new ByteConverter();

    private byte buffer;

    private byte countOfBitsWrittenToBuffer;

    public BitOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(Bit bit) throws IOException {
        buffer = (byte) (buffer << 1 | bit.getValue() & 0xff);
        if (++countOfBitsWrittenToBuffer == 8) {
            outputStream.write(buffer);
            buffer = 0;
            countOfBitsWrittenToBuffer = 0;
        }
    }

    public void writeByte(Byte aByte) throws IOException {
        for (int i = 0; i < 8; i++) {
            write(Bit.valueOf((aByte & (1 << 7 - i)) >> (7 - i)));
        }
    }

    public void writeLong(Long aLong) throws IOException {
        for (byte aByte : byteConverter.longToBytes(aLong)) {
            writeByte(aByte);
        }
    }

    public void flush() throws IOException {
        int numOfBitsToFlush = 8 - countOfBitsWrittenToBuffer;
        for (int i = 0; i < numOfBitsToFlush; i++) {
            write(zero());
        }
    }
}
