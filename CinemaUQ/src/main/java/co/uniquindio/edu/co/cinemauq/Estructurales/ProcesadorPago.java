package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;
import co.uniquindio.edu.co.cinemauq.Model.Usuario;

public interface ProcesadorPago {

    //Utiliza tarjeta para saber algunas caracteristicas necesarias, junto usuario para saber a quien corresponde
    // y monto para saber cuanto dinero va a pagar , y para otras validaciones
    public void cobrarPago(TarjetaVirtual tarjeta, Usuario usuario, double monto);
}
