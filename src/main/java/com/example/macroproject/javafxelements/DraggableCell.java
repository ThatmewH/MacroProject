package com.example.macroproject.javafxelements;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.controllers.MainController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class DraggableCell extends ListCell<Command> {

    static final DataFormat commandDF = new DataFormat("command");
    static int startIndex = 0;
    static int endIndex = 0;

    public DraggableCell(CommandFunction commandFunction, MainController mainController) {
        super();

        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getItem() != null) {

                    startIndex = getIndex();
                    Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                    dragboard.setDragView(snapshot(null, null));
                    ClipboardContent content = new ClipboardContent();
                    content.putString(getItem().toString());

                    dragboard.setContent(content);
                    event.consume();
                }
            }
        });

        setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            requestFocus();
            event.consume();
        });

        setOnDragEntered(event -> {
            endIndex = getIndex();
            mainController.switchCommand(DraggableCell.startIndex, endIndex);
            startIndex = getIndex();
        });

        setOnDragDone(dragEvent -> {
        });

    }

    @Override
    protected void updateItem(Command command, boolean empty) {
        super.updateItem(command, empty);
        setText(null);
        if (empty) {
            setGraphic(null);
        } else {
            setText(command.toString());
        }
    }
}
