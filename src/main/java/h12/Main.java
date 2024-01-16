package h12;

import h12.export.DotExporter;
import h12.template.errors.KissParserException;
import h12.template.fsm.Fsm;
import h12.template.fsm.OneHotEncoding;
import h12.export.SystemVerilogExporter;
import h12.ioFactory.FileSystemIOFactory;
import h12.ioFactory.IOFactory;
import h12.parse.CommentFreeReader;
import h12.parse.FsmBuilderImpl;
import h12.parse.FsmParser;
import h12.parse.Scanner;

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

        BufferedReader reader = ioFactory.createReader("C:\\Users\\juliv\\Downloads\\fopi\\in.kiss2");
        CommentFreeReader commentFreeReader = new CommentFreeReader(reader);
        Scanner scanner = new Scanner(commentFreeReader);

        FsmBuilderImpl fsmBuilder = new FsmBuilderImpl();
        FsmParser fsmParser = new FsmParser(scanner, fsmBuilder);

        fsmParser.parse();

        Fsm fsm = fsmBuilder.getFsm();

        BufferedWriter writer = ioFactory.createWriter("C:\\Users\\juliv\\Downloads\\fopi\\out.dot");
        DotExporter exporter = new DotExporter(writer);
        exporter.export(fsm);
        writer.close();
    }
}
