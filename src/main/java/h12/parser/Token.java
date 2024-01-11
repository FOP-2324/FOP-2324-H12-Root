package h12.parser;

public class Token {

    private final String value;

    public Token(String value){
        this.value = value;
    }

    public boolean is(Type type){
        return value.matches(type.pattern);
    }

    public String getValue(){
        return value;
    }


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
        Type(String pattern){
            this.pattern = pattern;
        }
    }


}
