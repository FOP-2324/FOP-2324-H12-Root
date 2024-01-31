package h12.h3;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.CommentFreeReader;
import h12.parse.Scanner;
import h12.parse.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H3_Tests {
    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("input", JsonNode::asText),
        Map.entry("tokens", n -> JsonConverters.toList(n, JsonNode::asText))
    );

    @Test
    public void testScanReturn() throws IOException {
        var input = "test\n";
        var expected = "test";
        var cfr = new H3_CommentFreeReader(input);
        var scanner = new Scanner(cfr);

        var context = contextBuilder()
            .add("input", input)
            .add("expected", expected)
            .subject("Scanner#scan()")
            .build();

        checkToken(expected, scanner.scan(), context);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Tokens.json", customConverters = "customConverters")
    public void testScanReadsNextToken(final JsonParameterSet params) throws IOException {
        var input = params.getString("input");
        var expected = params.<List<String>>get("tokens");
        var cfr = new H3_CommentFreeReader(input);
        var scanner = new Scanner(cfr);

        var context = contextBuilder()
            .add("input", input)
            .add("expected", expected)
            .subject("Scanner#scan()")
            .build();

        for (var expectedToken : expected) {
            checkToken(expectedToken, scanner.scan(), context);
        }
    }

    private void checkToken(String expected, Token token, Context context) {
        assertNotNull(token, context, TR -> "Token expected, but received null");
        assertEquals(expected, token.getValue(), context,
            TR -> "Expected token \"%s\", but got \"%s\"".formatted(expected, token.getValue()));
    }
}
