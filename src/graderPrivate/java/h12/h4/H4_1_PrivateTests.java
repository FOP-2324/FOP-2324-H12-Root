package h12.h4;

import h12.parse.FsmBuilder;
import h12.parse.FsmParser;
import h12.template.errors.BadIdentifierException;
import h12.template.errors.BadNumberException;
import h12.template.errors.BadTokenException;
import h12.template.errors.KissParserException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TestForSubmission
public class H4_1_PrivateTests {

    @Test
    public void testParseInitialState() throws IOException, KissParserException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Check normal behavior
        String state = "OFF";
        H4_TokenScanner scanner = new H4_TokenScanner(".r", state);
        FsmBuilder builder = Mockito.mock(FsmBuilder.class);
        FsmParser parser = new FsmParser(scanner, builder);
        Method method = Util.getPrivateParserMethod("parseInitialState");
        method.invoke(parser);
        Mockito.doNothing().when(builder).setInitialState(state);
        Mockito.verify(builder, Mockito.times(1)).setInitialState(state);
    }

    @Test
    public void testParseOutputWidthException() throws IOException, NoSuchMethodException {
        H4_TokenScanner scanner = new H4_TokenScanner(".o", "dsadsa");
        FsmBuilder builder = Mockito.mock(FsmBuilder.class);

        FsmParser parser = new FsmParser(scanner, builder);
        Method method = Util.getPrivateParserMethod("parseOutputWidth");
        Throwable throwable = Assertions2.assertThrows(InvocationTargetException.class, () -> method.invoke(parser), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
        Assertions2.assertEquals(BadNumberException.class, throwable.getCause().getClass(), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
    }

    @Test
    public void testParseNumberOfTermsException() throws IOException, NoSuchMethodException {
        H4_TokenScanner scanner = new H4_TokenScanner(".p", "dsa");
        FsmBuilder builder = Mockito.mock(FsmBuilder.class);
        FsmParser parser = new FsmParser(scanner, builder);
        Method method = Util.getPrivateParserMethod("parseNumberOfTerms");
        Throwable throwable = Assertions2.assertThrows(InvocationTargetException.class, () -> method.invoke(parser), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
        Assertions2.assertEquals(BadNumberException.class, throwable.getCause().getClass(), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
    }

    @Test
    public void testParseNumberOfStatesException() throws IOException, NoSuchMethodException {
        H4_TokenScanner scanner = new H4_TokenScanner(".p", "dsa");
        FsmBuilder builder = Mockito.mock(FsmBuilder.class);
        FsmParser parser = new FsmParser(scanner, builder);
        Method method = Util.getPrivateParserMethod("parseNumberOfStates");
        Throwable throwable = Assertions2.assertThrows(InvocationTargetException.class, () -> method.invoke(parser), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
        Assertions2.assertEquals(BadTokenException.class, throwable.getCause().getClass(), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
    }

    @Test
    public void testParseInitialStateException() throws IOException, NoSuchMethodException {
        H4_TokenScanner scanner = new H4_TokenScanner(".r", "");
        FsmBuilder builder = Mockito.mock(FsmBuilder.class);
        FsmParser parser = new FsmParser(scanner, builder);
        Method method = Util.getPrivateParserMethod("parseInitialState");
        Throwable throwable = Assertions2.assertThrows(InvocationTargetException.class, () -> method.invoke(parser), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
        Assertions2.assertEquals(BadIdentifierException.class, throwable.getCause().getClass(), Assertions2.emptyContext(), r -> "Wrong exception thrown or none.");
    }
}
