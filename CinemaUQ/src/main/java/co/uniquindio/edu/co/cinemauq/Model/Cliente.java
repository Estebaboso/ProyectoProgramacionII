package co.uniquindio.edu.co.cinemauq.Model;

import java.time.LocalDate;import java.util.ArrayList;
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


    public int consultarPuntosDisponibles() {

        int total = 0;
        LocalDate hoy = LocalDate.now();

        for (LotePuntos lote : lotesPuntos) {

            if (lote.getFechaVencimiento() != null
                    && !lote.getFechaVencimiento().isBefore(hoy)) {

                total += Math.max(0, lote.getCantidadRestante());
            }
        }

        return total;
    }

    public boolean redimirPuntos(int cantidad) {

        if (cantidad <= 0 || consultarPuntosDisponibles() < cantidad) {
            return false;
        }

        // Ordenar los puntos por fecha de vencimiento
        lotesPuntos.sort((a, b) ->
                a.getFechaVencimiento().compareTo(b.getFechaVencimiento())
        );

        int faltantes = cantidad;
        LocalDate hoy = LocalDate.now();

        for (LotePuntos lote : lotesPuntos) {

            if (lote.getFechaVencimiento().isBefore(hoy)) {
                continue;
            }

            int usados = Math.min(
                    faltantes,
                    lote.getCantidadRestante()
            );

            lote.setCantidadRestante(
                    lote.getCantidadRestante() - usados
            );

            faltantes -= usados;

            if (faltantes == 0) {
                break;
            }
        }

        return true;
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
