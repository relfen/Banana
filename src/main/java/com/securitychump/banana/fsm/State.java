package com.securitychump.banana.fsm;

public enum State {
    INITIAL(false),

    // Number
    INTEGER(true),
    NUMBERWITHPARTIALEXPONENT(false),
    FRACTIONALNUMBER(true),
    NUMBERWITHEXPONENT(true),
    NUMBERWITHPARTIALSIGNEDEXPONENT(false),
    MANTISSA(false),
    EOT(true);

    // Indicate whether a state is accepting, which means it can be the final state, creating the lexeme/token.
    private final boolean accepting;
    State(boolean accepting){
        this.accepting = accepting;
    }

    public boolean isAccepting(){
        return this.accepting;
    }
}
