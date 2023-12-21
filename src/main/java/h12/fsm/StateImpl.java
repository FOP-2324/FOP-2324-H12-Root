package h12.fsm;

import java.util.HashMap;
import java.util.Map;

public class StateImpl implements State{

    private final Map<String, State> transitions = new HashMap<>();
    private final String name;


    public StateImpl(String name){
        this.name = name;
    }

    @Override
    public void setTransition(String event, State state) {
        transitions.put(event, state);
    }

    @Override
    public State getNextState(String event) {
        State state = transitions.get(event);
        return state != null ? state : this;
    }

    @Override
    public String toString() {
        return "State{" + name + '}';
    }
}
