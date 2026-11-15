package co.uniquindio.edu.co.cinemauq.Creacionales;

import co.uniquindio.edu.co.cinemauq.Estructurales.Producto;
import co.uniquindio.edu.co.cinemauq.Exepciones.CompraInvalidaExcepcion;
import co.uniquindio.edu.co.cinemauq.Model.Cliente;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Entrada;
import co.uniquindio.edu.co.cinemauq.Model.EstadoFuncion;
import co.uniquindio.edu.co.cinemauq.Model.Funcion;
import co.uniquindio.edu.co.cinemauq.Model.Promocion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Patrón Builder: arma una Compra paso a paso y solo la entrega cuando es válida.
 * No cobra ni reserva asientos: solo valida y calcula.
 */
public class CompraBuilder {
    // Regla de negocio: IVA del 19 %
    private static final double TASA_IVA = 0.19;

    private final int id;
    private final Cliente cliente;
    private final List<Entrada> entradas;
    private final List<Producto> productos;
    private Promocion promocion;

    public CompraBuilder(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.entradas = new ArrayList<>();
        this.productos = new ArrayList<>();
    }

    // ---------- Pasos de construcción (devuelven el mismo builder) ----------

    public CompraBuilder agregarEntrada(Entrada entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("La entrada no puede ser nula");
        }
        this.entradas.add(entrada);
        return this;
    }

    public CompraBuilder agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        this.productos.add(producto);
        return this;
    }

    public CompraBuilder conPromocion(Promocion promocion) {
        this.promocion = promocion;
        return this;
    }

    // ---------- Paso final: valida, calcula y entrega la Compra ----------

    public Compra construir() throws CompraInvalidaExcepcion {
        validar();

        double bruto = 0;
        for (Entrada entrada : entradas) {
            bruto += entrada.getPrecio();
        }
        for (Producto producto : productos) {
            bruto += producto.obtenerPrecio();
        }

        // SUPUESTO: aplicarDescuento(monto) devuelve el monto ya descontado.
        // Si tu Promocion es distinta, solo hay que ajustar esta línea.
        double base = (promocion != null) ? promocion.aplicarDescuento(bruto) : bruto;
        double iva = base * TASA_IVA;

        Compra compra = new Compra(id, cliente, LocalDateTime.now(),
                new ArrayList<>(entradas), new ArrayList<>(productos), promocion);

        // El subtotal es la base gravable (ya con descuento); el IVA se calcula sobre ella
        compra.setSubtotal(base);
        compra.setIva(iva);
        compra.setTotal(base + iva);
        return compra;
    }

    // ---------- Validaciones de negocio ----------

    private void validar() throws CompraInvalidaExcepcion {
        if (cliente == null) {
            throw new CompraInvalidaExcepcion("La compra debe tener un cliente.");
        }
        if (entradas.isEmpty()) {
            throw new CompraInvalidaExcepcion("La compra debe tener al menos una entrada.");
        }

        Funcion funcion = entradas.get(0).getFuncion();
        if (funcion == null) {
            throw new CompraInvalidaExcepcion("Las entradas deben estar asociadas a una función.");
        }
        if (funcion.getEstadoFuncion() != EstadoFuncion.ACTIVA) {
            throw new CompraInvalidaExcepcion("La función no está disponible para la venta.");
        }

        Set<String> codigosEnLaCompra = new HashSet<>();
        for (Entrada entrada : entradas) {
            if (entrada.getFuncion() != funcion) {
                throw new CompraInvalidaExcepcion("Todas las entradas deben ser de la misma función.");
            }
            if (entrada.getAsiento() == null) {
                throw new CompraInvalidaExcepcion("Cada entrada debe tener un asiento.");
            }

            String codigo = entrada.getAsiento().getCodigo();
            if (!codigosEnLaCompra.add(codigo)) {
                throw new CompraInvalidaExcepcion("El asiento " + codigo + " está repetido en la compra.");
            }
            if (!existeEnSala(funcion, codigo)) {
                throw new CompraInvalidaExcepcion("El asiento " + codigo + " no existe en la sala de la función.");
            }
            if (funcion.getAsientosReservados().contains(codigo)) {
                throw new CompraInvalidaExcepcion("El asiento " + codigo + " ya está reservado para esta función.");
            }
        }
    }

    private boolean existeEnSala(Funcion funcion, String codigo) {
        return funcion.getSala().getAsientos().stream()
                .anyMatch(asiento -> asiento.getCodigo().equals(codigo));
    }
}