package com.example.macroproject.logicinterrupter;

import com.example.macroproject.variables.Variable;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class LogicOperation {

    protected ArrayList<Variable> variables = new ArrayList<>();
    /**
     * Integer arrray for variable offsets. For Example, for the command "1 + 1", the '+' symbol will be in position 1,
       and the variable offsets will be 0 and 2.
     * Another example, for the command "0 : 1 ? false", the symbol '?' will be in position 3, and the variable offsets
       will be 0, 2, 4
     */
    public ArrayList<Integer> variableOffsets;

    public LogicOperation(ArrayList<Integer> variableOffsets) {

        this.variableOffsets = variableOffsets;
    }
    protected void getVariables(ArrayList<String> variables) {
        for (String input : variables) {
            String t = "" + LogicInterrupter.runEvaluation(input);
            if (Variable.checkVariableExists(t)) {
                this.variables.add(Variable.getVariable(t));
            } else {
                this.variables.add(Variable.checkVariableReference(t, Variable.getInputValue(t).getClass(), true));
            }
        }
    }

    public abstract Serializable eval();
}
