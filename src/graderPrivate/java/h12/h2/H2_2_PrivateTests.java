package h12.h2;

import com.fasterxml.jackson.databind.JsonNode;
import h12.parse.CommentFreeReader;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission()
public class H2_2_PrivateTests {
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

    @Test
    public void testRead() throws NoSuchFieldException, IOException, IllegalAccessException {
        final String lookAheadString = "fop\n";
        final char expected = 'f';
        final String expected2 = "op\n";
        Context context = contextBuilder()
            .add("lookAheadString", lookAheadString)
            .subject("CommentFreeReader#peek()")
            .build();

        CommentFreeReader cfr = createCfrAndSetLookAhead(lookAheadString);
        assertEquals(expected, cfr.read(), context,
            TR -> "read should return '%c'".formatted(expected));
        TypeLink type = BasicTypeLink.of(CommentFreeReader.class);
        FieldLink field = Assertions3.assertFieldExists(type, Matcher.of(f -> f.name().equals("lookAheadString")));
        assertEquals(expected2, field.get(cfr), context,
            TR -> "lookAheadString should be '%s'".formatted(lookAheadString.substring(1)));
    }
}
