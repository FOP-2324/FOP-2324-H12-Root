package h12.template.fsm;

import h12.template.errors.BadBitfieldException;
import h12.parse.Token;

import java.util.Arrays;

/**
 * Class representing a Bit Vector
 */
public class BitField {

    /**
     * Enum representing the state of a single literal
     */
    public enum BitValue{
        DIRECT('1'), INDIRECT('0'), DC('-');

        private final char symbol;
        BitValue(char symbol){
            this.symbol = symbol;
        }

        public char getSymbol(){
            return symbol;
        }

    }

    private final BitValue[] field;

    /**
     * Creates a new fixed-length {@link BitField}
     * @param width the target length
     * @param value {@link BitValue} which gets replicated width times
     */
    public BitField(int width, BitValue value){
        field = new BitValue[width];
        for(int i = 0; i < width; i++){
            field[i] = value;
        }
    }

    /**
     * Create a {@link BitField} from a {@link String} representation
     * @param string the input
     * @throws BadBitfieldException Thrown if it can not be parsed
     */
    public BitField(String string) throws BadBitfieldException {

         this.field = new BitValue[string.length()];
         for(int i = 0; i < string.length(); i++){
             if(string.charAt(i) == '1'){
                 this.field[i] = BitValue.DIRECT;
             }else if(string.charAt(i) == '0'){
                 this.field[i] = BitValue.INDIRECT;
             } else if (string.charAt(i) == '-') {
                 this.field[i] = BitValue.DC;
             }else{
                 throw new BadBitfieldException(new Token(string));
             }
         }
    }

    /**
     *
     * @return the width of the vector
     */
    public int width(){
        return field.length;
    }

    /**
     * Check if current {@link BitField} is actiev for input
     * @param input The event
     * @return true, iff event is captured by this field
     */
    public boolean isActive(BitField input){
        if(input.field.length != field.length){
            throw new RuntimeException("Bad size of bitfield");
        }

        for(int i = 0; i < field.length; i++){
            if(field[i] != BitValue.DC && input.field[i] != BitValue.DC){
                // is relevant, so it has to be equal
                if(field[i] != input.field[i]){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Change a single value of Bitfield
     * @param index The index
     * @param value The value
     */
    public void setIndex(int index, BitValue value){
        field[index] = value;
    }

    /**
     * Or Combines two {@link BitField}s
     * @param other Other {@link BitField}
     * @return Or Combination
     */
    public BitField or(BitField other){
        if(field.length != other.field.length){
            throw new RuntimeException("Bad size of bitfield");
        }

        BitField output = new BitField(field.length, BitValue.DC);

        for(int i = 0; i < output.field.length; i++){
            if(field[i] == BitValue.DIRECT || other.field[i] == BitValue.DIRECT){
                output.field[i] = BitValue.DIRECT;
            }else if(field[i] == BitValue.INDIRECT && other.field[i] == BitValue.INDIRECT){
                output.field[i] = BitValue.INDIRECT;
            }
        }

        return output;
    }


    /**
     * Compute Intersection of two {@link BitField}s
     * @param other other {@link BitField}
     * @return the Intersection
     */
    public boolean intersect(BitField other){
        if(field.length != other.field.length){
            throw new RuntimeException("size missmatch");
        }

        for(int i = 0; i < field.length; i++){
            if(field[i] != BitValue.DC && other.field[i] != BitValue.DC){
                if(field[i] != other.field[i]){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return toString(BitValue.DC.getSymbol());
    }

    /**
     * Convert to {@link String} Representation
     * @param dcSymbol Char which is used for "Don't Care"
     * @return the generated {@link String}
     */
    public String toString(char dcSymbol) {
        StringBuilder stringBuilder = new StringBuilder();

        for (BitValue value : field) {
            stringBuilder.append(value == BitValue.DC ? dcSymbol : value.getSymbol());
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitField bitField = (BitField) o;
        return Arrays.equals(field, bitField.field);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }
}
