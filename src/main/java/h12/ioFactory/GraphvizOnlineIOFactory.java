package h12.ioFactory;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GraphvizOnlineIOFactory implements IOFactory{

    public static class GraphvizOnlineURLWriter extends BufferedWriter{
        private static final String BASE_URL = "https://dreampuf.github.io/GraphvizOnline/#";

        private final StringWriter stringWriter;
        private GraphvizOnlineURLWriter(StringWriter stringWriter) {
            super(stringWriter);
            this.stringWriter = stringWriter;
        }

        public static GraphvizOnlineURLWriter create(){
            return new GraphvizOnlineURLWriter(new StringWriter());
        }

        public String getURL() throws IOException {
            flush();
            return BASE_URL + URLEncoder.encode(stringWriter.toString(), StandardCharsets.UTF_8).replace("+", "%20");
        }
    }


    @Override
    public boolean supportsReader() {
        return false;
    }

    @Override
    public BufferedReader createReader(String ioName) throws IOException {
        throw new UnsupportedOperationException("GraphvizOnlineIOFactory does not support writing!");
    }

    @Override
    public boolean supportsWriter() {
        return true;
    }

    @Override
    public GraphvizOnlineURLWriter createWriter(String ioName) throws IOException {
        return GraphvizOnlineURLWriter.create();
    }
}
