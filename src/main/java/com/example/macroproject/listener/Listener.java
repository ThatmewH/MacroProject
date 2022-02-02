package com.example.macroproject.listener;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class Listener implements NativeMouseInputListener, NativeKeyListener {
    public static volatile String listeningInput = null;

    public void nativeMouseClicked(NativeMouseEvent e) {
//        System.out.println("Mouse Clicked: " + e.getX() + " " + e.getY());
    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        listeningInput = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
    }

    private static void waitForKey(Runnable callback) {
        while (listeningInput == null) {
            Thread.onSpinWait();
        }
        callback.run();
    }

    // Example use:
    //
    //    Listener.getNextKey(() -> listenerCallback(Listener.listeningInput));
    //
    //    public static void listenerCallback(String key) {
    //        System.out.println(key);
    //    }
    public static void getNextKey(Runnable callback) {
        Thread thread = new Thread(() -> Listener.waitForKey(callback));
        thread.start();
    }

}