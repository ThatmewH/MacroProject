package com.example.macroproject.logicinterrupter;

import com.example.macroproject.variables.Variable;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class LogicOperation {

    protected ArrayList<Variable> variables = new ArrayList<>();
    protected String operationSymbol;

    public LogicOperation(String operationSymbol, String... variables) {
        this.operationSymbol = operationSymbol;
        getVariables(variables);
    }
    protected void getVariables(String[] variables) {

        for (String input : variables) {
            String t = "" + LogicInterrupter.evalString(input);
            if (Variable.checkVariableExists(t)) {
                this.variables.add(Variable.getVariable(t));
            } else {
                this.variables.add(Variable.checkVariableReference(t, Variable.getInputValue(t).getClass(), true));
            }
        }
    }

    public String getOperationSymbol() {
        return operationSymbol;
    }

    public abstract Serializable eval();
}
