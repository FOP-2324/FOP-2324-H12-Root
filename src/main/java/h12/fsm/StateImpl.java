package h12.fsm;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateImpl implements State{


    private static class Transition{
        public BitField event;
        public State nextState;
        public BitField output;

        public Transition(BitField event, State nextState, BitField output){
            this.event = event;
            this.nextState = nextState;
            this.output = output;
        }
    }

    private final Set<Transition> transitions = new HashSet<>();
    private final String name;


    public StateImpl(String name){
        this.name = name;
    }

    @Override
    public void setTransition(BitField event, State nextState, BitField output) {
        // check for each existing transition, that multiple event have same nextState
        for(Transition existingTransition : transitions){
            if(existingTransition.event.intersect(event)){
                if(existingTransition.nextState != nextState){
                    throw new RuntimeException("Next state not equal");
                }
            }
        }

        transitions.add(new Transition(event, nextState, output));
    }

    @Override
    public State getNextState(BitField event) {
        // first matching determines state -> because overlap is checked at setTransition

        State nextState = this;

        for(Transition transition : transitions){
            if(transition.event.isActive(event)){
                return transition.nextState;
            }
        }

        return this;
    }

    @Override
    public void visit(FsmVisitor fsmVisitor) {
        fsmVisitor.visitState(this);

        for(Transition transition : transitions){
            fsmVisitor.visitTermOfState(transition.event, transition.nextState, transition.output);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "State{" + name + '}';
    }
}
