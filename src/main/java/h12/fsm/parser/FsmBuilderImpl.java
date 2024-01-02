package h12.fsm.parser;

import h12.errors.KissParserException;
import h12.fsm.BitField;
import h12.fsm.Fsm;
import h12.fsm.State;
import h12.fsm.StateFactory;

public class FsmBuilderImpl implements FsmBuilder{

    private int inputSize = -1;
    private int outputSize = -1;
    private int numberOfTerms = -1;
    private int numberOfStates = -1;
    private int numberOfTermsCounter = 0;

    private final StateFactory stateFactory;
    private final Fsm fsm = new Fsm();

    private boolean buildFinished = false;

    public FsmBuilderImpl(StateFactory stateFactory){
        this.stateFactory = stateFactory;
    }

    @Override
    public void setInputSize(int inputSize) throws KissParserException {
        if(this.inputSize != -1){
            throw new KissParserException("Input size already specified!");
        }

        this.inputSize = inputSize;
    }

    @Override
    public void setOutputSize(int outputSize) throws KissParserException {
        if(this.outputSize != -1){
            throw new KissParserException("Input size already specified!");
        }

        this.outputSize = outputSize;
    }

    @Override
    public void setNumberOfTerms(int numberOfTerms) throws KissParserException {
        if(this.numberOfTerms != -1){
            throw new KissParserException("Number of Terms already specified!");
        }

        this.numberOfTerms = numberOfTerms;
    }

    @Override
    public void setNumberOfStates(int numberOfStates) throws KissParserException {
        if(this.numberOfStates != -1){
            throw new KissParserException("Number Of states already specified!");
        }

        this.numberOfStates = numberOfStates;
    }

    @Override
    public void setInitialState(String initialStateIdentifier) throws KissParserException {
        State initialState = stateFactory.get(initialStateIdentifier);
        fsm.setInitialState(initialState);
    }

    @Override
    public void finishHeader() throws KissParserException { // TODO: in bestehende exceptions auslagern, auch oben bei set MEthoden
        // check

        if(inputSize == -1){
            throw new KissParserException("Input size not specified in header!");
        }

        if(outputSize == -1){
            throw new KissParserException("Output size not specified in header!");
        }

        if(numberOfTerms == -1){
            throw new KissParserException("NumberOfTerms not specified in header!");
        }

        if(numberOfStates == -1){
            throw new KissParserException("Number of states not specified in header!");
        }
    }

    @Override
    public void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField) throws KissParserException { // TODO: keine Parser Exception, sondern Builder
        if(inputField.width() != inputSize){
            throw new KissParserException("Input size not matching!");
        }

        if(outputField.width() != outputSize){
            throw new KissParserException("OutputSize not mathcing!");
        }


        State inputState = stateFactory.get(inputStateIdentifier);
        fsm.addState(inputState);

        State nextState = stateFactory.get(nextStateIdentifier);
        fsm.addState(nextState);

        inputState.setTransition(inputField, nextState, outputField);

        numberOfTermsCounter++;
    }

    @Override
    public void finishFSM() throws KissParserException {

        // check state factory size
        if(stateFactory.size() != numberOfStates){
            throw new KissParserException("Number of States missmatch!");
        }

        // check numberOfTerms
        if(numberOfTerms != numberOfTermsCounter){
            throw new KissParserException("Number of Terms Missmatch!");
        }

        buildFinished = true;
    }

    public Fsm getFsm() throws KissParserException {
        if(!buildFinished){
            throw new KissParserException("Build not finished!");
        }
        return fsm;
    }
}
