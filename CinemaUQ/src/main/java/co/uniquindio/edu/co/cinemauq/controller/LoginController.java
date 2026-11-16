package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.Administrador;
import co.uniquindio.edu.co.cinemauq.Model.Cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtCorreoLogin;

    @FXML
    private PasswordField txtContrasenaLogin;

    @FXML
    private Label lblMensajeLogin;

    private CinemaUQ cinema;

    @FXML
    public void initialize() {
        cinema = CinemaUQ.getInstance();
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {

        String correo = txtCorreoLogin.getText();
        String contrasena = txtContrasenaLogin.getText();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            lblMensajeLogin.setText("Complete todos los campos.");
            return;
        }

        for (Administrador administrador : cinema.getAdministradores()) {
            if (administrador.getCorreoE().equals(correo)
                    && administrador.getContrasena().equals(contrasena)) {

                abrirVista(event, "gestion-funciones-view.fxml");
                return;
            }
        }

        for (Cliente cliente : cinema.getClientes()) {
            if (cliente.getCorreoE().equals(correo)
                    && cliente.getContrasena().equals(contrasena)) {

                abrirVista(event, "peliculas-view.fxml");
                return;
            }
        }

        lblMensajeLogin.setText("Correo o contraseña incorrectos.");
    }

    private void abrirVista(ActionEvent event, String archivo) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co.uniquindio.edu.co.cinemauq/" + archivo)
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void volverInicio(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co.uniquindio.edu.co.cinemauq/inicio-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}