package h12.template.errors;

public class ParameterNotSpecifiedException extends KissParserException{
    public ParameterNotSpecifiedException(String parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
