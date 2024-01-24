package h12.template.errors;

import h12.parse.Token;

/**
 * Exception for a bad {@link Token}
 */
public class BadTokenException extends KissParserException{

    /**
     * Create a new {@link BadTokenException}
     * @param token The bad Token
     * @param expectedType The originally expected Type
     */
    public BadTokenException(Token token, Token.Type expectedType) {
        super("Bad token parsed: Token %s is not of type %s".formatted(token, expectedType));
    }
}
