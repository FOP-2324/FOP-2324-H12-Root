package h12.errors;

public class BadBitFieldException extends KissParserException{
    public BadBitFieldException() {
        super("Bad Bitfield");
    }
}
