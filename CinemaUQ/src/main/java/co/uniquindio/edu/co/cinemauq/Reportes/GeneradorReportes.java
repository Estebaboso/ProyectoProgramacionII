package co.uniquindio.edu.co.cinemauq.Reportes;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StatePagada;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StateCancelada;
import co.uniquindio.edu.co.cinemauq.Estructurales.*;
import co.uniquindio.edu.co.cinemauq.Model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeneradorReportes {

    private final CinemaUQ cinema = CinemaUQ.getInstance();

    // Verificar que la fecha este dentro del periodo
    private boolean enPeriodo(LocalDate fecha, LocalDate inicio, LocalDate fin) {

        return fecha != null && !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }

    // Obtener nombre del producto o combo
    private String nombreProducto(Producto producto) {

        if (producto instanceof ProductoIndividual individual) {
            return individual.getNombre();
        }

        if (producto instanceof Combo combo) {
            return combo.getNombre();
        }

        return "Producto";
    }

    // REPORTE 1: VENTAS
    public Reporte generarReporteVentas(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("VENTAS POR PERIODO", inicio, fin);

        for (Compra compra : cinema.getCompras()) {

            if (enPeriodo(compra.getFechaHora().toLocalDate(), inicio, fin) && compra.getEstado() instanceof StatePagada) {

                reporte.agregarRegistro("Compra " + compra.getId() + " | Cliente: " + compra.getCliente().getNombre() + " | Total: $" + compra.getTotal());

                reporte.setTotal(reporte.getTotal() + compra.getTotal());
            }
        }

        return reporte;
    }

    // REPORTE 2: ENTRADAS VENDIDAS
    public Reporte generarReporteEntradas(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("ENTRADAS POR PELICULA", inicio, fin);

        Map<String, Integer> datos = new LinkedHashMap<>();

        for (Compra compra : cinema.getCompras()) {

            if (enPeriodo(compra.getFechaHora().toLocalDate(), inicio, fin) && compra.getEstado() instanceof StatePagada) {

                for (Entrada entrada : compra.getEntradas()) {

                    String nombre = entrada.getFuncion().getPelicula().getNombre();

                    datos.put(nombre, datos.getOrDefault(nombre, 0) + 1);
                }
            }
        }

        for (String nombre : datos.keySet()) {

            int cantidad = datos.get(nombre);

            reporte.agregarRegistro(nombre + ": " + cantidad + " entradas");

            reporte.setTotal(reporte.getTotal() + cantidad);
        }

        return reporte;
    }

    // REPORTE 3: OCUPACION DE SALAS
    public Reporte generarReporteOcupacion(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("OCUPACION DE FUNCIONES", inicio, fin);

        for (Funcion funcion : cinema.getFunciones()) {

            if (enPeriodo(funcion.getFecha(), inicio, fin)) {

                int capacidad = funcion.getSala().getAsientos().size();

                int ocupados = funcion.getAsientosReservados().size();

                double porcentaje = 0;

                if (capacidad > 0) {
                    porcentaje = 100.0 * ocupados / capacidad;
                }

                reporte.agregarRegistro("Funcion " + funcion.getId() + " | Sala " + funcion.getSala().getNumSala() + " | " + ocupados + "/" + capacidad + " | " + String.format("%.1f", porcentaje) + "%");

                reporte.setTotal(reporte.getTotal() + ocupados);
            }
        }

        return reporte;
    }

    // REPORTE 4: PRODUCTOS Y COMBOS
    public Reporte generarReporteProductos(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("PRODUCTOS Y COMBOS VENDIDOS", inicio, fin);

        Map<String, Integer> cantidades = new LinkedHashMap<>();

        for (Compra compra : cinema.getCompras()) {

            if (enPeriodo(compra.getFechaHora().toLocalDate(), inicio, fin) && compra.getEstado() instanceof StatePagada) {

                for (Producto producto : compra.getProductos()) {

                    String nombre = nombreProducto(producto);

                    cantidades.put(nombre, cantidades.getOrDefault(nombre, 0) + 1);
                }
            }
        }

        for (String nombre : cantidades.keySet()) {

            int cantidad = cantidades.get(nombre);

            reporte.agregarRegistro(nombre + ": " + cantidad);

            reporte.setTotal(reporte.getTotal() + cantidad);
        }

        return reporte;
    }

    // REPORTE 5: RECARGAS
    public Reporte generarReporteRecargas(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("RECARGAS DE TARJETAS", inicio, fin);

        for (Cliente cliente : cinema.getClientes()) {

            if (cliente.getTarjeta() == null) {
                continue;
            }

            for (Movimiento movimiento : cliente.getTarjeta().getMovimientos()) {

                if (movimiento.tipo() == TipoMovimiento.RECARGA && enPeriodo(movimiento.fecha().toLocalDate(), inicio, fin)) {

                    reporte.agregarRegistro(cliente.getNombre() + " | " + movimiento.fecha() + " | $" + movimiento.valor());

                    reporte.setTotal(reporte.getTotal() + movimiento.valor());
                }
            }
        }

        return reporte;
    }

    // REPORTE 6: CANCELACIONES Y REEMBOLSOS
    public Reporte generarReporteCancelaciones(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("CANCELACIONES Y REEMBOLSOS", inicio, fin);

        for (Compra compra : cinema.getCompras()) {

            if (compra.getEstado() instanceof StateCancelada && enPeriodo(compra.getFechaHora().toLocalDate(), inicio, fin)) {

                reporte.agregarRegistro("Compra cancelada: " + compra.getId() + " | Cliente: " + compra.getCliente().getNombre());
            }
        }

        for (Cliente cliente : cinema.getClientes()) {

            if (cliente.getTarjeta() == null) {
                continue;
            }

            for (Movimiento movimiento : cliente.getTarjeta().getMovimientos()) {

                if (movimiento.tipo() == TipoMovimiento.REEMBOLSO && enPeriodo(movimiento.fecha().toLocalDate(), inicio, fin)) {

                    reporte.agregarRegistro("Reembolso: " + cliente.getNombre() + " | " + movimiento.fecha() + " | $" + movimiento.valor());

                    reporte.setTotal(reporte.getTotal() + movimiento.valor());
                }
            }
        }

        return reporte;
    }

    // REPORTE 7: PUNTOS
    public Reporte generarReportePuntos(LocalDate inicio, LocalDate fin) {

        Reporte reporte = new Reporte("PUNTOS OBTENIDOS Y REDIMIDOS", inicio, fin);

        for (Cliente cliente : cinema.getClientes()) {

            int obtenidos = 0;
            int usados = 0;

            for (LotePuntos lote : cliente.getLotesPuntos()) {

                if (!enPeriodo(lote.getFechaObtencion(), inicio, fin)) {
                    continue;
                }

                obtenidos += lote.getCantidad();

                usados += lote.getCantidad() - lote.getCantidadRestante();
            }

            reporte.agregarRegistro(cliente.getNombre() + " | Obtenidos: " + obtenidos + " | Redimidos: " + usados);

            reporte.setTotal(reporte.getTotal() + obtenidos);
        }

        return reporte;
    }

    // GUARDAR REPORTE TXT
    public void guardarReporte(Reporte reporte, String ruta) throws IOException {

        Files.writeString(Path.of(ruta), reporte.generarTexto());
    }
}
