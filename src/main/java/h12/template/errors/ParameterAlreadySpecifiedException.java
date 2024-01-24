package h12.template.errors;

/**
 * Exception, if Parameter is already specified
 */
public class ParameterAlreadySpecifiedException extends KissParserException{

    /**
     * Constructs a new {@link ParameterAlreadySpecifiedException}
     * @param parameter The Parameter which is already specified
     */
    public ParameterAlreadySpecifiedException(String parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
