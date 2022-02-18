package com.securitychump.banana;

import java.util.Objects;

public class Token {
    private TokenType type;
    private String value;
    private int line;
    private int column;

    public Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString(){
        return "Token Type: " + type.toString() + " Value: " + value + " Line: " + line + " Column: " + column;
    }

    @Override
    public boolean equals(Object object){
        if(object != null && object instanceof Token){
            Token t = (Token)object;
            if(t.getTokenType() == this.type &&
                    t.getColumn() == this.column &&
                    t.getLine() == this.line &&
                    (t.getValue() != null && t.getValue().equals(this.value))){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, line, column);
    }

//==== Getters/Setters ====//

    public void setTokenType(TokenType type) {
        this.type = type;
    }

    public TokenType getTokenType() {
        return this.type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
