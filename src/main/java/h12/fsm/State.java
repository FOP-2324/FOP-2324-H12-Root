package h12.fsm;

public interface State {
    void setTransition(BitField event, State nextState, BitField output);
    State getNextState(BitField event);

    void visit(FsmVisitor fsmVisitor);
    String getName();
}
