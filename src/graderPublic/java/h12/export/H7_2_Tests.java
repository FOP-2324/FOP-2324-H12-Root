package h12.export;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;

@TestForSubmission
public class H7_2_Tests extends H7_Tests {

    @ParameterizedTest
    @JsonParameterSetTest(value = "H7_2.json", customConverters = "customConverters")
    public void testVariable(JsonParameterSet params) {
        assertOperations(params, new TutorSystemVerilogExporter(delegate) {

            @Override
            protected void generateVariable(String variableName, int variableBitWidth) throws IOException {
                delegate.generateVariable(variableName, variableBitWidth);
            }
        });
    }
}
