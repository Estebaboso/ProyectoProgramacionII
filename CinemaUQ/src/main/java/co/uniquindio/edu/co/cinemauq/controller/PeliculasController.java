
package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.Pelicula;
import co.uniquindio.edu.co.cinemauq.Model.Funcion;
import co.uniquindio.edu.co.cinemauq.Model.EstadoFuncion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class PeliculasController {

    @FXML private TextField txtBuscarPelicula;

    @FXML private TableView<Pelicula> tblPeliculas;
    @FXML private TableColumn<Pelicula, Integer> colId;
    @FXML private TableColumn<Pelicula, String> colNombre;
    @FXML private TableColumn<Pelicula, String> colGenero;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, String> colRestriccion;

    private CinemaUQ cinema;
    private ObservableList<Pelicula> listaPeliculas;

    @FXML
    public void initialize() {
        cinema = CinemaUQ.getInstance();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colRestriccion.setCellValueFactory(new PropertyValueFactory<>("restriccion"));

        listaPeliculas = FXCollections.observableArrayList(cinema.getPeliculas());

        tblPeliculas.setItems(listaPeliculas);
    }

    @FXML
    private void buscarPelicula() {
        String nombreBuscado = txtBuscarPelicula.getText().trim().toLowerCase();

        ObservableList<Pelicula> peliculasEncontradas =
                FXCollections.observableArrayList();

        for (Pelicula pelicula : cinema.getPeliculas()) {
            if (pelicula.getNombre().toLowerCase().contains(nombreBuscado)) {
                peliculasEncontradas.add(pelicula);
            }
        }

        tblPeliculas.setItems(peliculasEncontradas);

        if (peliculasEncontradas.isEmpty()) {
            mostrarMensaje("No se encontraron películas con ese nombre.");
        }
    }

    @FXML
    private void verFunciones() {
        Pelicula peliculaSeleccionada =
                tblPeliculas.getSelectionModel().getSelectedItem();

        if (peliculaSeleccionada == null) {
            mostrarMensaje("Seleccione una película de la tabla.");
            return;
        }

        StringBuilder texto = new StringBuilder();

        for (Funcion funcion : cinema.getFunciones()) {
            if (funcion.getPelicula() == peliculaSeleccionada
                    && funcion.getEstadoFuncion() == EstadoFuncion.ACTIVA) {

                texto.append("Función: ").append(funcion.getId()).append("\n");
                texto.append("Sala: ").append(funcion.getSala().getNumSala()).append("\n");
                texto.append("Fecha: ").append(funcion.getFecha()).append("\n");
                texto.append("Hora: ").append(funcion.getHoraInicio()).append("\n");
                texto.append("Tipo: ").append(funcion.getTipoFuncion()).append("\n");
                texto.append("Precio: $").append(funcion.getPrecio()).append("\n");
                texto.append("------------------------------\n");
            }
        }

        if (texto.length() == 0) {
            mostrarMensaje("Esta película no tiene funciones activas.");
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Funciones disponibles");
            alerta.setHeaderText("Funciones de " + peliculaSeleccionada.getNombre());
            alerta.setContentText(texto.toString());
            alerta.getDialogPane().setMinWidth(400);
            alerta.showAndWait();
        }
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
        Navegacion.abrir(event, "menu-cliente-view.fxml");
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("CinemaUQ");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
