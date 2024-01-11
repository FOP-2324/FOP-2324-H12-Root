package h12.parser;

import java.io.IOException;

public class Scanner {

    private final CommentFreeReader reader;
    private Token prereadToken = null;

    public Scanner(CommentFreeReader reader) throws IOException {
        this.reader = reader;
        prereadToken = preRead();
    }

    private static boolean isWhitespace(char c){
        return  c == '\r' || c == ' ' || c == '\t' || c == '\n';
    }

    private Token preRead() throws IOException {
        StringBuilder tokenString = new StringBuilder();


        // Skip whitespaces
        while (true){
            if(!reader.hasNext()){ // EOF reached
                return null;
            }


            if(isWhitespace(reader.peek())){
                reader.read();
            }else{
                break; // non-whitespace character found
            }
        }


        while(!isWhitespace(reader.peek())){
            tokenString.append(reader.read());
        }

        return new Token(tokenString.toString());
    }

    public Token scan() throws IOException {
        Token token = prereadToken;
        prereadToken = preRead();
        return token;
    }

    public boolean hasNext(){
        return prereadToken != null;
    }
}
