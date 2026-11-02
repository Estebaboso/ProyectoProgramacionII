package co.uniquindio.edu.co.cinemauq.model;

public class Pelicula {
    private String nombre;
    private int id;
    private String genero;
    private double duracion; //temporal, (falta reestriccion)

    public Pelicula(String nombre, int id, String genero, double duracion) {
        this.nombre = nombre;
        this.id = id;
        this.genero = genero;
        this.duracion = duracion;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }


}

