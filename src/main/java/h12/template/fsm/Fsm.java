package h12.template.fsm;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Fsm implements Iterable<State>{

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

    public int getNumberOfStates(){
        return states.size();
    }

    @NotNull
    @Override
    public Iterator<State> iterator() {
        return states.iterator();
    }
}
