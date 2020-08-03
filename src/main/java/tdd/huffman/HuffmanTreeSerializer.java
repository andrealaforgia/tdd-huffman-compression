package tdd.huffman;

import java.io.IOException;

import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

public class HuffmanTreeSerializer {

    public void serialize(HuffmanTree huffmanTree, BitOutputStream bitOutputStream) {
        huffmanTree.visit(huffmanTreeNode -> {
            try {
                if (huffmanTreeNode.isTerminalNode()) {
                    serializeTerminalNode(bitOutputStream, huffmanTreeNode);
                } else {
                    serializeNonTerminalNode(bitOutputStream);
                }
            } catch(IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void serializeTerminalNode(BitOutputStream bitOutputStream, BinaryTree<Byte> huffmanTreeNode) throws IOException {
        bitOutputStream.write(one());
        bitOutputStream.writeByte(huffmanTreeNode.getNodeValue());
    }

    private void serializeNonTerminalNode(BitOutputStream bitOutputStream) throws IOException {
        bitOutputStream.write(zero());
    }
}
