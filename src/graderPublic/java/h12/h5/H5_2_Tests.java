package h12.h5;

import h12.parse.FsmBuilderImpl;
import h12.template.errors.KissParserException;
import h12.template.errors.SizeMismatchException;
import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.HeaderParameter;
import h12.template.fsm.State;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.callable.Callable;

import java.util.stream.StreamSupport;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertThrows;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission()
public class H5_2_Tests {
    @Test
    public void testInvalidBitFieldWidths() throws Throwable {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#addTerm()")
            .build();
        var builder = new FsmBuilderImpl();

        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerOutputSize").set(builder, 2);

        Callable inputWidthMismatch = () -> builder.addTerm(new BitField("01"), "foo", "bar", new BitField("11"));
        assertThrows(SizeMismatchException.class,
            inputWidthMismatch,
            context, TR -> "addTerm() didn't throw SizeMismatchException");
        try {
            inputWidthMismatch.call();
        } catch (SizeMismatchException exc) {
            assertEquals(new SizeMismatchException(HeaderParameter.INPUT_SIZE).getMessage(), exc.getMessage(), context,
                TR -> "addTerm() threw SizeMismatchException with wrong message");
        }

        Callable outputWidthMismatch = () -> builder.addTerm(new BitField("110"), "foo", "bar", new BitField("0"));
        assertThrows(SizeMismatchException.class,
            outputWidthMismatch,
            context, TR -> "addTerm() didn't throw SizeMismatchException");
        try {
            outputWidthMismatch.call();
        } catch (SizeMismatchException exc) {
            assertEquals(new SizeMismatchException(HeaderParameter.OUTPUT_SIZE).getMessage(), exc.getMessage(), context,
                TR -> "addTerm() threw SizeMismatchException with wrong message");
        }
    }


    private static void setHeadersAndAddTerm(FsmBuilderImpl builder) throws IllegalAccessException, NoSuchFieldException, KissParserException {
        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerOutputSize").set(builder, 2);
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);

        builder.addTerm(new BitField("110"), "foo", "bar", new BitField("01"));
    }

    @Test
    public void testAddTermToFsm() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#addTerm()")
            .build();
        var builder = new FsmBuilderImpl();

        var fsmField = Util.getPrivateField("fsm");
        var fsm = spy((Fsm) fsmField.get(builder));
        fsmField.set(builder, fsm);

        setHeadersAndAddTerm(builder);

        verify(fsm).addState(new State("foo"));
        verify(fsm).addState(new State("bar"));

        var inputStateFound = false;
        for (State state : fsm) {
            if (state.getName().equals("foo")) {
                inputStateFound = true;
                var transitions = StreamSupport.stream(state.spliterator(), false).toList();
                assertEquals(1, transitions.size(), context, TR -> "input state has more than one transition");
                var transition = transitions.get(0);
                assertEquals("110", transition.getEvent().toString(), context, TR -> "transition has wrong input event");
                assertEquals("bar", transition.getNextState().getName(), context, TR -> "transition has wrong next state");
                assertEquals("01", transition.getOutput().toString(), context, TR -> "transition has wrong output");
            }
        }
        assertTrue(inputStateFound, context, TR -> "input state was not added to the Fsm");

        assertEquals(1, (int) Util.getPrivateField("numberOfTermsCounter").get(builder), context,
            TR -> "numberOfTermsCounter was not incremented");
    }
}
