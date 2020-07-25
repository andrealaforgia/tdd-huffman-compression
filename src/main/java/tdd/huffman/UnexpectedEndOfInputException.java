package tdd.huffman;

public class UnexpectedEndOfInputException extends RuntimeException {

    public UnexpectedEndOfInputException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Unexpected end of input";
    }
}
