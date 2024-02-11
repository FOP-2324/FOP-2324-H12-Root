package h12.h4;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.FsmBuilder;
import h12.parse.FsmParser;
import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@TestForSubmission
public class H4_2_PrivateTests {
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("terms", n -> JsonConverters.toList(n, Term::fromJson))
    );

    @Test
    public void testParseTerms() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, KissParserException {
        var scanner = new H4_TokenScanner(
            // 1. term
            "-1",
            "OFF",
            "ON",
            "0",
            // 2. term
            "0",
            "OFF",
            "OFF",
            "0",
            // 3. term
            "1",
            "ON",
            "OFF",
            "1"
        );
        var builder = mock(FsmBuilder.class);
        var parser = new FsmParser(scanner, builder);
        var method = Util.getPrivateParserMethod("parseTerms");

        method.invoke(parser);
        verify(builder).addTerm(new BitField("-1"), "OFF", "ON", new BitField("0"));
        verify(builder).addTerm(new BitField("0"), "OFF", "OFF", new BitField("0"));

        verify(builder).addTerm(new BitField("1"), "ON", "OFF", new BitField("1"));

        verifyNoMoreInteractions(builder);
    }
}
