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
import java.util.List;

public class GestionConfiteriaController {
    @FXML private TextField txtIdProducto,txtNombreProducto,txtPrecioProducto,txtStockProducto;
    @FXML private TextField txtNombreCombo,txtPrecioCombo,txtCantidadCombo;
    @FXML private ComboBox<Producto> cbProductoCombo;
    @FXML private TextField txtIdPromocion,txtNombrePromocion,txtDescuento;
    @FXML private DatePicker dpInicio,dpFin;
    @FXML private TableView<Producto> tblCatalogo;
    @FXML private TableColumn<Producto,String> colId,colNombre,colTipo,colPrecio,colStock;
    private final List<Producto> productosDelCombo = new ArrayList<>();

    @FXML public void initialize() {
        colId.setCellValueFactory(c -> new SimpleStringProperty(id(c.getValue())));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(nombre(c.getValue())));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue() instanceof Combo ? "Combo" : "Individual"));
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty("$" + c.getValue().obtenerPrecio()));
        colStock.setCellValueFactory(c -> new SimpleStringProperty("No definido"));
        cbProductoCombo.setCellFactory(c -> new ListCell<>() { @Override protected void updateItem(Producto p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : nombre(p));
        }});
        cbProductoCombo.setButtonCell(new ListCell<>() { @Override protected void updateItem(Producto p,boolean empty) {
            super.updateItem(p,empty); setText(empty || p == null ? null : nombre(p));
        }});
        actualizarTabla();
    }
    private String nombre(Producto p) { return p instanceof ProductoIndividual i ? i.getNombre() : p instanceof Combo c ? c.getNombre() : "Producto"; }
    private String id(Producto p) { return p instanceof ProductoIndividual i ? String.valueOf(i.getId()) : p instanceof Combo c ? String.valueOf(c.getId()) : "-"; }
    private void actualizarTabla() {
        tblCatalogo.setItems(FXCollections.observableArrayList(CinemaUQ.getInstance().getProductos()));
        cbProductoCombo.setItems(FXCollections.observableArrayList(CinemaUQ.getInstance().getProductos()));
    }
    @FXML private void registrarProducto() {
        try {
            int id = Integer.parseInt(txtIdProducto.getText().trim());
            double precio = Double.parseDouble(txtPrecioProducto.getText().trim());
            String nombre = txtNombreProducto.getText().trim();
            if (nombre.isEmpty() || precio <= 0 || !Double.isFinite(precio)) { Navegacion.mensaje("Nombre o precio inválidos."); return; }
            for (Producto p : CinemaUQ.getInstance().getProductos()) {
                if (id(p).equals(String.valueOf(id))) { Navegacion.mensaje("Ese ID ya existe."); return; }
            }
            CinemaUQ.getInstance().registrarProducto(new ProductoIndividual(nombre,id,precio));
            txtStockProducto.clear(); // El modelo actual no tiene inventario.
            actualizarTabla();
            Navegacion.mensaje("Producto registrado. Para stock falta agregar inventario a ProductoIndividual.");
        } catch (NumberFormatException ex) { Navegacion.mensaje("ID y precio deben ser numéricos."); }
    }
    @FXML private void actualizarProducto() {
        Producto p = tblCatalogo.getSelectionModel().getSelectedItem();
        if (!(p instanceof ProductoIndividual producto)) { Navegacion.mensaje("Selecciona un producto individual."); return; }
        try {
            double precio = Double.parseDouble(txtPrecioProducto.getText().trim());
            if (precio <= 0 || !Double.isFinite(precio)) { Navegacion.mensaje("Precio inválido."); return; }
            if (!txtNombreProducto.getText().isBlank()) producto.setNombre(txtNombreProducto.getText().trim());
            producto.setPrecio(precio);
            actualizarTabla();
        } catch (NumberFormatException ex) { Navegacion.mensaje("Precio inválido."); }
    }
    @FXML private void agregarAlCombo() {
        Producto p = cbProductoCombo.getValue();
        if (p == null) { Navegacion.mensaje("Selecciona un producto."); return; }
        try {
            int cantidad = Integer.parseInt(txtCantidadCombo.getText().trim());
            if (cantidad < 1 || cantidad > 100) { Navegacion.mensaje("Cantidad entre 1 y 100."); return; }
            for (int i = 0; i < cantidad; i++) productosDelCombo.add(p);
            Navegacion.mensaje("Productos agregados al combo: " + productosDelCombo.size());
        } catch (NumberFormatException ex) { Navegacion.mensaje("Cantidad inválida."); }
    }
    @FXML private void guardarCombo() {
        if (txtNombreCombo.getText().isBlank() || productosDelCombo.isEmpty()) { Navegacion.mensaje("Indica un nombre y agrega productos."); return; }
        int id = CinemaUQ.getInstance().getProductos().stream().mapToInt(p -> Integer.parseInt(id(p))).max().orElse(0) + 1;
        Combo combo = new Combo(id,txtNombreCombo.getText().trim(),0);
        for (Producto p : productosDelCombo) combo.agregarProducto(p);
        // El modelo Combo calcula el valor desde sus productos; txtPrecioCombo no se utiliza.
        CinemaUQ.getInstance().registrarProducto(combo);
        productosDelCombo.clear(); actualizarTabla();
        Navegacion.mensaje("Combo registrado con precio calculado desde sus productos.");
    }
    @FXML private void registrarPromocion() {
        try {
            int id = Integer.parseInt(txtIdPromocion.getText().trim());
            double descuento = Double.parseDouble(txtDescuento.getText().trim());
            if (txtNombrePromocion.getText().isBlank() || dpInicio.getValue() == null || dpFin.getValue() == null
                    || dpInicio.getValue().isAfter(dpFin.getValue()) || descuento < 0 || descuento > 100) {
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
        CinemaUQ.getInstance().getProductos().remove(p); actualizarTabla();
    }
    @FXML private void volverMenu(ActionEvent e) { Navegacion.abrir(e,"menu-administrador-view.fxml"); }
}
