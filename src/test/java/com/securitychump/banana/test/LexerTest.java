package com.securitychump.banana.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.securitychump.banana.Lexer;
import com.securitychump.banana.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexerTest {

    @Test
    public void tokenizeId(){
        Lexer lexer = new Lexer("id1 id2");
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
        System.out.println("\nTokens: ");
        tokens.forEach((t) -> System.out.println(t));
    }
}
