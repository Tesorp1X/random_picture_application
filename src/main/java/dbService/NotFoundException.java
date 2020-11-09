package dbService;

public class NotFoundException extends Exception{
    public NotFoundException() {
        super();
    }

    /**
     * @param message id, that caused a problem.
     */
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "No such id in DB.";
    }
}
