package co.uniquindio.edu.co.cinemauq.model;

public class Compra {
    public double fecha; //deberia ser date pero me da pereza ahorita mismo entonces temporal
    public double horaCompra;

    public Compra (double fecha, double horaCompra) {
        this.fecha = fecha;
        this.horaCompra = horaCompra;
    }
    public double getFecha() {
        return fecha;
    }
    public void setFecha(double fecha) {
        this.fecha = fecha;
    }
    public double getHoraCompra() {
        return horaCompra;
    }
    public void sethoraCompra(double horaCompra) {
        this.horaCompra = horaCompra;
    }
}
