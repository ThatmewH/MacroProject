package com.example.macroproject.listener;

import com.example.macroproject.variables.IntegerVariable;
import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class MouseInput extends Input<Integer> {

    protected IntegerVariable mouseButton;

    protected IntegerVariable outputMouseX;
    protected IntegerVariable outputMouseY;
    protected IntegerVariable outputMouseClickNum;

    public MouseInput(Runnable callbackFunction, IntegerVariable mouseButton, IntegerVariable outputMouseX
            , IntegerVariable outputMouseY, IntegerVariable outputMouseClickNum) {
        super(callbackFunction);

        this.mouseButton = mouseButton;
        this.outputMouseX = outputMouseX;
        this.outputMouseY = outputMouseY;
        this.outputMouseClickNum = outputMouseClickNum;
    }

    @Override
    protected void handleEvent(NativeInputEvent event) {
        if (event instanceof NativeMouseEvent mouseEvent) {
            int inputtedMouse = mouseEvent.getButton();

            if (mouseButton.getValue() == null || inputtedMouse == mouseButton.getValue()) {
                setInputValue(inputtedMouse);
                setOutputs(mouseEvent);
                callbackFunction.run();
            }
        }
    }

    protected void setOutputs(NativeMouseEvent mouseEvent) {
        outputMouseX.setValue(mouseEvent.getX());
        outputMouseY.setValue(mouseEvent.getY());
        outputMouseClickNum.setValue(mouseEvent.getClickCount());
    }
}
