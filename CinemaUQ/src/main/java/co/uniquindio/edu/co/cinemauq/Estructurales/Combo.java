package co.uniquindio.edu.co.cinemauq.Estructurales;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto {

    private int id;
    private String nombre;
    private double descuento;
    private List<Producto> listaDeProductos;

    public Combo(int id, String nombre, double descuento){

        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
        this.listaDeProductos = new ArrayList<>();
    }

    //Metodo para agregar productos al combo
    public void agregarProducto(Producto producto){
        listaDeProductos.add(producto);
    }

    //Metodo para quitar productos del combo
    public void quitarProducto(Producto producto){
        listaDeProductos.remove(producto);
    }

    @Override
    public double obtenerPrecio() {
        return obtenerPrecio();  //temporal
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }
}
