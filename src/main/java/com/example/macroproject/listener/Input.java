package com.example.macroproject.listener;

import com.github.kwhat.jnativehook.NativeInputEvent;

import java.io.Serializable;

public abstract class Input<returnValue extends Serializable> {
    protected boolean isListening = false;
    protected final Runnable callbackFunction;

    protected returnValue inputValue;

    protected Input(Runnable callbackFunction) {
        this.callbackFunction = callbackFunction;
        Listener.addKeyListener(this);
    }

    protected abstract void handleEvent(NativeInputEvent event);

    public returnValue getInputValue() {
        return inputValue;
    }

    protected void setInputValue(returnValue value) {
        inputValue = value;
    }

    public void getInputEvent(NativeInputEvent event) {
        if (isListening && event != null) {
            handleEvent(event);
        }
    }

    public void startListening() {
        isListening = true;
    }

    public void stopListening() {
        isListening = false;
    }

    public void removeSelf() {
        Listener.removeKeyListener(this);
    }
}
