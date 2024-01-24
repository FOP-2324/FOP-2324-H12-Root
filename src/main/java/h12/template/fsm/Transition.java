package h12.template.fsm;

/**
 * Entity representing a Transition of Automata
 */
public class Transition {

    private final BitField event;
    private final State nextState;
    private final BitField output;

    /**
     * Create a new {@link Transition}
     * @param event Input symbol
     * @param nextState next {@link State}
     * @param output appropriate Output Symbol
     */
    public Transition(BitField event, State nextState, BitField output){
        this.event = event;
        this.nextState = nextState;
        this.output = output;
    }

    /**
     *
     * @return the input Event
     */
    public BitField getEvent() {
        return event;
    }

    /**
     *
     * @return the next {@link State}
     */
    public State getNextState() {
        return nextState;
    }

    /**
     *
     * @return the Output Symbol
     */
    public BitField getOutput() {
        return output;
    }
}
