package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;
import co.uniquindio.edu.co.cinemauq.Model.Usuario;

public class PagoTarjetaVirtual implements ProcesadorPago {
    private TarjetaVirtual tarjeta;

    public PagoTarjetaVirtual() {
        this.tarjeta = tarjeta;
    }

    @Override
    public void cobrarPago(TarjetaVirtual tarjeta, Usuario usuario, double monto) {
        tarjeta.setSaldo(tarjeta.getSaldo() - monto);

        System.out.println("[PAGO] Cobro de " + monto + " realizado a " + usuario.getNombre()
                + " Saldo restante: " + tarjeta.getSaldo());
    }


    public TarjetaVirtual getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaVirtual tarjeta) {
        this.tarjeta = tarjeta;
    }
}
