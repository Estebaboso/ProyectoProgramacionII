package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class GestionFuncionesController {

    @FXML
    private TextField txtIdPelicula;
    @FXML
    private TextField txtNombrePelicula;
    @FXML
    private TextField txtGeneroPelicula;
    @FXML
    private TextField txtDuracionPelicula;
    @FXML
    private ComboBox<RestriccionPelicula> cbRestriccionPelicula;
    @FXML
    private TextArea txtSinopsisPelicula;

    @FXML
    private TextField txtNumeroSala;
    @FXML
    private TextField txtFilasSala;
    @FXML
    private TextField txtColumnasSala;

    @FXML
    private TextField txtIdFuncion;
    @FXML
    private ComboBox<Pelicula> cbPeliculaFuncion;
    @FXML
    private ComboBox<Sala> cbSalaFuncion;
    @FXML
    private DatePicker dpFechaFuncion;
    @FXML
    private TextField txtHoraInicioFuncion;
    @FXML
    private TextField txtPrecioFuncion;
    @FXML
    private ComboBox<TipoFuncion> cbTipoFuncion;
    @FXML
    private ComboBox<EstadoFuncion> cbEstadoFuncion;

    @FXML
    private TableView<Funcion> tblFunciones;
    @FXML
    private TableColumn<Funcion, String> colPelicula;
    @FXML
    private TableColumn<Funcion, Integer> colSala;
    @FXML
    private TableColumn<Funcion, LocalDate> colFecha;
    @FXML
    private TableColumn<Funcion, LocalTime> colHoraInicio;
    @FXML
    private TableColumn<Funcion, TipoFuncion> colTipo;
    @FXML
    private TableColumn<Funcion, Double> colPrecio;
    @FXML
    private TableColumn<Funcion, EstadoFuncion> colEstado;

    private CinemaUQ cinema;
    private ObservableList<Funcion> listaFunciones;

    @FXML
    public void initialize() {

        cinema = CinemaUQ.getInstance();

        cbRestriccionPelicula.setItems(
                FXCollections.observableArrayList(RestriccionPelicula.values())
        );

        cbTipoFuncion.setItems(
                FXCollections.observableArrayList(TipoFuncion.values())
        );

        cbEstadoFuncion.setItems(
                FXCollections.observableArrayList(EstadoFuncion.values())
        );

        cargarCombos();
        configurarTabla();
        cargarFunciones();
    }

    private void cargarCombos() {
        cbPeliculaFuncion.setItems(
                FXCollections.observableArrayList(cinema.getPeliculas())
        );

        cbSalaFuncion.setItems(
                FXCollections.observableArrayList(cinema.getSalas())
        );
    }

    private void configurarTabla() {

        colPelicula.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleStringProperty(
                        celda.getValue().getPelicula().getNombre()
                )
        );

        colSala.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleObjectProperty<>(
                        celda.getValue().getSala().getNumSala()
                )
        );

        colFecha.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleObjectProperty<>(
                        celda.getValue().getFecha()
                )
        );

        colHoraInicio.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleObjectProperty<>(
                        celda.getValue().getHoraInicio()
                )
        );

        colTipo.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleObjectProperty<>(
                        celda.getValue().getTipoFuncion()
                )
        );

        colPrecio.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleObjectProperty<>(
                        celda.getValue().getPrecio()
                )
        );

        colEstado.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleObjectProperty<>(
                        celda.getValue().getEstadoFuncion()
                )
        );
    }

    private void cargarFunciones() {
        listaFunciones = FXCollections.observableArrayList(cinema.getFunciones());
        tblFunciones.setItems(listaFunciones);
    }

    @FXML
    private void registrarPelicula() {

        try {
            int id = Integer.parseInt(txtIdPelicula.getText());
            int duracion = Integer.parseInt(txtDuracionPelicula.getText());

            String nombre = txtNombrePelicula.getText();
            String genero = txtGeneroPelicula.getText();

            RestriccionPelicula restriccion = cbRestriccionPelicula.getValue();

            if (nombre.isEmpty() || genero.isEmpty() || restriccion == null) {
                mostrarMensaje("Complete todos los campos de la película.");
                return;
            }

            for (Pelicula pelicula : cinema.getPeliculas()) {
                if (pelicula.getId() == id) {
                    mostrarMensaje("Ya existe una película con ese ID.");
                    return;
                }
            }

            Pelicula pelicula = new Pelicula(
                    nombre, id, genero, duracion, restriccion
            );

            cinema.getPeliculas().add(pelicula);

            cbPeliculaFuncion.getItems().add(pelicula);

            mostrarMensaje("Película registrada correctamente.");
            limpiarCamposPelicula();

        } catch (NumberFormatException e) {
            mostrarMensaje("El ID y la duración deben ser números.");
        }
    }

    @FXML
    private void registrarSala() {

        try {
            int numero = Integer.parseInt(txtNumeroSala.getText());
            int filas = Integer.parseInt(txtFilasSala.getText());
            int columnas = Integer.parseInt(txtColumnasSala.getText());

            if (filas <= 0 || columnas <= 0) {
                mostrarMensaje("Las filas y columnas deben ser mayores que cero.");
                return;
            }

            for (Sala sala : cinema.getSalas()) {
                if (sala.getNumSala() == numero) {
                    mostrarMensaje("Ya existe una sala con ese número.");
                    return;
                }
            }

            Sala sala = new Sala(numero);

            for (int fila = 1; fila <= filas; fila++) {
                for (int columna = 1; columna <= columnas; columna++) {

                    String codigo = (char) ('A' + fila - 1) + String.valueOf(columna);

                    sala.getAsientos().add(
                            new Asiento(codigo, fila, columna)
                    );
                }
            }

            cinema.getSalas().add(sala);
            cbSalaFuncion.getItems().add(sala);

            mostrarMensaje("Sala registrada correctamente.");
            txtNumeroSala.clear();
            txtFilasSala.clear();
            txtColumnasSala.clear();

        } catch (NumberFormatException e) {
            mostrarMensaje("Los datos de la sala deben ser números.");
        }
    }

    @FXML
    private void registrarFuncion() {

        try {
            int id = Integer.parseInt(txtIdFuncion.getText());
            double precio = Double.parseDouble(txtPrecioFuncion.getText());

            Pelicula pelicula = cbPeliculaFuncion.getValue();
            Sala sala = cbSalaFuncion.getValue();
            LocalDate fecha = dpFechaFuncion.getValue();

            LocalTime hora = LocalTime.parse(txtHoraInicioFuncion.getText());

            TipoFuncion tipo = cbTipoFuncion.getValue();
            EstadoFuncion estado = cbEstadoFuncion.getValue();

            if (pelicula == null || sala == null || fecha == null
                    || tipo == null || estado == null) {
                mostrarMensaje("Complete todos los campos de la función.");
                return;
            }

            if (precio <= 0) {
                mostrarMensaje("El precio debe ser mayor que cero.");
                return;
            }

            for (Funcion funcion : cinema.getFunciones()) {
                if (funcion.getId() == id) {
                    mostrarMensaje("Ya existe una función con ese ID.");
                    return;
                }
            }

            Funcion funcion = new Funcion(
                    id, pelicula, sala, fecha, hora, precio, estado, tipo
            );

            cinema.getFunciones().add(funcion);

            listaFunciones.add(funcion);

            mostrarMensaje("Función registrada correctamente.");
            limpiarCamposFuncion();

        } catch (NumberFormatException e) {
            mostrarMensaje("El ID y el precio deben ser números.");
        } catch (java.time.format.DateTimeParseException e) {
            mostrarMensaje("La hora debe tener el formato HH:mm.");
        }
    }

    @FXML
    private void cancelarFuncion() {

        Funcion funcion = tblFunciones.getSelectionModel().getSelectedItem();

        if (funcion == null) {
            mostrarMensaje("Seleccione una función de la tabla.");
            return;
        }

        if (funcion.getEstadoFuncion() == EstadoFuncion.CANCELADA) {
            mostrarMensaje("Esta función ya está cancelada.");
            return;
        }

        funcion.cancelar();
        tblFunciones.refresh();

        mostrarMensaje("Función cancelada correctamente.");
    }

    private void limpiarCamposPelicula() {
        txtIdPelicula.clear();
        txtNombrePelicula.clear();
        txtGeneroPelicula.clear();
        txtDuracionPelicula.clear();
        txtSinopsisPelicula.clear();
        cbRestriccionPelicula.setValue(null);
    }

    private void limpiarCamposFuncion() {
        txtIdFuncion.clear();
        txtHoraInicioFuncion.clear();
        txtPrecioFuncion.clear();
        dpFechaFuncion.setValue(null);
        cbPeliculaFuncion.setValue(null);
        cbSalaFuncion.setValue(null);
        cbTipoFuncion.setValue(null);
        cbEstadoFuncion.setValue(null);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("CinemaUQ");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
        Navegacion.abrir(event, "menu-administrador-view.fxml");
    }
}