package com.example.macroproject.variables;

public class DoubleVariable extends Variable<Double> {
    //TODO make a superclass called NumberVariable with all the add, sub, mul and divide
    // with a generic extending number
    public DoubleVariable(String name, Double _double) {
        super(name, _double, Double.class);
    }

    public void addValue(double addValue) {
        this.value += addValue;
    }

    public void subtractValue(double subValue) {
        this.value -= subValue;
    }

    public void multiplyValue(double mulValue) {
        this.value *= mulValue;
    }

    public void divideValue(double divValue) {
        this.value /= divValue;
    }
}
