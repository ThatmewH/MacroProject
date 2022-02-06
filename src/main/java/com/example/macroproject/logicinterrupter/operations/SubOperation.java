package com.example.macroproject.logicinterrupter.operations;

import com.example.macroproject.logicinterrupter.LogicOperation;
import com.example.macroproject.logicinterrupter.RegisteredLogicOperation;

import java.io.Serializable;
import java.util.ArrayList;

public class SubOperation extends LogicOperation {

    public SubOperation() {
        super(new ArrayList<>(){{
                    add(0);
                    add(2);
                }}
        );
    }

    @Override
    public Serializable eval() {
        try {
            if (variables.get(0).getValue() instanceof Integer) {
                return ((Integer) variables.get(1).getValue()) - ((Integer) variables.get(0).getValue());
            } else if (variables.get(0).getValue() instanceof Float) {
                return ((Float) variables.get(1).getValue()) - ((Float) variables.get(0).getValue());
            } else if (variables.get(0).getValue() instanceof Double) {
                return ((Double) variables.get(1).getValue()) - ((Double) variables.get(0).getValue());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Variables must be of same class");
        }

        return null;
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation("-", SubOperation.class);
    }
}
