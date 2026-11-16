package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MisComprasController {
    @FXML private TableView<Compra> tblMisCompras;
    @FXML private TableColumn<Compra,Integer> colId;
    @FXML private TableColumn<Compra,Double> colTotal;
    @FXML private TableColumn<Compra,String> colFecha,colEstado,colDetalle;
    @FXML private Label lblDetalleCompra;
    @FXML public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().toString()));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().getClass().getSimpleName()));
        colDetalle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEntradas().size() + " entradas, " + c.getValue().getProductos().size() + " productos"));
        tblMisCompras.getSelectionModel().selectedItemProperty().addListener((obs,old,c) -> {
            if (c != null) lblDetalleCompra.setText("Compra " + c.getId() + ", total $" + c.getTotal());
        });
        actualizarCompras();
    }
    @FXML private void actualizarCompras() {
        if (Sesion.clienteActual != null)
            tblMisCompras.setItems(FXCollections.observableArrayList(Sesion.clienteActual.getCompras()));
    }
    @FXML private void cancelarCompra() {
        Compra c = tblMisCompras.getSelectionModel().getSelectedItem();
        if (c == null) { Navegacion.mensaje("Selecciona una compra."); return; }
        if (c.getEstado() instanceof co.uniquindio.edu.co.cinemauq.Comportamentales.StateCancelada) {
            Navegacion.mensaje("La compra ya está cancelada."); return;
        }
        c.cancelar();
        actualizarCompras();
        Navegacion.mensaje("Solicitud procesada. Comprueba el estado y los movimientos de tu tarjeta.");
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-cliente-view.fxml"); }
}

