package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;

public class PagoTarjetaVirtual implements ProcesadorPago {
    private TarjetaVirtual tarjeta;

    public PagoTarjetaVirtual(TarjetaVirtual tarjeta) {
        this.tarjeta = tarjeta;
    }

    public TarjetaVirtual getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaVirtual tarjeta) {
        this.tarjeta = tarjeta;
    }
}
