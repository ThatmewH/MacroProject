package com.example.macroproject;

import com.example.macroproject.commands.CommandFunction;
import com.example.macroproject.variables.ImageVariable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.highgui.HighGui;
import org.opencv.highgui.ImageWindow;

import java.io.IOException;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        CommandFunction.CreateMainFunction();
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("OpenCV Macro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop(){
        HighGui.destroyAllWindows();
        HighGui.waitKey();
    }
}