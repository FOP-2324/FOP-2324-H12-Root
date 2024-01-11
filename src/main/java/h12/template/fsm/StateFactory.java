package h12.template.fsm;

import java.util.HashMap;

public class StateFactory {

    private final HashMap<String, State> stateMap = new HashMap<>();

    public State get(String identifier) {
        return stateMap.computeIfAbsent(identifier, State::new);
    }

    public int getNumberOfStates() {
        return stateMap.size();
    }
}
