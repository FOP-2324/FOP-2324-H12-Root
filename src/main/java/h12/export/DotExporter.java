package h12.export;

import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.State;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class DotExporter implements FsmExporter{

    private static class StatePair{
        private final State first;
        private final State second;

        public StatePair(State first, State second){
            this.first = first;
            this.second = second;
        }

        public State getFirst() {
            return first;
        }

        public State getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StatePair statePair = (StatePair) o;
            return Objects.equals(first, statePair.first) && Objects.equals(second, statePair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }


    private BufferedWriter writer;

    public DotExporter(BufferedWriter writer){
        this.writer = writer;
    }

    private void writeHeader() throws IOException {
        writer.write("digraph G {\n");
        writer.write("\trankdir=\"LR\";\n");
    }

    private void writeFooter() throws IOException {
        writer.write("}");
    }

    private void writeState(State state, BitField output) throws IOException {
        writer.write("\t");
        writer.write(state.getName());
        writer.write("[label=<");
        writer.write(state.getName());
        writer.write("<BR /> <FONT POINT-SIZE=\"10\">");
        writer.write(output.toString('X'));
        writer.write("</FONT>>];");
        writer.newLine();
    }

    private void writeInitial(State state) throws IOException {
        writer.write("\t__initial [margin=0 fontcolor=black fillcolor=black fontsize=0 width=0.5 shape=circle style=filled];\n");
        writer.write("\t__initial -> ");
        writer.write(state.getName());
        writer.write(";\n");
    }

    private void writeTransition(State from, State to, Set<BitField> events) throws IOException {
        //a1 -> b3[label="abc"];
        writer.write("\t");
        writer.write(from.getName());
        writer.write(" -> ");
        writer.write(to.getName());
        writer.write("[label=\"");

        Iterator<BitField> iterator = events.iterator();
        while (iterator.hasNext()){
            BitField event = iterator.next();
            writer.write(event.toString());
            if(iterator.hasNext()){
                writer.write(", ");
            }
        }
        writer.write("\"];\n");
    }


    @Override
    public void export(Fsm fsm) throws IOException {
        if(!fsm.isVerbose()){
            fsm = fsm.toVerboseFsm();
        }

        // Collect output of state
        HashMap<State, BitField> outputOfState = new HashMap<>();

        fsm.forEach(from -> from.forEach(transition -> outputOfState.put(transition.getNextState(), transition.getOutput())));
        BitField unspecified = new BitField(outputOfState.values().iterator().next().width(), BitField.BitValue.DC);
        fsm.forEach(state -> outputOfState.computeIfAbsent(state, state1 -> unspecified));


        writeHeader();

        fsm.forEach(state -> {
            try {
                writeState(state, outputOfState.get(state));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        writer.newLine();

        if(fsm.getInitialState() != null){
            writeInitial(fsm.getInitialState());
        }


        HashMap<StatePair, Set<BitField>> transitions = new HashMap<>();

        fsm.forEach(from -> from.forEach(transition -> {
            var eventSet = transitions.computeIfAbsent(new StatePair(from, transition.getNextState()), statePair -> new HashSet<>());
            eventSet.add(transition.getEvent());
        }));

        for (var transition : transitions.entrySet()){
            writeTransition(transition.getKey().getFirst(), transition.getKey().getSecond(), transition.getValue());
        }

        writeFooter();
    }
}