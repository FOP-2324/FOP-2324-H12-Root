package h12.template.errors;


import h12.template.fsm.HeaderParameter;

/**
 * Exception, if Parameter is not specified
 */
public class ParameterNotSpecifiedException extends KissParserException{

    /**
     * Constructs a new {@link ParameterNotSpecifiedException}
     * @param parameter The Parameter which is not specified
     */
    public ParameterNotSpecifiedException(HeaderParameter parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
