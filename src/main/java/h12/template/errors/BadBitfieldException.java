package h12.template.errors;

import h12.parse.Token;

/**
 * Exception representing a bad Bitfield
 */
public class BadBitfieldException extends BadTokenException{

    /**
     * Construct a new {@link BadBitfieldException}
     * @param token The bad {@link Token}
     */
    public BadBitfieldException(Token token) {
        super(token, Token.Type.BITFIELD);
    }
}
