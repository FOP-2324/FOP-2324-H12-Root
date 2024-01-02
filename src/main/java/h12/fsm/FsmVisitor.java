package h12.fsm;

public interface FsmVisitor {
    void visitState(State state);
    void visitTermOfState(BitField inputField, State nextState, BitField outputField);
}
