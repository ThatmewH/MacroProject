package com.example.macroproject.logicinterrupter.operations;

import com.example.macroproject.logicinterrupter.LogicOperation;
import com.example.macroproject.logicinterrupter.RegisteredLogicOperation;

import java.io.Serializable;
import java.util.ArrayList;

public class EqualsOperation extends LogicOperation {
    public EqualsOperation() {
        super(new ArrayList<>(){{
                    add(0);
                    add(2);
                }}
        );
    }

    @Override
    public Serializable eval() {
        return variables.get(0).getValue().equals(variables.get(1).getValue());
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation("==", EqualsOperation.class);
    }
}
