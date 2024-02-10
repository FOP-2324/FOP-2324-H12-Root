package h12.export;

import h12.template.fsm.BitField;
import h12.template.fsm.State;
import org.junit.jupiter.params.ParameterizedTest;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@TestForSubmission
public class H7_4_Tests extends H7_Tests {

    @ParameterizedTest
    @JsonParameterSetTest(value = "H7_4_ConditionHeader.json", customConverters = "customConverters")
    public void testConditionsHeader(JsonParameterSet params) {
        assertOperations(params, new TutorSystemVerilogExporter(delegate) {

            @Override
            protected void generateConditionsHeader(int inputBitWidth) throws IOException {
                this.delegate.generateConditionsHeader(inputBitWidth);
            }
        });
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "H7_4_Conditions.json", customConverters = "customConverters")
    public void testConditions(JsonParameterSet params) {
        SystemVerilogExporter exporter = new TutorSystemVerilogExporter(delegate) {

            @Override
            protected void generateCondition(State startState, BitField event, State endState, BitField output) throws IOException {
                this.delegate.generateCondition(startState, event, endState, output);
            }
        };
        try {
            assertOperationsFallback(params, exporter,
                    (expected, actual, context) -> {
                        String[] e = Arrays.stream(expected.split("\t\t\t")).map(String::trim).toArray(String[]::new);
                        String[] a = Arrays.stream(actual.split("\t\t\t")).map(String::trim).toArray(String[]::new);
                        Arrays.sort(e, Comparator.naturalOrder());
                        Arrays.sort(a, Comparator.naturalOrder());
                        Assertions2.assertEquals(e.length, a.length, context, result -> "Expected length %s, but got %s".formatted(e.length, a.length));
                        Assertions2.assertEquals(List.of(e), List.of(a), context, result -> "Expected %s, but got %s".formatted(Arrays.toString(e), Arrays.toString(a)));
                    }
            );
        } catch (AssertionFailedError e) {
            assertOperations(params, exporter,
                    (expected, actual, context) -> {
                        expected.sort(Comparator.naturalOrder());
                        actual.sort(Comparator.naturalOrder());
                        Assertions2.assertEquals(expected.size(), actual.size(), context, result -> "Expected length %s, but got %s".formatted(expected.size(), actual.size()));
                        Assertions2.assertEquals(expected, actual, context, result -> "Expected %s, but got %s".formatted(expected, actual));
                    }
            );
        }
    }
}
