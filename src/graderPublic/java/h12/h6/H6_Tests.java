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
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

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
}
