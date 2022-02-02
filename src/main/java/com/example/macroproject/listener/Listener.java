package com.example.macroproject.listener;


import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;

public class Listener implements NativeMouseInputListener, NativeKeyListener {
    //TODO add subclass for key and mouse listeners

    public static ArrayList<KeyListener> keyListeners = new ArrayList<>();
    public static ArrayList<KeyListener> mouseListeners = new ArrayList<>();

    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        System.out.println(keyListeners);
        for (KeyListener keyListener : keyListeners) {
            keyListener.getKeyEvent(nativeEvent);
        }
    }

    public static void addKeyListener(KeyListener keyListener) {
        keyListeners.add(keyListener);
    }

    public static void removeKeyListener(KeyListener keyListener) {
        keyListeners.remove(keyListener);
    }

}