package h12.fsm;

public interface State {
    void setTransition(String event, State state);
    State getNextState(String event);
}
