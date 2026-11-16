package co.uniquindio.edu.co.cinemauq.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class Navegacion {
    public static void abrir(ActionEvent event, String vista) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource(
                    "/co.uniquindio.edu.co.cinemauq/" + vista));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("CinemaUQ");
            stage.show();
        } catch (IOException | IllegalStateException e) {
            mensaje("No se pudo abrir " + vista + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void mensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("CinemaUQ");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

