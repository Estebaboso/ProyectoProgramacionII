package co.uniquindio.edu.co.cinemauq.model;

public class TarjetaVirtual {

    private double saldo;
    private EstadoTarjetaVirtual estadoTarjeta;

    public TarjetaVirtual(double saldo, EstadoTarjetaVirtual estadoTarjeta) {
        this.saldo = saldo;
        this.estadoTarjeta = estadoTarjeta;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}
