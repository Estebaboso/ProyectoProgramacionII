package co.uniquindio.edu.co.cinemauq.Estructurales;

import java.util.ArrayList;
import java.util.List;

public class ProductoIndividual implements Producto {
    private String nombre;
    private int id;
    private double precio;
    private String tamano;
    private List<String> adicionales;

    public ProductoIndividual(String nombre, int id, double precio, String tamano) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
        this.tamano = tamano;
        this.adicionales = new ArrayList<>();
    }


    @Override
    public double obtenerPrecio() {
        return precio;   //temporal
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getTamano() {
        return tamano;
    }
    public void setTamano(String tamano) {
        this.tamano = tamano;
    }
    public List<String> getAdicionales() {
        return adicionales;
    }
    public void setAdicionales(List<String> adicionales) {
        this.adicionales = adicionales;
    }
}
