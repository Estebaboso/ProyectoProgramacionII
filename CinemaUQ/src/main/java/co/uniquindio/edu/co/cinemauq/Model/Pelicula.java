package co.uniquindio.edu.co.cinemauq.Model;

public class Pelicula {
    private String nombre;
    private int id;
    private String genero;
    private int duracion; // en minutos
    private RestriccionPelicula restriccion;
    private String sinopsis;

    public Pelicula(String nombre, int id, String genero, int duracion, RestriccionPelicula restriccion, String sinopsis) {
        this.nombre = nombre;
        this.id = id;
        this.genero = genero;
        this.duracion = duracion;
        this.restriccion = restriccion;
        this.sinopsis = sinopsis;
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

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
