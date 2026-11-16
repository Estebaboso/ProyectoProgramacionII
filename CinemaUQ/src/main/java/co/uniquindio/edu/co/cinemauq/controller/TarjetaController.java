package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

public class TarjetaController {
    @FXML private Label lblNumeroTarjeta,lblEstadoTarjeta,lblSaldo;
    @FXML private TableView<Movimiento> tblMovimientos;
    @FXML private TableColumn<Movimiento,String> colFecha,colTipo,colCompra;
    @FXML private TableColumn<Movimiento,Double> colMonto;
    @FXML public void initialize() {
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().fecha().toString()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().tipo().toString()));
        colMonto.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().valor()));
        colCompra.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().compra() == null ? "-" : String.valueOf(c.getValue().compra().getId())));
        actualizarTarjeta();
    }
    @FXML private void actualizarTarjeta() {
        if (Sesion.clienteActual == null) return;
        TarjetaVirtual tarjeta = Sesion.clienteActual.getTarjeta();
        lblNumeroTarjeta.setText("Número: " + tarjeta.getNumTarjeta());
        lblEstadoTarjeta.setText("Estado: " + tarjeta.getEstadoTarjeta());
        lblSaldo.setText("Saldo: $" + tarjeta.getSaldo());
        tblMovimientos.setItems(FXCollections.observableArrayList(tarjeta.getMovimientos()));
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-cliente-view.fxml"); }
}

