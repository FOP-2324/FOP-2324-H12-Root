package h12.ioFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface abstracting the creation of Reader and Writer
 */
public interface IOFactory {

    /**
     *
     * @return true, iff {@link IOFactory} supports reading
     */
    boolean supportsReader();

    /**
     * Create a Reader for a specific file
     * @param ioName Name of IO
     * @return corresponding Reader
     * @throws IOException Can be thrown in case of File Problem
     */
    BufferedReader createReader(String ioName) throws IOException;

    /**
     *
     * @return true, iff {@link IOFactory} supports reading
     */

    boolean supportsWriter();

    /**
     * Create a Writer for a specific file
     * @param ioName Name of IO
     * @return corresponding Writer
     * @throws IOException Can be thrown in case of File Problem
     */
    BufferedWriter createWriter(String ioName) throws IOException;

}
