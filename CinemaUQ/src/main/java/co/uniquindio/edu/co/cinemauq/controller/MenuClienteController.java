package co.uniquindio.edu.co.cinemauq.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuClienteController {
    @FXML private Label lblBienvenida;
    @FXML public void initialize() {
        if (Sesion.clienteActual != null) lblBienvenida.setText("Bienvenido, " + Sesion.clienteActual.getNombre());
    }
    @FXML private void abrirCartelera(ActionEvent e) { Navegacion.abrir(e, "peliculas-view.fxml"); }
    @FXML private void abrirCompra(ActionEvent e) { Navegacion.abrir(e, "seleccion-asientos-view.fxml"); }
    @FXML private void abrirTarjeta(ActionEvent e) { Navegacion.abrir(e, "tarjeta-view.fxml"); }
    @FXML private void abrirMisCompras(ActionEvent e) { Navegacion.abrir(e, "mis-compras-view.fxml"); }
    @FXML private void abrirPuntos(ActionEvent e) { Navegacion.abrir(e, "puntos-view.fxml"); }
    @FXML private void cerrarSesion(ActionEvent e) { Sesion.cerrar(); Navegacion.abrir(e, "inicio-view.fxml"); }
}

