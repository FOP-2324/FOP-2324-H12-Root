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
            throw new ParameterAlreadySpecifiedException(HeaderParameter.INPUT_SIZE);
        }

        this.headerInputSize = inputSize;
    }

    @Override
    public void setOutputSize(int outputSize) throws KissParserException {
        if(this.headerOutputSize != -1){
            throw new ParameterAlreadySpecifiedException(HeaderParameter.OUTPUT_SIZE);
        }

        this.headerOutputSize = outputSize;
    }

    @Override
    public void setNumberOfTerms(int numberOfTerms) throws KissParserException {
        if(this.headerNumberOfTerms != -1){
            throw new ParameterAlreadySpecifiedException(HeaderParameter.NUMBER_OF_TERMS);
        }

        this.headerNumberOfTerms = numberOfTerms;
    }

    @Override
    public void setNumberOfStates(int numberOfStates) throws KissParserException {
        if(this.headerNumberOfStates != -1){
            throw new ParameterAlreadySpecifiedException(HeaderParameter.NUMBER_OF_STATES);
        }

        this.headerNumberOfStates = numberOfStates;
    }

    @Override
    public void setInitialState(String initialStateIdentifier) throws KissParserException {
        if(fsm.getInitialState() != null){
            throw new ParameterAlreadySpecifiedException(HeaderParameter.INITIAL_STATE);
        }

        State initialState = stateFactory.get(initialStateIdentifier);
        fsm.setInitialState(initialState);
    }

    @Override
    public void finishHeader() throws KissParserException {
        // check

        if(headerInputSize == -1){
            throw new ParameterNotSpecifiedException(HeaderParameter.INPUT_SIZE);
        }

        if(headerOutputSize == -1){
            throw new ParameterNotSpecifiedException(HeaderParameter.OUTPUT_SIZE);
        }

        if(headerNumberOfTerms == -1){
            throw new ParameterNotSpecifiedException(HeaderParameter.NUMBER_OF_TERMS);
        }

        if(headerNumberOfStates == -1){
            throw new ParameterNotSpecifiedException(HeaderParameter.NUMBER_OF_STATES);
        }
    }

    @Override
    public void addTerm(BitField inputField, String inputStateIdentifier, String nextStateIdentifier, BitField outputField) throws KissParserException {
        if(inputField.width() != headerInputSize){
            throw new SizeMismatchException(HeaderParameter.INPUT_SIZE);
        }

        if(outputField.width() != headerOutputSize){
            throw new SizeMismatchException(HeaderParameter.OUTPUT_SIZE);
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
            throw new SizeMismatchException(HeaderParameter.NUMBER_OF_STATES);
        }

        // check numberOfTerms
        if(headerNumberOfTerms != numberOfTermsCounter){
            throw new SizeMismatchException(HeaderParameter.NUMBER_OF_TERMS);
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
