package com.example.macroproject.logicinterrupter.operations;

import com.example.macroproject.logicinterrupter.LogicOperation;
import com.example.macroproject.logicinterrupter.RegisteredLogicOperation;

import java.io.Serializable;
import java.util.ArrayList;

public class ToListOperation extends LogicOperation {

    public ToListOperation() {
        super(new ArrayList<>(){{
            add(0);
            add(2);
        }});
    }

    @Override
    public Serializable eval() {
        return variables.get(0).getValue() + ", " + variables.get(1).getValue();
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation(":", ToListOperation.class);
    }
}
