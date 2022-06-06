package com.github.jx4e.installer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.jx4e.installer.App.MC_VER;
import static java.lang.System.getProperty;

public class FileUtil {
    public static FileType findFileType(File file) {
        if (!file.exists()) return FileType.EMPTY;
        if (file.isDirectory()) return FileType.DIR;
        if (file.isFile()) return FileType.FILE;
        else return FileType.HIDDEN;
    }

    public static File defaultMinecraftDirectory() {
        return new File(getProperty("user.home") + "/AppData/Roaming/.minecraft");
    }

    public static File defaultModsDirectory() {
        File parent = defaultMinecraftDirectory();
        return new File(parent, "mods");
    }

    public static File defaultVersionsDirectory() {
        File parent = defaultMinecraftDirectory();
        return new File(parent, "versions");
    }

    public static File defaultDirectory(String dir) {
        File parent = defaultMinecraftDirectory();
        return new File(parent, dir);
    }

    public static List<File> getDirectoryContents(File file) throws FileNotFoundException {
        if (findFileType(file) != FileType.DIR) throw new FileNotFoundException();

        return Arrays.asList(Objects.requireNonNull(file.listFiles()));
    }

    public static boolean hasFabricApi(File modsDir) {
        try {
           return getDirectoryContents(modsDir).stream()
                   .anyMatch(file -> file.getName().contains("fabric-api")  && file.getName().contains(MC_VER));
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean hasFabric(File versions) {
        try {
            return getDirectoryContents(versions).stream()
                    .anyMatch(file -> file.getName().contains("fabric-loader") && file.getName().contains(MC_VER));
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public enum FileType {
        DIR, FILE, HIDDEN, EMPTY
    }
}
