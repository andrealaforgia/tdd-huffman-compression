package tdd.huffman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class HuffmanTreeSerializerTest {

    private HuffmanTree huffmanTree;
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
    private final HuffmanTreeSerializer huffmanTreeSerializer = new HuffmanTreeSerializer();

    @Test
    void shouldSerializeHuffmanTree() throws IOException {
        givenHuffmanTree();
        whenSerializingHuffmanTree();
        thenHuffmanTreeShouldBeSerialized();
    }

    private void thenHuffmanTreeShouldBeSerialized() throws IOException {
        this.bitOutputStream.flush();
        assertThat(byteArrayOutputStream.toByteArray()).isEqualTo(expectedBytes());
    }

    private byte[] expectedBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
        // 0 1 [A] 0 1 [B] 0 0 1 [C] 1 [D] 1 [E]
        bitOutputStream.write(zero());
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte)'A');
        bitOutputStream.write(zero());
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte)'B');
        bitOutputStream.write(zero());
        bitOutputStream.write(zero());
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte)'C');
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte)'D');
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte)'E');
        bitOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    private void whenSerializingHuffmanTree() {
        huffmanTreeSerializer.serialize(huffmanTree, bitOutputStream);
    }

    private void givenHuffmanTree() {
        this.huffmanTree = new HuffmanTree(
                BinaryTree.newNonTerminalNode(
                        BinaryTree.newTerminalNode((byte)'A'),
                        BinaryTree.newNonTerminalNode(
                                BinaryTree.newTerminalNode((byte)'B'),
                                BinaryTree.newNonTerminalNode(
                                        BinaryTree.newNonTerminalNode(
                                                BinaryTree.newTerminalNode((byte)'C'),
                                                BinaryTree.newTerminalNode((byte)'D')
                                        ),
                                        BinaryTree.newTerminalNode((byte)'E')
                                )
                        )
                )
        );
    }
}