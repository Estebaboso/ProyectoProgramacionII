package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.model.TarjetaVirtual;

public class ProxyPago implements ProcesadorPago {
    private TarjetaVirtual tarjeta;
    private ProcesadorPago procesadorReal;

    public ProxyPago(TarjetaVirtual tarjeta, ProcesadorPago procesadorReal) {
        this.tarjeta = tarjeta;
        this.procesadorReal = procesadorReal;
    }

    public TarjetaVirtual getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaVirtual tarjeta) {
        this.tarjeta = tarjeta;
    }

    public ProcesadorPago getProcesadorReal() {
        return procesadorReal;
    }

    public void setProcesadorReal(ProcesadorPago procesadorReal) {
        this.procesadorReal = procesadorReal;
    }
}
