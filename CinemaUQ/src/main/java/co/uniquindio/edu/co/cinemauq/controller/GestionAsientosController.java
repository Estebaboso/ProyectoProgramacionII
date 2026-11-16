package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionAsientosController {
    @FXML private ComboBox<Sala> cbSala;
    @FXML private TextField txtFila,txtColumna;
    @FXML private TableView<Asiento> tblAsientos;
    @FXML private TableColumn<Asiento,String> colCodigo,colEstado;
    @FXML private TableColumn<Asiento,Integer> colFila,colColumna;
    @FXML public void initialize() {
        cbSala.setItems(FXCollections.observableArrayList(CinemaUQ.getInstance().getSalas()));
        cbSala.setCellFactory(c -> new ListCell<>() { @Override protected void updateItem(Sala sala,boolean empty) {
            super.updateItem(sala,empty); setText(empty || sala == null ? null : "Sala " + sala.getNumSala()); }});
        cbSala.setButtonCell(new ListCell<>() { @Override protected void updateItem(Sala sala,boolean empty) {
            super.updateItem(sala,empty); setText(empty || sala == null ? null : "Sala " + sala.getNumSala()); }});
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colFila.setCellValueFactory(new PropertyValueFactory<>("fila"));
        colColumna.setCellValueFactory(new PropertyValueFactory<>("columna"));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty("Creado"));
        cbSala.setOnAction(e -> cargarAsientos());
    }
    @FXML private void cargarAsientos() {
        Sala sala = cbSala.getValue();
        if (sala != null) tblAsientos.setItems(FXCollections.observableArrayList(sala.getAsientos()));
    }
    @FXML private void agregarAsiento() {
        Sala sala = cbSala.getValue();
        if (sala == null) { Navegacion.mensaje("Selecciona una sala."); return; }
        try {
            int fila = Integer.parseInt(txtFila.getText().trim());
            int columna = Integer.parseInt(txtColumna.getText().trim());
            if (fila < 1 || columna < 1) { Navegacion.mensaje("Fila y columna deben ser positivas."); return; }
            String codigo = "F" + fila + "C" + columna;
            for (Asiento a : sala.getAsientos()) if (a.getCodigo().equals(codigo)) {
                Navegacion.mensaje("Ese asiento ya existe."); return;
            }
            sala.getAsientos().add(new Asiento(codigo,fila,columna));
            cargarAsientos();
        } catch (NumberFormatException ex) { Navegacion.mensaje("Fila y columna deben ser números enteros."); }
    }
    @FXML private void eliminarAsiento() {
        Sala sala = cbSala.getValue();
        Asiento a = tblAsientos.getSelectionModel().getSelectedItem();
        if (sala == null || a == null) { Navegacion.mensaje("Selecciona un asiento."); return; }
        for (Funcion f : CinemaUQ.getInstance().getFunciones()) {
            if (f.getSala() == sala && f.getAsientosReservados().contains(a.getCodigo())) {
                Navegacion.mensaje("No puedes eliminar un asiento reservado."); return;
            }
        }
        sala.getAsientos().remove(a);
        cargarAsientos();
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-administrador-view.fxml"); }
}

