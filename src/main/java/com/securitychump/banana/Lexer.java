package com.securitychump.banana;

import com.securitychump.banana.fsm.Machine;
import com.securitychump.banana.fsm.State;
import com.securitychump.banana.fsm.Transition;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position = 0;
    private int line = 0;
    private int column = 0;
    private final static String digit = "0123456789";
    private final static String plus = "+";
    private final static String minus = "-";
    private final static String sign = plus + minus;
    private final static String period = ".";
    private final static String comma = ",";
    private final static String exp = "eE";

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();
        Token token = nextToken();

        // Ignoring null tokens here, so we can continue parsing the file after we hit any errors.
        while(true){
            if(token == null) {
                token = nextToken();
            } else if(token.getType() != TokenType.EOF) {
                tokens.add(token);
                token = nextToken();
            } else {
                break;
            }
        }

        return tokens;
    }

    public Token nextToken(){
        if(position >= input.length()){
            return new Token(TokenType.EOF, "", line, column);
        }

        try {
            ignoreWhitespace();
            char c = input.charAt(position);

            // Identifiers are currently only allowed to start with a character [a-zA-Z]
            if (Character.isAlphabetic(c)) {
                return matchIdentifier();
            }

            if (c == '(' || c == ')') {
                return matchParenthesis();
            }

            if (Character.isDigit(c)) {
                return matchNumber();
            }

            // undefined character classification
            // Throw an error, since no grammar rules match
            throw new Error("Undefined character classification for " + c + " at line [" + line + "] and column [" + column + "].");
        }
        catch(Error e) {
            System.out.println(e.getLocalizedMessage());
        }

        return null;
    }

    private Token matchNumber() {
        int position = this.position;
        StringBuilder num = new StringBuilder();
        List<Transition> transitions = buildNumberTransitions();
        State next = null;
        State previous = null;
        Character c = ' ';

        Machine machine = new Machine(State.INITIAL, transitions);
        while(position < input.length()){
            c = input.charAt(position);
            next = machine.getNextState(c);

            // Valid numeric found. Continue building out the token value
            if(next != null) {
                num.append(c);
                position++;
                previous = next;
            } else {
                break;
            }
        }

        this.position += num.length();
        this.column += num.length();

        // We either hit the end of file, or end of token.
        //TODO: Modify check to support lists or parenthesis. The terminator could be a comma.
        if(position == input.length() || Character.isWhitespace(input.charAt(position))){
            if(previous != null && previous.isAccepting()) {
                return new Token(TokenType.NUMBER, num.toString(), this.line, this.column);
            }
        } else {
            // We hit an invalid character. Update positions for error reporting, below.
            this.position++;
            this.column++;
        }

        // Did not full match the number
        throw new Error("Tokenizing Error: '" + c + "' is not a number.  At line [" + this.line + "] and column [" + this.column  + "].");
    }

    private List<Transition> buildNumberTransitions() {
        List<Transition> transitions = new ArrayList<Transition>();

        // Rules for numbers:
        // Starts with digit [0-9]
        //TODO: Add support for negative numbers
        transitions.add(new Transition(State.INITIAL, digit, State.INTEGER));

        // Still an INT
        transitions.add(new Transition(State.INTEGER, digit, State.INTEGER));

        // Decimal point found
        transitions.add(new Transition(State.INTEGER, period, State.MANTISSA));
        transitions.add(new Transition(State.MANTISSA, digit, State.FRACTIONALNUMBER));
        transitions.add(new Transition(State.FRACTIONALNUMBER, digit, State.FRACTIONALNUMBER));
        transitions.add(new Transition(State.FRACTIONALNUMBER, exp, State.NUMBERWITHPARTIALEXPONENT));

        // Exponent found
        transitions.add(new Transition(State.INTEGER, exp, State.NUMBERWITHPARTIALEXPONENT));
        transitions.add(new Transition(State.NUMBERWITHPARTIALEXPONENT, digit, State.NUMBERWITHEXPONENT));
        transitions.add(new Transition(State.NUMBERWITHPARTIALEXPONENT, sign, State.NUMBERWITHPARTIALSIGNEDEXPONENT));
        transitions.add(new Transition(State.NUMBERWITHPARTIALSIGNEDEXPONENT, digit, State.NUMBERWITHEXPONENT));
        transitions.add(new Transition(State.NUMBERWITHEXPONENT, digit, State.NUMBERWITHEXPONENT));

        return transitions;
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

        column++;
        return new Token(tt, String.valueOf(c), line, column);
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
