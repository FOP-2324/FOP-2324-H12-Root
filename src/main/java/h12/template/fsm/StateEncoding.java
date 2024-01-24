package h12.template.fsm;

/**
 * Class used to encode {@link State}s
 */
public interface StateEncoding {

    /**
     * Inits the {@link StateEncoding} with
     * @param numberOfStates a specific count of states
     */
    void init(int numberOfStates);

    /**
     * Encodes a state
     * @param state Satte to encode
     * @return the appropriate encoding
     */
    BitField encode(State state);

    /**
     *
     * @return the bit width of encoding
     */
    int getWidth();
}
