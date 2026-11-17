package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Estructurales.*;
import co.uniquindio.edu.co.cinemauq.Model.Promocion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionConfiteriaController {
    @FXML private TextField txtIdProducto,txtNombreProducto,txtPrecioProducto,txtStockProducto;
    @FXML private TextField txtNombreCombo,txtPrecioCombo,txtCantidadCombo;
    @FXML private ComboBox<Producto> cbProductoCombo;
    @FXML private TextField txtIdPromocion,txtNombrePromocion,txtDescuento;
    @FXML private DatePicker dpInicio,dpFin;
    @FXML private TableView<Producto> tblCatalogo;
    @FXML private TableColumn<Producto,String> colId,colNombre,colTipo,colPrecio,colStock;
    @FXML private TextField txtPuntosEntrada, txtPuntosProducto, txtPuntosCombo;
    private final List<Producto> productosDelCombo = new ArrayList<>();

    @FXML public void initialize() {
        colId.setCellValueFactory(c -> new SimpleStringProperty(id(c.getValue())));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(nombre(c.getValue())));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue() instanceof Combo ? "Combo" : "Individual"));
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty("$" + c.getValue().obtenerPrecio()));
        colStock.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(stockDisponible(c.getValue()))));
        cbProductoCombo.setCellFactory(c -> new ListCell<>() { @Override protected void updateItem(Producto p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : nombre(p));
        }});
        cbProductoCombo.setButtonCell(new ListCell<>() { @Override protected void updateItem(Producto p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : nombre(p));
        }});
        actualizarTabla();
        CinemaUQ cine = CinemaUQ.getInstance();
        txtPuntosEntrada.setText("" + cine.getPuntosEntrada());
        txtPuntosProducto.setText("" + cine.getPuntosProducto());
        txtPuntosCombo.setText("" + cine.getPuntosCombo());
    }

    @FXML private void guardarCostosPuntos() {
        try {
            int entrada = Integer.parseInt(txtPuntosEntrada.getText().trim());
            int producto = Integer.parseInt(txtPuntosProducto.getText().trim());
            int combo = Integer.parseInt(txtPuntosCombo.getText().trim());
            if (entrada <= 0 || producto <= 0 || combo <= 0) {
                Navegacion.mensaje("Los puntos deben ser mayores que cero.");
                return;
            }
            CinemaUQ cine = CinemaUQ.getInstance();
            cine.setPuntosEntrada(entrada);
            cine.setPuntosProducto(producto);
            cine.setPuntosCombo(combo);
            Navegacion.mensaje("Costos de redención actualizados.");
        } catch (NumberFormatException ex) {
            Navegacion.mensaje("Escribe cantidades enteras de puntos.");
        }
    }

    private String nombre(Producto p) {
        if (p instanceof ProductoIndividual i) return i.getNombre();
        if (p instanceof Combo c) return c.getNombre();
        return "Producto";
    }
    private String id(Producto p) {
        if (p instanceof ProductoIndividual i) return String.valueOf(i.getId());
        if (p instanceof Combo c) return String.valueOf(c.getId());
        return "-";
    }
    private int stockDisponible(Producto p) {
        if (p instanceof ProductoIndividual i) return i.getStock();
        if (p instanceof Combo c) {
            Map<ProductoIndividual,Integer> cantidades = new HashMap<>();
            for (Producto item : c.getListaDeProductos()) {
                if (item instanceof ProductoIndividual i) cantidades.merge(i, 1, Integer::sum);
            }
            if (cantidades.isEmpty()) return 0;
            int minimo = Integer.MAX_VALUE;
            for (Map.Entry<ProductoIndividual,Integer> par : cantidades.entrySet()) {
                minimo = Math.min(minimo, par.getKey().getStock() / par.getValue());
            }
            return minimo;
        }
        return 0;
    }
    private void actualizarTabla() {
        List<Producto> catalogo = CinemaUQ.getInstance().getProductos();
        tblCatalogo.setItems(FXCollections.observableArrayList(catalogo));
        // Un combo solo puede contener productos individuales.
        List<Producto> individuales = new ArrayList<>();
        for (Producto p : catalogo) if (p instanceof ProductoIndividual) individuales.add(p);
        cbProductoCombo.setItems(FXCollections.observableArrayList(individuales));
        tblCatalogo.refresh();
    }

    @FXML private void registrarProducto() {
        try {
            int id = Integer.parseInt(txtIdProducto.getText().trim());
            double precio = Double.parseDouble(txtPrecioProducto.getText().trim());
            int stock = Integer.parseInt(txtStockProducto.getText().trim());
            String nombre = txtNombreProducto.getText().trim();
            if (id <= 0 || nombre.isEmpty() || precio <= 0 || !Double.isFinite(precio) || stock < 0) {
                Navegacion.mensaje("Nombre, ID, precio o stock inválidos."); return;
            }
            for (Producto p : CinemaUQ.getInstance().getProductos()) {
                if (id(p).equals(String.valueOf(id))) { Navegacion.mensaje("Ese ID ya existe."); return; }
            }
            CinemaUQ.getInstance().registrarProducto(new ProductoIndividual(nombre,id,precio,stock));
            actualizarTabla();
            Navegacion.mensaje("Producto registrado con " + stock + " unidades.");
        } catch (NumberFormatException ex) { Navegacion.mensaje("ID, precio y cantidad deben ser numéricos."); }
    }

    @FXML private void actualizarProducto() {
        Producto p = tblCatalogo.getSelectionModel().getSelectedItem();
        if (!(p instanceof ProductoIndividual producto)) { Navegacion.mensaje("Selecciona un producto individual."); return; }
        try {
            double precio = producto.getPrecio();
            if (!txtPrecioProducto.getText().isBlank()) {
                precio = Double.parseDouble(txtPrecioProducto.getText().trim());
                if (precio <= 0 || !Double.isFinite(precio)) { Navegacion.mensaje("Precio inválido."); return; }
            }
            int nuevoStock = producto.getStock();
            if (!txtStockProducto.getText().isBlank()) {
                nuevoStock = Integer.parseInt(txtStockProducto.getText().trim());
                if (nuevoStock < 0) { Navegacion.mensaje("La cantidad no puede ser negativa."); return; }
            }
            if (!txtNombreProducto.getText().isBlank()) producto.setNombre(txtNombreProducto.getText().trim());
            producto.setPrecio(precio);
            producto.setStock(nuevoStock);
            actualizarTabla();
            Navegacion.mensaje("Producto actualizado.");
        } catch (NumberFormatException ex) { Navegacion.mensaje("Precio o stock inválidos."); }
    }

    @FXML private void agregarAlCombo() {
        Producto p = cbProductoCombo.getValue();
        if (!(p instanceof ProductoIndividual)) { Navegacion.mensaje("Selecciona un producto individual."); return; }
        try {
            int cantidad = Integer.parseInt(txtCantidadCombo.getText().trim());
            if (cantidad < 1 || cantidad > 100) { Navegacion.mensaje("Cantidad entre 1 y 100."); return; }
            for (int i = 0; i < cantidad; i++) productosDelCombo.add(p);
            Navegacion.mensaje("Productos agregados al combo: " + productosDelCombo.size());
        } catch (NumberFormatException ex) { Navegacion.mensaje("Cantidad inválida."); }
    }

    @FXML private void guardarCombo() {
        try {
            String nombre = txtNombreCombo.getText().trim();
            double precio = Double.parseDouble(txtPrecioCombo.getText().trim());
            if (nombre.isBlank() || productosDelCombo.isEmpty() || !Double.isFinite(precio) || precio <= 0) {
                Navegacion.mensaje("Indica nombre, precio válido y productos del combo."); return;
            }
            int id = CinemaUQ.getInstance().getProductos().stream().mapToInt(p -> Integer.parseInt(id(p))).max().orElse(0) + 1;
            Combo combo = new Combo(id, nombre, 0, precio);
            for (Producto p : productosDelCombo) combo.agregarProducto(p);
            CinemaUQ.getInstance().registrarProducto(combo);
            productosDelCombo.clear(); actualizarTabla();
            Navegacion.mensaje("Combo registrado con precio de $" + precio);
        } catch (NumberFormatException ex) { Navegacion.mensaje("Precio del combo inválido."); }
    }

    @FXML private void registrarPromocion() {
        try {
            int id = Integer.parseInt(txtIdPromocion.getText().trim());
            double descuento = Double.parseDouble(txtDescuento.getText().trim());
            if (txtNombrePromocion.getText().isBlank() || dpInicio.getValue() == null || dpFin.getValue() == null
                    || dpInicio.getValue().isAfter(dpFin.getValue()) || !Double.isFinite(descuento)
                    || descuento < 0 || descuento > 100) {
                Navegacion.mensaje("Datos de promoción inválidos."); return;
            }
            boolean agregado = CinemaUQ.getInstance().registrarPromocion(new Promocion(id,txtNombrePromocion.getText().trim(),
                    descuento,dpInicio.getValue(),dpFin.getValue()));
            Navegacion.mensaje(agregado ? "Promoción registrada." : "Ese ID de promoción ya existe.");
        } catch (NumberFormatException ex) { Navegacion.mensaje("ID y descuento deben ser numéricos."); }
    }
    @FXML private void eliminarSeleccionado() {
        Producto p = tblCatalogo.getSelectionModel().getSelectedItem();
        if (p == null) { Navegacion.mensaje("Selecciona un producto o combo."); return; }
        // No eliminar un ingrediente de combos existentes: seguirían apuntando a él.
        if (p instanceof ProductoIndividual) {
            for (Producto item : CinemaUQ.getInstance().getProductos()) {
                if (item instanceof Combo c && c.getListaDeProductos().contains(p)) {
                    Navegacion.mensaje("No puedes eliminar un producto utilizado por un combo."); return;
                }
            }
        }
        CinemaUQ.getInstance().getProductos().remove(p);
        actualizarTabla();
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-administrador-view.fxml"); }
}
