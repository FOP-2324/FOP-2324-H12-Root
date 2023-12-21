package h12.fsm.parser;

import h12.fsm.State;

public interface StateFactory {
    State getState(String identifier);
    int getNumberOfStates();
}
