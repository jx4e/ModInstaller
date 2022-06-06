package com.github.jx4e.installer;

import com.github.jx4e.installer.util.FileUtil;
import com.github.jx4e.installer.util.PopupAlert;
import com.github.jx4e.installer.util.Installation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Arrays;

public class Controller {
    private final String[] actions = new String[]{"Install", "Uninstall"};

    @FXML
    private VBox vbox;

    @FXML
    private Label title;

    @FXML
    private TextField locationEntry;

    @FXML
    private CheckBox defaultButton;

    @FXML
    private ChoiceBox actionMenu;

    @FXML
    private Button goButton;

    public void initialize() {
        title.setText(App.NAME + " v" + App.VER);

        Arrays.stream(actions).forEach(actionMenu.getItems()::add);
    }

    @FXML
    private void onKeyTyped() {
        setGoButtonVisibility();
    }

    @FXML
    private void onDefaultButtonPressed() {
        if (defaultButton.isSelected()) {
            locationEntry.setDisable(true);
            locationEntry.setText(FileUtil.defaultMinecraftDirectory().getAbsolutePath());
        } else {
            locationEntry.setDisable(false);
        }

        setGoButtonVisibility();
    }

    @FXML
    private void onActionSelected() {
        setGoButtonVisibility();
    }

    @FXML
    private void onGoButtonPressed() {
        if (actionMenu.getValue() == null) {
            PopupAlert.display(
                    "Error", "Error Selecting Option",
                    "Please select a valid option!",
                    Alert.AlertType.ERROR
            );
        }

        Installation installation = new Installation(new File(locationEntry.getText()));

        if (actionMenu.getValue().equals("Install")) {
            installation.install();
        } else if (actionMenu.getValue().equals("Uninstall")) {
            installation.uninstall();
        }
    }

    private void setGoButtonVisibility() {
        goButton.setDisable(actionMenu.getValue() == null || locationEntry.getText().equals(""));
    }
}