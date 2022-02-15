package com.securitychump.banana;

import java.util.ArrayList;

public class Lexer {
    private String input;
    private int position = 0;
    private int line = 0;
    private int column = 0;

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
        if(position >= input.length()){
            //TODO: Determine line/column values for EOF marker
            return new Token(TokenType.EOF, "", -1, -1);
        }

        char c = input.charAt(position);

        // Identifiers are currently only allowed to start with a character [a-zA-Z]
        if(Character.isAlphabetic(c)){
            return matchIdentifier();
        }

        return null;
    }

    private Token matchIdentifier() {
        int pos = this.position;
        int col = this.column;
        StringBuilder identifier = new StringBuilder();

        while(pos < input.length()) {
            char c = input.charAt(pos);

            // After the first character, Identifiers may contain: [a-zA-Z]|[0-9]|[_]
            if(!(Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')){
                break;
            }

            identifier.append(c);
            pos++;
        }

        this.position += identifier.length();
        this.column += identifier.length();

        return new Token(TokenType.ID, identifier.toString(), this.line, col);
    }

    //==== Getters/Setters ====//

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
