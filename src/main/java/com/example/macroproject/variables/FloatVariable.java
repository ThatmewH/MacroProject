package com.example.macroproject.variables;

public class FloatVariable extends Variable<Float> {
    //TODO make a superclass called NumberVariable with all the add, sub, mul and divide
    // with a generic extending number
    public FloatVariable(String name, Float _float) {
        super(name, _float, Float.class);
    }

    public void addValue(float addValue) {
        this.value += addValue;
    }

    public void subtractValue(float subValue) {
        this.value -= subValue;
    }

    public void multiplyValue(float mulValue) {
        this.value *= mulValue;
    }

    public void divideValue(float divValue) {
        this.value /= divValue;
    }
}
