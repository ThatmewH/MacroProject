package com.example.macroproject.variables;

public class IntegerVariable extends Variable<Integer> {
    //TODO make a superclass called NumberVariable with all the add, sub, mul and divide
    // with a generic extending number
    public IntegerVariable(String name, Integer integer) {
        super(name, integer, Integer.class);
    }

    public void addValue(int addValue) {
        this.value += addValue;
    }

    public void subtractValue(int subValue) {
        this.value -= subValue;
    }

    public void multiplyValue(int mulValue) {
        this.value *= mulValue;
    }

    public void divideValue(int divValue) {
        this.value /= divValue;
    }
}
