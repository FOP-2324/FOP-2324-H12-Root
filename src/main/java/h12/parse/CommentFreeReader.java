package h12.parse;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class to read line-wise and  provide char-wise stream and removes comments
 */
public class CommentFreeReader {
    final static String COMMENT = "//";
    final static char NEWLINE = '\n';
    private final BufferedReader reader;

    private String lookAheadString = "";


    /**
     * Constructs new {@link CommentFreeReader}
     * @param reader the Reader for the file
     * @throws IOException Can be thrown in case of File Problem
     */
    public CommentFreeReader(BufferedReader reader) throws IOException {
        this.reader = reader;
        lookAhead();
    }

    /**
     *
     * @return true, iff there is a next char
     */
    public boolean hasNext(){
        return !lookAheadString.isEmpty();
    }

    /**
     * Reads the next char from stream
     * @return next character
     * @throws IOException Can be thrown in case of File Problem
     */
    public char read() throws IOException {
        char character = lookAheadString.charAt(0);
        lookAheadString = lookAheadString.substring(1);

        if(lookAheadString.isEmpty()){
            lookAhead();
        }

        return character;
    }

    /**
     * Return the next char, but do not remove it from stream
     * @return the next char
     */
    public char peek(){
        return lookAheadString.charAt(0);
    }

    /**
     * Looks new Line Ahead and removes comments
     * @throws IOException Can be thrown in case of File Problem
     */
    private void lookAhead() throws IOException {
        String line = reader.readLine();

        if(line != null){ // if there is a new line
            String[] lineSplit = line.split(COMMENT);
            String commentFreeLine = lineSplit[0];
            if(!commentFreeLine.isEmpty()){
                commentFreeLine += NEWLINE;
                lookAheadString = commentFreeLine;
            }else{ // this line is completely empty
                lookAhead(); // look ahead again
            }
        } else {
            lookAheadString = "";
        }
    }

}
