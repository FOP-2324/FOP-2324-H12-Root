package h12.template.fsm;

public class FsmInstance {

    private final Fsm fsm;
    private State currentState;

    public FsmInstance(Fsm fsm, State initialState){
        this.fsm = fsm;
        this.currentState = initialState;
    }

    public void step(BitField event){
        State nextState = currentState.getNextState(event);
        currentState = nextState;
    }

    public State getCurrentState(){
        return currentState;
    }

}
