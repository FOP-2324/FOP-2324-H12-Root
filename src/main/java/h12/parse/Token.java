package h12.parse;

/**
 * Representing q Token
 */
public class Token {

    private final String value;

    /**
     * Create a new Token
     * @param value The {@link String} Representation of the Token
     */
    public Token(String value){
        this.value = value;
    }

    /**
     *
     * @param type expected type
     * @return true, iff the type matches with the expected
     */
    public boolean is(Type type){
        return value.matches(type.pattern);
    }

    /**
     *
     * @return the {@link String} representation of the Token
     */
    public String getValue(){
        return value;
    }

    /**
     * The Type of the {@link Token}
     */
    public enum Type{
        KEYWORD_INPUT_WIDTH(".i"),
        KEYWORD_OUTPUT_WIDTH(".o"),
        KEYWORD_NUMBER_OF_TERMS(".p"),
        KEYWORD_NUMBER_OF_STATES(".s"),
        KEYWORD_INITIAL_STATE(".r"),

        NUMBER("\\d+"),
        IDENTIFIER_STATE("[^\\s-]+"),
        BITFIELD("[01-]+");


        final String pattern;

        /**
         * Create a new {@link Type}
         * @param pattern The regex pattern of the {@link Type}
         */
        Type(String pattern){
            this.pattern = pattern;
        }
    }


}
