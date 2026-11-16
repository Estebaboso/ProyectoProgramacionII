package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField txtCorreoLogin;
    @FXML private PasswordField txtContrasenaLogin;
    @FXML private Label lblMensajeLogin;
    @FXML private void iniciarSesion(ActionEvent e) {
        String correo = txtCorreoLogin.getText().trim();
        String clave = txtContrasenaLogin.getText();
        for (Administrador admin : CinemaUQ.getInstance().getAdministradores()) {
            if (admin.getCorreoE().equalsIgnoreCase(correo) && admin.getContrasena().equals(clave)) {
                Sesion.cerrar();
                Sesion.administradorActual = admin;
                Navegacion.abrir(e, "menu-administrador-view.fxml");
                return;
            }
        }
        for (Cliente cliente : CinemaUQ.getInstance().getClientes()) {
            if (cliente.getCorreoE().equalsIgnoreCase(correo) && cliente.getContrasena().equals(clave)) {
                Sesion.cerrar();
                Sesion.clienteActual = cliente;
                Navegacion.abrir(e, "menu-cliente-view.fxml");
                return;
            }
        }
        lblMensajeLogin.setText("Correo o contraseña incorrectos.");
    }
    @FXML private void volverInicio(ActionEvent e) { Navegacion.abrir(e, "inicio-view.fxml"); }
}

