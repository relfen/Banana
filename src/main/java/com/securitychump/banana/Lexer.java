package com.securitychump.banana;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position = 0;
    private int line = 0;
    private int column = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();
        Token token = nextToken();

        while(token.getType() != TokenType.EOF){
            tokens.add(token);
            token = nextToken();
        }

        return tokens;
    }

    public Token nextToken(){
        if(position >= input.length()){
            return new Token(TokenType.EOF, "", line, column);
        }

        ignoreWhitespace();
        char c = input.charAt(position);

        // Identifiers are currently only allowed to start with a character [a-zA-Z]
        if(Character.isAlphabetic(c)){
            return matchIdentifier();
        }

        if(c == '(' || c == ')'){
            return matchParenthesis();
        }

        // undefined character classification
        // Throw an error, since no grammar rules match
        throw new Error("Undefined character classification for " + c + " at line [" + line + "] and column [" + column + "].");
    }

    private void ignoreWhitespace() {
        while(position < input.length() && Character.isWhitespace(input.charAt(position))){
            position++;

            // Should be good enough for major OS line endings. Nothing we currently care about is using \r, by itself.
            if(input.charAt(position) == '\n'){
                line++;
                column = 0;
            } else {
                column++;
            }
        }
    }

    private Token matchIdentifier() {
        int position = this.position;
        int column = this.column;
        StringBuilder identifier = new StringBuilder();

        while(position < input.length()) {
            char c = input.charAt(position);

            // After the first character, Identifiers may contain: [a-zA-Z]|[0-9]|[_]
            if(!(Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')){
                break;
            }

            identifier.append(c);
            position++;
        }

        this.position += identifier.length();
        this.column += identifier.length();

        return new Token(TokenType.ID, identifier.toString(), this.line, column);
    }

    private Token matchParenthesis(){
        char c = input.charAt(position++);
        TokenType tt;

        if(c == '('){
            tt = TokenType.LEFTPAREN;
        } else {
            tt = TokenType.RIGHTPAREN;
        }

        return new Token(tt, String.valueOf(c), line, column++);
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
