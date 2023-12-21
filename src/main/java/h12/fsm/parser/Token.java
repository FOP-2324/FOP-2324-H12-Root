package h12.fsm.parser;

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
        KEYWORD_INPUT(".i"),
        KEYWORD_OUTPUT(".o"),
        KEYWORD_TERMS(".p"),
        KEYWORD_STATES(".s"),

        NUMBER("\\d*"),
        IDENTIFIER_STATE("\\S*"), //TODO: korrigieren
        BITFIELD("[01-]*");


        final String pattern;
        Type(String pattern){
            this.pattern = pattern;
        }
    }


}
