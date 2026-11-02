package co.uniquindio.edu.co.cinemauq.model;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto {

    private String nombre;
    private List<Producto> listaDeProductos;

    public Combo(String nombre){

        this.nombre = nombre;
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
}
