
package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Estructurales.ProcesadorPago;
import co.uniquindio.edu.co.cinemauq.Estructurales.ProxyPago;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Entrada;
import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;

public class StatePendiente implements StateCompra {

    private Compra compra;

    public StatePendiente(Compra compra) {
        this.compra = compra;
    }

    @Override
    public void cancelar() {
        System.out.println("La compra pendiente ha sido cancelada.");

        compra.setEstado(new StateCancelada(compra));
    }

    @Override
    public boolean pagar() {
        System.out.println("La compra esta pendiente de pago");

        // Obtener la tarjeta del cliente
        TarjetaVirtual tarjeta = compra.getCliente().getTarjeta();

        // Crear el proxy para procesar el pago
        ProcesadorPago proxy = new ProxyPago(tarjeta, null);

        // Verificar si se realiza el pago
        boolean pagoRealizado = proxy.cobrarPago(
                tarjeta,
                compra.getCliente(),
                compra.getTotal()
        );

        // Si el pago falla, la compra sigue pendiente
        if (!pagoRealizado) {
            System.out.println("No se pudo realizar el pago");
            return false;
        }

        // Reservar los asientos de las entradas
        for (Entrada entrada : compra.getEntradas()) {
            entrada.getFuncion().getAsientosReservados().add(
                    entrada.getAsiento().getCodigo()
            );
        }

        // Cambiar el estado a pagada
        compra.setEstado(new StatePagada(compra));

        // Registrar la compra
        compra.getCliente().getCompras().add(compra);
        CinemaUQ.getInstance().getCompras().add(compra);

        System.out.println("La compra se realizo correctamente");

        // Notificar a los observadores
        compra.notificarObservadores(
                new EventoCine(TipoEvento.COMPRA_PAGADA, compra)
        );

        return true;
    }
}
