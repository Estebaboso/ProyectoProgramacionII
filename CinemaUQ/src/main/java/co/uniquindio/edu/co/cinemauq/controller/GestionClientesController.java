package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

public class GestionClientesController {
    @FXML private TextField txtBuscarCliente, txtMontoRecarga;
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente,String> colId,colNombre,colCorreo,colTelefono;
    @FXML private Label lblClienteSeleccionado,lblSaldoActual,lblMensajeRecarga;
    @FXML private TableView<Compra> tblCompras;
    @FXML private TableColumn<Compra,Integer> colIdCompra;
    @FXML private TableColumn<Compra,String> colClienteCompra,colFechaCompra,colEstadoCompra;
    @FXML private TableColumn<Compra,Double> colTotalCompra;

    @FXML public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoE"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colIdCompra.setCellValueFactory(new PropertyValueFactory<>("id"));
        colClienteCompra.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCliente().getNombre()));
        colFechaCompra.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().toString()));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<>("total"));
        colEstadoCompra.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().getClass().getSimpleName()));
        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs,anterior,cliente) -> {
            if (cliente != null) {
                lblClienteSeleccionado.setText("Cliente: " + cliente.getNombre());
                lblSaldoActual.setText("Saldo: $" + cliente.getTarjeta().getSaldo());
            }
        });
        buscarCliente();
        tblCompras.setItems(FXCollections.observableArrayList(CinemaUQ.getInstance().getCompras()));
    }
    @FXML private void buscarCliente() {
        String buscado = txtBuscarCliente.getText().trim().toLowerCase();
        var resultado = FXCollections.<Cliente>observableArrayList();
        for (Cliente c : CinemaUQ.getInstance().getClientes())
            if (c.getId().toLowerCase().contains(buscado) || c.getNombre().toLowerCase().contains(buscado)
                    || c.getCorreoE().toLowerCase().contains(buscado)) resultado.add(c);
        tblClientes.setItems(resultado);
    }
    @FXML private void recargarTarjeta() {
        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) { lblMensajeRecarga.setText("Selecciona un cliente."); return; }
        try {
            double monto = Double.parseDouble(txtMontoRecarga.getText().trim());
            if (!Double.isFinite(monto) || monto <= 0) { lblMensajeRecarga.setText("Monto inválido."); return; }
            TarjetaVirtual tarjeta = cliente.getTarjeta();
            if (tarjeta == null || !tarjeta.recargar(monto)) {
                lblMensajeRecarga.setText("La tarjeta no permite esta recarga."); return;
            }
            lblSaldoActual.setText("Saldo: $" + tarjeta.getSaldo());
            lblMensajeRecarga.setText("Recarga registrada correctamente.");
            txtMontoRecarga.clear();
        } catch (NumberFormatException ex) { lblMensajeRecarga.setText("Escribe un monto numérico."); }
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-administrador-view.fxml"); }
}

