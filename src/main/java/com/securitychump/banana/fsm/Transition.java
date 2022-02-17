package com.securitychump.banana.fsm;

public class Transition {
    private State from;
    private String validInput;
    private State to;

    public Transition(State from, String validInput, State to) {
        this.from = from;
        this.validInput = validInput;
        this.to = to;
    }


    //==== Getters/Setters ====//

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public String getValidInput() {
        return validInput;
    }

    public void setValidInput(String validInput) {
        this.validInput = validInput;
    }

    public State getTo() {
        return to;
    }

    public void setTo(State to) {
        this.to = to;
    }
}
