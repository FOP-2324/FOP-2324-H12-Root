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
public class H3_PrivateTests {
    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("input", JsonNode::asText),
        Map.entry("tokens", n -> JsonConverters.toList(n, JsonNode::asText))
    );

    @Test
    public void testHasNextFalse() throws IOException {
        var cfr = mock(CommentFreeReader.class);
        when(cfr.hasNext()).thenReturn(false);
        when(cfr.peek()).thenThrow(new IndexOutOfBoundsException());
        when(cfr.read()).thenThrow(new IndexOutOfBoundsException());

        var scanner = new Scanner(cfr);

        var context = contextBuilder()
            .add("input", "")
            .add("expected", false)
            .subject("Scanner#hasNext()")
            .build();

        assertFalse(scanner.hasNext(), context,
            TR -> "hasNext should return false when there is no next token");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test\n", "hello world\n"})
    public void testHasNextTrue(String input) throws IOException {
        var cfr = new H3_CommentFreeReader(input);
        var scanner = new Scanner(cfr);

        var context = contextBuilder()
            .add("input", input)
            .add("expected", true)
            .subject("Scanner#hasNext()")
            .build();

        assertTrue(scanner.hasNext(), context,
            TR -> "hasNext should return true when there is a next token");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Tokens.json", customConverters = "customConverters")
    public void testEof(final JsonParameterSet params) throws IOException {
        var input = params.getString("input");
        var expected = params.<List<String>>get("tokens");
        var cfr = new H3_CommentFreeReader(input);
        var scanner = new Scanner(cfr);

        var context = contextBuilder()
            .add("input", input)
            .subject("Scanner#scan()")
            .build();

        for (var expectedToken : expected) {
            assertTrue(scanner.hasNext(), context,
                TR -> "hasNext should return true when there is a next token");
            scanner.scan();
        }
        assertFalse(scanner.hasNext(), context,
            TR -> "hasNext should return false when there is no next token");
    }
}
