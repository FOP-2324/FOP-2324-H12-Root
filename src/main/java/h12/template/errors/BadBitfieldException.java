package h12.template.errors;

import h12.parser.Token;

public class BadBitfieldException extends BadTokenException{
    public BadBitfieldException(Token token) {
        super(token, Token.Type.BITFIELD);
    }
}
