package h12.ioFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IOFactory {

    boolean supportsReader();
    BufferedReader createReader(String ioName) throws IOException;

    boolean supportsWriter();
    BufferedWriter createWriter(String ioName) throws IOException;

}
