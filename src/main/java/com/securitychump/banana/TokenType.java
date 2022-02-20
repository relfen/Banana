package com.securitychump.banana;

public enum TokenType {
    // Generic
    IDENTIFIER,
    NUMBER,
    OPERATOR,
    EOF,
    UNKNOWN,

    // Researched keywords/Identifiers
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    FOR("for"),
    CLASS("class"),
    PUBLIC("public"),
    PRIVATE("private"),
    FUNC("func"),
    RETURN("return"),
    BREAK("break"),
    NEXT("next"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    THIS("this"),

    // Delimiters
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    COMMA(","),
    SEMICOLON(";"),
    DOT("."),

    // Operators
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
