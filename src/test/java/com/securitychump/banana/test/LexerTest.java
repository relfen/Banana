package com.securitychump.banana.test;

import static com.securitychump.banana.TokenType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.securitychump.banana.Lexer;
import com.securitychump.banana.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexerTest {

    @Test
    public void tokenizeId(){
        Lexer lexer = new Lexer("id1 id2 for while while1 return break breaking null");
        List<Token> tokens;

        tokens = lexer.tokenize();
        tokens.forEach((t) -> System.out.println(t));
    }

    @Test
    public void tokenizeParens(){
        Lexer lexer = new Lexer("(Foo bar)");
        List<Token> tokens;

        tokens = lexer.tokenize();
        tokens.forEach((t) -> System.out.println(t));
    }

    @Test
    public void tokenizeNumber(){
        Lexer lexer = new Lexer("12345 1.1 1.5e-9");
        List<Token> tokens;

        tokens = lexer.tokenize();
        System.out.println("\nNumeric Tokens: ");
        tokens.forEach((t) -> System.out.println(t));
    }

    @Test
    public void tokenizeOperatorEQ(){
        Lexer lexer = new Lexer("=");
        List<Token> tokens;

        Token goodToken = new Token(EQ, "=", 1, 1);
        tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(goodToken.equals(tokens.get(0)));
        //System.out.println("\nOperator Tokens: ");
        //tokens.forEach((t) -> System.out.println(t));
    }

    @Test
    public void tokenizeOperatorEQEQ(){
        Lexer lexer = new Lexer("= == >> << >= <= ! != += -= ++ -- / /= * *= ** % %= .");
        List<Token> tokens;

        tokens = lexer.tokenize();
        System.out.println("\nOperator Tokens: ");
        tokens.forEach((t) -> System.out.println(t));
    }

    @Test
    public void tokenizeDelimiters(){
        Lexer lexer = new Lexer("[ ] { } ( ) ! , ; \n{}[](),!;");
        List<Token> tokens;

        tokens = lexer.tokenize();
        System.out.println("\nDelimiter Tokens: ");
        tokens.forEach((t) -> System.out.println(t));
    }
}
