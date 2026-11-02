package co.uniquindio.edu.co.cinemauq.model;

public class Sala {
    private int numSalas;
    private int cantidadAsientos;
    public Sala(int numSalas, int cantidadAsientos) {
        this.numSalas = numSalas;
        this.cantidadAsientos = cantidadAsientos;
    }
    public int getNumSalas() {
        return numSalas;
    }
    public void setNumSalas(int numSalas) {
        this.numSalas = numSalas;
    }
    public int getCantidadAsientos() {
        return cantidadAsientos;
    }
    public void setCantidadAsientos(int cantidadAsientos) {
        this.cantidadAsientos = cantidadAsientos;
    }
}

