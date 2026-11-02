package co.uniquindio.edu.co.cinemauq.model;

public class ProductoIndividual implements Producto {
    private String nombre;
    private int id;
    private double precio;

    public ProductoIndividual(String nombre, int id, double precio) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
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
}

