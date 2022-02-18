package com.securitychump.banana;

import com.securitychump.banana.fsm.Machine;
import com.securitychump.banana.fsm.State;
import com.securitychump.banana.fsm.Transition;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position = 0;
    private int line = 1;
    private int column = 0;
    private final static String digit = "0123456789";
    private final static String plus = "+";
    private final static String minus = "-";
    private final static String sign = plus + minus;
    private final static String period = ".";
    private final static String comma = ",";
    private final static String exp = "eE";
    private final static String eq = "=";
    private final static String gt = ">";
    private final static String lt = "<";
    private final static String not = "!";
    private final static String pipe = "|";
    private final static String amp = "&";
    private final static String asterisk = "*";
    private final static String slash = "/";
    private final static String mod = "%";

    private final static String operators = sign + eq + gt + lt + not + pipe + amp + asterisk + slash + mod;

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
        try {
            ignoreWhitespace();

            if(position >= input.length()){
                return new Token(TokenType.EOF, "", line, column);
            }

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

            if(operators.contains(String.valueOf(c))){
                return matchOperator();
            }

            // undefined character classification
            // Throw an error, since no grammar rules match
            position++;
            column++;
            throw new Error("Undefined character classification for " + c + " at line [" + line + "] and column [" + column + "].");
        }
        catch(Error e) {
            System.out.println(e.getLocalizedMessage());
        }

        return null;
    }

    private Token matchOperator() {
        List<Transition> transitions = buildOperatorTransitions();
        Token token = matchToken(TokenType.OPERATOR, transitions);

        if(token == null){
            // Get the offending character
            Character c = this.input.charAt(this.column-1);

            // Did not fully match the number
            throw new Error("Tokenizing Error: '" + c + "' is not valid for this operator.  At line [" + this.line + "] and column [" + this.column  + "].");
        }

        return token;
    }

    private List<Transition> buildOperatorTransitions() {
        List<Transition> transitions = new ArrayList<>();

        // Rules for operators:
        // Each operator from operators string need to be represented in first stage
        transitions.add(new Transition(State.INITIAL, eq, State.EQ));        // =
        transitions.add(new Transition(State.INITIAL, not, State.NOT));      // !
        transitions.add(new Transition(State.INITIAL, lt, State.LT));        // <
        transitions.add(new Transition(State.INITIAL, gt, State.GT));        // >
        transitions.add(new Transition(State.INITIAL, amp, State.BINAND));   // &
        transitions.add(new Transition(State.INITIAL, pipe, State.BINOR));   // |
        transitions.add(new Transition(State.INITIAL, plus, State.PLUS));    // +
        transitions.add(new Transition(State.INITIAL, minus, State.MINUS));  // -
        transitions.add(new Transition(State.INITIAL, asterisk, State.MUL)); // *
        transitions.add(new Transition(State.INITIAL, slash, State.DIV));    // /
        transitions.add(new Transition(State.INITIAL, mod, State.MOD));      // %


        // Second stage operators
        transitions.add(new Transition(State.EQ, eq, State.EQEQ));            // ==
        transitions.add(new Transition(State.NOT, eq, State.NOTEQ));          // !=
        transitions.add(new Transition(State.LT, eq, State.LTE));             // <=
        transitions.add(new Transition(State.GT, eq, State.GTE));             // >=
        transitions.add(new Transition(State.BINAND, amp, State.AND));        // &&
        transitions.add(new Transition(State.BINOR, pipe, State.OR));         // ||
        transitions.add(new Transition(State.PLUS, plus, State.INCREMENT));   // ++
        transitions.add(new Transition(State.MINUS, minus, State.DECREMENT)); // --
        transitions.add(new Transition(State.PLUS, eq, State.PLUSEQ));        // +=
        transitions.add(new Transition(State.MINUS, eq, State.MINUSEQ));      // -=
        transitions.add(new Transition(State.MOD, eq, State.MODEQ));          // %=
        transitions.add(new Transition(State.MUL, eq, State.MULEQ));          // *=
        transitions.add(new Transition(State.MUL, asterisk, State.POW));      // **
        transitions.add(new Transition(State.DIV, eq, State.DIVEQ));          // /=

        return transitions;
    }

    private Token matchNumber() {
        List<Transition> transitions = buildNumberTransitions();
        Token token = matchToken(TokenType.NUMBER, transitions);

        if(token == null){
            // Get the offending character
            Character c = this.input.charAt(this.column-1);

            // Did not fully match the number
            throw new Error("Tokenizing Error: '" + c + "' is not a number.  At line [" + this.line + "] and column [" + this.column  + "].");
        }

        return token;
    }

    private List<Transition> buildNumberTransitions() {
        List<Transition> transitions = new ArrayList<>();

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

    private Token matchToken(TokenType type, List<Transition> transitions){
        int position = this.position;
        StringBuilder token = new StringBuilder();
        State next = null;
        State previous = null;
        Character c = ' ';

        Machine machine = new Machine(State.INITIAL, transitions);
        while(position < input.length()){
            c = input.charAt(position);
            next = machine.getNextState(c);

            // Valid numeric found. Continue building out the token value
            if(next != null) {
                token.append(c);
                position++;
                previous = next;
            } else {
                break;
            }
        }

        this.position += token.length();
        this.column += token.length();

        // We either hit the end of file, or end of token.
        //TODO: Modify check to support lists or parenthesis. The terminator could be a comma.
        if(position == input.length() || Character.isWhitespace(input.charAt(position))){
            if(previous != null && previous.isAccepting()) {
                return new Token(type, token.toString(), this.line, this.column);
            }
        } else {
            // We hit an invalid character. Update positions for error reporting, below.
            this.position++;
            this.column++;
        }

        return null;
    }

    private void ignoreWhitespace() {
        while(position < input.length() && Character.isWhitespace(input.charAt(position))){
            // Should be good enough for major OS line endings. Nothing we currently care about is using \r, by itself.
            if(input.charAt(position) == '\n'){
                line++;
                column = 0;
            } else {
                column++;
            }

            position++;
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
