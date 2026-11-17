package co.uniquindio.edu.co.cinemauq.Estructurales;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto {
    private int id;
    private String nombre;
    private double descuento;
    private double precioManual;
    private List<Producto> listaDeProductos;

    public Combo(int id, String nombre, double descuento, double precioManual) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
        setPrecioManual(precioManual);
        this.listaDeProductos = new ArrayList<>();
    }


    public Combo(int id, String nombre, double descuento) {
        this(id, nombre, descuento, 0);
    }

    public void agregarProducto(Producto producto) { listaDeProductos.add(producto); }
    public void quitarProducto(Producto producto) { listaDeProductos.remove(producto); }

    @Override
    public double obtenerPrecio() {
        if (precioManual > 0) return precioManual;
        double total = 0;
        for (Producto producto : listaDeProductos) total += producto.obtenerPrecio();
        return total;
    }

    @Override
    public int obtenerPuntos() { return 50; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }
    public double getPrecioManual() { return precioManual; }
    public void setPrecioManual(double precioManual) {
        if (!Double.isFinite(precioManual) || precioManual < 0)
            throw new IllegalArgumentException("Precio de combo inválido.");
        this.precioManual = precioManual;
    }
    public List<Producto> getListaDeProductos() { return listaDeProductos; }
    public void setListaDeProductos(List<Producto> listaDeProductos) { this.listaDeProductos = listaDeProductos; }
}