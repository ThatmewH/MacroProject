package com.example.macroproject.variables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public abstract class Variable <Value extends java.io.Serializable> {

    public static ArrayList<Class> classesWithVariables = new ArrayList<>() {
        {
            add(Integer.class);
            add(Float.class);
            add(Boolean.class);
            add(String.class);
            add(ArrayList.class);
        }
    };

    public static ArrayList<Variable> variables = new ArrayList<>();

    public String name;
    public Value value;

    public Variable(String name, Value value) {
        this.name = name;
        this.value = value;
    }

    public void setToVariable(Variable newVariable) {
        this.name = newVariable.name;
        this.value = (Value) newVariable.value;
    }

    public void setValue(Value newValue) {
        value = newValue;
    }

    public Value getValue() {
        return this.value;
    }

    public Class getValueType() {
        return this.value.getClass();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static void addNewVariable(Variable variable) {
        if (!checkVariableExists(variable.name)) {
            variables.add(variable);
        }
    }

    public static boolean checkVariableExists(String name) {
        return getVariable(name) != null;
    }

    public static Variable getVariable(String variableName) {
        for (Variable variable : variables) {
            if (variable.name.equalsIgnoreCase(variableName)) {
                return variable;
            }
        }
        return null;
    }

    public static Serializable getInputValue(String input) {
        input = input.toLowerCase(Locale.ROOT);

        if (input.contains(".")) {
            try {
                return Float.valueOf(input);
            } catch (Exception ignored) {}

            try {
                return Double.valueOf(input);
            } catch (Exception ignored) {}

            try {
                if (input.contains(","))
                return new ArrayList<>(Arrays.asList(input.replace("[", "").replace("]", "").split(",")));
            } catch (Exception ignored) {}
        } else {
            try {
                return Integer.valueOf(input);
            } catch (Exception ignored) {}

            try {
                return Long.valueOf(input);
            } catch (Exception ignored) {}

            try {
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false"))
                return Boolean.valueOf(input);
            } catch (Exception ignored) {}

            try {
                if (input.contains(","))
                    return new ArrayList<>(Arrays.asList(input.replace("[", "").replace("]", "").split(",")));
            } catch (Exception ignored) {}
        }

        // The input variable type is a String
        return input;
    }

    public static Variable getVariableFromClass(Serializable value) {
        if (value.getClass() == Integer.class) {
            return new IntegerVariable("", (Integer) value);
        } else if (value.getClass() == Boolean.class) {
            return new BooleanVariable("", (Boolean) value);
        } else if (value.getClass() == Float.class) {
            return new FloatVariable("", (Float) value);
        } else if (value.getClass() == String.class) {
            return new StringVariable("", (String) value);
        } else if (value.getClass() == ArrayList.class) {
            return new ListVariable("", (ArrayList) value);
        }
        return null;
    }

    public static Variable checkExistingVariables(String input, Class variableClass) {
        Variable variable = Variable.getVariable(input);
        if (variable != null) {
            if (variable.getValueType() == variableClass) {
                return variable;
            }
        }
        return null;
    }

    /**
     * Checks if the user input is the correct type or references an existing variable in the existing static list
     *
     * @param input
     */
    public static Variable checkVariableReference(String input, Class variableClass, boolean checkExistingVariables) {
        if (input.equals("")) {
            return null;
        }

        if (checkExistingVariables) {
            Variable variable = checkExistingVariables(input, variableClass);
            if (variable != null) {
                return variable;
            }
        }

        Serializable value = Variable.getInputValue(input);
        if (variableClass == value.getClass()) {
            return Variable.getVariableFromClass(value);
        } else if (variableClass == String.class) {
            // String is a special case because you can put any variable type in a string
            return new StringVariable("", input);
        }

        return null;
    }

    public static void deleteVariable(String variableName) {
        Variable variable = getVariable(variableName);
        if (variable != null) {
            variables.remove(variable);
        }
    }
}
