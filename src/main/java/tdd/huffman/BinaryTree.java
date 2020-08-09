package tdd.huffman;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.function.Consumer;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BinaryTree<T> {

    T nodeValue;
    BinaryTree<T> leftNode;
    BinaryTree<T> rightNode;

    public static<T> BinaryTree<T> newTerminalNode(T nodeValue) {
        return new BinaryTree<>(nodeValue, null, null);
    }

    public static<T> BinaryTree<T> newNonTerminalNode(BinaryTree<T> leftNode, BinaryTree<T> rightNode) {
        return new BinaryTree<>(null, leftNode, rightNode);
    }

    public void visit(Consumer<BinaryTree<T>> consumer) {
        consumer.accept(this);
        if (this.leftNode != null) {
            this.leftNode.visit(consumer);
        }
        if (this.rightNode != null) {
            this.rightNode.visit(consumer);
        }
    }

    public boolean isTerminalNode() {
        return this.leftNode == null && this.rightNode == null;
    }
}