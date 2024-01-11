package h12.parse;

import java.io.BufferedReader;
import java.io.IOException;

public class CommentFreeReader {
    final static String COMMENT = "//";
    final static char NEWLINE = '\n';
    private final BufferedReader reader;

    private String lookAheadString = "";


    public CommentFreeReader(BufferedReader reader) throws IOException {
        this.reader = reader;
        lookAhead();
    }

    public boolean hasNext(){
        return !lookAheadString.isEmpty();
    }

    public char read() throws IOException {
        char character = lookAheadString.charAt(0);
        lookAheadString = lookAheadString.substring(1);

        if(lookAheadString.isEmpty()){
            lookAhead();
        }

        return character;
    }

    public char peek(){
        return lookAheadString.charAt(0);
    }

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
        }
    }

}
