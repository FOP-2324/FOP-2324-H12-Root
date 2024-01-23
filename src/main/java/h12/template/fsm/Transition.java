package h12.template.fsm;

public class Transition {

    private final BitField event;
    private final State nextState;
    private final BitField output;

    public Transition(BitField event, State nextState, BitField output){
        this.event = event;
        this.nextState = nextState;
        this.output = output;
    }

    public BitField getEvent() {
        return event;
    }

    public State getNextState() {
        return nextState;
    }

    public BitField getOutput() {
        return output;
    }
}
