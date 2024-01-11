package h12.template.fsm;

public interface StateVisitor {
    void visitState(State state);
    void visitTermOfState(BitField inputField, State nextState, BitField outputField);
}
