package co.uniquindio.edu.co.cinemauq;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.Administrador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloAplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Obtener instancia de CinemaUQ
        CinemaUQ cinema = CinemaUQ.getInstance();

        // Crear administrador por defecto
        Administrador adminDefault = new Administrador(
                "Admin Principal",
                "01N",
                "3001234567",
                "Admin123",
                "admin@cinemauq.com"
        );

        // Agregar administrador
        cinema.getAdministradores().add(adminDefault);

        // Abrir pantalla inicial
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