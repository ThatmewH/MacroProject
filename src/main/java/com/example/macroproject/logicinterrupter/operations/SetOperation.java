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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation("~", SetOperation.class);
    }
}
