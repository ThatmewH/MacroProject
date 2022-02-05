package com.example.macroproject.logicinterrupter.operations;

import com.example.macroproject.logicinterrupter.LogicOperation;
import com.example.macroproject.logicinterrupter.RegisteredLogicOperation;

import java.io.Serializable;

public class SetOperation extends LogicOperation {

    public SetOperation(String firstVariable, String secondVariable) {
        super(firstVariable, secondVariable);
    }

    @Override
    public Serializable eval() {
        try {
            variables.get(0).setValue(variables.get(1).getValue());
            return variables.get(0).getValue();
        } catch (Exception e) {
            return variables.get(0).getValue();
        }
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation("=", SetOperation.class);
    }
}
