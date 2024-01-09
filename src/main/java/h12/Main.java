package h12;

import h12.errors.KissParserException;
import h12.fsm.BufferedStateFactory;
import h12.fsm.Fsm;
import h12.fsm.OneHotEncoding;
import h12.fsm.export.SystemVerilogExporter;
import h12.fsm.parser.*;
import h12.ioFactory.FileSystemIOFactory;
import h12.ioFactory.IOFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) throws IOException, KissParserException {
        IOFactory ioFactory = new FileSystemIOFactory();

        BufferedReader reader = ioFactory.createReader("C:\\Users\\juliv\\Downloads\\tmppp\\bbara.kiss2");
        CommentFreeReader commentFreeReader = new CommentFreeReader(reader);
        Scanner scanner = new Scanner(commentFreeReader);

        FsmBuilderImpl fsmBuilder = new FsmBuilderImpl(new BufferedStateFactory());
        FsmParser fsmParser = new FsmParser(scanner, fsmBuilder);

        fsmParser.parse();

        Fsm fsm = fsmBuilder.getFsm();

        BufferedWriter writer = ioFactory.createWriter("C:\\Users\\juliv\\Downloads\\tmppp\\bbara_out.sv");
        SystemVerilogExporter exporter = new SystemVerilogExporter(writer, new OneHotEncoding(), "fsm");
        exporter.export(fsm);
    }
}
