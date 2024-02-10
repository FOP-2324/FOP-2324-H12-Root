package h12.h5;

import h12.parse.FsmBuilderImpl;
import h12.template.errors.KissParserException;
import h12.template.fsm.Fsm;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertSame;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertThrows;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission()
public class H5_4_Tests {
    @Test
    public void testGetFsmUnfinished() {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#getFsm()")
            .build();
        var builder = new FsmBuilderImpl();
        assertThrows(KissParserException.class, builder::getFsm, context,
            TR -> "getFsm() didn't throw the required exception for an unfinished FSM");
    }

    @Test
    public void testGetFsmFinished() throws NoSuchFieldException, IllegalAccessException, KissParserException {
        var context = contextBuilder()
            .subject("FsmBuilderImpl#getFsm()")
            .build();
        var builder = new FsmBuilderImpl();
        var fsm = (Fsm) Util.getPrivateField("fsm").get(builder);
        Util.getPrivateField("buildFinished").set(builder, true);
        assertSame(fsm, builder.getFsm(), context,
            TR -> "getFsm() didn't return the finished FSM");
    }
}
