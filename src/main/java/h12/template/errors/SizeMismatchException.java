package h12.template.errors;

/**
 * Exception if there is a size mismatch
 */
public class SizeMismatchException extends KissParserException{

    /**
     * Constructs a new {@link ParameterNotSpecifiedException}
     * @param parameter The Parameter which has a size missmatch
     */
    public SizeMismatchException(String parameter) {
        super("Size missmatch for %s!".formatted(parameter));
    }
}
