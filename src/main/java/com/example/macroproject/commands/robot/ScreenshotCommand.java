package com.example.macroproject.commands.robot;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.commands.opencv.OpenCVHelper;
import com.example.macroproject.variables.ImageVariable;
import com.example.macroproject.variables.IntegerVariable;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;

public class ScreenshotCommand extends Command {
    static Robot bot;

    protected IntegerVariable x1;
    protected IntegerVariable y1;
    protected IntegerVariable width;
    protected IntegerVariable height;
    protected ImageVariable outputImage;

    static {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public ScreenshotCommand(CommandFunction function, IntegerVariable x1, IntegerVariable y1
            , IntegerVariable width, IntegerVariable height, ImageVariable outputImage) {
        super(function);
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
        this.outputImage = outputImage;
    }

    @Override
    public void run() {
        MultiResolutionImage multiResolutionImage = bot.createMultiResolutionScreenCapture(
                new Rectangle(x1.getValue(), y1.getValue(), width.getValue(), height.getValue()));

        Image image = multiResolutionImage.getResolutionVariant(OpenCVHelper.getFullScreenRectangle().width + 1
                , OpenCVHelper.getFullScreenRectangle().height + 1);

        Mat imageMat = OpenCVHelper.matify((BufferedImage) image);
        outputImage.setValue(imageMat);
    }

    @Override
    public String toString() {
        return String.format("Screenshot x:%s y:%s width:%s height:%s", x1.getValue(), y1.getValue()
                , width.getValue(), height.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Screenshot", ScreenshotCommand.class, "commands/screenshot.fxml");
    }
}
