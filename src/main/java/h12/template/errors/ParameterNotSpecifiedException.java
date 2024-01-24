package h12.template.errors;


/**
 * Exception, if Parameter is not specified
 */
public class ParameterNotSpecifiedException extends KissParserException{

    /**
     * Constructs a new {@link ParameterNotSpecifiedException}
     * @param parameter The Parameter which is not specified
     */
    public ParameterNotSpecifiedException(String parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
