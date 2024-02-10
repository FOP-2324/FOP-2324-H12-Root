package h12.h5;

import h12.parse.FsmBuilderImpl;
import h12.template.errors.KissParserException;
import h12.template.errors.ParameterAlreadySpecifiedException;
import h12.template.fsm.Fsm;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H5_1_PrivateTests {

    @Test
    public void testSetInitialState() throws KissParserException, NoSuchFieldException, IllegalAccessException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#setInitialState()")
            .build();
        var builder = new FsmBuilderImpl();
        builder.setInitialState("START");
        var field = Util.getPrivateField("fsm");
        var fieldValue = (Fsm) field.get(builder);
        assertEquals("START", fieldValue.getInitialState().getName(), context, TR -> "setInitialState() didn't store the value");
    }

    @Test
    public void testSetInitialStateException() throws KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#setInitialState()")
            .build();
        var builder = new FsmBuilderImpl();
        builder.setInitialState("START");
        assertThrows(ParameterAlreadySpecifiedException.class, () -> builder.setInitialState("TEST"), context,
            TR -> "setInitialState() didn't throw an exception when called twice");
    }
}
