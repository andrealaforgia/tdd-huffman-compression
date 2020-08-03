package tdd.huffman;

import lombok.Value;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static tdd.huffman.BinaryTree.newNonTerminalNode;
import static tdd.huffman.BinaryTree.newTerminalNode;

public class HuffmanTreeBuilder {

    public HuffmanTree build(Map<Byte, Long> symbolWeightMap) {
        List<WeightedNode> weightedNodes = symbolWeightMap.entrySet().stream()
                .map(e -> new WeightedNode(newTerminalNode(e.getKey()), e.getValue()))
                .collect(toList());

        while (weightedNodes.size() > 1) {
            WeightedNode node1 = selectNodeWithMinimumWeight(weightedNodes);
            WeightedNode node2 = selectNodeWithMinimumWeightDifferentFrom(node1, weightedNodes);
            weightedNodes.remove(node1);
            weightedNodes.remove(node2);
            weightedNodes.add(new WeightedNode(
                    newNonTerminalNode(node1.getNode(), node2.getNode()),
                    node1.getWeight() + node2.getWeight()
            ));
        }
        return new HuffmanTree(weightedNodes.get(0).getNode());
    }

    private WeightedNode selectNodeWithMinimumWeight(List<WeightedNode> weightedNodes) {
        return selectNodeWithMinimumWeightDifferentFrom(null, weightedNodes);
    }

    private WeightedNode selectNodeWithMinimumWeightDifferentFrom(WeightedNode nodeToSkip, List<WeightedNode> weightedNodes) {
        return (nodeToSkip == null ? weightedNodes.stream() : weightedNodes.stream().filter(wn -> !wn.equals(nodeToSkip)))
                .min(comparingLong(WeightedNode::getWeight))
                .stream().findFirst().orElseThrow(() -> new IllegalStateException("Could not find node!"));
    }

    @Value
    public class WeightedNode {
        private BinaryTree<Byte> node;
        private long weight;
    }
}
