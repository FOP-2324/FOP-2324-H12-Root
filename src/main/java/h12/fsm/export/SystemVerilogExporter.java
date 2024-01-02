package h12.fsm.export;

import h12.fsm.*;

import java.io.BufferedWriter;
import java.io.IOException;

public class SystemVerilogExporter implements FsmExporter{

    private final BufferedWriter writer;
    private final StateEncoding stateEncoding;
    private final String moduleName;

    public SystemVerilogExporter(BufferedWriter writer, StateEncoding stateEncoding, String moduleName){
        this.writer = writer;
        this.stateEncoding = stateEncoding;
        this.moduleName = moduleName;
    }

    @Override
    public void export(Fsm fsm) throws IOException {
        stateEncoding.init(fsm.getNumberOfStates());

        final int[] inputWidth = {0};
        final int[] outputWidth = {0};
        final State[] currentState = {null};

        StringBuilder stringBuilder = new StringBuilder();

        fsm.visit(new FsmVisitor() {
            @Override
            public void visitState(State state) {
                currentState[0] = state;
            }

            @Override
            public void visitTermOfState(BitField inputField, State nextState, BitField outputField) {
                inputWidth[0] = inputField.width();
                outputWidth[0] = outputField.width();

                // Generate line
                stringBuilder.append("\t\t\t"); // indentation
                stringBuilder.append('{');
                stringBuilder.append(inputWidth[0]);
                stringBuilder.append("'b");
                stringBuilder.append(inputField.toString('?'));
                stringBuilder.append(", ");
                stringBuilder.append(stateEncoding.getWidth());
                stringBuilder.append("'b");
                stringBuilder.append(stateEncoding.encode(currentState[0]).toString('?'));
                stringBuilder.append("} : begin nextState = ");
                stringBuilder.append(stateEncoding.getWidth());
                stringBuilder.append("'b");
                stringBuilder.append(stateEncoding.encode(nextState).toString('?'));
                stringBuilder.append("; nextOut = ");
                stringBuilder.append(outputWidth[0]);
                stringBuilder.append("'b");
                stringBuilder.append(outputField.toString('?'));
                stringBuilder.append("; end");
                stringBuilder.append('\n');
            }
        });

        // module definition
        writer.write("module ");
        writer.write(moduleName);
        writer.write("(input clk, input rst, input [");
        writer.write(String.valueOf(inputWidth[0] - 1));
        writer.write(":0] in, output bit[");
        writer.write(String.valueOf(outputWidth[0] - 1));
        writer.write(":0] out);");
        writer.newLine();
        writer.newLine();


        writer.write("\tbit [");
        writer.write(String.valueOf(stateEncoding.getWidth() - 1));
        writer.write(":0] state;");
        writer.newLine();

        writer.write("\tbit [");
        writer.write(String.valueOf(stateEncoding.getWidth() - 1));
        writer.write(":0] nextState;");
        writer.newLine();

        writer.write("\tbit [");
        writer.write(String.valueOf(outputWidth[0] - 1));
        writer.write(":0] nextOut;");
        writer.newLine();
        writer.newLine();

        State initialState = fsm.createInstance().getCurrentState();

        writer.write("\talways_ff @(posedge clk) begin\n\t\tstate <= rst ? ");
        writer.write(String.valueOf(stateEncoding.getWidth()));
        writer.write("'b");
        writer.write(stateEncoding.encode(initialState).toString('?'));
        writer.write(" : nextState;\n");
        writer.write("\t\tout <= nextOut;\n\tend\n");
        writer.newLine();

        writer.write("\twire [");
        writer.write(String.valueOf(inputWidth[0] + stateEncoding.getWidth() - 1));
        writer.write(":0] tmp;");
        writer.newLine();
        writer.write("\tassign tmp = {in, state};\n");
        writer.newLine();
        writer.write("\talways_comb begin\n");
        writer.write("\t\tnextOut = out;\n");
        writer.write("\t\tnextState = state;\n");
        writer.write("\t\tcasez(tmp)\n");
        writer.write(stringBuilder.toString());
        writer.write("\t\tendcase\n");
        writer.write("\tend\n");
        writer.write("endmodule\n");

        writer.close();
    }
}
