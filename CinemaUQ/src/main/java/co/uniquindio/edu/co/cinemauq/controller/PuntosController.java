package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Estructurales.*;
import co.uniquindio.edu.co.cinemauq.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class PuntosController {
    @FXML private Label lblPuntosDisponibles, lblPuntosNecesarios, lblMensajeRedencion;
    @FXML private ComboBox<String> cbTipoRedencion, cbElementoRedencion;
    @FXML private TableView<LotePuntos> tblLotesPuntos;
    @FXML private TableColumn<LotePuntos, String> colFecha, colPuntos, colVencimiento, colEstado;

    @FXML public void initialize() {
        cbTipoRedencion.setItems(FXCollections.observableArrayList("Entrada", "Producto", "Combo"));
        cbTipoRedencion.setOnAction(e -> cargarElementos());
        cbElementoRedencion.setOnAction(e -> actualizarCosto());
        colFecha.setCellValueFactory(c -> new SimpleStringProperty("" + c.getValue().getFechaObtencion()));
        colPuntos.setCellValueFactory(c -> new SimpleStringProperty("" + c.getValue().getCantidadRestante()));
        colVencimiento.setCellValueFactory(c -> new SimpleStringProperty("" + c.getValue().getFechaVencimiento()));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFechaVencimiento().isBefore(LocalDate.now()) ? "Vencido" : "Vigente"));
        actualizarPuntos();
    }

    private int obtenerCosto() {
        String tipo = cbTipoRedencion.getValue();
        CinemaUQ cine = CinemaUQ.getInstance();
        if ("Entrada".equals(tipo)) return cine.getPuntosEntrada();
        if ("Producto".equals(tipo)) return cine.getPuntosProducto();
        if ("Combo".equals(tipo)) return cine.getPuntosCombo();
        return 0;
    }

    private void actualizarCosto() {
        lblPuntosNecesarios.setText("Puntos necesarios: " + obtenerCosto());
    }

    private void cargarElementos() {
        cbElementoRedencion.getItems().clear();
        cbElementoRedencion.setValue(null);
        String tipo = cbTipoRedencion.getValue();
        if (tipo == null) return;
        if (tipo.equals("Entrada")) {
            for (Funcion f : CinemaUQ.getInstance().getFunciones()) {
                if (f.getEstadoFuncion() == EstadoFuncion.ACTIVA
                        && f.getFecha().atTime(f.getHoraInicio()).isAfter(java.time.LocalDateTime.now())) {
                    cbElementoRedencion.getItems().add(f.getId() + " - " + f.getPelicula().getNombre());
                }
            }
        } else {
            for (Producto p : CinemaUQ.getInstance().getProductos()) {
                if (tipo.equals("Producto") && p instanceof ProductoIndividual) {
                    ProductoIndividual individual = (ProductoIndividual) p;
                    if (individual.getStock() > 0) {
                        cbElementoRedencion.getItems().add(individual.getId() + " - " + individual.getNombre());
                    }
                }
                if (tipo.equals("Combo") && p instanceof Combo) {
                    Combo combo = (Combo) p;
                    cbElementoRedencion.getItems().add(combo.getId() + " - " + combo.getNombre());
                }
            }
        }
        actualizarCosto();
    }

    @FXML private void actualizarPuntos() {
        if (Sesion.clienteActual == null) return;
        lblPuntosDisponibles.setText("Puntos disponibles: " + Sesion.clienteActual.consultarPuntosDisponibles());
        tblLotesPuntos.setItems(FXCollections.observableArrayList(Sesion.clienteActual.getLotesPuntos()));
        tblLotesPuntos.refresh();
    }

    @FXML private void redimirPuntos(ActionEvent e) {
        Cliente cliente = Sesion.clienteActual;
        String tipo = cbTipoRedencion.getValue();
        String elegido = cbElementoRedencion.getValue();
        int costo = obtenerCosto();

        if (cliente == null || tipo == null || elegido == null) {
            lblMensajeRedencion.setText("Selecciona una recompensa.");
            return;
        }
        if (cliente.consultarPuntosDisponibles() < costo) {
            lblMensajeRedencion.setText("No tienes suficientes puntos.");
            return;
        }

        if (tipo.equals("Entrada")) {
            for (Funcion funcion : CinemaUQ.getInstance().getFunciones()) {
                if (elegido.equals(funcion.getId() + " - " + funcion.getPelicula().getNombre())
                        && funcion.getEstadoFuncion() == EstadoFuncion.ACTIVA
                        && funcion.getFecha().atTime(funcion.getHoraInicio()).isAfter(java.time.LocalDateTime.now())) {
                    Sesion.funcionSeleccionada = funcion;
                    Sesion.asientosSeleccionados.clear();
                    Sesion.redimiendoEntrada = true;
                    Navegacion.abrir(e, "seleccion-asientos-view.fxml");
                    return;
                }
            }
            lblMensajeRedencion.setText("La función ya no está disponible.");
            return;
        }

        Producto seleccionado = null;
        for (Producto producto : CinemaUQ.getInstance().getProductos()) {
            if (producto instanceof ProductoIndividual) {
                ProductoIndividual p = (ProductoIndividual) producto;
                if (tipo.equals("Producto") && elegido.equals(p.getId() + " - " + p.getNombre())) {
                    seleccionado = p;
                }
            }
            if (producto instanceof Combo) {
                Combo c = (Combo) producto;
                if (tipo.equals("Combo") && elegido.equals(c.getId() + " - " + c.getNombre())) {
                    seleccionado = c;
                }
            }
        }
        if (seleccionado == null) {
            lblMensajeRedencion.setText("La recompensa no existe.");
            return;
        }

        java.util.List<ProductoIndividual> necesarios = new java.util.ArrayList<>();
        if (seleccionado instanceof ProductoIndividual) {
            necesarios.add((ProductoIndividual) seleccionado);
        }
        if (seleccionado instanceof Combo) {
            Combo combo = (Combo) seleccionado;
            for (Producto producto : combo.getListaDeProductos()) {
                if (producto instanceof ProductoIndividual) {
                    necesarios.add((ProductoIndividual) producto);
                }
            }
        }
        if (necesarios.isEmpty()) {
            lblMensajeRedencion.setText("La recompensa no tiene productos.");
            return;
        }
        for (ProductoIndividual producto : necesarios) {
            int cantidad = 0;
            for (ProductoIndividual otro : necesarios) {
                if (producto == otro) cantidad++;
            }
            if (producto.getStock() < cantidad) {
                lblMensajeRedencion.setText("Inventario insuficiente.");
                return;
            }
        }

        if (cliente.redimirPuntos(costo)) {
            for (ProductoIndividual producto : necesarios) producto.descontarStock(1);
            lblMensajeRedencion.setText("Redención realizada. Entregar: " + elegido);
            actualizarPuntos();
            cargarElementos();
        }
    }

    @FXML private void volverMenu(ActionEvent e) {
        Navegacion.abrir(e, "menu-cliente-view.fxml");
    }
}
