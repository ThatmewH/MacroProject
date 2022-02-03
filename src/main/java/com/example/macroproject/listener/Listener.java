package com.example.macroproject.listener;


import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.util.ArrayList;

public class Listener implements NativeMouseInputListener, NativeKeyListener {
    //TODO add subclass for key and mouse listeners

    public static ArrayList<Input> inputListeners = new ArrayList<>();

    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        for (Input inputListener : inputListeners) {
            inputListener.getInputEvent(nativeEvent);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        for (Input inputListener : inputListeners) {
            inputListener.getInputEvent(nativeEvent);
        }
    }

    public static void addKeyListener(Input inputListener) {
        inputListeners.add(inputListener);
    }

    public static void removeKeyListener(Input keyListener) {
        inputListeners.remove(keyListener);
    }

}