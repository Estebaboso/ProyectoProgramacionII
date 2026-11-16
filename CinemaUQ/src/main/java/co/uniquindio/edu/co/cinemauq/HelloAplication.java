package co.uniquindio.edu.co.cinemauq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloAplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                HelloAplication.class.getResource(
                        "/co.uniquindio.edu.co.cinemauq/inicio-view.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("CinemaUQ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
