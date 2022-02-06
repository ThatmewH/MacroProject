package com.example.macroproject.logicinterrupter;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.Variable;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;

public class RegisteredLogicOperation {
    public static ArrayList<String> operationSymbols = new ArrayList<>();

    public Class<? extends LogicOperation> operationClass;
    public String symbol;

    public RegisteredLogicOperation(String symbol, Class<? extends LogicOperation> operationClass) {
        operationSymbols.add(symbol);
        this.operationClass = operationClass;
        this.symbol = symbol;
    }

    public static LogicOperation createOperationFromRegisteredOperation(RegisteredLogicOperation logicOperation) {
        try {
            LogicOperation operation = (LogicOperation) logicOperation.operationClass.getConstructors()[0].newInstance();
            return operation;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LogicOperation getLogicOperationFromSymbol(String operationSymbol) {
        for (RegisteredLogicOperation registeredLogicOperation : LogicInterrupter.operations) {
            if (registeredLogicOperation.symbol.equals(operationSymbol)) {
                return createOperationFromRegisteredOperation(registeredLogicOperation);
            }
        }
        return null;
    }
}
