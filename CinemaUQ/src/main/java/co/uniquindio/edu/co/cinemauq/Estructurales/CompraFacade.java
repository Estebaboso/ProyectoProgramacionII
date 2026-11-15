package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Creacionales.CompraBuilder;
import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Exepciones.CompraInvalidaExcepcion;
import co.uniquindio.edu.co.cinemauq.Model.Cliente;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Entrada;
import co.uniquindio.edu.co.cinemauq.Model.Funcion;
import co.uniquindio.edu.co.cinemauq.Model.Promocion;

import java.util.List;

public class CompraFacade {

    // Referencia al sistema CinemaUQ para registrar las compras realizadas
    private CinemaUQ cinemaUQ;

    public CompraFacade(CinemaUQ cinemaUQ) {
        this.cinemaUQ = cinemaUQ;
    }

    //esta wea va a organizar el proceso de compra
    public Compra realizarCompra(
            int id,
            Cliente cliente,
            List<Entrada> entradas,
            List<Producto> productos,
            Promocion promocion) throws CompraInvalidaExcepcion {

        // inicia el builder para construirlo
        CompraBuilder builder = new CompraBuilder(id, cliente);

        for (Entrada entrada : entradas) {
            builder.agregarEntrada(entrada);
        }

        // Agrega los productos o combos
        for (Producto producto : productos) {
            builder.agregarProducto(producto);
        }

        // aplica una promoción solamente si el cliente seleccionó alguna
        if (promocion != null) {
            builder.conPromocion(promocion);
        }

        // El Builder valida los datos y calcula
        Compra compra = builder.construir();

        // StatePendiente se encarga de utilizar el Proxy y verificar el pago.
        // Si el pago falla, se devuelve la compra sin registrarla ni reservar asientos.
        if (!compra.getEstado().pagar()) {
            return compra;
        }

        //  Registra la compra en la lista general de CinemaUQ
        cinemaUQ.getCompras().add(compra);

        // Agrega la compra al historial del cliente
        cliente.getCompras().add(compra);

        // El Builder ya valido que todas las entradas sean de la misma función
        Funcion funcion = entradas.get(0).getFuncion();

        // Esto evita que otros clientes puedan adquirir los mismos asientos
        for (Entrada entrada : entradas) {
            funcion.getAsientosReservados().add(entrada.getAsiento().getCodigo());
        }


        System.out.println("Compra registrada correctamente.");

        // devuelve la compra realizada
        return compra;
    }
}
