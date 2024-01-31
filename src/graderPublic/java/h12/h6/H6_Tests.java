package h12.h6;

import h12.export.KissExporter;
import h12.template.errors.BadBitfieldException;
import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.StateFactory;
import h12.template.fsm.Transition;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H6_Tests {
    private String exportFsm(Fsm fsm) throws IOException {
        var stringWriter = new StringWriter();
        var bufferedWriter = new BufferedWriter(stringWriter);
        var exporter = new KissExporter(bufferedWriter);
        exporter.export(fsm);
        bufferedWriter.flush();
        return stringWriter.toString();
    }

    @Test
    public void testHeaders() throws BadBitfieldException, IOException {
        var context = contextBuilder()
            .subject("KissExporter#export()")
            .build();
        var stateFactory = new StateFactory();
        var fsm = new Fsm();
        var stateOff = stateFactory.get("OFF");
        var stateOn = stateFactory.get("ON");
        fsm.addState(stateOff);
        fsm.addState(stateOn);
        stateOff.setTransition(new Transition(new BitField("---"), stateOn, new BitField("10")));
        stateOn.setTransition(new Transition(new BitField("110"), stateOff, new BitField("01")));

        var result = exportFsm(fsm);

        var headers = result.lines().filter(line -> line.startsWith(".")).sorted().toList();

        var expectedHeaders = """
            .i 3
            .o 2
            .p 2
            .s 2""".lines().sorted().toList();

        assertEquals(expectedHeaders, headers, context,
            TR -> "KissExporter didn't return the expected headers");
    }

    @Test
    public void testInitialState() throws BadBitfieldException, IOException {
        var context = contextBuilder()
            .subject("KissExporter#export()")
            .build();
        var stateFactory = new StateFactory();
        var fsm = new Fsm();
        var stateOff = stateFactory.get("START");
        var stateOn = stateFactory.get("ON");
        fsm.addState(stateOff);
        fsm.addState(stateOn);
        fsm.setInitialState(stateOff);
        stateOff.setTransition(new Transition(new BitField("---"), stateOn, new BitField("10")));
        stateOn.setTransition(new Transition(new BitField("110"), stateOff, new BitField("01")));

        var result = exportFsm(fsm);

        var headers = result.lines().filter(line -> line.startsWith(".")).sorted().toList();

        var expectedHeader = ".r START";
        assertTrue(headers.contains(expectedHeader), context,
            TR -> "The expected initial state header is missing");
    }

    @Test
    public void testSingleTerm() throws BadBitfieldException, IOException {
        var context = contextBuilder()
            .subject("KissExporter#export()")
            .build();
        var stateFactory = new StateFactory();
        var fsm = new Fsm();
        var stateOff = stateFactory.get("OFF");
        var stateOn = stateFactory.get("ON");
        fsm.addState(stateOff);
        fsm.addState(stateOn);
        fsm.setInitialState(stateOff);
        stateOff.setTransition(new Transition(new BitField("010"), stateOn, new BitField("11")));

        var result = exportFsm(fsm);

        var term = result.lines().filter(line -> !line.startsWith(".")).collect(Collectors.joining("\n")).trim();

        var expectedTerm = "010 OFF ON 11";
        assertEquals(expectedTerm, term, context,
            TR -> "The term is not formatted correctly");
    }

    @Test
    public void testFsmWithInitialState() throws BadBitfieldException, IOException {
        var context = contextBuilder()
            .subject("KissExporter#export()")
            .build();
        var stateFactory = new StateFactory();
        var fsm = new Fsm();
        var stateOff = stateFactory.get("OFF");
        var stateOn = stateFactory.get("ON");
        fsm.setInitialState(stateOff);
        fsm.addState(stateOff);
        fsm.addState(stateOn);
        stateOff.setTransition(new Transition(new BitField("-1"), stateOff, new BitField("0")));
        stateOff.setTransition(new Transition(new BitField("00"), stateOff, new BitField("0")));
        stateOff.setTransition(new Transition(new BitField("10"), stateOn, new BitField("1")));
        stateOn.setTransition(new Transition(new BitField("-0"), stateOn, new BitField("1")));
        stateOn.setTransition(new Transition(new BitField("-1"), stateOff, new BitField("0")));

        var result = exportFsm(fsm);

        var inBody = new AtomicBoolean(false);
        var allHeadersBeforeBody = result.lines().allMatch(line -> {
            if (line.startsWith(".")) {
                return !inBody.get();
            } else {
                inBody.set(true);
                return true;
            }
        });

        assertTrue(allHeadersBeforeBody, context,
            TR -> "All headers must come before the terms");

        var headers = result.lines().filter(line -> line.startsWith(".")).sorted().toList();
        var terms = result.lines().filter(line -> !line.startsWith(".")).sorted().toList();

        var expectedHeaders = """
            .i 2
            .o 1
            .p 5
            .s 2
            .r OFF""".lines().sorted().toList();

        var expectedTerms = """
            -1 OFF OFF 0
            00 OFF OFF 0
            10 OFF ON 1
            -0 ON ON 1
            -1 ON OFF 0""".lines().sorted().toList();

        assertEquals(expectedHeaders, headers, context,
            TR -> "KissExporter didn't return the expected headers");

        assertEquals(expectedTerms, terms, context,
            TR -> "KissExporter didn't return the expected terms");
    }
}
