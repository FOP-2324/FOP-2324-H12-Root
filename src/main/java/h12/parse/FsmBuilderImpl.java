package h12.parse;

import h12.template.errors.KissParserException;
import h12.template.errors.ParameterAlreadySpecifiedException;
import h12.template.errors.ParameterNotSpecifiedException;
import h12.template.errors.SizeMismatchException;
import h12.template.fsm.*;

/**
 * Implementation of {@link FsmBuilder}
 */
public class FsmBuilderImpl implements FsmBuilder{

    private int headerInputSize = -1;
    private int headerOutputSize = -1;
    private int headerNumberOfTerms = -1;
    private int headerNumberOfStates = -1;
    private int numberOfTermsCounter = 0;

    private final StateFactory stateFactory = new StateFactory();
    private final Fsm fsm = new Fsm();

    private boolean buildFinished = false;

    @Override
    public void setInputSize(int inputSize) throws KissParserException {
        if(this.headerInputSize != -1){
            throw new ParameterAlreadySpecifiedException("InputSize");
        }

        this.headerInputSize = inputSize;
    }

    @Override
    public void setOutputSize(int outputSize) throws KissParserException {
        if(this.headerOutputSize != -1){
            throw new ParameterAlreadySpecifiedException("Output Size!");
        }

        this.headerOutputSize = outputSize;
    }

    @Override
    public void setNumberOfTerms(int numberOfTerms) throws KissParserException {
        if(this.headerNumberOfTerms != -1){
            throw new ParameterAlreadySpecifiedException("Number of Terms!");
        }

        this.headerNumberOfTerms = numberOfTerms;
    }

    @Override
    public void setNumberOfStates(int numberOfStates) throws KissParserException {
        if(this.headerNumberOfStates != -1){
            throw new ParameterAlreadySpecifiedException("Number Of states!");
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
            throw new ParameterNotSpecifiedException("Input Size");
        }

        if(headerOutputSize == -1){
            throw new ParameterNotSpecifiedException("Output Size");
        }

        if(headerNumberOfTerms == -1){
            throw new ParameterNotSpecifiedException("number of Terms");
        }

        if(headerNumberOfStates == -1){
            throw new ParameterNotSpecifiedException("number of States");
        }
    }

    @Override
    public void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField) throws KissParserException { // TODO: keine Parser Exception, sondern Builder
        if(inputField.width() != headerInputSize){
            throw new SizeMismatchException("Input Size");
        }

        if(outputField.width() != headerOutputSize){
            throw new SizeMismatchException("Output size");
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
            throw new SizeMismatchException("Number of states");
        }

        // check numberOfTerms
        if(headerNumberOfTerms != numberOfTermsCounter){
            throw new SizeMismatchException("Number of Terms");
        }

        buildFinished = true;
    }

    /**
     *
     * @return the created {@link Fsm}
     * @throws KissParserException
     */
    public Fsm getFsm() throws KissParserException {
        if(!buildFinished){
            throw new KissParserException("Build not finished!");
        }
        return fsm;
    }
}
