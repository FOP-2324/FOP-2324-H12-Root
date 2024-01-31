package h12.h5;

import h12.parse.FsmBuilderImpl;
import h12.template.errors.KissParserException;
import h12.template.errors.ParameterAlreadySpecifiedException;
import h12.template.fsm.Fsm;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H5_1_Tests {
    @Test
    public void testSetOutputSize() throws KissParserException, NoSuchFieldException, IllegalAccessException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#setOutputSize()")
            .build();
        var builder = new FsmBuilderImpl();
        builder.setOutputSize(4);
        var field = Util.getPrivateField("headerOutputSize");
        var fieldValue = (Integer) field.get(builder);
        assertEquals(4, fieldValue, context, TR -> "setOutputSize() didn't store the value");
    }

    @Test
    public void testSetOutputSizeException() throws KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#setOutputSize()")
            .build();
        var builder = new FsmBuilderImpl();
        builder.setOutputSize(8);
        assertThrows(ParameterAlreadySpecifiedException.class, () -> builder.setOutputSize(15), context,
            TR -> "setOutputSize() didn't throw an exception when called twice");
    }

    @Test
    public void testSetNumberOfTerms() throws KissParserException, NoSuchFieldException, IllegalAccessException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#setNumberOfTerms()")
            .build();
        var builder = new FsmBuilderImpl();
        builder.setNumberOfTerms(16);
        var field = Util.getPrivateField("headerNumberOfTerms");
        var fieldValue = (Integer) field.get(builder);
        assertEquals(16, fieldValue, context, TR -> "setNumberOfTerms() didn't store the value");
    }

    @Test
    public void testSetNumberOfTermsException() throws KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#setNumberOfTerms()")
            .build();
        var builder = new FsmBuilderImpl();
        builder.setNumberOfTerms(23);
        assertThrows(ParameterAlreadySpecifiedException.class, () -> builder.setNumberOfTerms(42), context,
            TR -> "setNumberOfTerms() didn't throw an exception when called twice");
    }

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
