package com.example.macroproject.listener;


import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Listener implements NativeMouseInputListener, NativeKeyListener {
    public static volatile String listeningInput = null;

    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        listeningInput = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
    }

    private static void waitForKey(Runnable callback, String key) {
        while (!listeningInput.equals(key) || (key == null && listeningInput != null)) {
            Thread.onSpinWait();
        }
        callback.run();
    }

    /**
     * Runs the waitForKey function in another thread and runs the callback specified when the specified key
     * is detected.
     *
     * @param callback (Runnable) - the callback function run when the specified key is pressed
     * @param key (String) - only runs the callback when this key string is detected. If null, then any key press will
     *            run the callback function
     */
    public static void getNextKey(Runnable callback, String key) {
        Thread thread = new Thread(() -> Listener.waitForKey(callback, key));
        thread.start();
    }

    public static int stringToKeyCode(String key) {
        switch (key) {
            case "A":
                return KeyEvent.VK_A;
            case "B":
                return KeyEvent.VK_B;
            case "C":
                return KeyEvent.VK_C;
            case "D":
                return KeyEvent.VK_D;
            case "E":
                return KeyEvent.VK_E;
            case "F":
                return KeyEvent.VK_F;
            case "G":
                return KeyEvent.VK_G;
            case "H":
                return KeyEvent.VK_H;
            case "I":
                return KeyEvent.VK_I;
            case "J":
                return KeyEvent.VK_J;
            case "K":
                return KeyEvent.VK_K;
            case "L":
                return KeyEvent.VK_L;
            case "M":
                return KeyEvent.VK_M;
            case "N":
                return KeyEvent.VK_N;
            case "O":
                return KeyEvent.VK_O;
            case "P":
                return KeyEvent.VK_P;
            case "Q":
                return KeyEvent.VK_Q;
            case "R":
                return KeyEvent.VK_R;
            case "S":
                return KeyEvent.VK_S;
            case "T":
                return KeyEvent.VK_T;
            case "U":
                return KeyEvent.VK_U;
            case "V":
                return KeyEvent.VK_V;
            case "W":
                return KeyEvent.VK_W;
            case "X":
                return KeyEvent.VK_X;
            case "Y":
                return KeyEvent.VK_Y;
            case "Z":
                return KeyEvent.VK_Z;
            case "0":
                return KeyEvent.VK_0;
            case "1":
                return KeyEvent.VK_1;
            case "2":
                return KeyEvent.VK_2;
            case "3":
                return KeyEvent.VK_3;
            case "4":
                return KeyEvent.VK_4;
            case "5":
                return KeyEvent.VK_5;
            case "6":
                return KeyEvent.VK_6;
            case "7":
                return KeyEvent.VK_7;
            case "8":
                return KeyEvent.VK_8;
            case "9":
                return KeyEvent.VK_9;
        }
        return -1;
    }

}