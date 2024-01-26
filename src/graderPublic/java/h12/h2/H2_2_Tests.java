package h12.h2;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.CommentFreeReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H2_2_Tests {
    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> hasNextConverters = Map.ofEntries(
        Map.entry("input", JsonNode::asText),
        Map.entry("expected", JsonNode::asBoolean)
    );

    private CommentFreeReader createCfrAndSetLookAhead(String lookAheadString) throws IOException, NoSuchFieldException, IllegalAccessException {
        CommentFreeReader cfr = new CommentFreeReader(new BufferedReader(new StringReader(lookAheadString)));
        Field field = CommentFreeReader.class.getDeclaredField("lookAheadString");
        field.setAccessible(true);
        field.set(cfr, lookAheadString);
        return cfr;
    }

    @ParameterizedTest
    @Disabled("Depends on lookAhead()")
    @JsonParameterSetTest(value = "H2_2_HasNext.json", customConverters = "hasNextConverters")
    public void testHasNext(final JsonParameterSet params) throws IOException {
        final String input = params.getString("input");
        final boolean expected = params.getBoolean("expected");

        Context context = contextBuilder()
            .add("input", input)
            .add("expected", expected)
            .subject("CommentFreeReader#hasNext()")
            .build();

        CommentFreeReader cfr = new CommentFreeReader(new BufferedReader(new StringReader(input)));
        assertEquals(expected, cfr.hasNext(), context,
            TR -> "hasNext should return %b".formatted(expected));
    }

    @Test
    public void testHasNextEmptyLookAhead() throws NoSuchFieldException, IOException, IllegalAccessException {
        Context context = contextBuilder()
            .add("lookAheadString", "")
            .subject("CommentFreeReader#hasNext()")
            .build();

        CommentFreeReader cfr = createCfrAndSetLookAhead("");
        assertFalse(cfr.hasNext(), context,
            TR -> "hasNext should return false when lookAheadString is empty");
    }

    @Test
    public void testHasNextNonEmptyLookAhead() throws NoSuchFieldException, IOException, IllegalAccessException {
        final String lookAheadString = "test\n";
        Context context = contextBuilder()
            .add("lookAheadString", lookAheadString)
            .subject("CommentFreeReader#hasNext()")
            .build();

        CommentFreeReader cfr = createCfrAndSetLookAhead(lookAheadString);
        assertTrue(cfr.hasNext(), context,
            TR -> "hasNext should return true when lookAheadString is not empty");
    }

    @Test
    public void testPeek() throws NoSuchFieldException, IOException, IllegalAccessException {
        final String lookAheadString = "fop\n";
        final char expected = 'f';
        Context context = contextBuilder()
            .add("lookAheadString", lookAheadString)
            .subject("CommentFreeReader#peek()")
            .build();

        CommentFreeReader cfr = createCfrAndSetLookAhead(lookAheadString);
        assertEquals(expected, cfr.peek(), context,
            TR -> "peek should return '%c'".formatted(expected));
    }

    @Test
    public void testRead() throws IOException {
        // TODO
    }
}
