
package co.uniquindio.edu.co.cinemauq.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InicioController {

    @FXML
    private void abrirLogin(ActionEvent event) {
        Navegacion.abrir(event, "login-view.fxml");
    }

    @FXML
    private void abrirRegistro(ActionEvent event) {
        Navegacion.abrir(event, "registro-view.fxml");
    }

    @FXML
    private void salir() {
        System.exit(0);
    }
}
