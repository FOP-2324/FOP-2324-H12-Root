package h12.fsm;

public interface StateVisitor {
    void visitState(State state);
    void visitTermOfState(BitField inputField, State nextState, BitField outputField);
}
