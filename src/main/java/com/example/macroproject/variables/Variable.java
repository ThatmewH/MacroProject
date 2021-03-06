package com.example.macroproject.variables;

import com.example.macroproject.commands.opencv.OpenCVHelper;
import org.opencv.core.Mat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public abstract class Variable <Value> {

    public static ArrayList<Class> classesWithVariables = new ArrayList<>() {
        {
            add(Integer.class);
            add(Double.class);
            add(Boolean.class);
            add(String.class);
            add(ArrayList.class);
            add(Mat.class);
        }
    };

    public static ArrayList<Variable> variables = new ArrayList<>();

    public String name;
    public Value value;
    public Class variableClass;

    public Variable(String name, Value value, Class<Value> variableClass) {
        this.name = name;
        this.value = value;
        this.variableClass = variableClass;
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
        return this.variableClass;
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

    public static Object getInputValue(String input) {
        input = input.toLowerCase(Locale.ROOT);

        if (input.contains(".")) {
            try {
                return OpenCVHelper.openImage(input);
            } catch (Exception ignored) {}
            //TODO: Will always choose double over float, need to find a way to choose the suitable class, or just
            //      always use double
            try {
                return Double.valueOf(input);
            } catch (Exception ignored) {}
            try {
                return Float.valueOf(input);
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

    public static Variable getVariableFromObject(Object value, Class valueClass) {
        if (valueClass == Integer.class) {
            return new IntegerVariable("", (Integer) value);
        } else if (valueClass == Boolean.class) {
            return new BooleanVariable("", (Boolean) value);
        } else if (valueClass == Float.class) {
            return new FloatVariable("", (Float) value);
        } else if (valueClass == Double.class) {
            return new DoubleVariable("", (Double) value);
        } else if (valueClass == String.class) {
            return new StringVariable("", (String) value);
        } else if (valueClass == ArrayList.class) {
            return new ListVariable("", (ArrayList) value);
        } else if (valueClass == Mat.class) {
            return new ImageVariable("", (Mat) value);
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
        } else if (input.equals("null")) {
            return Variable.getVariableFromObject(null, variableClass);
        }

        if (checkExistingVariables) {
            Variable variable = checkExistingVariables(input, variableClass);
            if (variable != null) {
                return variable;
            }
        }

        Object value = Variable.getInputValue(input);
        if (variableClass == value.getClass()) {
            return Variable.getVariableFromObject(value, value.getClass());
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
