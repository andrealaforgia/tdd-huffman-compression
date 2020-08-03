package tdd.huffman;

import java.io.IOException;
import java.util.Optional;

public class HuffmanTreeDeserializer {
    public HuffmanTree deserialize(BitInputStream bitInputStream) throws IOException {
        return new HuffmanTree(deserializeBinaryTree(bitInputStream));
    }

    private BinaryTree<Byte> deserializeBinaryTree(BitInputStream bitInputStream) throws IOException {
        Optional<Bit> optionalBit = bitInputStream.read();
        if (optionalBit.isPresent()) {
            if (optionalBit.get().isZero()) {
                BinaryTree<Byte> leftNode = deserializeBinaryTree(bitInputStream);
                BinaryTree<Byte> rightNode= deserializeBinaryTree(bitInputStream);
                return BinaryTree.newNonTerminalNode(leftNode,rightNode);

            } else {
                return BinaryTree.newTerminalNode(bitInputStream.expectByte());
            }
        }
        throw new UnexpectedEndOfInputException();
    }
}
