package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.UUID;

public class RegistroController {
    @FXML private TextField txtNombre, txtId, txtTelefono, txtCorreo;
    @FXML private PasswordField txtContrasena, txtConfirmarContrasena;
    @FXML private Label lblMensaje;

    @FXML private void registrarCliente(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String id = txtId.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String clave = txtContrasena.getText();
        if (nombre.isEmpty() || id.isEmpty() || telefono.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Debes completar todos los campos."); return;
        }
        if (!clave.equals(txtConfirmarContrasena.getText())) {
            lblMensaje.setText("Las contraseñas no coinciden."); return;
        }
        CinemaUQ cinema = CinemaUQ.getInstance();
        TarjetaVirtual tarjeta = new TarjetaVirtual(UUID.randomUUID().toString().substring(0, 8), 0, EstadoTarjetaVirtual.ACTIVA);
        Cliente cliente = new Cliente(nombre, id, telefono, clave, correo, tarjeta);
        if (!cinema.registrarCliente(cliente)) {
            lblMensaje.setText("Documento o correo ya registrado.");
            return;
        }
        Navegacion.mensaje("Cliente registrado. Ahora puedes iniciar sesión.");
        Navegacion.abrir(e, "login-view.fxml");
    }
    @FXML private void volverInicio(ActionEvent e) { Navegacion.abrir(e, "inicio-view.fxml"); }
}

