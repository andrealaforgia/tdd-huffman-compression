package tdd.huffman;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class HuffmanCodeTest {

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldCalculateHuffmanCodeLength() {

    }

    private static Stream<Arguments> provideArguments() {
        return null;
    }
}