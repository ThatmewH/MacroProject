package com.example.macroproject.commands.opencv;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.commands.opencv.OpenCVHelper;
import com.example.macroproject.commands.robot.ClickCommand;
import com.example.macroproject.variables.ImageVariable;
import com.example.macroproject.variables.IntegerVariable;
import com.example.macroproject.variables.StringVariable;
import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;

public class SaveImageCommand extends Command {

    protected ImageVariable image;
    protected StringVariable imagePath;

    public SaveImageCommand(CommandFunction function, ImageVariable image, StringVariable imagePath) {
        super(function);
        this.image = image;
        this.imagePath = imagePath;
    }

    @Override
    public void run() {
        OpenCVHelper.writeImage(imagePath.getValue(), image.getValue());
    }

    @Override
    public String toString() {
        return String.format("Save Image: %s to %s", image, imagePath.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Save Image", SaveImageCommand.class, "commands/saveimage.fxml");
    }
}
