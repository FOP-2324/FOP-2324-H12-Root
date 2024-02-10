package h12.h5;

import h12.parse.FsmBuilderImpl;
import h12.template.errors.KissParserException;
import h12.template.errors.SizeMismatchException;
import h12.template.fsm.*;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.callable.Callable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class H5_2_PrivateTests {

    private static void setHeadersAndAddTerm(FsmBuilderImpl builder) throws IllegalAccessException, NoSuchFieldException, KissParserException {
        Util.getPrivateField("headerInputSize").set(builder, 3);
        Util.getPrivateField("headerOutputSize").set(builder, 2);
        Util.getPrivateField("headerNumberOfTerms").set(builder, 1);
        Util.getPrivateField("headerNumberOfStates").set(builder, 2);

        builder.addTerm(new BitField("110"), "foo", "bar", new BitField("01"));
    }

    @Test
    public void testStateFactoryUsed() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#addTerm()")
            .build();
        var builder = new FsmBuilderImpl();

        var stateFactoryField = Util.getPrivateField("stateFactory");
        var stateFactory = spy((StateFactory) stateFactoryField.get(builder));
        stateFactoryField.set(builder, stateFactory);

        setHeadersAndAddTerm(builder);

        verify(stateFactory).get("foo");
        verify(stateFactory).get("bar");

        var fooState = stateFactory.get("foo");
        var barState = stateFactory.get("bar");

        var fsm = (Fsm) Util.getPrivateField("fsm").get(builder);

        var fsmStates = StreamSupport.stream(fsm.spliterator(), false)
            .sorted(Comparator.comparing(State::getName))
            .toList();

        assertEquals(List.of(barState, fooState), fsmStates, context,
            TR -> "Fsm doesn't use the states created by the StateFactory");
    }
}
