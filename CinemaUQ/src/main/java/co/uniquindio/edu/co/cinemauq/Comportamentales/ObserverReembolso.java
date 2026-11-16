package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.Compra;

public class ObserverReembolso implements ObserverEvento {

    @Override
    public void actualizar(EventoCine evento) {
        if (evento.getTipo() != TipoEvento.FUNCION_CANCELADA) {
            return;
        }

        for (Compra compra : CinemaUQ.getInstance().getCompras()) {
            if (compra.perteneceA(evento.getFuncion())) {
                compra.cancelar();
            }
        }
    }
}