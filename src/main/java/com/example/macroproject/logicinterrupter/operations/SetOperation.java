package com.example.macroproject.logicinterrupter.operations;

import com.example.macroproject.logicinterrupter.LogicInterrupter;
import com.example.macroproject.logicinterrupter.LogicOperation;
import com.example.macroproject.logicinterrupter.RegisteredLogicOperation;
import com.example.macroproject.variables.Variable;

import java.io.Serializable;
import java.util.ArrayList;

public class SetOperation extends LogicOperation {

    String variableName;

    public SetOperation() {
        super(new ArrayList<>(){{
                    add(0);
                    add(2);
                }}
        );
    }

    @Override
    protected void getVariables(ArrayList<String> variables) {
        super.getVariables(variables);
        variableName = variables.get(0);
    }

    @Override
    public Object eval() {
        try {
            if (Variable.getVariable(variables.get(0).name) == null) {
                throw new Exception("Variable Doesn't Exist");
            }
            variables.get(0).setValue(variables.get(1).getValue());
            return variables.get(0).getValue();

        } catch (Exception e) {
            e.printStackTrace();
            return variables.get(0).getValue();
        }
    }

    public static RegisteredLogicOperation registerOperation() {
        return new RegisteredLogicOperation("=", SetOperation.class);
    }
}
