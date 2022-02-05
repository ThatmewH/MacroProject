package com.example.macroproject.logicinterrupter;

import com.example.macroproject.logicinterrupter.operations.*;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.Variable;

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
        }
    };

    public static String reverseString(String string) {
        string = string.replace(" ", "");
        for (String operationSymbol : RegisteredLogicOperation.operationSymbols) {
            if (string.contains(operationSymbol)) {
                string = string.replace(operationSymbol, "]" + operationSymbol + "]");
            }
        }
        String[] splitString = string.split("]");

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

    public static Serializable evalString(String command) {
        int letterIndex = 0;
        for (Character character : command.toCharArray()) {
            String letter = character.toString();
            if (RegisteredLogicOperation.operationSymbols.contains(letter)) {
                String firstVar =  command.substring(0, letterIndex);
                String secondVar =  command.substring(letterIndex + 1, command.toCharArray().length);
                LogicOperation logicOperation = RegisteredLogicOperation.getLogicOperationFromSymbol(letter, firstVar, secondVar);
                return logicOperation.eval();
            }
            letterIndex++;
        }
        return command;
    }

    public static void main(String[] args) {
        Variable.addNewVariable(new IntegerVariable("test", 3));
        System.out.println(evalString(reverseString("6 + 1 * 4 + 50 - 1 / 2 - test = 35")));
    }
}
