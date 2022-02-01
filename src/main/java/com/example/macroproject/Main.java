package com.example.macroproject;

import com.example.macroproject.listener.Listener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class Main {
    public static void main(String[] args) throws NativeHookException {
        GlobalScreen.registerNativeHook();
        Listener l = new Listener();
        GlobalScreen.addNativeMouseListener(l);
        GlobalScreen.addNativeKeyListener(l);

        Thread thread = new Thread(() -> {
            Listener.waitForKey(() -> listenerCallback(Listener.listeningInput));
        });
        thread.start();

        StartApplication.main(args);
    }

    public static void listenerCallback(String key) {
        System.out.println(key);
    }
}