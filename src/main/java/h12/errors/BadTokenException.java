package h12.errors;

import h12.fsm.parser.Token;

public class BadTokenException extends KissParserException{
    public BadTokenException(Token token, Token.Type expectedType) {
        super("Bad token parsed: Token %s is not of type %s".formatted(token, expectedType));
    }
}
