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
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@TestForSubmission
public class H4_2_PrivateTests {
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("terms", n -> JsonConverters.toList(n, Term::fromJson))
    );

    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_2.json", customConverters = "customConverters")
    public void testParseTerms(JsonParameterSet params) {
        // TODO: implement this test
    }
}
