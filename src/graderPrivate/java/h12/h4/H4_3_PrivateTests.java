package h12.h4;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.FsmBuilder;
import h12.parse.FsmParser;
import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

@TestForSubmission
public class H4_3_PrivateTests {
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("fsm", n -> JsonConverters.toList(n, Term::fromJson))
    );

    @Test
    public void testParseFsm() throws IOException, KissParserException {
        H4_TokenScanner scanner = new H4_TokenScanner(
            ".i", "2",
            ".o", "1",
            ".p", "5",
            ".s", "2",
            ".r", "OFF",
            "-1", "OFF", "OFF", "0",
            "00", "OFF", "OFF", "0",
            "10", "OFF", "ON", "1",
            "-0", "ON", "ON", "1",
            "-1", "ON", "OFF", "0"
        );
        FsmBuilder builder = Mockito.mock(FsmBuilder.class);
        FsmParser parser = new FsmParser(scanner, builder);
        parser.parse();

        Mockito.verify(builder).setInputSize(2);
        Mockito.verify(builder).setOutputSize(1);
        Mockito.verify(builder).setNumberOfTerms(5);
        Mockito.verify(builder).setNumberOfStates(2);
        Mockito.verify(builder).setInitialState("OFF");
        Mockito.verify(builder).addTerm(new BitField("-1"), "OFF", "OFF", new BitField("0"));
        Mockito.verify(builder).addTerm(new BitField("00"), "OFF", "OFF", new BitField("0"));
        Mockito.verify(builder).addTerm(new BitField("10"), "OFF", "ON", new BitField("1"));
        Mockito.verify(builder).addTerm(new BitField("-0"), "ON", "ON", new BitField("1"));
        Mockito.verify(builder).addTerm(new BitField("-1"), "ON", "OFF", new BitField("0"));
        Mockito.verify(builder).finishHeader();
    }
}
