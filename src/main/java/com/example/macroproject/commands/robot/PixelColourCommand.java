package com.example.macroproject.commands.robot;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class PixelColourCommand extends Command {
    static Robot bot;

    static {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public PixelColourCommand(CommandFunction function) {
        super(function);
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return null;
    }
}
