package h12.template.fsm;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class State implements Iterable<Transition>{


    private final Set<Transition> transitions = new HashSet<>();
    private final String name;


    public State(String name){
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

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
