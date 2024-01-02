package h12.fsm;

import java.util.HashMap;
import java.util.Map;

public class BufferedStateFactory implements StateFactory{

    Map<String, State> stateMap = new HashMap<>();

    public void clear(){
        stateMap.clear();
    }

    @Override
    public State get(String name) {
        return stateMap.computeIfAbsent(name, StateImpl::new);
    }

    @Override
    public int size() {
        return stateMap.size();
    }
}
