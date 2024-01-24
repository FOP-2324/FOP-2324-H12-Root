package h12.parse;

import h12.template.errors.BadIdentifierException;
import h12.template.errors.BadNumberException;
import h12.template.errors.BadTokenException;
import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;

import java.io.IOException;

/**
 * Class implementing a parser for the kiss2 file
 */
public class FsmParser {

    private final Scanner scanner;
    private final FsmBuilder builder;

    private Token currentToken;

    /**
     * Constructs a new {@link FsmParser}
     * @param scanner A scanner used as {@link Token} stream
     * @param builder The {@link FsmBuilder} used to build the {@link h12.template.fsm.Fsm}
     * @throws IOException Can be thrown in case of File Problem
     */
    public FsmParser(Scanner scanner, FsmBuilder builder) throws IOException {
        this.scanner = scanner;
        this.builder = builder;

        currentToken = scanner.scan();
    }

    /**
     * Consumes the current Token
     * @return the current Token
     * @throws IOException Can be thrown in case of File Problem
     */
    private Token consumeToken() throws IOException {
        Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    /**
     * Consumes and checks the current Token
     * @param type the expected Type of the current Token
     * @return the current Token
     * @throws IOException Can be thrown in case of File Problem
     * @throws KissParserException If there is a Token missmatch
     */
    private Token consumeAndCheckToken(Token.Type type) throws IOException, KissParserException {
        if(!currentToken.is(type)){
            throw new BadTokenException(currentToken, type);
        }

        Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    /**
     * Parse the Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    public void parse() throws KissParserException, IOException {
        parseFSM();
    }

    /**
     * Parse the Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */

    private void parseFSM() throws IOException, KissParserException {
        parseHeader();
        parseTerms();
        builder.finishFSM();
    }

    /**
     * Parse the Header
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseHeader() throws IOException, KissParserException {
        while (true){
            if(currentToken.is(Token.Type.KEYWORD_INPUT_WIDTH)){
                parseInputWidth();
            } else if (currentToken.is(Token.Type.KEYWORD_OUTPUT_WIDTH)) {
                parseOutputWidth();
            } else if (currentToken.is(Token.Type.KEYWORD_NUMBER_OF_TERMS)) {
                parseNumberOfTerms();
            }else if (currentToken.is(Token.Type.KEYWORD_NUMBER_OF_STATES)){
                parseNumberOfStates();
            }else if (currentToken.is(Token.Type.KEYWORD_INITIAL_STATE)){
                parseInitialState();
            }else{
                builder.finishHeader();
                return;
            }
        }

    }

    /**
     * Parse the Input Width
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseInputWidth() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_INPUT_WIDTH);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setInputSize(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }


    /**
     * Parse the Output Width
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseOutputWidth() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_OUTPUT_WIDTH);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setOutputSize(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    /**
     * Parse the Number of Terms
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseNumberOfTerms() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_NUMBER_OF_TERMS);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setNumberOfTerms(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    /**
     * Parse the Number of States
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseNumberOfStates() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_NUMBER_OF_STATES);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setNumberOfStates(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    /**
     * Parse the Initial State of Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseInitialState() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_INITIAL_STATE);

        if(currentToken.is(Token.Type.IDENTIFIER_STATE)){
            builder.setInitialState(currentToken.getValue());
            consumeToken();
        }else{
            throw new BadIdentifierException(currentToken);
        }
    }

    /**
     * Parse all Terms
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseTerms() throws KissParserException, IOException {
        while(scanner.hasNext()){
            parseTerm();
        }
    }

    /**
     * Parse a single Term of Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseTerm() throws KissParserException, IOException {
        Token inputBitField = consumeAndCheckToken(Token.Type.BITFIELD);
        Token inputStateIdentifier = consumeAndCheckToken(Token.Type.IDENTIFIER_STATE);
        Token nextStateIdentifier = consumeAndCheckToken(Token.Type.IDENTIFIER_STATE);
        Token outputBitField = consumeAndCheckToken(Token.Type.BITFIELD);

        builder.addTerm(new BitField(inputBitField.getValue()), inputStateIdentifier.getValue(), nextStateIdentifier.getValue(), new BitField(outputBitField.getValue()));
    }

}
