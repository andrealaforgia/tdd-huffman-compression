package tdd.huffman;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HuffmanTreeTraverserTest {

    private HuffmanTree huffmanTree;
    private HuffmanTreeTraverser huffmanTreeTraverser;

    @Test
    void shouldNavigateAHuffmanTree() {
        givenAHuffmanTree();
        whenTraversingTheHuffmanTree();
        thenTheTreeShouldBeNavigatedAsExpected();
    }

    private void whenTraversingTheHuffmanTree() {
        huffmanTreeTraverser = new HuffmanTreeTraverser(huffmanTree);
    }

    private void thenTheTreeShouldBeNavigatedAsExpected() {
        assertThat(huffmanTreeTraverser.isCurrentNodeTerminal()).isFalse();
        huffmanTreeTraverser.traverseLeftBranch();
        assertThat(huffmanTreeTraverser.isCurrentNodeTerminal()).isTrue();
        assertThat(huffmanTreeTraverser.getCurrentNodeValue()).isEqualTo((byte)'A');
        huffmanTreeTraverser.reset();
        huffmanTreeTraverser.traverseRightBranch();
        assertThat(huffmanTreeTraverser.isCurrentNodeTerminal()).isFalse();
        huffmanTreeTraverser.traverseLeftBranch();
        assertThat(huffmanTreeTraverser.getCurrentNodeValue()).isEqualTo((byte)'B');
    }

    private void givenAHuffmanTree() {
        huffmanTree = new HuffmanTree(
                BinaryTree.newNonTerminalNode(
                        BinaryTree.newTerminalNode((byte)'A'),
                        BinaryTree.newNonTerminalNode(
                                BinaryTree.newTerminalNode((byte)'B'),
                                BinaryTree.newTerminalNode((byte)'C')
                        )
                )
        );
    }

}