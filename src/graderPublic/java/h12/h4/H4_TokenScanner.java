package h12.h4;

import h12.h3.H3_CommentFreeReader;
import h12.parse.Scanner;
import h12.parse.Token;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class H4_TokenScanner extends Scanner {
    private final List<Token> tokens;
    private int scans = 0;

    public H4_TokenScanner(List<String> tokens) throws IOException {
        super(new H3_CommentFreeReader(String.join(" ", tokens) + "\n"));
        this.tokens = tokens.stream().map(Token::new).toList();
    }

    public H4_TokenScanner(String... tokens) throws IOException {
        this(Arrays.asList(tokens));
    }

    @Override
    public Token scan() throws IOException {
        var token = scans < tokens.size() ? tokens.get(scans) : null;
        scans++;
        return token;
    }

    @Override
    public boolean hasNext() {
        return scans < tokens.size();
    }
}
