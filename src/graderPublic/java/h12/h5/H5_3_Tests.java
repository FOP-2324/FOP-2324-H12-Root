package h12.h5;

import h12.parse.FsmBuilderImpl;
import h12.template.errors.BadBitfieldException;
import h12.template.errors.KissParserException;
import h12.template.errors.ParameterNotSpecifiedException;
import h12.template.errors.SizeMismatchException;
import h12.template.fsm.*;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H5_3_Tests {
    @Test
    public void testFinishHeaderAllParametersSet() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerOutputSize").set(builder, 2);
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);
        builder.finishHeader();
    }

    @Test
    public void testFinishHeaderMissingInputSize() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishHeader()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerOutputSize").set(builder, 2);
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);

        assertThrows(ParameterNotSpecifiedException.class,
            builder::finishHeader,
            context, TR -> "finishHeader() didn't throw ParameterNotSpecifiedException");
        try {
            builder.finishHeader();
        } catch (ParameterNotSpecifiedException exc) {
            assertEquals(new ParameterNotSpecifiedException(HeaderParameter.INPUT_SIZE).getMessage(), exc.getMessage(), context,
                TR -> "finishHeader() threw ParameterNotSpecifiedException with wrong message");
        }
    }

    @Test
    public void testFinishHeaderMissingOutputSize() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishHeader()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);

        assertThrows(ParameterNotSpecifiedException.class,
            builder::finishHeader,
            context, TR -> "finishHeader() didn't throw ParameterNotSpecifiedException");
        try {
            builder.finishHeader();
        } catch (ParameterNotSpecifiedException exc) {
            assertEquals(new ParameterNotSpecifiedException(HeaderParameter.OUTPUT_SIZE).getMessage(), exc.getMessage(), context,
                TR -> "finishHeader() threw ParameterNotSpecifiedException with wrong message");
        }
    }

    @Test
    public void testFinishHeaderMissingNumberOfTerms() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishHeader()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerOutputSize").set(builder, 2);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);

        assertThrows(ParameterNotSpecifiedException.class,
            builder::finishHeader,
            context, TR -> "finishHeader() didn't throw ParameterNotSpecifiedException");
        try {
            builder.finishHeader();
        } catch (ParameterNotSpecifiedException exc) {
            assertEquals(new ParameterNotSpecifiedException(HeaderParameter.NUMBER_OF_TERMS).getMessage(), exc.getMessage(), context,
                TR -> "finishHeader() threw ParameterNotSpecifiedException with wrong message");
        }
    }

    @Test
    public void testFinishHeaderMissingNumberOfStates() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishHeader()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerOutputSize").set(builder, 2);
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);

        assertThrows(ParameterNotSpecifiedException.class,
            builder::finishHeader,
            context, TR -> "finishHeader() didn't throw ParameterNotSpecifiedException");
        try {
            builder.finishHeader();
        } catch (ParameterNotSpecifiedException exc) {
            assertEquals(new ParameterNotSpecifiedException(HeaderParameter.NUMBER_OF_STATES).getMessage(), exc.getMessage(), context,
                TR -> "finishHeader() threw ParameterNotSpecifiedException with wrong message");
        }
    }

    private static void buildTestFsm(FsmBuilderImpl builder) throws IllegalAccessException, NoSuchFieldException, BadBitfieldException {
        var stateFactory = (StateFactory) Util.getPrivateField("stateFactory").get(builder);
        var fsm = (Fsm) Util.getPrivateField("fsm").get(builder);

        var fooState = stateFactory.get("foo");
        var barState = stateFactory.get("bar");
        fsm.addState(fooState);
        fsm.addState(barState);
        fooState.setTransition(new Transition(new BitField("0"), barState, new BitField("0")));
    }

    @Test
    public void testFinishFsmValid() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishFSM()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);
        Util.getPrivateField("numberOfTermsCounter").set(builder, 1);

        buildTestFsm(builder);

        builder.finishFSM();

        assertEquals(true, (boolean) Util.getPrivateField("buildFinished").get(builder), context,
            TR -> "finishFSM() didn't set buildFinished to true");
    }

    @Test
    public void testFinishFsmStateMismatch() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishFSM()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 3);
        Util.getPrivateField("numberOfTermsCounter").set(builder, 1);

        buildTestFsm(builder);

        assertThrows(SizeMismatchException.class,
            builder::finishFSM,
            context, TR -> "finishFSM() didn't throw SizeMismatchException");
        try {
            builder.finishFSM();
        } catch (SizeMismatchException exc) {
            assertEquals(new SizeMismatchException(HeaderParameter.NUMBER_OF_STATES).getMessage(), exc.getMessage(), context,
                TR -> "finishFSM() threw SizeMismatchException with wrong message");
        }
    }

    @Test
    public void testFinishFsmTermMismatch() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#finishFSM()")
            .build();
        var builder = new FsmBuilderImpl();
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);
        Util.getPrivateField("numberOfTermsCounter").set(builder, 4);

        buildTestFsm(builder);

        assertThrows(SizeMismatchException.class,
            builder::finishFSM,
            context, TR -> "finishFSM() didn't throw SizeMismatchException");
        try {
            builder.finishFSM();
        } catch (SizeMismatchException exc) {
            assertEquals(new SizeMismatchException(HeaderParameter.NUMBER_OF_TERMS).getMessage(), exc.getMessage(), context,
                TR -> "finishFSM() threw SizeMismatchException with wrong message");
        }
    }
}
