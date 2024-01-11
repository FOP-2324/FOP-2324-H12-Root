package h12.fsm;

public class BitField {

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

    public BitField(int width, BitValue value){
        field = new BitValue[width];
        for(int i = 0; i < width; i++){
            field[i] = value;
        }
    }

    public BitField(String string) throws BadBitFieldException {

         this.field = new BitValue[string.length()];
         for(int i = 0; i < string.length(); i++){
             if(string.charAt(i) == '1'){
                 this.field[i] = BitValue.DIRECT;
             }else if(string.charAt(i) == '0'){
                 this.field[i] = BitValue.INDIRECT;
             } else if (string.charAt(i) == '-') {
                 this.field[i] = BitValue.DC;
             }else{
                 throw new BadBitFieldException();
             }
         }
    }

    public int width(){
        return field.length;
    }

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

    public void setIndex(int index, BitValue value){
        field[index] = value;
    }

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

    public String toString(char dcSymbol) {
        StringBuilder stringBuilder = new StringBuilder();

        for (BitValue value : field) {
            stringBuilder.append(value == BitValue.DC ? dcSymbol : value.getSymbol());
        }

        return stringBuilder.toString();
    }
}
