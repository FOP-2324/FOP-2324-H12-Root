package h12.h4;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.FsmBuilder;
import h12.parse.FsmParser;
import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@TestForSubmission()
public class H4_2_Tests {
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("terms", n -> JsonConverters.toList(n, Term::fromJson))
    );

    @Test
    public void testParseTerm() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, KissParserException {
        var scanner = new H4_TokenScanner(
            "-1",
            "OFF",
            "ON",
            "0"
        );
        var builder = mock(FsmBuilder.class);
        var parser = new FsmParser(scanner, builder);
        var method = Util.getPrivateParserMethod("parseTerm");

        method.invoke(parser);
        verify(builder).addTerm(new BitField("-1"), "OFF", "ON", new BitField("0"));
        verifyNoMoreInteractions(builder);
    }

    @Test
    public void testParseFSM() throws IOException, KissParserException {
        var input = """
            .i 2
            .o 1
            .p 5
            .s 2
            .r OFF
            -1 OFF OFF 0
            00 OFF OFF 0
            10 OFF ON  1
            -0 ON  ON  1
            -1 ON  OFF 0
            """;
        var scanner = new H4_TokenScanner(
            input.split("\\s+")
        );
        var builder = mock(FsmBuilder.class);
        var parser = spy(new FsmParser(scanner, builder));
        parser.parse();

        var inOrder = inOrder(builder);
        inOrder.verify(builder).setInputSize(2);
        inOrder.verify(builder).setOutputSize(1);
        inOrder.verify(builder).setNumberOfTerms(5);
        inOrder.verify(builder).setNumberOfStates(2);
        inOrder.verify(builder).setInitialState("OFF");
        inOrder.verify(builder).finishHeader();
        inOrder.verify(builder).addTerm(new BitField("-1"), "OFF", "OFF", new BitField("0"));
        inOrder.verify(builder).addTerm(new BitField("00"), "OFF", "OFF", new BitField("0"));
        inOrder.verify(builder).addTerm(new BitField("10"), "OFF", "ON", new BitField("1"));
        inOrder.verify(builder).addTerm(new BitField("-0"), "ON", "ON", new BitField("1"));
        inOrder.verify(builder).addTerm(new BitField("-1"), "ON", "OFF", new BitField("0"));
        inOrder.verify(builder).finishFSM();
        inOrder.verifyNoMoreInteractions();
    }
}
