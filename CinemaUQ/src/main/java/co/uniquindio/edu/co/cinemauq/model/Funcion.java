package co.uniquindio.edu.co.cinemauq.model;

public class Funcion {
    private double precio;
    private double fecha;             //temporal
    private double horaInicio; //temporal
    private EstadoFuncion estadoFuncion;

    public Funcion (double precio, double fecha, double horaInicio, EstadoFuncion estadoFuncion) {
        this.precio = precio;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.estadoFuncion = estadoFuncion;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getFecha() {
        return fecha;
    }
    public void setFecha(double fecha) {
        this.fecha = fecha;
    }
    public double getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(double horaInicio) {
        this.horaInicio = horaInicio;
    }
    public EstadoFuncion getEstadoFuncion() {
        return estadoFuncion;
    }
    public void setEstadoFuncion(EstadoFuncion estadoFuncion) {
        this.estadoFuncion = estadoFuncion;
    }


}
