package tdd.huffman;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class BinaryTreeTest {

    private BinaryTree<Integer> node;
    private final List<BinaryTree<Integer>> visitedNodes = new ArrayList<>();

    @Test
    void shouldCreateATerminalNode() {
        whenCreatingATerminalNode();
        thenTheTerminalNodeShouldBeCreated();
    }

    @Test
    void shouldCreateANonTerminalNode() {
        whenCreatingANonTerminalNode();
        thenTheNonTerminalNodeShouldBeCreated();
    }

    @Test
    void shouldVisitTree() {
        givenABinaryTree();
        whenVisitingTheBinaryTree();
        thenTheBinaryTreeShouldBeVisited();
    }

    void thenTheBinaryTreeShouldBeVisited() {
        assertThat(visitedNodes).isEqualTo(asList(
                node,
                node.getLeftNode(),
                node.getRightNode(),
                node.getRightNode().getLeftNode(),
                node.getRightNode().getLeftNode().getLeftNode(),
                node.getRightNode().getLeftNode().getRightNode(),
                node.getRightNode().getRightNode()
        ));
    }

    private void whenVisitingTheBinaryTree() {
        node.visit(visitedNodes::add);
    }

    private void givenABinaryTree() {
        node = BinaryTree.newNonTerminalNode(
                BinaryTree.newTerminalNode(100),
                BinaryTree.newNonTerminalNode(
                        BinaryTree.newNonTerminalNode(
                                BinaryTree.newTerminalNode(50),
                                BinaryTree.newTerminalNode(60)
                        ),
                        BinaryTree.newTerminalNode(70)
                )
        );
    }

    private void thenTheNonTerminalNodeShouldBeCreated() {
        assertThat(node.isTerminalNode()).isFalse();
    }

    private void thenTheTerminalNodeShouldBeCreated() {
        assertThat(node.isTerminalNode()).isTrue();
    }

    private void whenCreatingANonTerminalNode() {
        node = BinaryTree.newNonTerminalNode(
                BinaryTree.newTerminalNode(100),
                BinaryTree.newTerminalNode(200)
        );
    }

    private void whenCreatingATerminalNode() {
        node = BinaryTree.newTerminalNode(100);
    }
}