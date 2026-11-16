package co.uniquindio.edu.co.cinemauq.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    public boolean recargar(double monto) {
        if (estadoTarjeta != EstadoTarjetaVirtual.ACTIVA || !Double.isFinite(monto) || monto <= 0
                || !Double.isFinite(saldo + monto)) {
            return false;
        }

        saldo += monto;

        movimientos.add(new Movimiento(UUID.randomUUID().toString(), TipoMovimiento.RECARGA, monto,
                LocalDateTime.now(), null));

        return true;
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
