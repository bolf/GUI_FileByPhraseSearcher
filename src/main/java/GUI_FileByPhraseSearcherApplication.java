import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GUI_FileByPhraseSearcherApplication extends Application {

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainForm.fxml"));
        AnchorPane root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Ищем файлы");
        stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("images/icon.png")));
        stage.show();

        MainFormController controller = loader.getController();
        controller.setPrimaryStage(stage);
    }
}

