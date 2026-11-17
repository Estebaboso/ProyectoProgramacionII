package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;
import co.uniquindio.edu.co.cinemauq.Creacionales.Entrada2D;
import co.uniquindio.edu.co.cinemauq.Creacionales.Entrada3D;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StatePagada;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SeleccionAsientosController {
    @FXML private ComboBox<Funcion> cbFuncion;
    @FXML private Label lblPelicula,lblSala,lblFecha,lblAsientosSeleccionados,lblTotalEntradas;
    @FXML private GridPane gridAsientos;

    @FXML public void initialize() {
        var activas = FXCollections.<Funcion>observableArrayList();
        for (Funcion f: CinemaUQ.getInstance().getFunciones()) {
            if (f.getEstadoFuncion() == EstadoFuncion.ACTIVA
                    && f.getFecha().atTime(f.getHoraInicio()).isAfter(LocalDateTime.now())) activas.add(f);
        }
        cbFuncion.setItems(activas);
        cbFuncion.setCellFactory(c -> new ListCell<>() {
            @Override protected void updateItem(Funcion f, boolean empty) {
                super.updateItem(f,empty); setText(empty || f == null ? null : f.getPelicula().getNombre() + " - " + f.getFecha() + " " + f.getHoraInicio());
            }
        });
        cbFuncion.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Funcion f, boolean empty) {
                super.updateItem(f,empty); setText(empty || f == null ? null : f.getPelicula().getNombre() + " - " + f.getFecha() + " " + f.getHoraInicio());
            }
        });
        cbFuncion.setOnAction(e -> mostrarAsientos());
        if (Sesion.funcionSeleccionada != null && activas.contains(Sesion.funcionSeleccionada)) cbFuncion.setValue(Sesion.funcionSeleccionada);
    }
    @FXML private void mostrarAsientos() {
        Funcion f = cbFuncion.getValue();
        gridAsientos.getChildren().clear();
        Sesion.asientosSeleccionados.clear();
        if (f == null) return;
        Sesion.funcionSeleccionada = f;
        lblPelicula.setText("Película: " + f.getPelicula().getNombre());
        lblSala.setText("Sala: " + f.getSala().getNumSala());
        lblFecha.setText("Fecha: " + f.getFecha() + "  " + f.getHoraInicio());
        for (Asiento asiento : f.getSala().getAsientos()) {
            Button boton = new Button(asiento.getCodigo());
            boton.setPrefWidth(85);
            boolean ocupado = f.getAsientosReservados().contains(asiento.getCodigo());
            boton.setDisable(ocupado);
            boton.setStyle(ocupado ? "-fx-background-color:#dc3545; -fx-text-fill:white" : "-fx-background-color:#28a745; -fx-text-fill:white");
            boton.setOnAction(e -> {
                if (Sesion.asientosSeleccionados.contains(asiento)) {
                    Sesion.asientosSeleccionados.remove(asiento);
                    boton.setStyle("-fx-background-color:#28a745; -fx-text-fill:white");
                } else {
                    Sesion.asientosSeleccionados.add(asiento);
                    boton.setStyle("-fx-background-color:#007bff; -fx-text-fill:white");
                }
                actualizarSeleccion();
            });
            gridAsientos.add(boton,Math.max(0,asiento.getColumna()-1),Math.max(0,asiento.getFila()-1));
        }
        actualizarSeleccion();
    }
    private void actualizarSeleccion() {
        lblAsientosSeleccionados.setText("Asientos seleccionados: " + Sesion.asientosSeleccionados.stream().map(Asiento::getCodigo).toList());
        lblTotalEntradas.setText("Total entradas: $" + (Sesion.asientosSeleccionados.size() * Sesion.funcionSeleccionada.getPrecio()));
    }
    @FXML private void continuarCompra(ActionEvent e) {
        if (Sesion.clienteActual == null) { Navegacion.mensaje("Inicia sesión como cliente."); return; }
        if (Sesion.funcionSeleccionada == null || Sesion.asientosSeleccionados.isEmpty()) { Navegacion.mensaje("Selecciona al menos un asiento."); return; }
        if (Sesion.redimiendoEntrada) {
            redimirEntrada(e);
            return;
        }
        Navegacion.abrir(e,"compra-view.fxml");
    }
    private void redimirEntrada(ActionEvent e) {
        CinemaUQ cine = CinemaUQ.getInstance();
        Funcion funcion = Sesion.funcionSeleccionada;
        Cliente cliente = Sesion.clienteActual;
        int costo = cine.getPuntosEntrada();

        if (Sesion.asientosSeleccionados.size() != 1) {
            Navegacion.mensaje("Selecciona un solo asiento por redención.");
            return;
        }
        if (funcion.getEstadoFuncion() != EstadoFuncion.ACTIVA
                || !cine.getFunciones().contains(funcion)
                || !funcion.getFecha().atTime(funcion.getHoraInicio()).isAfter(LocalDateTime.now())) {
            Navegacion.mensaje("La función ya no está disponible.");
            return;
        }
        Asiento asiento = Sesion.asientosSeleccionados.get(0);
        if (funcion.getAsientosReservados().contains(asiento.getCodigo())) {
            Navegacion.mensaje("El asiento ya está reservado.");
            mostrarAsientos();
            return;
        }
        if (cliente.consultarPuntosDisponibles() < costo) {
            Navegacion.mensaje("No tienes suficientes puntos.");
            return;
        }

        int idCompra = 1;
        int idEntrada = 1;
        for (Compra compra : cine.getCompras()) {
            if (compra.getId() >= idCompra) idCompra = compra.getId() + 1;
            for (Entrada entrada : compra.getEntradas()) {
                if (entrada.getId() >= idEntrada) idEntrada = entrada.getId() + 1;
            }
        }

        Entrada entrada;
        if (funcion.getTipoFuncion() == TipoFuncion.TRES_D) {
            entrada = new Entrada3D(idEntrada, funcion, asiento, 0);
        } else {
            entrada = new Entrada2D(idEntrada, funcion, asiento, 0);
        }
        ArrayList<Entrada> entradas = new ArrayList<>();
        entradas.add(entrada);
        Compra compra = new Compra(idCompra, cliente, LocalDateTime.now(),
                entradas, new ArrayList<>(), null);
        compra.setSubtotal(0);
        compra.setIva(0);
        compra.setTotal(0);
        compra.setRedimidaConPuntos(true);
        compra.setEstado(new StatePagada(compra));

        if (!cliente.redimirPuntos(costo)) {
            Navegacion.mensaje("No se pudieron descontar los puntos.");
            return;
        }
        funcion.getAsientosReservados().add(asiento.getCodigo());
        cliente.getCompras().add(compra);
        cine.getCompras().add(compra);
        Sesion.redimiendoEntrada = false;
        Sesion.asientosSeleccionados.clear();
        Sesion.funcionSeleccionada = null;
        Navegacion.mensaje("Entrada redimida correctamente. Compra #" + idCompra);
        Navegacion.abrir(e, "mis-compras-view.fxml");
    }

    @FXML private void volverMenu(ActionEvent e) {
        Sesion.redimiendoEntrada = false;
        Sesion.asientosSeleccionados.clear();
        Sesion.funcionSeleccionada = null;
        Navegacion.abrir(e,"menu-cliente-view.fxml");
    }
}
