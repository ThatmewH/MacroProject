package com.example.macroproject.commands.opencv;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.DoubleVariable;
import com.example.macroproject.variables.ImageVariable;

public class TemplateMatchingCommand extends Command {
    protected BooleanVariable showImage;
    protected ImageVariable background;
    protected ImageVariable template;
    protected DoubleVariable threshhold;

    protected BooleanVariable outputImageWasFound;
    protected DoubleVariable outputX1;
    protected DoubleVariable outputY1;
    protected DoubleVariable outputX2;
    protected DoubleVariable outputY2;

    public TemplateMatchingCommand(CommandFunction function, BooleanVariable showImage, ImageVariable background
            , ImageVariable template, DoubleVariable threshhold, BooleanVariable outputImageWasFound
            , DoubleVariable outputX1, DoubleVariable outputY1, DoubleVariable outputX2, DoubleVariable outputY2) {
        super(function);

        if (showImage == null || background == null || template == null || threshhold == null || outputImageWasFound == null
                || outputX1 == null || outputY1 == null || outputX2 == null || outputY2 == null) {
            throw new IllegalArgumentException("Arguments can't be null");
        }

        this.showImage = showImage;
        this.background = background;
        this.template = template;
        this.threshhold = threshhold;

        this.outputImageWasFound = outputImageWasFound;
        this.outputX1 = outputX1;
        this.outputY1 = outputY1;
        this.outputX2 = outputX2;
        this.outputY2 = outputY2;
    }

    @Override
    public void run() {
        double[] position = OpenCVHelper.templateMatch(background.getValue(), template.getValue(), threshhold.getValue()
                , showImage.getValue());

        if (position != null) {
            outputImageWasFound.setValue(true);
            outputX1.setValue(position[0]);
            outputY1.setValue(position[1]);
            outputX2.setValue(position[2]);
            outputY2.setValue(position[3]);
        } else {
            outputImageWasFound.setValue(false);
        }
    }

    @Override
    public String toString() {
        return String.format("Template Match: %s to %s", template, background);
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Template Match", TemplateMatchingCommand.class, "commands/templatematch.fxml");
    }
}
