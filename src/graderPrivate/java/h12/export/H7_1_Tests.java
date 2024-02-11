package h12.export;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.io.IOException;

@TestForSubmission
public class H7_1_Tests extends H7_Tests {

    @ParameterizedTest
    @JsonParameterSetTest(value = "H7_1.json", customConverters = "customConverters")
    public void testModuleHeader(JsonParameterSet params) {
        assertOperations(params, new TutorSystemVerilogExporter(delegate) {

            @Override
            protected void generateModuleHeader(int inputBitWidth, int outputBitWith) throws IOException {
                delegate.generateModuleHeader(inputBitWidth, outputBitWith);
            }
        });
    }
}
