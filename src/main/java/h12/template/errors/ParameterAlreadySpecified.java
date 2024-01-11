package h12.template.errors;

public class ParameterAlreadySpecified extends KissParserException{
    public ParameterAlreadySpecified(String parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
