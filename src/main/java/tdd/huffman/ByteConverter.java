package tdd.huffman;

import java.nio.ByteBuffer;

public class ByteConverter {
    private final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    public byte[] longToBytes(long aLong) {
        buffer.putLong(0, aLong);
        return buffer.array();
    }

    public long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }
}