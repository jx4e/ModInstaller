package com.github.jx4e.installer.util;

import javafx.scene.control.Alert;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public record Installation(File minecraftDir) {
    public boolean install() {
        if (!FileUtil.hasFabric(FileUtil.defaultVersionsDirectory())) {
            PopupAlert.displayConfirmation("Fabric Not Installed", "Fabric not installed",
                    "Would you like to install fabric?",
                    () -> {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            try {
                                Desktop.getDesktop().browse(new URI("https://fabricmc.net/use/installer/"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            return false;
        }

        if (!FileUtil.hasFabricApi(FileUtil.defaultModsDirectory())) {
            PopupAlert.displayConfirmation("Fabric API Not Installed", "Fabric API not installed",
                    "Would you like to install Fabric API?",
                    () -> {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            try {
                                Desktop.getDesktop().browse(new URI("https://www.curseforge.com/minecraft/mc-mods/fabric-api"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            return false;
        }

        try {
            URL url = new URL(
                    "https://maven.fabricmc.net/net/fabricmc/fabric-installer/0.10.2/fabric-installer-0.10.2.jar");

            File mods = new File(minecraftDir, "mods");

            if (!mods.isDirectory()) {
                mods.delete();
                mods.mkdir();
            }

            File mod = new File(mods, "mod.jar");

            if (!downloadFile(url, mod)) {
                PopupAlert.display(
                        "Failed To install", "Failed To install",
                        "The mod has not been successfully installed",
                        Alert.AlertType.ERROR
                );

                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        PopupAlert.display(
                "Installation Complete", "Installation Complete",
                "The mod has been successfully installed at: " + minecraftDir.getAbsolutePath(),
                Alert.AlertType.INFORMATION
        );

        return true;
    }

    public boolean uninstall() {
        File mods = new File(minecraftDir, "mods");

        if (mods.isDirectory()) {
            File mod = new File(mods, "mod.jar");
            mod.delete();
        }

        PopupAlert.display(
                "Uninstallation Complete", "Uninstallation Complete",
                "The mod has been successfully uninstalled from: " + minecraftDir.getAbsolutePath(),
                Alert.AlertType.INFORMATION
        );

        return true;
    }

    private boolean downloadFile(URL inputURL, File outputFile) {
        if (inputURL == null || outputFile == null) return false;

        try (BufferedInputStream in = new BufferedInputStream(inputURL.openStream());
             FileOutputStream out = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
