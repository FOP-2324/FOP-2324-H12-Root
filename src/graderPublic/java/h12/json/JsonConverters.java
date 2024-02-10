package h12.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import h12.template.errors.BadBitfieldException;
import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.State;
import h12.template.fsm.Transition;
import org.apache.commons.lang3.stream.Streams;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

public class JsonConverters extends org.tudalgo.algoutils.tutor.general.json.JsonConverters {

    public static BitField toBitField(JsonNode node) {
        if (!node.isTextual()) {
            throw new IllegalArgumentException("Expected node %s to be textual".formatted(node));
        }
        String text = node.asText();
        try {
            return new BitField(text);
        } catch (BadBitfieldException e) {
            return Assertions2.fail(e, emptyContext(), r -> "Could not convert %s to bit field".formatted(node));
        }
    }

    public static Collection<State> toStates(ArrayNode node) {
        Map<String, State> states = new LinkedHashMap<>();
        for (JsonNode state : node) {
            String sourceName = state.get("name").asText();
            State sourceState = states.computeIfAbsent(sourceName, State::new);
            ArrayNode transitions = (ArrayNode) state.get("transitions");
            List<Transition> transitionList = new ArrayList<>();
            for (JsonNode transition : transitions) {
                BitField event = toBitField(transition.get("event"));
                String targetName = transition.get("nextState").asText();
                State targetState = states.computeIfAbsent(targetName, State::new);
                BitField output = toBitField(transition.get("output"));
                transitionList.add(new Transition(event, targetState, output));
            }
            transitionList.sort(Comparator.comparing((Transition t) -> t.getEvent().toString()).thenComparing((Transition t) -> t.getOutput().toString()));
            transitionList.forEach(sourceState::setTransition);
        }
        return states.values();
    }


    public static Fsm toFsm(JsonNode node) {
        Fsm fsm = new Fsm();
        if (!node.has("initialState")) {
            throw new IllegalArgumentException("Node %s does not contain a field called 'initialState".formatted(node));
        }
        String initialStateName = node.get("initialState").asText();
        if (!node.has("states") || !(node.get("states") instanceof ArrayNode states)) {
            throw new IllegalArgumentException("Node %s does not contain an array field called 'states".formatted(node));
        }
        toStates(states).forEach(fsm::addState);
        State initialState = Streams.of(fsm).filter(s -> s.getName().equals(initialStateName)).findFirst().orElseThrow();
        fsm.setInitialState(initialState);
        return fsm;
    }
}
