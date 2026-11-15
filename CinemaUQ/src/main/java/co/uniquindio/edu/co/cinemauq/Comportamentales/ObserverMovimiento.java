package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Movimiento;
import co.uniquindio.edu.co.cinemauq.Model.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.UUID;

public class ObserverMovimiento implements ObserverEvento {

    // Registra los movimientos del cliente
    //El UUID genera un id aleatorio para identificar el movimiento. Sin embargo todavia no se bien si dejarlo
    // o asignar un contador propio


    @Override
    public void actualizar(EventoCine evento) {
        if (evento.getTipo() != TipoEvento.COMPRA_PAGADA) {
            return;
        }

        Compra compra = evento.getCompra();

        compra.getCliente().getTarjeta().getMovimientos()
                .add(new Movimiento(UUID.randomUUID().toString(), TipoMovimiento.PAGO, compra.getTotal(),
                        LocalDateTime.now(), compra));
    }
}