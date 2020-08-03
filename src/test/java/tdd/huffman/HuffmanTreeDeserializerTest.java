package tdd.huffman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

class HuffmanTreeDeserializerTest {

    private HuffmanTree huffmanTree;
    private ByteArrayInputStream byteArrayInputStream;
    private BitInputStream bitInputStream;
    private HuffmanTreeDeserializer huffmanTreeDeserializer = new HuffmanTreeDeserializer();

    @Test
    public void shouldSerializeHuffmanTree() throws IOException {
        givenASerializedHuffmanTree();
        whenDeserializingHuffmanTree();
        thenHuffmanTreeShouldBeDeserialized();
    }

    private void givenASerializedHuffmanTree() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitOutputStream bitOutputStream = new BitOutputStream(byteArrayOutputStream);
        // 0 1 [A] 0 1 [B] 0 0 1 [C] 1 [D] 1 [E]
        bitOutputStream.write(zero());
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte) 'A');
        bitOutputStream.write(zero());
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte) 'B');
        bitOutputStream.write(zero());
        bitOutputStream.write(zero());
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte) 'C');
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte) 'D');
        bitOutputStream.write(one());
        bitOutputStream.writeByte((byte) 'E');
        bitOutputStream.flush();
        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        bitInputStream = new BitInputStream(byteArrayInputStream);
    }

    private void thenHuffmanTreeShouldBeDeserialized() {
        assertThat(this.huffmanTree).isEqualTo(
                new HuffmanTree(
                        BinaryTree.newNonTerminalNode(
                                BinaryTree.newTerminalNode((byte) 'A'),
                                BinaryTree.newNonTerminalNode(
                                        BinaryTree.newTerminalNode((byte) 'B'),
                                        BinaryTree.newNonTerminalNode(
                                                BinaryTree.newNonTerminalNode(
                                                        BinaryTree.newTerminalNode((byte) 'C'),
                                                        BinaryTree.newTerminalNode((byte) 'D')
                                                ),
                                                BinaryTree.newTerminalNode((byte) 'E')
                                        )
                                )
                        )
                )
        );
    }

    private void whenDeserializingHuffmanTree() throws IOException {
        huffmanTree = huffmanTreeDeserializer.deserialize(bitInputStream);
    }
}