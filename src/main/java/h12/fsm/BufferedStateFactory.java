package h12.fsm;

import java.util.HashMap;
import java.util.Map;

public class BufferedStateFactory implements StateFactory{

    Map<String, State> stateMap = new HashMap<>();

    public void clear(){
        stateMap.clear();
    }

    @Override
    public State create(String name) {
        State state = stateMap.get(name);

        if(state == null){
            state = new StateImpl(name);
        }

        return state;
    }
}
