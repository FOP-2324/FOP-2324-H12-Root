package h12.template.fsm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of {@link StateEncoding} for One-Hot
 */
public class OneHotEncoding implements StateEncoding {

    private int numberOfStates = -1;
    private int stateCounter = 0;

    private final Map<State, BitField> encoding = new HashMap<>();

    @Override
    public void init(int numberOfStates) {
        if(this.numberOfStates != -1){
            throw new RuntimeException("already inited!");
        }

        this.numberOfStates = numberOfStates;
    }

    @Override
    public BitField encode(State state) {
        if(numberOfStates == -1){
            throw new RuntimeException("not inited");
        }

        return encoding.computeIfAbsent(state, new Function<State, BitField>() {
            @Override
            public BitField apply(State state) {
                if(stateCounter >= numberOfStates){
                    throw new RuntimeException("To much States to encode!");
                }

                BitField field = new BitField(numberOfStates, BitField.BitValue.INDIRECT);
                field.setIndex(numberOfStates - 1 - stateCounter, BitField.BitValue.DIRECT);
                stateCounter++;
                return field;
            }
        });
    }

    @Override
    public int getWidth() {
        if(numberOfStates == -1){
            throw new RuntimeException("not inited");
        }
        return numberOfStates;
    }
}
