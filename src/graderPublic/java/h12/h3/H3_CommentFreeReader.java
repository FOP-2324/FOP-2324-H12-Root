package h12.h3;

import h12.parse.CommentFreeReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class H3_CommentFreeReader extends CommentFreeReader {
    private final String data;
    private int pos;

    public H3_CommentFreeReader(String data) throws IOException {
        super(new BufferedReader(new StringReader(data)));
        this.data = data;
        this.pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char read() throws IOException {
        return data.charAt(pos++);
    }

    @Override
    public char peek() {
        return data.charAt(pos);
    }
}
