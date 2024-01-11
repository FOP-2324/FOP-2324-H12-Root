package h12.parse;

import h12.template.errors.KissParserException;
import h12.template.fsm.*;

public class FsmBuilderImpl implements FsmBuilder{

    private int headerInputSize = -1;
    private int headerOutputSize = -1;
    private int headerNumberOfTerms = -1;
    private int headerNumberOfStates = -1;
    private int numberOfTermsCounter = 0;

    private final StateFactory stateFactory = new StateFactory();
    private final Fsm fsm = new Fsm();

    private boolean buildFinished = false;

    public FsmBuilderImpl(){
    }

    @Override
    public void setInputSize(int inputSize) throws KissParserException {
        if(this.headerInputSize != -1){
            throw new KissParserException("Input size already specified!");
        }

        this.headerInputSize = inputSize;
    }

    @Override
    public void setOutputSize(int outputSize) throws KissParserException {
        if(this.headerOutputSize != -1){
            throw new KissParserException("Input size already specified!");
        }

        this.headerOutputSize = outputSize;
    }

    @Override
    public void setNumberOfTerms(int numberOfTerms) throws KissParserException {
        if(this.headerNumberOfTerms != -1){
            throw new KissParserException("Number of Terms already specified!");
        }

        this.headerNumberOfTerms = numberOfTerms;
    }

    @Override
    public void setNumberOfStates(int numberOfStates) throws KissParserException {
        if(this.headerNumberOfStates != -1){
            throw new KissParserException("Number Of states already specified!");
        }

        this.headerNumberOfStates = numberOfStates;
    }

    @Override
    public void setInitialState(String initialStateIdentifier) throws KissParserException {
        State initialState = stateFactory.get(initialStateIdentifier);
        fsm.setInitialState(initialState);
    }

    @Override
    public void finishHeader() throws KissParserException { // TODO: in bestehende exceptions auslagern, auch oben bei set MEthoden
        // check

        if(headerInputSize == -1){
            throw new KissParserException("Input size not specified in header!");
        }

        if(headerOutputSize == -1){
            throw new KissParserException("Output size not specified in header!");
        }

        if(headerNumberOfTerms == -1){
            throw new KissParserException("NumberOfTerms not specified in header!");
        }

        if(headerNumberOfStates == -1){
            throw new KissParserException("Number of states not specified in header!");
        }
    }

    @Override
    public void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField) throws KissParserException { // TODO: keine Parser Exception, sondern Builder
        if(inputField.width() != headerInputSize){
            throw new KissParserException("Input size not matching!");
        }

        if(outputField.width() != headerOutputSize){
            throw new KissParserException("OutputSize not mathcing!");
        }


        State inputState = stateFactory.get(inputStateIdentifier);
        fsm.addState(inputState);

        State nextState = stateFactory.get(nextStateIdentifier);
        fsm.addState(nextState);

        inputState.setTransition(new Transition(inputField, nextState, outputField));

        numberOfTermsCounter++;
    }

    @Override
    public void finishFSM() throws KissParserException {

        // check state factory size
        if(stateFactory.getNumberOfStates() != headerNumberOfStates){
            throw new KissParserException("Number of States missmatch!");
        }

        // check numberOfTerms
        if(headerNumberOfTerms != numberOfTermsCounter){
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
