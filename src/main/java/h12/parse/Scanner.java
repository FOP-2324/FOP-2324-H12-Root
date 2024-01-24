package h12.parse;

import java.io.IOException;

/**
 * Class used to read char-wise and provides a token-wise stream
 */
public class Scanner {

    private final CommentFreeReader reader;
    private Token prereadToken = null;

    /**
     * Create a new {@link Scanner}
     * @param reader The {@link CommentFreeReader} which has to be used
     * @throws IOException Can be thrown in case of File Problem
     */
    public Scanner(CommentFreeReader reader) throws IOException {
        this.reader = reader;
        prereadToken = preRead();
    }

    /**
     *
     * @param c Input Char
     * @return true, iff there if the input char is a whitespace
     */
    private static boolean isWhitespace(char c){
        return  c == '\r' || c == ' ' || c == '\t' || c == '\n';
    }

    /**
     * Prereads a new Token
     * @return the read Token
     * @throws IOException Can be thrown in case of File Problem
     */
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

    /**
     * Read next token
     * @return Return current Token and reads the next one
     * @throws IOException Can be thrown in case of File Problem
     */
    public Token scan() throws IOException {
        Token token = prereadToken;
        prereadToken = preRead();
        return token;
    }

    /**
     *
     * @return true, iff there is a next Token
     */
    public boolean hasNext(){
        return prereadToken != null;
    }
}
