package h12.template.errors;

/**
 * Exception for a Kiss Parse Problem
 */
public class KissParserException extends Exception{

    /**
     * Create a {@link KissParserException} with specific message
     * @param message The specific Exception Message
     */
    public KissParserException(String message){
        super(message);
    }
}
