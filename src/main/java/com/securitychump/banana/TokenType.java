package com.securitychump.banana;

public enum TokenType {
    ID,
    NUMBER,
    OPERATOR,
    EOF,
    BADTOKEN,
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    COMMA(","),
    SEMICOLON(";"),
    DOT("."),
    NOT("!"),
    NOTEQ("!="),
    EQ("="),
    EQEQ("=="),
    GT(">"),
    GTE(">="),
    RSHIFT(">>"),
    LT("<"),
    LTE("<="),
    LSHIFT("<<"),
    PLUS("+"),
    PLUSEQ("+="),
    INCREMENT("++"),
    MINUS("-"),
    MINUSEQ("-="),
    DECREMENT("--"),
    MOD("%"),
    MODEQ("%="),
    DIV("/"),
    DIVEQ("/="),
    MULT("*"),
    MULTEQ("*="),
    POW("**");

    private final String value;
    TokenType(){
        this("");
    };

    TokenType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
