package com.example.macroproject.commands.click;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.commands.RegisteredCommand;
import com.example.macroproject.variables.BooleanVariable;
import com.example.macroproject.variables.IntegerVariable;

import java.awt.*;
import java.awt.event.InputEvent;

public class ClickCommand extends Command {
    static Robot bot;

    static {
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // User can either choose clicking speed by entering cps or a start delay
    protected IntegerVariable clickStartDelay;
    protected IntegerVariable cps;
    protected BooleanVariable useCPS;

    protected IntegerVariable numClicks;
    //TODO: add time as a way to end command
    protected IntegerVariable runTime;
    protected BooleanVariable useNumClicks;

    protected IntegerVariable posX;
    protected IntegerVariable posY;
    protected IntegerVariable clickLength;

    public ClickCommand(int startDelay, CommandFunction function, IntegerVariable clickStartDelay, IntegerVariable cps , IntegerVariable posX, IntegerVariable posY
            , IntegerVariable clickLength, IntegerVariable numClicks, BooleanVariable useCPS) {
        super(startDelay, function);

        if (clickStartDelay == null || cps == null || posX == null || posY == null
                || clickLength == null || numClicks == null || useCPS == null) {
            throw new IllegalArgumentException("ClickCommand Argument is null");
        }


        this.clickStartDelay = clickStartDelay;
        this.cps = cps;
        this.posX = posX;
        this.posY = posY;
        this.clickLength = clickLength;
        this.numClicks = numClicks;
        this.useCPS = useCPS;
    }

    @Override
    public void run() {
        int delay = !useCPS.getValue() ? 1000 / cps.getValue() - clickLength.getValue() : clickStartDelay.getValue();

        int extraTime = 0;
        long startTime;
        long endTime;
        int properDelayTime;

        for (int x = 0; x < numClicks.getValue(); x++) {
            startTime = System.currentTimeMillis();
            if (!function.isRunning()) {
                return;
            }
            moveMouse();

            properDelayTime = Math.max(delay - extraTime + 1, 1);
            click();
            bot.delay(properDelayTime);
            endTime = System.currentTimeMillis();
            extraTime = (int) (endTime - startTime - properDelayTime);
        }
    }

    protected void click() {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.delay(clickLength.getValue());
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    protected void moveMouse() {
        if (posX.getValue() != -1) {
            bot.mouseMove(posX.getValue(), MouseInfo.getPointerInfo().getLocation().y);
        }
        if (posY.getValue() != -1) {
            bot.mouseMove(MouseInfo.getPointerInfo().getLocation().x, posY.getValue());
        }
    }



    @Override
    public String toString() {
        return String.format("Click - PosX: %s, PosY: %s - NumClicks: %s - Cps: %s - ClickLength: %s"
                , posX.getValue(), posY.getValue(), numClicks.getValue(), cps.getValue(), clickLength.getValue());
    }

    public static RegisteredCommand registerCommand() {
        return new RegisteredCommand("Click", ClickCommand.class, "commands/click.fxml");
    }
}
