package h12.export;


import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.StateVisitor;
import h12.template.fsm.State;

import java.io.BufferedWriter;
import java.io.IOException;
public class KissExporter implements FsmExporter {

    private final BufferedWriter writer;

    public KissExporter(BufferedWriter writer){
        this.writer = writer;
    }

    @Override
    public void export(Fsm fsm) throws IOException {
        final int numberOfStates = fsm.getNumberOfStates();
        final int[] numberOfTerms = {0};
        final int[] inputWidth = {0};
        final int[] outputWidth = {0};

        StringBuilder stringBuilder = new StringBuilder();
        final State[] currentState = {null};


        fsm.visit(new StateVisitor() {
            @Override
            public void visitState(State state) {
                currentState[0] = state;
            }

            @Override
            public void visitTermOfState(BitField inputField, State nextState, BitField outputField) {
                numberOfTerms[0]++;
                inputWidth[0] = inputField.width();
                outputWidth[0] = outputField.width();

                stringBuilder.append(inputField.toString());
                stringBuilder.append(' ');
                stringBuilder.append(currentState[0].getName());
                stringBuilder.append(' ');
                stringBuilder.append(nextState.getName());
                stringBuilder.append(' ');
                stringBuilder.append(outputField.toString());
                stringBuilder.append('\n');
            }
        });


        // Header
        writer.write(".i ");
        writer.write(String.valueOf(inputWidth[0]));
        writer.newLine();
        writer.write(".o ");
        writer.write(String.valueOf(outputWidth[0]));
        writer.newLine();
        writer.write(".p ");
        writer.write(String.valueOf(numberOfTerms[0]));
        writer.newLine();
        writer.write(".s ");
        writer.write(String.valueOf(numberOfStates));
        writer.newLine();
        if(fsm.getInitialState() != null){
            writer.write(".r ");
            writer.write(fsm.getInitialState().getName());
            writer.newLine();
        }

        // Content
        writer.write(stringBuilder.toString());


        writer.close(); // TODO: ja oder nein?
    }
}
