package tdd.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SymbolWeightMapBuilder {

    public Map<Byte, Long> build(InputStream inputStream) throws IOException {
        Map<Byte, Long> symbolWeightMap = new HashMap<>();
        int readInt;
        while ((readInt = inputStream.read()) != -1) {
            symbolWeightMap.compute((byte) readInt,
                    (symbol, occurrence) -> occurrence == null ? 1 : occurrence + 1
            );
        }
        return symbolWeightMap;
    }
}
