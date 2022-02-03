package com.example.macroproject.listener;

import com.example.macroproject.variables.StringVariable;
import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.util.Locale;

public class KeyInput extends Input<String> {

    private StringVariable key;

    public KeyInput(Runnable callbackFunction, StringVariable key) {
        super(callbackFunction);
        this.key = key;
    }

    protected void handleEvent(NativeInputEvent event) {
        if (event instanceof NativeKeyEvent keyEvent) {
            String inputtedKey = NativeKeyEvent.getKeyText(keyEvent.getKeyCode()).toLowerCase();

            if (key == null || inputtedKey.equals(key.getValue().toLowerCase())) {
                setInputValue(NativeKeyEvent.getKeyText(keyEvent.getKeyCode()));
                callbackFunction.run();
            }
        }
    }

    public StringVariable getListeningKey() {
        return key;
    }
}
