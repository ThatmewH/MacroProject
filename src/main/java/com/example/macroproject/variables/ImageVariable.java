package com.example.macroproject.variables;

import org.opencv.core.Mat;

public class ImageVariable extends Variable<Mat> {
    public ImageVariable(String name, Mat image) {
        super(name, image);
    }
}
