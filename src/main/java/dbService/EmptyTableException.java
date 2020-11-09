package dbService;

public class EmptyTableException extends Exception{
    public EmptyTableException() {
        super();
    }

    public EmptyTableException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
