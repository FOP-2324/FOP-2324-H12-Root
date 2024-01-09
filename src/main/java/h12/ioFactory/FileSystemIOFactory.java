package h12.ioFactory;

import java.io.*;

public class FileSystemIOFactory implements IOFactory{

    @Override
    public boolean supportsReader() {
        return true;
    }

    @Override
    public BufferedReader createReader(String ioName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(ioName));
    }

    @Override
    public boolean supportsWriter() {
        return true;
    }

    @Override
    public BufferedWriter createWriter(String ioName) throws IOException {
        return new BufferedWriter(new FileWriter(ioName));
    }
}
