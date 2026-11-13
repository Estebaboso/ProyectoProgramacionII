package co.uniquindio.edu.co.cinemauq.Model;

import java.util.ArrayList;
import java.util.List;

public class TarjetaVirtual {
    private String numTarjeta;
    private double saldo;
    private EstadoTarjetaVirtual estadoTarjeta;
    private List<Movimiento> movimientos;

    public TarjetaVirtual(String numTarjeta, double saldo, EstadoTarjetaVirtual estadoTarjeta) {
        this.numTarjeta = numTarjeta;
        this.saldo = saldo;
        this.estadoTarjeta = estadoTarjeta;
        this.movimientos = new ArrayList<>();
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public EstadoTarjetaVirtual getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(EstadoTarjetaVirtual estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
