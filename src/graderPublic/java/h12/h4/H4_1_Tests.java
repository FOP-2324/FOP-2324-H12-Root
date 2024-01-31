package h12.h4;

import h12.parse.FsmBuilder;
import h12.parse.FsmParser;
import h12.template.errors.BadIdentifierException;
import h12.template.errors.BadNumberException;
import h12.template.errors.KissParserException;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertThrows;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission()
public class H4_1_Tests {
    @Test
    public void testParseOutputWidth() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, KissParserException {
        var scanner = new H4_TokenScanner(".o", "42");
        var builder = mock(FsmBuilder.class);
        var parser = new FsmParser(scanner, builder);
        var method = Util.getPrivateParserMethod("parseOutputWidth");

        method.invoke(parser);
        verify(builder).setOutputSize(42);
        verifyNoMoreInteractions(builder);
    }

    @Test
    public void testParseNumberOfTerms() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, KissParserException {
        var scanner = new H4_TokenScanner(".p", "4");
        var builder = mock(FsmBuilder.class);
        var parser = new FsmParser(scanner, builder);
        var method = Util.getPrivateParserMethod("parseNumberOfTerms");

        method.invoke(parser);
        verify(builder).setNumberOfTerms(4);
        verifyNoMoreInteractions(builder);
    }

    @Test
    public void testParseNumberOfStates() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, KissParserException {
        var scanner = new H4_TokenScanner(".s", "8");
        var builder = mock(FsmBuilder.class);
        var parser = new FsmParser(scanner, builder);
        var method = Util.getPrivateParserMethod("parseNumberOfStates");

        method.invoke(parser);
        verify(builder).setNumberOfStates(8);
        verifyNoMoreInteractions(builder);
    }

    @Test
    public void testParseHeader() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, KissParserException {
        var scanner = new H4_TokenScanner(
            ".i",
            "2",
            ".o",
            "1",
            ".p",
            "5",
            ".s",
            "2",
            ".r",
            "OFF",
            "-1"
        );
        var builder = mock(FsmBuilder.class);
        var parser = new FsmParser(scanner, builder);
        var method = Util.getPrivateParserMethod("parseHeader");

        method.invoke(parser);
        var inOrder = inOrder(builder);
        inOrder.verify(builder).setInputSize(2);
        inOrder.verify(builder).setOutputSize(1);
        inOrder.verify(builder).setNumberOfTerms(5);
        inOrder.verify(builder).setNumberOfStates(2);
        inOrder.verify(builder).setInitialState("OFF");
        inOrder.verify(builder).finishHeader();
        inOrder.verifyNoMoreInteractions();
    }
}
