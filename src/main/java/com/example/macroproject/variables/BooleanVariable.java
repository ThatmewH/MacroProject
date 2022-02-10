package com.example.macroproject.variables;

public class BooleanVariable extends Variable<Boolean> {

    public BooleanVariable(String name, Boolean aBoolean) {
        super(name, aBoolean, Boolean.class);
    }

    public void toggleValue() {
        this.value = !this.value;
    }
}
