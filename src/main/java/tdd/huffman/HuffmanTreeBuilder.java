package tdd.huffman;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static tdd.huffman.BinaryTree.newNonTerminalNode;
import static tdd.huffman.BinaryTree.newTerminalNode;

public class HuffmanTreeBuilder {

    @Value
    public class WeightedNode {
        private BinaryTree<Byte> node;
        private long weight;
    }

    public HuffmanTree build(Map<Byte, Long> symbolWeightMap) {
        List<WeightedNode> weightedNodes = symbolWeightMap.entrySet().stream()
                .map(e -> new WeightedNode(newTerminalNode(e.getKey()), e.getValue()))
                .collect(toList());

        while (weightedNodes.size() > 1) {
            Optional<WeightedNode> node1 = selectNodeWithMinimumWeight(weightedNodes);
            Optional<WeightedNode> node2 = selectNodeWithMinimumWeightDifferentFrom(node1.get(), weightedNodes);
            weightedNodes.remove(node1.get());
            weightedNodes.remove(node2.get());
            weightedNodes.add(new WeightedNode(
                    newNonTerminalNode(node1.get().getNode(), node2.get().getNode()),
                    node1.get().getWeight() + node2.get().getWeight()
            ));
        }
        return new HuffmanTree(weightedNodes.get(0).getNode());
    }

    private Optional<WeightedNode> selectNodeWithMinimumWeightDifferentFrom(WeightedNode nodeToSkip, List<WeightedNode> weightedNodes) {
        return weightedNodes.stream().filter(wn -> !wn.equals(nodeToSkip)).min(comparingLong(WeightedNode::getWeight));
    }

    private Optional<WeightedNode> selectNodeWithMinimumWeight(List<WeightedNode> weightedNodes) {
        return weightedNodes.stream().min(comparingLong(WeightedNode::getWeight));
    }
}
