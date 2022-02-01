package com.example.macroproject.commands;

import com.example.macroproject.controllers.commands.CommandController;

import java.lang.reflect.InvocationTargetException;

public class RegisteredCommand {
    public String name;
    public Class<? extends Command> commandClass;
    public String FXMLView;

    public RegisteredCommand(String name, Class<? extends Command> commandClass, String FXMLView) {
        this.name = name;
        this.commandClass = commandClass;
        this.FXMLView = FXMLView;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Command createCommandFromRegisteredCommand(RegisteredCommand registeredCommand) {
        try {
                Command command = registeredCommand.commandClass.getDeclaredConstructor().newInstance();
                return command;
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        return null;
    }
}
