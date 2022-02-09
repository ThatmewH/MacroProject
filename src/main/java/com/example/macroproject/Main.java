package com.example.macroproject;

import com.example.macroproject.listener.Listener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import org.opencv.core.Core;



public class Main {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) throws NativeHookException {
        GlobalScreen.registerNativeHook();
        Listener l = new Listener();

        GlobalScreen.addNativeMouseListener(l);
        GlobalScreen.addNativeKeyListener(l);

        StartApplication.main(args);

        GlobalScreen.removeNativeKeyListener(l);
        GlobalScreen.removeNativeMouseListener(l);
        GlobalScreen.unregisterNativeHook();
    }
}