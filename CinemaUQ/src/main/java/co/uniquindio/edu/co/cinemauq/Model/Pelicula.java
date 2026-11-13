package co.uniquindio.edu.co.cinemauq.Model;

public class Pelicula {
    private String nombre;
    private int id;
    private String genero;
    private int duracion;
    private RestriccionPelicula restriccion;

    public Pelicula(String nombre, int id, String genero, int duracion, RestriccionPelicula restriccion) {
        this.nombre = nombre;
        this.id = id;
        this.genero = genero;
        this.duracion = duracion;
        this.restriccion = restriccion;

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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public RestriccionPelicula getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(RestriccionPelicula restriccion) {
        this.restriccion = restriccion;
    }

}
