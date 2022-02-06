package com.example.macroproject.logicinterrupter;

import com.example.macroproject.logicinterrupter.operations.*;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.Variable;

import java.io.Serializable;
import java.util.ArrayList;

public class LogicInterrupter {

    public static ArrayList<RegisteredLogicOperation> operations = new ArrayList<>() {
        {
            add(AddOperation.registerOperation());
            add(DivideOperation.registerOperation());
            add(EqualsOperation.registerOperation());
            add(MultiplyOperation.registerOperation());
            add(SubOperation.registerOperation());
            add(SetOperation.registerOperation());
            add(IfOperation.registerOperation());
            add(ToListOperation.registerOperation());
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
        String seperatorSymbol = "}";

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
            String[] splitString = command.split("`");

            LogicOperation logicOperation = RegisteredLogicOperation.getLogicOperationFromSymbol(splitString[1]);
            // 1 `+` 1                            = 2 replaceFirsts - 2 variableOffsets
            // bool `?` int `:` int                 = 4 replaceFirsts - 3 variableOffsets
            // bool `?` int `:` int `-` int           = 6 replaceFirsts - 4 variableOffsets
            // bool `?` int `:` int `-` int `)` int     = 8 replaceFirsts - 5 variableOffsets
            //
            // split the string further for commands that need more than 2 vairables, such as the ? : command
            for (int i = 0; i < (logicOperation.variableOffsets.size() - 1) * 2 - 2; i++) {
                command = command.replaceFirst(seperatorSymbol, "`");
                command = command.replaceFirst(seperatorSymbol, "`");
            }
            command = command.replace(seperatorSymbol, "");
            splitString = command.split("`");


            ArrayList<String> variables = new ArrayList<>();
            for (int variableOffset : logicOperation.variableOffsets) {
                variables.add(splitString[variableOffset]);
            }
            logicOperation.getVariables(variables);

            return logicOperation.eval();
        }

        return command;
    }

    private static String evalStringBrackets(String command) {
        while (command.contains("(") && command.contains(")")) {
            int startIndex = command.indexOf("(");
            int endIndex = command.lastIndexOf(")");
            String bracketCommand = command.substring(startIndex, endIndex + 1);
            String commandWithoutBrackets = command.substring(startIndex + 1, endIndex);
            command = command.replace(bracketCommand, evalString(commandWithoutBrackets).toString());
        }
        return command;
    }

    public static Serializable evalString(String command) {
        command = evalStringBrackets(command);
        return runEvaluation(reverseString(command));
    }


    /**
     * Commands must be written from left to right, so something like 5 * 5 : 6 ? 6 * 6 == 36 will not work because it
       will do the "5 * 5 : 6" command first which will work, then it should do the "6 * 6 == 36" to get a boolean,
       however, that would be jumping to the right to run a command and ignoring the "?" command, which cant happen
       so it takes the 6 as the supposed boolean and returns an error. Brackets must be used to run a command first
     * @param args
     */
    public static void main(String[] args) {
        Variable.addNewVariable(new BooleanVariable("test", false));
        System.out.println(evalString("((t = 50)/50) = p)"));
    }
}
