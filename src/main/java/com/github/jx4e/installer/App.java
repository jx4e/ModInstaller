package com.github.jx4e.installer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static final String NAME = "Mod Installer";
    public static final String VER = "1.0";
    public static final String MC_VER = "1.18.2";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("panel.fxml"));
        Image icon = new Image(Objects.requireNonNull(App.class.getResourceAsStream("icon.png")));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(NAME + " v" + VER);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}