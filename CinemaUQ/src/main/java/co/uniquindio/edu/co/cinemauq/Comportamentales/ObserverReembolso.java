package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Funcion;

public class ObserverReembolso implements ObserverEvento {

    private Funcion funcion;

    @Override
    public void actualizar(EventoCine evento) {

        if(evento.getTipo() != TipoEvento.FUNCION_CANCELADA) {


        }
    }
}