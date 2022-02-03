module com.example.macroproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.datatransfer;
    requires java.desktop;
    requires jnativehook;

    opens com.example.macroproject to javafx.fxml;
    opens com.example.macroproject.commands to javafx.fxml;
    opens com.example.macroproject.controllers to javafx.fxml;
    opens com.example.macroproject.controllers.commands to javafx.fxml;
    opens com.example.macroproject.controllers.listeners to javafx.fxml;
    exports com.example.macroproject;
    exports com.example.macroproject.commands;
    exports com.example.macroproject.variables;
    exports com.example.macroproject.listener;
}