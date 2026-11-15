package co.uniquindio.edu.co.cinemauq.Model;

public abstract class Entrada {
    private int id;
    private Funcion funcion;
    private Asiento asiento;
    private double precio;

    public Entrada(int id, Funcion funcion, Asiento asiento, double precio) {
        this.id = id;
        this.funcion = funcion;
        this.asiento = asiento;
        this.precio = precio;
    }

    //Metodo para obtener los puntos luego en el observer

    public int obtenerPuntos() {
        return 100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
