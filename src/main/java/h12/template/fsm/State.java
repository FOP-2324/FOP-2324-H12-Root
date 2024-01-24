package h12.template.fsm;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;


/**
 * Representation of a State of a Automata
 */
public class State implements Iterable<Transition>{


    private final Set<Transition> transitions = new HashSet<>();
    private final String name;

    /**
     * Create a new State
     * @param name desired unique identifier
     */
    public State(String name){
        this.name = name;
    }

    /**
     * Add a transition to state
     * @param transition target transition
     */
    public void setTransition(Transition transition) {
        // check for each existing transition, that multiple event have same nextState
        for(Transition existingTransition : transitions){
            if(existingTransition.getEvent().intersect(transition.getEvent())){
                if(existingTransition.getNextState() != transition.getNextState()){
                    throw new RuntimeException("Next state not equal");
                }
            }
        }

        transitions.add(transition);
    }

    /**
     * Get the next state for specific input
     * @param event specific input symbol
     * @return the next state
     */
    public State getNextState(BitField event) {
        // first matching determines state -> because overlap is checked at setTransition

        State nextState = this;

        for(Transition transition : transitions){
            if(transition.getEvent().isActive(event)){
                return transition.getNextState();
            }
        }

        return this;
    }

    /**
     *
     * @return the identifier of state
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "State{" + name + '}';
    }

    @NotNull
    @Override
    public Iterator<Transition> iterator() {
        return transitions.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State that = (State) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
