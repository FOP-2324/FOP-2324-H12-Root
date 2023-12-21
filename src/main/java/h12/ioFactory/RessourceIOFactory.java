package h12.ioFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RessourceIOFactory implements IOFactory{
    @Override
    public boolean supportsReader() {
        return true;
    }

    @Override
    public BufferedReader createReader(String ioName) throws FileNotFoundException {
        InputStream resourceStream = getClass().getResourceAsStream(ioName);
        if (resourceStream == null) {
            throw new FileNotFoundException("Ressource does not exist: %s".formatted(ioName));
        }
        return new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
    }

    @Override
    public boolean supportsWriter() {
        return false;
    }

    @Override
    public BufferedWriter createWriter(String ioName) throws IOException {
        throw new UnsupportedOperationException("RessourceIOFactory does not support writing!");
    }
}
