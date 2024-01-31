package h12.export;

import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.State;
import h12.template.fsm.StateEncoding;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Implementation of {@link FsmExporter}, which can be used to export {@link Fsm} as System Verilog-File
 */
public class SystemVerilogExporter implements FsmExporter {

    private final BufferedWriter writer;
    private final StateEncoding stateEncoding;
    private final String moduleName;

    /**
     * Constructs a instance of q {@link SystemVerilogExporter}
     *
     * @param writer        Writer which is used to write the output
     * @param stateEncoding {@link StateEncoding} which is used to encode the States of exported {@link Fsm}
     * @param moduleName    The name of the Module for System Verilog Header
     */
    public SystemVerilogExporter(BufferedWriter writer, StateEncoding stateEncoding, String moduleName) {
        this.writer = writer;
        this.stateEncoding = stateEncoding;
        this.moduleName = moduleName;
    }

    /**
     * Writes the Module Header
     *
     * @param inputBitWidth The Bit Width of Input
     * @param outputBitWith The Bit Width of Output
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateModuleHeader(int inputBitWidth, int outputBitWith) throws IOException {
        writer.write("module ");
        writer.write(moduleName);
        writer.write("(input clk, input rst, input [");
        writer.write(String.valueOf(inputBitWidth - 1));
        writer.write(":0] in, output bit[");
        writer.write(String.valueOf(outputBitWith - 1));
        writer.write(":0] out);");
        writer.newLine();
    }

    /**
     * Writes the Module Footer
     *
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateModuleFooter() throws IOException {
        writer.write("endmodule\n");
    }

    /**
     * Writes a Variable
     *
     * @param variableName     The name of the variable
     * @param variableBitWidth The Bit Width of this variable
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateVariable(String variableName, int variableBitWidth) throws IOException {
        writer.write("\tbit [");
        writer.write(String.valueOf(variableBitWidth - 1));
        writer.write(":0] ");
        writer.write(variableName);
        writer.write(";");
        writer.newLine();
    }

    /**
     * Writes the Posedge Block
     *
     * @param initialState The initial state of the Automata
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generatePosedgeBlock(State initialState) throws IOException {
        writer.write("\talways_ff @(posedge clk) begin\n\t\tstate <= rst ? ");
        writer.write(String.valueOf(stateEncoding.getWidth()));
        writer.write("'b");
        writer.write(stateEncoding.encode(initialState).toString('?'));
        writer.write(" : nextState;\n");
        writer.write("\t\tout <= nextOut;\n\tend\n");
        writer.newLine();
    }

    /**
     * Writes the Condition Block Header
     *
     * @param inputBitWidth The width of the Input Symbol
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateConditionsHeader(int inputBitWidth) throws IOException {
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

    /**
     * Writes one Condition
     *
     * @param startState The start state of transition
     * @param event      The input event of transition
     * @param endState   The end state of transition
     * @param output     The output of transition
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateCondition(State startState, BitField event, State endState, BitField output) throws IOException {
        writer.write("\t\t\t"); // indentation // schon gegeben
        writer.write('{');
        writer.write(String.valueOf(event.width()));
        writer.write("'b");
        writer.write(event.toString('?'));
        writer.write(", ");
        writer.write(String.valueOf(stateEncoding.getWidth()));
        writer.write("'b");
        writer.write(stateEncoding.encode(startState).toString());
        writer.write("} : begin nextState = ");
        writer.write(String.valueOf(stateEncoding.getWidth()));
        writer.write("'b");
        writer.write(stateEncoding.encode(endState).toString());
        writer.write("; nextOut = ");
        writer.write(String.valueOf(output.width()));
        writer.write("'b");
        writer.write(output.toString('?'));
        writer.write("; end");
        writer.write('\n');
    }

    /**
     * Writes the Condition Footer
     *
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateConditionsFooter() throws IOException {
        writer.write("\t\tendcase\n");
        writer.write("\tend\n");
    }

    @Override
    public void export(Fsm fsm) throws IOException {
        stateEncoding.init(fsm.getNumberOfStates());

        final int[] inputWidth = {0};
        final int[] outputWidth = {0};

        fsm.forEach(from -> from.forEach(transition -> {
            inputWidth[0] = transition.getEvent().width();
            outputWidth[0] = transition.getOutput().width();
        }));

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
