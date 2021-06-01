package me.blf.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileViewer {
    public static String getFileText(Path path){
        try {
            return Files.lines(path)
                    .collect(Collectors.joining("\r\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
