package h12.fsm;

import java.util.HashSet;
import java.util.Set;

public class Fsm {

    private final Set<State> states = new HashSet<>();
    private State initialState = null;

    public void addState(State state){
        states.add(state);
    }

    public void setInitialState(State state){
        initialState = state;
    }

    public State getInitialState(){
        return initialState;
    }

    public FsmInstance createInstance(){
        return new FsmInstance(this, initialState != null ? initialState : states.iterator().next());
    }

    public void visit(FsmVisitor fsmVisitor){
        for (State state : states){
            state.visit(fsmVisitor);
        }
    }

    public int getNumberOfStates(){
        return states.size();
    }

}
