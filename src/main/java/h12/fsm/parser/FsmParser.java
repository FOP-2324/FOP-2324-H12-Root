package h12.fsm.parser;

import h12.errors.BadNumberException;
import h12.errors.KissParserException;
import h12.fsm.BitField;

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
            throw new KissParserException("Missmatch type"); // TODO: eigener type
        }

        Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    public void parseFSM() throws IOException, KissParserException {
        parseHeader();
        builder.finishHeader();
        parseTerms();
        builder.finishFSM();
    }

    private void parseHeader() throws IOException, BadNumberException {
        while (true){
            if(currentToken.is(Token.Type.KEYWORD_INPUT)){
                parseInputSize();
            } else if (currentToken.is(Token.Type.KEYWORD_OUTPUT)) {
                parseOutputSize();
            } else if (currentToken.is(Token.Type.KEYWORD_TERMS)) {
                parseNumberOfTerms();
            }else if (currentToken.is(Token.Type.KEYWORD_STATES)){
                parseNumberOfStates();
            }else{
                return;
            }
        }
    }

    public void parseInputSize() throws IOException, BadNumberException {
        consumeToken(); // TODO: ggf mit check?

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setInputSize(Integer.parseInt(currentToken.getValue()));
        }else{
            throw new BadNumberException("");
        }
    }

    public void parseOutputSize() throws IOException, BadNumberException {
        consumeToken(); // TODO: ggf mit check?

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setOutputSize(Integer.parseInt(currentToken.getValue()));
        }else{
            throw new BadNumberException("");
        }
    }

    public void parseNumberOfTerms() throws IOException, BadNumberException {
        consumeToken(); // TODO: ggf mit check?

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setNumberOfTerms(Integer.parseInt(currentToken.getValue()));
        }else{
            throw new BadNumberException("");
        }
    }

    public void parseNumberOfStates() throws IOException, BadNumberException {
        consumeToken(); // TODO: ggf mit check?

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setNumberOfStates(Integer.parseInt(currentToken.getValue()));
        }else{
            throw new BadNumberException("");
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
