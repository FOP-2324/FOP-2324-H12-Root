package h12.errors;

import h12.fsm.parser.Token;

public class BadNumberException extends BadTokenException{
    public BadNumberException(Token token) {
        super(token, Token.Type.NUMBER);
    }
}
