package tdd.huffman;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.function.Consumer;

import static tdd.huffman.Bit.one;
import static tdd.huffman.Bit.zero;

@Value
public class HuffmanTree {

    private final BinaryTree<Byte> binaryTree;

    public List<Bit> findHuffmanCodeForSymbol(Byte symbol) {
        var ref = new Object() {
            List<Bit> huffmanCode = ImmutableList.of();
        };
        recursiveFindHuffmanCodeForSymbol(binaryTree,
                ImmutableList.of(),
                symbol,
                foundHuffmanCode -> ref.huffmanCode = foundHuffmanCode);
        return ref.huffmanCode;
    }

    public void visit(Consumer<BinaryTree<Byte>> nodeConsumer) {
        this.binaryTree.visit(nodeConsumer);
    }

    private void recursiveFindHuffmanCodeForSymbol(BinaryTree<Byte> node,
                                                   List<Bit> huffmanCode,
                                                   Byte symbol,
                                                   Consumer<List<Bit>> huffmanCodeConsumer) {
        if (symbol.equals(node.getNodeValue())) {
            huffmanCodeConsumer.accept(huffmanCode);

        } else {
            if (node.getLeftNode() != null) {
                recursiveFindHuffmanCodeForSymbol(node.getLeftNode(),
                        ImmutableList.<Bit>builder().addAll(huffmanCode).add(zero()).build(),
                        symbol,
                        huffmanCodeConsumer);
            }
            if (node.getRightNode() != null) {
                recursiveFindHuffmanCodeForSymbol(node.getRightNode(),
                        ImmutableList.<Bit>builder().addAll(huffmanCode).add(one()).build(),
                        symbol,
                        huffmanCodeConsumer);
            }
        }
    }
}
