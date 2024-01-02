package h12.fsm;

public interface StateEncoding {
    void init(int numberOfStates);
    BitField encode(State state);
    int getWidth();
}
