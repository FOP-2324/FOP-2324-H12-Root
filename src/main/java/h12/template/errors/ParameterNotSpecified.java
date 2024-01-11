package h12.template.errors;

public class ParameterNotSpecified extends KissParserException{
    public ParameterNotSpecified(String parameter) {
        super("Header not specified: %s".formatted(parameter));
    }
}
