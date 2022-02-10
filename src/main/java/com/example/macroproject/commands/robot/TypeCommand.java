package com.example.macroproject.commands.robot;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class TypeCommand extends Command {
    public static Hashtable<Character, Integer> shiftCharacters = new Hashtable<>() {
        {
            put('~', KeyEvent.getExtendedKeyCodeForChar('`'));
            put('!', KeyEvent.getExtendedKeyCodeForChar('1'));
            put('@', KeyEvent.getExtendedKeyCodeForChar('2'));
            put('#', KeyEvent.getExtendedKeyCodeForChar('3'));
            put('$', KeyEvent.getExtendedKeyCodeForChar('4'));
            put('%', KeyEvent.getExtendedKeyCodeForChar('5'));
            put('^', KeyEvent.getExtendedKeyCodeForChar('6'));
            put('&', KeyEvent.getExtendedKeyCodeForChar('7'));
            put('*', KeyEvent.getExtendedKeyCodeForChar('8'));
            put('(', KeyEvent.getExtendedKeyCodeForChar('9'));
            put(')', KeyEvent.getExtendedKeyCodeForChar('0'));
            put('_', KeyEvent.getExtendedKeyCodeForChar('-'));
            put('+', KeyEvent.getExtendedKeyCodeForChar('='));
            put('{', KeyEvent.getExtendedKeyCodeForChar('['));
            put('}', KeyEvent.getExtendedKeyCodeForChar(']'));
            put('|', KeyEvent.getExtendedKeyCodeForChar('\\'));
            put(':', KeyEvent.getExtendedKeyCodeForChar(';'));
            put('"', KeyEvent.getExtendedKeyCodeForChar('\''));
            put('<', KeyEvent.getExtendedKeyCodeForChar(','));
            put('>', KeyEvent.getExtendedKeyCodeForChar('.'));
            put('?', KeyEvent.getExtendedKeyCodeForChar('/'));
        }
    };

    static Robot bot;

    protected StringVariable typeString;
    protected IntegerVariable keyPressLength;
    protected IntegerVariable keyDelay;

    static {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public TypeCommand(CommandFunction function, StringVariable typeString, IntegerVariable keyPressLength
            , IntegerVariable keyDelay) {
        super(function);

        if (typeString == null || keyPressLength == null || keyDelay == null) {
            throw new IllegalArgumentException("Command Arguments cant be null");
        }

        this.typeString = typeString;
        this.keyPressLength = keyPressLength;
        this.keyDelay = keyDelay;
    }

    @Override
    public void run() {
        for (char c : typeString.getValue().toCharArray()) {
            if (!function.isRunning()) {
                return;
            }

            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                continue;
            }

            try {
                bot.keyPress(keyCode);
                bot.delay(keyPressLength.getValue());
                bot.keyRelease(keyCode);
                bot.delay(keyDelay.getValue());
            } catch (Exception ignored) {
                if (shiftCharacters.containsKey(c)) {
                    keyCode = shiftCharacters.get(c);

                    bot.keyPress(KeyEvent.VK_SHIFT);
                    bot.keyPress(keyCode);

                    bot.delay(keyPressLength.getValue());

                    bot.keyRelease(keyCode);
                    bot.keyRelease(KeyEvent.VK_SHIFT);

                    bot.delay(keyDelay.getValue());
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Type: %s, KeyPressLength: %s, KeyDelay: %s", typeString.getValue()
                , keyPressLength.getValue(), keyDelay.getValue());
    }
}
