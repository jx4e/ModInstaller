package com.github.jx4e.installer.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PopupAlert {
    public static void display(String title, String header, String body, Alert.AlertType type) {
        try {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(body);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void display(String title, String header, String body, Alert.AlertType type, Runnable ok) {
        try {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(body);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) ok.run();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayConfirmation(String title, String header, String body, Runnable ok) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(body);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) ok.run();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
