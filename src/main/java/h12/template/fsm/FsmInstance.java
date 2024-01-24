package h12.template.fsm;

/**
 * Representation of an instance of a {@link Fsm}
 */
public class FsmInstance {

    private final Fsm fsm;
    private State currentState;

    /**
     * Create a new Instance
     * @param fsm The target Automata
     * @param initialState The initial State
     */
    public FsmInstance(Fsm fsm, State initialState){
        this.fsm = fsm;
        this.currentState = initialState;
    }

    /**
     * Make a step of Automata
     * @param event input symbol
     */
    public void step(BitField event){
        State nextState = currentState.getNextState(event);
        currentState = nextState;
    }

    /**
     *
     * @return the current state of automata
     */
    public State getCurrentState(){
        return currentState;
    }

}
