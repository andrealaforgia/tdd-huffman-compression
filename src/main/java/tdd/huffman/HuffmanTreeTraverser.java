package tdd.huffman;

import javax.swing.plaf.IconUIResource;

public class HuffmanTreeTraverser {
    private final HuffmanTree huffmanTree;
    private BinaryTree<Byte> cursor;

    public HuffmanTreeTraverser(HuffmanTree huffmanTree) {
        this.huffmanTree = huffmanTree;
        reset();
    }

    public void traverseLeftBranch() {
        cursor = cursor.getLeftNode();
    }

    public void traverseRightBranch() {
        cursor = cursor.getRightNode();
    }

    public boolean isCurrentNodeTerminal() {
        return cursor.isTerminalNode();
    }

    public byte getCurrentNodeValue() {
        return cursor.getNodeValue();
    }

    public void reset() {
        cursor = huffmanTree.getBinaryTree();
    }
}
