package h12.template.errors;

import h12.parse.Token;

/**
 * Expression used for bad Identifier
 */
public class BadIdentifierException extends BadTokenException{

    /**
     * Construct new {@link BadIdentifierException}
     * @param token The {@link Token}, which is bad
     */
    public BadIdentifierException(Token token) {
        super(token, Token.Type.IDENTIFIER_STATE);
    }
}
