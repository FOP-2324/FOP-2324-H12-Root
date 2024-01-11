package h12.template.errors;

import h12.parse.Token;

public class BadTokenException extends KissParserException{
    public BadTokenException(Token token, Token.Type expectedType) {
        super("Bad token parsed: Token %s is not of type %s".formatted(token, expectedType));
    }
}
