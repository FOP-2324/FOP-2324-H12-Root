package h12.export;

import com.fasterxml.jackson.databind.JsonNode;
import h12.json.JsonConverters;
import h12.template.fsm.Fsm;
import h12.template.fsm.OneHotEncoding;
import h12.template.fsm.StateEncoding;
import org.apache.logging.log4j.util.TriConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class H7_Tests {

    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> customConverters = Map.ofEntries(
        Map.entry("fsm", JsonConverters::toFsm),
        Map.entry("expected", JsonNode::asText)
    );
    private final TypeLink type = BasicTypeLink.of(SystemVerilogExporter.class);

    private final MethodLink method = Assertions3.assertMethodExists(type, Matcher.of(m -> m.name().equals("export")));

    private StateEncoding encoding = new OneHotEncoding();
    private final String moduleName = "Test_H7";

    protected StringWriter writer;

    protected BufferedWriter bufferedWriter;

    SystemVerilogExporter delegate;

    @BeforeEach
    public void setup() {
        encoding = new OneHotEncoding();
        writer = new StringWriter();
        bufferedWriter = new BufferedWriter(writer);
        delegate = new SystemVerilogExporter(bufferedWriter, encoding, moduleName);
    }

    @AfterEach
    public void tearDown() throws IOException {
        bufferedWriter.close();
    }

    protected Context.Builder<?> contextBuilder() {
        return Assertions2.contextBuilder().subject(method);
    }

    protected void assertOperations(JsonParameterSet params, SystemVerilogExporter exporter) {
        assertOperations(params, exporter, (expected, actual, context) -> Assertions2.assertEquals(expected, actual, context, result -> "Expected %s, but got %s".formatted(expected, actual)));
    }

    protected void assertOperations(JsonParameterSet params, SystemVerilogExporter exporter, TriConsumer<List<String>, List<String>, Context> assertion) {
        Fsm fsm = params.get("fsm");
        String expected = params.get("expected");
        call(() -> {
                exporter.export(fsm);
                bufferedWriter.flush();
            },
            emptyContext(),
            result -> "Failed to export FSM"
        );
        String actual = writer.toString();
        assertion.accept(toList(expected), toList(actual), contextBuilder().add("Expected", expected).add("Actual", actual).build());
    }

    private List<String> toList(String s) {
        return Arrays.stream(s.trim().split(";")).filter(Predicate.not(String::isBlank)).map(String::trim).map(s1 -> s1 + ";").collect(Collectors.toList());
    }
}
