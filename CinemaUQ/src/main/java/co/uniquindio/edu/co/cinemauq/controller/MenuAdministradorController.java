package co.uniquindio.edu.co.cinemauq.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuAdministradorController {
    @FXML private void abrirGestionFunciones(ActionEvent e) { Navegacion.abrir(e, "gestion-funciones-view.fxml"); }
    @FXML private void abrirGestionAsientos(ActionEvent e) { Navegacion.abrir(e, "gestion-asientos-view.fxml"); }
    @FXML private void abrirConfiteria(ActionEvent e) { Navegacion.abrir(e, "gestion-confiteria-view.fxml"); }
    @FXML private void abrirGestionClientes(ActionEvent e) { Navegacion.abrir(e, "gestion-clientes-view.fxml"); }
    @FXML private void abrirReportes(ActionEvent e) { Navegacion.abrir(e, "reportes-view.fxml"); }
    @FXML private void abrirReportesVisuales(ActionEvent e) { Navegacion.abrir(e, "reportes-visuales-view.fxml"); }
    @FXML private void cerrarSesion(ActionEvent e) { Sesion.cerrar(); Navegacion.abrir(e, "inicio-view.fxml"); }
}

