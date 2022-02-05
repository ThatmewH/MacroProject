package com.example.macroproject.logicinterrupter;

import com.example.macroproject.logicinterrupter.operations.*;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.Variable;

import javax.crypto.BadPaddingException;
import javax.swing.text.BadLocationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class LogicInterrupter {

    public static ArrayList<RegisteredLogicOperation> operations = new ArrayList<>() {
        {
            add(AddOperation.registerOperation());
            add(DivideOperation.registerOperation());
            add(EqualsOperation.registerOperation());
            add(MultiplyOperation.registerOperation());
            add(SubOperation.registerOperation());
            add(SetOperation.registerOperation());
        }
    };

    public static String reverseString(String string) {
        String seperatorSymbol = "]";
        string = string.replace(" ", "");
        for (String operationSymbol : RegisteredLogicOperation.operationSymbols) {
            if (string.contains(operationSymbol)) {
                string = string.replace(operationSymbol, seperatorSymbol + operationSymbol + seperatorSymbol);
            }
        }
        String[] splitString = string.split(seperatorSymbol);

        for(int i = 0; i < splitString.length / 2; i++)
        {
            String temp = splitString[i];
            splitString[i] = splitString[splitString.length - i - 1];
            splitString[splitString.length - i - 1] = temp;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : splitString) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static Serializable runEvaluation(String command) {
        String seperatorSymbol = "]";

        for (String operationSymbol : RegisteredLogicOperation.operationSymbols) {
            if (command.contains(operationSymbol)) {
                command = command.replace(operationSymbol, seperatorSymbol + operationSymbol + seperatorSymbol);
            }
            // Have to change "==" to something different and change it back once the "=" operator has been searched for
            // otherwise it will add unnecessary seperator symbols
            if (operationSymbol.equals("==")) {
                command = command.replace(operationSymbol, "~");
            }
        }
        command = command.replace("~", "==");
        // If there is still commands to be evaluated
        if (command.contains(seperatorSymbol)) {
            command = command.replaceFirst(seperatorSymbol, "`");
            command = command.replaceFirst(seperatorSymbol, "`");

            command = command.replace(seperatorSymbol, "");

            String[] splitString = command.split("`");

            String firstVar =  splitString[0];
            String secondVar =  splitString[2];

            LogicOperation logicOperation = RegisteredLogicOperation.getLogicOperationFromSymbol(splitString[1], firstVar, secondVar);
            return logicOperation.eval();
        }

        return command;
    }

    public static Serializable evalString(String string) {
        return runEvaluation(reverseString(string));
    }

    public static void main(String[] args) {
        Variable.addNewVariable(new IntegerVariable("test", 3));
        Variable.addNewVariable(new IntegerVariable("testt", 5));
        System.out.println(evalString("20 / 2 = test"));
    }
}
