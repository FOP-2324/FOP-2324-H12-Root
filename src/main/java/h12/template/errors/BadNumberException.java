package h12.template.errors;

import h12.parse.Token;

/**
 * A Exception used for a bad Number
 */
public class BadNumberException extends BadTokenException{

    /**
     * Construct a new {@link BadNumberException}
     * @param token The {@link Token}, which is the bad number
     */
    public BadNumberException(Token token) {
        super(token, Token.Type.NUMBER);
    }
}
