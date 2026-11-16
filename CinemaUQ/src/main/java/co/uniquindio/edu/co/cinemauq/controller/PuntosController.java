package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PuntosController {
    @FXML private Label lblPuntosDisponibles, lblPuntosNecesarios, lblMensajeRedencion;
    @FXML private ComboBox<String> cbTipoRedencion, cbElementoRedencion;
    @FXML private TableView<LotePuntos> tblLotesPuntos;
    @FXML private TableColumn<LotePuntos,String> colFecha,colPuntos,colVencimiento,colEstado;
    @FXML public void initialize() {
        cbTipoRedencion.setItems(FXCollections.observableArrayList("Entrada", "Producto", "Combo"));
        cbTipoRedencion.setOnAction(e -> {
            cbElementoRedencion.getItems().clear();
            lblPuntosNecesarios.setText("Costo en puntos: sin definir");
        });
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getFechaObtencion())));
        colPuntos.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCantidadRestante())));
        colVencimiento.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getFechaVencimiento())));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaVencimiento().isBefore(java.time.LocalDate.now()) ? "Vencido" : "Vigente"));
        actualizarPuntos();
    }
    @FXML private void actualizarPuntos() {
        if (Sesion.clienteActual == null) return;
        lblPuntosDisponibles.setText("Puntos disponibles: " + Sesion.clienteActual.consultarPuntosDisponibles());
        tblLotesPuntos.setItems(FXCollections.observableArrayList(Sesion.clienteActual.getLotesPuntos()));
    }
    @FXML private void redimirPuntos() {
        lblMensajeRedencion.setText("Falta definir en el modelo el costo y la entrega de premios por puntos.");
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-cliente-view.fxml"); }
}
