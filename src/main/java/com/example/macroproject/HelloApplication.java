package com.example.macroproject;

import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.variables.Variable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Matthew's Macro");
        stage.setScene(scene);
        stage.show();
        CommandFunction.CreateMainFunction();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop(){
        System.out.println("Exiting App");
        // Save file

    }
}