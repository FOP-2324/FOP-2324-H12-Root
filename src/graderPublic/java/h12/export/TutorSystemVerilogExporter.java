package h12.export;

import h12.template.fsm.BitField;
import h12.template.fsm.State;
import h12.template.fsm.StateEncoding;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;

public abstract class TutorSystemVerilogExporter extends SystemVerilogExporter implements Closeable {

    protected final SystemVerilogExporter delegate;

    public TutorSystemVerilogExporter(SystemVerilogExporter delegate) {
        super(
            (BufferedWriter) getField(delegate, "writer"),
            (StateEncoding) getField(delegate, "stateEncoding"),
            (String) getField(delegate, "moduleName")
        );
        this.delegate = delegate;
    }

    private static Object getField(SystemVerilogExporter delegate, String name) {
        try {
            Field field = delegate.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(delegate);
        } catch (NoSuchFieldException e) {
            return Assertions2.fail(Assertions2.emptyContext(), result -> "Field %s not found".formatted(name));
        } catch (IllegalAccessException e) {
            return Assertions2.fail(Assertions2.emptyContext(), result -> "Field %s could not be accessed".formatted(name));
        }
    }

    @Override
    protected void generateModuleHeader(int inputBitWidth, int outputBitWith) throws IOException {

    }

    @Override
    protected void generateModuleFooter() throws IOException {

    }

    @Override
    protected void generateVariable(String variableName, int variableBitWidth) throws IOException {

    }

    @Override
    protected void generatePosedgeBlock(State initialState) throws IOException {

    }

    @Override
    protected void generateConditionsHeader(int inputBitWidth) throws IOException {

    }

    @Override
    protected void generateCondition(State startState, BitField event, State endState, BitField output) throws IOException {

    }

    @Override
    protected void generateConditionsFooter() throws IOException {

    }

    @Override
    public void close() throws IOException {
        ((BufferedWriter) getField(this, "writer")).close();
    }
}
