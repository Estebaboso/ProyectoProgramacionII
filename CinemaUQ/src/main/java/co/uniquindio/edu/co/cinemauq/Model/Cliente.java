package co.uniquindio.edu.co.cinemauq.Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private TarjetaVirtual tarjeta;
    private List<LotePuntos> lotesPuntos;
    private List<Compra> compras;

    public Cliente(String nombre, String id, String telefono, String contrasena, String correoE, TarjetaVirtual tarjeta) {
        super(nombre, id, telefono, contrasena, correoE);
        this.tarjeta = tarjeta;
        this.lotesPuntos = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public TarjetaVirtual getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaVirtual tarjeta) {
        this.tarjeta = tarjeta;
    }

    public List<LotePuntos> getLotesPuntos() {
        return lotesPuntos;
    }

    public void setLotesPuntos(List<LotePuntos> lotesPuntos) {
        this.lotesPuntos = lotesPuntos;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
}
