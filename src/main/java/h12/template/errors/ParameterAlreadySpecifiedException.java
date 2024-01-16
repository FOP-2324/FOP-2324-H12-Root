package h12.template.errors;

public class ParameterAlreadySpecifiedException extends KissParserException{
    public ParameterAlreadySpecifiedException(String parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
