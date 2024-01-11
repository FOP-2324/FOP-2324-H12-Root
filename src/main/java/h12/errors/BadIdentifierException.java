package h12.errors;

import h12.fsm.parser.Token;

public class BadIdentifierException extends BadTokenException{
    public BadIdentifierException(Token token) {
        super(token, Token.Type.IDENTIFIER_STATE);
    }
}
