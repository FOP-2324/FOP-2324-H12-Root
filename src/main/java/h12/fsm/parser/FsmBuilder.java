package h12.fsm.parser;

import h12.errors.KissParserException;
import h12.fsm.BitField;

public interface FsmBuilder {

    void setInputSize(int inputSize) throws KissParserException;
    void setOutputSize(int outputSize) throws KissParserException;
    void setNumberOfTerms(int numberOfTerms) throws KissParserException;
    void setNumberOfStates(int numberOfStates) throws KissParserException;
    void finishHeader() throws KissParserException;
    void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField);
    void finishFSM();
}
