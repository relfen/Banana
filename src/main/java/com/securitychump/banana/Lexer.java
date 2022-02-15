package com.securitychump.banana;

import java.util.ArrayList;

public class Lexer {
    private String input;
    private int pos = 0;
    private int line = 0;
    private int col = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public ArrayList<Token> tokenize(){
        ArrayList<Token> tokens = new ArrayList<>();
        Token token = nextToken();

        while(token.getType() == TokenType.EOF){
            tokens.add(token);
            token = nextToken();
        }

        return tokens;
    }

    public Token nextToken(){
        if(pos >= input.length()){
            //TODO: Determine line/column values for EOF marker
            return new Token(TokenType.EOF, "", -1, -1);
        }

        return null;
    }

    //==== Getters/Setters ====//

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
