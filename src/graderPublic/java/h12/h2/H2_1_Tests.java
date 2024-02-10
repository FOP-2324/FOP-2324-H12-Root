package h12.h2;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.CommentFreeReader;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonConverters;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H2_1_Tests {
    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("input", JsonNode::asText),
        Map.entry("expected", n -> JsonConverters.toList(n, JsonNode::asText))
    );

    private Method getLookAheadMethod() throws NoSuchMethodException {
        Method m = CommentFreeReader.class.getDeclaredMethod("lookAhead");
        m.setAccessible(true);
        return m;
    }

    private Field getLookAheadStringField() throws NoSuchFieldException {
        Field f = CommentFreeReader.class.getDeclaredField("lookAheadString");
        f.setAccessible(true);
        return f;
    }

    private void callLookAhead(CommentFreeReader cfr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        getLookAheadMethod().invoke(cfr);
    }

    private String getLookAheadString(CommentFreeReader cfr) throws NoSuchFieldException, IllegalAccessException {
        return (String) getLookAheadStringField().get(cfr);
    }

    public void testLookAhead(final JsonParameterSet params) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        final String input = params.getString("input");
        final List<String> expected = params.<List<String>>get("expected");

        Context context = contextBuilder()
            .add("input", input)
            .add("expected", expected)
            .subject("CommentFreeReader#lookAhead()")
            .build();

        CommentFreeReader cfr = new CommentFreeReader(new BufferedReader(new StringReader(input)));
        int callNr = 0;
        for (String s : expected) {
            final int thisCallNr = ++callNr;
            assertEquals(s, getLookAheadString(cfr), context,
                TR -> "Expected \"%s\" (call %d)".formatted(s, thisCallNr));
            callLookAhead(cfr);
        }
        assertEquals("", getLookAheadString(cfr), context,
            TR -> "Expected empty string at the end of the input");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H2_1_NoComments.json", customConverters = "customConverters")
    public void testNoComments(final JsonParameterSet params) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        testLookAhead(params);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H2_1_ContentPlusComments.json", customConverters = "customConverters")
    public void testContentPlusComments(final JsonParameterSet params) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        testLookAhead(params);
    }
}
