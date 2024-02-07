package h12.export;

import h12.template.fsm.BitField;
import h12.template.fsm.State;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

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
        assertOperations(params, new TutorSystemVerilogExporter(delegate) {

                @Override
                protected void generateCondition(State startState, BitField event, State endState, BitField output) throws IOException {
                    this.delegate.generateCondition(startState, event, endState, output);
                }
            },
            (expected, actual, context) -> {
                expected.sort(Comparator.naturalOrder());
                actual.sort(Comparator.naturalOrder());
                Assertions2.assertEquals(expected.size(), actual.size(), context, result -> "Expected length %s, but got %s".formatted(expected.size(), actual.size()));
                Assertions2.assertEquals(expected, actual, context, result -> "Expected %s, but got %s".formatted(expected, actual));
            }
        );
    }
}
