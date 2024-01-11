package h12.parser;

import h12.template.errors.BadIdentifierException;
import h12.template.errors.BadNumberException;
import h12.template.errors.BadTokenException;
import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;

import java.io.IOException;

public class FsmParser {

    private final Scanner scanner;
    private final FsmBuilder builder;

    private Token currentToken;

    public FsmParser(Scanner scanner, FsmBuilder builder) throws IOException {
        this.scanner = scanner;
        this.builder = builder;

        currentToken = scanner.scan();
    }

    private Token consumeToken() throws IOException {
        Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    private Token consumeAndCheckToken(Token.Type type) throws IOException, KissParserException {
        if(!currentToken.is(type)){
            throw new BadTokenException(currentToken, type);
        }

        Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    public void parse() throws KissParserException, IOException {
        parseFSM();
    }

    private void parseFSM() throws IOException, KissParserException {
        parseHeader();
        parseTerms();
        builder.finishFSM();
    }

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

    private void parseInputWidth() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_INPUT_WIDTH);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setInputSize(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    private void parseOutputWidth() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_OUTPUT_WIDTH);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setOutputSize(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    private void parseNumberOfTerms() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_NUMBER_OF_TERMS);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setNumberOfTerms(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    private void parseNumberOfStates() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_NUMBER_OF_STATES);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setNumberOfStates(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }

    private void parseInitialState() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_INITIAL_STATE);

        if(currentToken.is(Token.Type.IDENTIFIER_STATE)){
            builder.setInitialState(currentToken.getValue());
            consumeToken();
        }else{
            throw new BadIdentifierException(currentToken);
        }
    }

    private void parseTerms() throws KissParserException, IOException {
        while(scanner.hasNext()){
            parseTerm();
        }
    }

    private void parseTerm() throws KissParserException, IOException {
        Token inputBitField = consumeAndCheckToken(Token.Type.BITFIELD);
        Token inputStateIdentifier = consumeAndCheckToken(Token.Type.IDENTIFIER_STATE);
        Token nextStateIdentifier = consumeAndCheckToken(Token.Type.IDENTIFIER_STATE);
        Token outputBitField = consumeAndCheckToken(Token.Type.BITFIELD);

        builder.addTerm(new BitField(inputBitField.getValue()), inputStateIdentifier.getValue(), nextStateIdentifier.getValue(), new BitField(outputBitField.getValue()));
    }

}
