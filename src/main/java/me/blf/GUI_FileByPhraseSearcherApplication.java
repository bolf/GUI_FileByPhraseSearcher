package me.blf;

import me.blf.controllers.MainFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GUI_FileByPhraseSearcherApplication extends Application {
    private final String iconPath = "images/icon.png";
    private final String formPath = "fxml/mainForm.fxml";

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(formPath));

        //FXMLLoader loader = new FXMLLoader(getClass().getResource(formPath));
        AnchorPane root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Ищем файлы");
        stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream(iconPath)));
        stage.show();

        MainFormController controller = loader.getController();
        controller.setPrimaryStage(stage);
    }
}

