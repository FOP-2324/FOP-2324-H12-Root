package h12.parse;

import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;

/**
 * Builder to construct a {@link h12.template.fsm.Fsm}
 */
public interface FsmBuilder {
    /**
     * Sets the Input Size
     * @param inputSize Input Size
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void setInputSize(int inputSize) throws KissParserException;

    /**
     * Sets the Output Size
     * @param outputSize Output Size
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void setOutputSize(int outputSize) throws KissParserException;

    /**
     * Sets the Number of Terms
     * @param numberOfTerms The number of terms of Automata
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void setNumberOfTerms(int numberOfTerms) throws KissParserException;

    /**
     * Sets the Number of States
     * @param numberOfStates The number of states of Automata
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void setNumberOfStates(int numberOfStates) throws KissParserException;

    /**
     * Sets the Initial State
     * @param initialStateIdentifier The initial State
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void setInitialState(String initialStateIdentifier) throws KissParserException;

    /**
     * Finishes the header
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void finishHeader() throws KissParserException;

    /**
     * Adds a new Term
     * @param inputField The Input Symbol of Transition
     * @param inputStateIdentifier The start state if Transition
     * @param nextStateIdentifier the end state of transition
     * @param outputField The Output Symbol of Transition
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField) throws KissParserException;

    /**
     * Finish the Fsm
     * @throws KissParserException Thrown in case if Parameter is already Specified
     */
    void finishFSM() throws KissParserException;
}
