package h12.template.errors;

import h12.parser.Token;

public class BadNumberException extends BadTokenException{
    public BadNumberException(Token token) {
        super(token, Token.Type.NUMBER);
    }
}
