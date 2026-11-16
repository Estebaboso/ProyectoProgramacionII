package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.*;
import co.uniquindio.edu.co.cinemauq.Estructurales.*;
import co.uniquindio.edu.co.cinemauq.Model.*;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StatePagada;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController {
    @FXML private Label lblResumenEntradas,lblSubtotal,lblDescuento,lblTotal,lblSaldoTarjeta,lblMensajePago;
    @FXML private ComboBox<Producto> cbProducto;
    @FXML private ComboBox<Promocion> cbPromocion;
    @FXML private TextField txtCantidad;
    @FXML private TableView<Producto> tblDetalleCompra;
    @FXML private TableColumn<Producto,String> colDescripcion;
    @FXML private TableColumn<Producto,Integer> colCantidad;
    @FXML private TableColumn<Producto,Double> colSubtotal;
    private final List<Producto> productos = new ArrayList<>();

    @FXML public void initialize() {
        CinemaUQ cinema = CinemaUQ.getInstance();
        cbProducto.setItems(FXCollections.observableArrayList(cinema.getProductos()));
        cbProducto.setCellFactory(c -> new ListCell<>() { @Override protected void updateItem(Producto p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : nombre(p) + " - $" + p.obtenerPrecio());
        }});
        cbProducto.setButtonCell(new ListCell<>() { @Override protected void updateItem(Producto p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : nombre(p));
        }});
        cbPromocion.setItems(FXCollections.observableArrayList(cinema.getPromociones()));
        cbPromocion.setCellFactory(c -> new ListCell<>() { @Override protected void updateItem(Promocion p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : p.getNombre() + " - " + p.getDescuento() + "%");
        }});
        cbPromocion.setButtonCell(new ListCell<>() { @Override protected void updateItem(Promocion p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : p.getNombre());
        }});
        colDescripcion.setCellValueFactory(c -> new SimpleStringProperty(nombre(c.getValue())));
        colCantidad.setCellValueFactory(c -> new SimpleObjectProperty<>(1));
        colSubtotal.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().obtenerPrecio()));
        actualizarResumen();
    }
    private String nombre(Producto p) {
        if (p instanceof ProductoIndividual i) return i.getNombre();
        if (p instanceof Combo c) return c.getNombre();
        return "Producto";
    }
    @FXML private void agregarProducto() {
        Producto p = cbProducto.getValue();
        if (p == null) { lblMensajePago.setText("Selecciona un producto."); return; }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad < 1 || cantidad > 100) { lblMensajePago.setText("Cantidad entre 1 y 100."); return; }
            for (int i = 0; i < cantidad; i++) productos.add(p);
            tblDetalleCompra.setItems(FXCollections.observableArrayList(productos));
            actualizarResumen();
        } catch (NumberFormatException ex) { lblMensajePago.setText("Cantidad inválida."); }
    }
    @FXML private void aplicarPromocion() { actualizarResumen(); }
    private void actualizarResumen() {
        double subtotal = 0;
        for (Asiento asiento : Sesion.asientosSeleccionados) subtotal += Sesion.funcionSeleccionada.getPrecio();
        for (Producto p : productos) subtotal += p.obtenerPrecio();
        double conDescuento = cbPromocion.getValue() == null ? subtotal : cbPromocion.getValue().aplicarDescuento(subtotal);
        lblResumenEntradas.setText("Entradas seleccionadas: " + Sesion.asientosSeleccionados.size());
        lblSubtotal.setText("Subtotal: $" + subtotal);
        lblDescuento.setText("Descuento: $" + (subtotal-conDescuento));
        lblTotal.setText("Total con IVA (19%): $" + (conDescuento*1.19));
        if (Sesion.clienteActual != null) lblSaldoTarjeta.setText("Saldo tarjeta: $" + Sesion.clienteActual.getTarjeta().getSaldo());
    }
    @FXML private void pagarCompra(ActionEvent e) {
        if (Sesion.clienteActual == null || Sesion.funcionSeleccionada == null || Sesion.asientosSeleccionados.isEmpty()) {
            lblMensajePago.setText("Selecciona primero los asientos."); return;
        }
        try {
            CinemaUQ cine = CinemaUQ.getInstance();
            List<Entrada> entradas = new ArrayList<>();
            int siguiente = cine.getCompras().size()*100 + 1;
            for (Asiento asiento : Sesion.asientosSeleccionados) {
                entradas.add(new Entrada2D(siguiente++, Sesion.funcionSeleccionada, asiento, Sesion.funcionSeleccionada.getPrecio()));
            }
            int idCompra = cine.getCompras().stream().mapToInt(Compra::getId).max().orElse(0) + 1;
            Compra compra = new CompraFacade(cine).realizarCompra(idCompra, Sesion.clienteActual, entradas,
                    new ArrayList<>(productos), cbPromocion.getValue());
            if (!(compra.getEstado() instanceof StatePagada)) {
                lblMensajePago.setText("No se pudo realizar el pago. Revisa el saldo y estado de tu tarjeta."); return;
            }
            Sesion.asientosSeleccionados.clear(); Sesion.funcionSeleccionada = null;
            Navegacion.mensaje("Compra realizada. ID: " + compra.getId());
            Navegacion.abrir(e,"mis-compras-view.fxml");
        } catch (Exception ex) { lblMensajePago.setText("No se pudo comprar: " + ex.getMessage()); }
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-cliente-view.fxml"); }
}
