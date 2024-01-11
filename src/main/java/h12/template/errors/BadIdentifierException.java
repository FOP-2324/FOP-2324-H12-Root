package h12.template.errors;

import h12.parser.Token;

public class BadIdentifierException extends BadTokenException{
    public BadIdentifierException(Token token) {
        super(token, Token.Type.IDENTIFIER_STATE);
    }
}
