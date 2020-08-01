package tdd.huffman;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class HuffmanTreeTest {

    private HuffmanTree huffmanTree;

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        BinaryTree.newNonTerminalNode(
                                BinaryTree.newTerminalNode((byte)1),
                                BinaryTree.newTerminalNode((byte)2)
                        ),
                        ImmutableMap.of(
                                (byte)1, singletonList(zero()),
                                (byte)2, singletonList(one())
                        )
                ),
                Arguments.of(
                        BinaryTree.newNonTerminalNode(
                                BinaryTree.newTerminalNode((byte)1),
                                BinaryTree.newNonTerminalNode(
                                        BinaryTree.newNonTerminalNode(
                                                BinaryTree.newTerminalNode((byte)2),
                                                BinaryTree.newTerminalNode((byte)3)
                                        ),
                                        BinaryTree.newTerminalNode((byte)4)
                                )
                        ),
                        ImmutableMap.of(
                                (byte)1, singletonList(zero()),
                                (byte)2, asList(one(), zero(), zero()),
                                (byte)3, asList(one(), zero(), one()),
                                (byte)4, asList(one(), one())
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldFindHuffmanCodeForSymbol(BinaryTree<Byte> binaryTree, Map<Byte, List<Bit>> expectedHuffmanCodeMap) {
        givenHuffmanTreeFor(binaryTree);
        thenHuffmanCodeShouldBe(expectedHuffmanCodeMap);
    }

    private void thenHuffmanCodeShouldBe(Map<Byte, List<Bit>> expectedHuffmanCodeMap) {
        expectedHuffmanCodeMap.forEach((symbol, huffmanCode) ->
                assertThat(huffmanTree.findHuffmanCodeForSymbol(symbol)).isEqualTo(huffmanCode));
    }

    private void givenHuffmanTreeFor(BinaryTree<Byte> binaryTree) {
        this.huffmanTree = new HuffmanTree(binaryTree);
    }
}