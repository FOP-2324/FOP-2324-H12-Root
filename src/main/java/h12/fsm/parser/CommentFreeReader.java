package h12.fsm.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class CommentFreeReader {
    final static char SLASH = '/';
    final static char NEWLINE = '\n';
    private final BufferedReader reader;

    private String lookAheadString = "";


    public CommentFreeReader(BufferedReader reader) throws IOException {
        this.reader = reader;
        lookAhead();
    }

    public boolean isEofNotReached(){
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

    private void lookAhead() throws IOException { //TODO: implement end of file
        char firstChar = (char) reader.read();

        if(firstChar == SLASH){
            char secondChar = (char) reader.read();
            if(secondChar == SLASH){
                // Kommentar

                for(char commentChar = (char) reader.read(); commentChar != NEWLINE; commentChar = (char) reader.read()){} // skip all comment chars until newline

                lookAhead(); // Look ahead after comment
            }else{
                // kein Kommentar
                lookAheadString += firstChar;
                if(secondChar != '\uFFFF') {
                    lookAheadString += secondChar;
                }
            }
        }else{
            // kein Kommentar
            if(firstChar != '\uFFFF') {
                lookAheadString += firstChar;
            }
        }
    }

}
