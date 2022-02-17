package com.securitychump.banana.fsm;

import java.util.List;

public class Machine {
    private State current;
    private List<Transition> transitions;

    public Machine(State initial, List<Transition> transitions){
        this.current = initial;
        this.transitions = transitions;
    }

    public State getNextState(Character input){
        for(Transition t: transitions){
            if(t.getFrom() == current && t.getValidInput().contains(input.toString())){
                current = t.getTo();
                return current;
            }
        }

        return null;
    }

    //==== Getters/Setters ====//

}
