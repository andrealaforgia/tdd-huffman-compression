package tdd.huffman;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class HuffmanTreeBuilderTest {

    private Map<Byte, Long> symbolWeightMap;
    private HuffmanTreeBuilder huffmanTreeBuilder = new HuffmanTreeBuilder();
    private HuffmanTree huffmanTree;

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        ImmutableMap.of(
                                (byte) 'a', 5L,
                                (byte) 'b', 2L,
                                (byte) 'c', 1L,
                                (byte) 'd', 1L,
                                (byte) 'r', 2L)
                ),
                Arguments.of(
                        ImmutableMap.builder()
                                .put((byte) 'T', 2L)
                                .put((byte) 'o', 3L)
                                .put((byte) 'B', 2L)
                                .put((byte) 'e', 2L)
                                .put((byte) 'O', 1L)
                                .put((byte) 'r', 1L)
                                .put((byte) 'N', 1L)
                                .put((byte) 't', 1L)
                                .put((byte) ' ', 5L)
                                .build()),
                Arguments.of(
                        ImmutableMap.builder()
                                .put((byte) ' ', 8L)
                                .put((byte) 'o', 4L)
                                .put((byte) 'e', 3L)
                                .put((byte) 'h', 2L)
                                .put((byte) 'u', 2L)
                                .put((byte) 'r', 2L)
                                .put((byte) 'T', 1L)
                                .put((byte) 'q', 1L)
                                .put((byte) 'i', 1L)
                                .put((byte) 'c', 1L)
                                .put((byte) 'k', 1L)
                                .put((byte) 'b', 1L)
                                .put((byte) 'w', 1L)
                                .put((byte) 'n', 1L)
                                .put((byte) 'f', 1L)
                                .put((byte) 'x', 1L)
                                .put((byte) 'j', 1L)
                                .put((byte) 'm', 1L)
                                .put((byte) 'p', 1L)
                                .put((byte) 's', 1L)
                                .put((byte) 'v', 1L)
                                .put((byte) 't', 1L)
                                .put((byte) 'l', 1L)
                                .put((byte) 'a', 1L)
                                .put((byte) 'z', 1L)
                                .put((byte) 'y', 1L)
                                .put((byte) 'd', 1L)
                                .put((byte) 'g', 1L)
                                .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldBuildHuffmanTree(Map<Byte, Long> symbolWeightMap) {
        givenSymbolWeightMap(symbolWeightMap);
        whenBuildingHuffmanTree();
        thenHuffmanTreeShouldBeBuilt();
    }

    private void givenSymbolWeightMap(Map<Byte, Long> symbolWeightMap) {
        this.symbolWeightMap = symbolWeightMap;
    }

    private void thenHuffmanTreeShouldBeBuilt() {
        symbolWeightMap.forEach((symbol, weight) -> {
            List<Bit> huffmanCode = huffmanTree.findHuffmanCodeForSymbol(symbol);

            var heavierSymbols = symbolWeightMap.entrySet().stream()
                    .filter(symbolWeightEntry ->
                            !symbolWeightEntry.getKey().equals(symbol) && symbolWeightEntry.getValue() > weight)
                    .collect(toSet());

            if (!heavierSymbols.isEmpty()) {
                assertThat(heavierSymbols).allMatch(symbolWeightEntry -> {
                    List<Bit> otherHuffmanCode = huffmanTree.findHuffmanCodeForSymbol(symbolWeightEntry.getKey());
                    return otherHuffmanCode.size() <= huffmanCode.size();
                });
            }
        });
    }

    private void whenBuildingHuffmanTree() {
        huffmanTree = huffmanTreeBuilder.build(symbolWeightMap);
    }

}