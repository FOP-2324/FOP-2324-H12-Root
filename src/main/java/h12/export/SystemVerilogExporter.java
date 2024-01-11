package h12.export;

import h12.template.fsm.*;

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

    public void generateModuleHeader(int inputBitWidth, int outputBitWith) throws IOException {
        writer.write("module ");
        writer.write(moduleName);
        writer.write("(input clk, input rst, input [");
        writer.write(String.valueOf(inputBitWidth - 1));
        writer.write(":0] in, output bit[");
        writer.write(String.valueOf(outputBitWith - 1));
        writer.write(":0] out);");
        writer.newLine();
    }

    public void generateModuleFooter() throws IOException {
        writer.write("endmodule\n");
    }

    public void generateVariable(String variableName, int variableBitWidth) throws IOException {
        writer.write("\tbit [");
        writer.write(String.valueOf(variableBitWidth - 1));
        writer.write(":0] ");
        writer.write(variableName);
        writer.write(";");
        writer.newLine();

    }


    public void generatePosedgeBlock(State initialState) throws IOException {
        writer.write("\talways_ff @(posedge clk) begin\n\t\tstate <= rst ? ");
        writer.write(String.valueOf(stateEncoding.getWidth()));
        writer.write("'b");
        writer.write(stateEncoding.encode(initialState).toString('?'));
        writer.write(" : nextState;\n");
        writer.write("\t\tout <= nextOut;\n\tend\n");
        writer.newLine();
    }

    public void generateConditionsHeader(int inputBitWidth) throws IOException {
        writer.write("\twire [");
        writer.write(String.valueOf(inputBitWidth + stateEncoding.getWidth() - 1));
        writer.write(":0] tmp;");
        writer.newLine();
        writer.write("\tassign tmp = {in, state};\n");
        writer.newLine();
        writer.write("\talways_comb begin\n");
        writer.write("\t\tnextOut = out;\n");
        writer.write("\t\tnextState = state;\n");
        writer.write("\t\tcasez(tmp)\n");
    }

    public void generateCondition(State startState, BitField event, State endState, BitField output) throws IOException {
        writer.write("\t\t\t"); // indentation
        writer.write('{');
        writer.write(event.width());
        writer.write("'b");
        writer.write(event.toString('?'));
        writer.write(", ");
        writer.write(stateEncoding.getWidth());
        writer.write("'b");
        writer.write(stateEncoding.encode(startState).toString());
        writer.write("} : begin nextState = ");
        writer.write(stateEncoding.getWidth());
        writer.write("'b");
        writer.write(stateEncoding.encode(endState).toString());
        writer.write("; nextOut = ");
        writer.write(output.width());
        writer.write("'b");
        writer.write(output.toString('?'));
        writer.write("; end");
        writer.write('\n');
    }

    public void generateConditionsFooter() throws IOException {
        writer.write("\t\tendcase\n");
        writer.write("\tend\n");
    }

    @Override
    public void export(Fsm fsm) throws IOException {
        stateEncoding.init(fsm.getNumberOfStates());

        final int[] inputWidth = {0};
        final int[] outputWidth = {0};

        // module definition
        generateModuleHeader(inputWidth[0], outputWidth[0]);
        writer.newLine();

        generateVariable("state", stateEncoding.getWidth());
        generateVariable("nextState", stateEncoding.getWidth());
        generateVariable("nextOut", outputWidth[0]);
        writer.newLine();

        State initialState = fsm.createInstance().getCurrentState();
        generatePosedgeBlock(initialState);

        generateConditionsHeader(inputWidth[0]);


        fsm.forEach(state -> state.forEach(transition -> {
            try {
                generateCondition(state, transition.getEvent(), transition.getNextState(), transition.getOutput());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        generateConditionsFooter();

        generateModuleFooter();

    }
}
