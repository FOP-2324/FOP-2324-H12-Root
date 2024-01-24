package h12.template.fsm;

import java.util.HashMap;

/**
 * Class used to create  {@link State}s
 */
public class StateFactory {

    private final HashMap<String, State> stateMap = new HashMap<>();

    /**
     * Get state for identifier
     * @param identifier a name of state
     * @return corresponding {@link State}
     */
    public State get(String identifier) {
        return stateMap.computeIfAbsent(identifier, State::new);
    }

    /**
     *
     * @return the number of {@link State}s used
     */
    public int getNumberOfStates() {
        return stateMap.size();
    }
}
