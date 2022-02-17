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
    EOT(true),

    // Operators
    PLUS(true),
    INCREMENT(true),
    DECREMENT(true),
    MINUS(true),
    EQ(true),
    EQEQ(true),
    PLUSEQ(true),
    MINUSEQ(true),
    GT(true),
    GTE(true),
    LT(true),
    LTE(true),
    NOT(true),
    NOTEQ(true),
    OR(true),
    BINOR(true),
    AND(true),
    BINAND(true),
    MUL(true),
    MULEQ(true),
    DIV(true),
    DIVEQ(true),
    MOD(true),
    POW(true),
    MODEQ(true);

    // Indicate whether a state is accepting, which means it can be the final state, creating the lexeme/token.
    private final boolean accepting;
    State(boolean accepting){
        this.accepting = accepting;
    }

    public boolean isAccepting(){
        return this.accepting;
    }
}
