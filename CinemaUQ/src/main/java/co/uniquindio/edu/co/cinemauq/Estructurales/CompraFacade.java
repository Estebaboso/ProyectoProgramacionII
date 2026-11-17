package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Creacionales.CompraBuilder;
import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Exepciones.CompraInvalidaExcepcion;
import co.uniquindio.edu.co.cinemauq.Model.Cliente;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Entrada;
import co.uniquindio.edu.co.cinemauq.Model.Promocion;

import java.util.ArrayList;
import java.util.List;

public class CompraFacade {

    private CinemaUQ cinemaUQ;

    public CompraFacade(CinemaUQ cinemaUQ) {
        this.cinemaUQ = cinemaUQ;
    }

    public Compra realizarCompra(int id, Cliente cliente, List<Entrada> entradas, List<Producto> productos, Promocion promocion) throws CompraInvalidaExcepcion {

        CompraBuilder builder = new CompraBuilder(id, cliente);

        for (Entrada entrada : entradas) {
            builder.agregarEntrada(entrada);
        }

        for (Producto producto : productos) {
            builder.agregarProducto(producto);
        }

        if (promocion != null) {
            builder.conPromocion(promocion);
        }

        Compra compra = builder.construir();

        // Obtener los productos individuales de la compra
        List<ProductoIndividual> productosVendidos = new ArrayList<>();

        for (Producto producto : productos) {

            if (producto instanceof ProductoIndividual) {
                productosVendidos.add((ProductoIndividual) producto);
            }

            if (producto instanceof Combo) {
                Combo combo = (Combo) producto;

                for (Producto item : combo.getListaDeProductos()) {

                    if (item instanceof ProductoIndividual) {
                        productosVendidos.add((ProductoIndividual) item);
                    }
                }
            }
        }

        // Verificar que exista suficiente inventario
        for (ProductoIndividual producto : productosVendidos) {

            int contador = 0;

            for (ProductoIndividual otro : productosVendidos) {
                if (producto == otro) {
                    contador++;
                }
            }

            if (producto.getStock() < contador) {
                throw new CompraInvalidaExcepcion("No hay suficiente stock de " + producto.getNombre());
            }
        }

        // Realizar el pago
        if (compra.getEstado().pagar()) {

            // Descontar los productos comprados
            for (ProductoIndividual producto : productosVendidos) {
                producto.descontarStock(1);
            }
        }

        return compra;
    }
}
