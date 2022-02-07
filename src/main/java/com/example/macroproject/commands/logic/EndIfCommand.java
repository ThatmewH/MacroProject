package com.example.macroproject.commands.logic;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.logicinterrupter.LogicInterrupter;
import com.example.macroproject.variables.StringVariable;

public class EndIfCommand extends Command {

    public EndIfCommand(CommandFunction function) {
        super(function);
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return "End If";
    }
}
