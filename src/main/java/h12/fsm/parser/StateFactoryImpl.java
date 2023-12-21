package h12.fsm.parser;

// Todo: use factory for stzatze

import h12.fsm.State;
import h12.fsm.StateImpl;

import java.util.HashMap;

public class StateFactoryImpl implements StateFactory{


    private final HashMap<String, State> stateMap = new HashMap<>();

    @Override
    public State getState(String identifier) {
        return stateMap.computeIfAbsent(identifier, StateImpl::new);
    }

    @Override
    public int getNumberOfStates() {
        return stateMap.size();
    }
}
