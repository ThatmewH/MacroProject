package com.example.macroproject.variables;

public class StringVariable extends Variable<String> {
    public StringVariable(String name, String string) {
        super(name, string, String.class);
    }
}
