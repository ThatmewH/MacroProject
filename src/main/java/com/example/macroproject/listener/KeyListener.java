package com.example.macroproject.listener;

import com.example.macroproject.variables.StringVariable;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyListener {
    private final Runnable callbackFunction;
    private boolean isListening = false;
    private StringVariable key;
    private String inputKey;

    public KeyListener(Runnable callbackFunction, StringVariable key) {
        this.callbackFunction = callbackFunction;
        this.key = key;
        Listener.addKeyListener(this);
    }

    public void getKeyEvent(NativeKeyEvent keyEvent) {
        if (isListening) {
            String inputtedKey = NativeKeyEvent.getKeyText(keyEvent.getKeyCode()).toLowerCase();

            if (key == null || inputtedKey.equals(key.getValue())) {
                inputKey = NativeKeyEvent.getKeyText(keyEvent.getKeyCode());
                callbackFunction.run();
            }
        }
    }

    public void startListening() {
        isListening = true;
    }

    public void stopListening() {
        isListening = false;
    }

    public String getInputKey() {
        return inputKey;
    }

    public void removeSelf() {
        Listener.removeKeyListener(this);
    }
}
