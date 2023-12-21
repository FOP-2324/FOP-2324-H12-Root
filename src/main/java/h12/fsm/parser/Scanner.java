package h12.fsm.parser;

import java.io.IOException;

public class Scanner {

    final CommentFreeReader reader;

    public Scanner(CommentFreeReader reader){
        this.reader = reader;
    }

    private boolean isWhitespace(char c){
        return  c == ' ' || c == '\t';
    }

    public Token scan() throws IOException {
        StringBuilder tokenString = new StringBuilder();

        while(isWhitespace(reader.peek())){
            reader.read();
        }

        while(!isWhitespace(reader.peek())){
            tokenString.append(reader.read());
        }

        return new Token(tokenString.toString());
    }

    public boolean hasNext(){
        return false; // TODO: still to do
    }
}
