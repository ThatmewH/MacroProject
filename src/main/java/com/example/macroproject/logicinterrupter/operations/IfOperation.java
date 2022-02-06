package com.example.macroproject.logicinterrupter.operations;

import com.example.macroproject.logicinterrupter.LogicOperation;
import com.example.macroproject.logicinterrupter.RegisteredLogicOperation;

import java.io.Serializable;
import java.util.ArrayList;

public class IfOperation extends LogicOperation {

    public IfOperation() {
        super(new ArrayList<>(){{
                    add(0);
                    add(2);
                }}
        );
    }

    @Override
    public Serializable eval() {
        try {
            if ((boolean)variables.get(0).getValue()) {
                return (Serializable) ((ArrayList<?>) variables.get(1).getValue()).get(1);
            } else {
                return (Serializable) ((ArrayList<?>) variables.get(1).getValue()).get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Variables must be of same class");
        }
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation("?", IfOperation.class);
    }
}
