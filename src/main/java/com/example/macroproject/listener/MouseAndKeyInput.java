package com.example.macroproject.listener;

import com.github.kwhat.jnativehook.NativeInputEvent;

import java.io.Serializable;

public class MouseAndKeyInput extends Input<Serializable> {
    protected MouseAndKeyInput(Runnable callbackFunction) {
        super(callbackFunction);
    }

    @Override
    protected void handleEvent(NativeInputEvent event) {

    }
}
