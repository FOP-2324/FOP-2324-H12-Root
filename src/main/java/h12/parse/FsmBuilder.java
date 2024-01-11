package h12.parse;

import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;

public interface FsmBuilder {

    void setInputSize(int inputSize) throws KissParserException;
    void setOutputSize(int outputSize) throws KissParserException;
    void setNumberOfTerms(int numberOfTerms) throws KissParserException;
    void setNumberOfStates(int numberOfStates) throws KissParserException;
    void setInitialState(String initialStateIdentifier) throws KissParserException;

    void finishHeader() throws KissParserException;
    void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField) throws KissParserException;
    void finishFSM() throws KissParserException;
}
