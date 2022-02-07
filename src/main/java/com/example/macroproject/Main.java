package com.example.macroproject;

import com.example.macroproject.listener.Listener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class Main {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) throws NativeHookException {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));


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