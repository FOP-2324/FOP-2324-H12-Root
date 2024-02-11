package h12.export;

import h12.template.fsm.State;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;

@TestForSubmission
public class H7_3_Tests extends H7_Tests {

    @ParameterizedTest
    @JsonParameterSetTest(value = "H7_3.json", customConverters = "customConverters")
    public void testPosedgeBlock(JsonParameterSet params) {
        assertOperations(params, new TutorSystemVerilogExporter(delegate) {

            @Override
            protected void generatePosedgeBlock(State initialState) throws IOException {
                delegate.generatePosedgeBlock(initialState);
            }
        });
    }
}
