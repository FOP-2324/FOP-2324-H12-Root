package h12.h6;

import com.fasterxml.jackson.databind.JsonNode;
import h12.export.KissExporter;
import h12.export.SystemVerilogExporter;
import h12.json.JsonConverters;
import h12.template.fsm.Fsm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission()
public class H6_PrivateTests {

    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("fsm", JsonConverters::toFsm),
        Map.entry("expected", JsonNode::asText)
    );
    private final TypeLink type = BasicTypeLink.of(SystemVerilogExporter.class);

    private final MethodLink method = Assertions3.assertMethodExists(type, Matcher.of(m -> m.name().equals("export")));

    protected StringWriter writer;

    protected BufferedWriter bufferedWriter;

    KissExporter delegate;

    @BeforeEach
    public void setup() {
        writer = new StringWriter();
        bufferedWriter = new BufferedWriter(writer);
        delegate = new KissExporter(bufferedWriter);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H6_initial.json", customConverters = "customConverters")
    public void testInitialState(JsonParameterSet params) {
        Fsm fsm = params.get("fsm");
        String expected = replaceLineEndings(params.get("expected"));
        call(() -> {
                delegate.export(fsm);
                bufferedWriter.flush();
            },
            emptyContext(),
            result -> "Failed to export FSM"
        );
        String actual = replaceLineEndings(writer.toString());
        List<String> actualList = toList(actual);
        Assertions2.assertTrue(actualList.contains(expected), contextBuilder().subject(method).add("Expected", expected).add("Actual", actual).build(), r -> "Export output mismatch!");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H6_export.json", customConverters = "customConverters")
    public void testExport(JsonParameterSet params) {
        Fsm fsm = params.get("fsm");
        String expected = replaceLineEndings(params.get("expected"));
        call(() -> {
                delegate.export(fsm);
                bufferedWriter.flush();
            },
            emptyContext(),
            result -> "Failed to export FSM"
        );
        String actual = replaceLineEndings(writer.toString());
        Comparator<String> cmp = Comparator.naturalOrder();
        List<String> expectedList = toList(expected);
        List<String> actualList = toList(actual);
        expectedList.sort(cmp);
        actualList.sort(cmp);
        Assertions2.assertEquals(expectedList, actualList, contextBuilder().subject(method).add("Expected", expected).add("Actual", actual).build(), r -> "Export output mismatch!");
    }

    private List<String> toList(String s) {
        return Arrays.stream(s.trim().split("\n")).filter(Predicate.not(String::isBlank)).map(str -> str.replaceAll("\\s+", " ")).collect(Collectors.toList());
    }

    private String replaceLineEndings(String s) {
        return s.replaceAll(System.lineSeparator(), "\n");
    }
}
