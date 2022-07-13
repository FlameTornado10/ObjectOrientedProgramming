package ucas.oop.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends javafx.application.Application {
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        URL location = getClass().getResource("MainInterface.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);
        Scene scene = new Scene(loader.load(), 1200, 800);
        NoteBookOperation controller = loader.getController();
        controller.setApp(this);
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Simple Notebook");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public boolean showPersonEditDialog(String url) {
        try {
            URL location = getClass().getResource("UploadDialog.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            Scene scene = new Scene(loader.load(), 400, 300);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Upload successfully");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UploadController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUrl(url);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}