package co.uniquindio.edu.co.cinemauq.Reportes;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StatePagada;
import co.uniquindio.edu.co.cinemauq.Model.Compra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class GeneradorReportes {

    private CinemaUQ cinema;

    public GeneradorReportes() {
        this.cinema = CinemaUQ.getInstance();
    }

    public Reporte generarReporteVentas(LocalDate fechaInicio, LocalDate fechaFin) {
 // crea un reporte
        Reporte reporte = new Reporte("REPORTE DE VENTAS", fechaInicio, fechaFin
        );

        List<Compra> compras = cinema.getCompras();

        for (Compra compra : compras) {
    // Revisa los datos y los agrega al registro, termina retornando un reporte
            LocalDate fechaCompra = compra.getFechaHora().toLocalDate();

            boolean estaEnRango = !fechaCompra.isBefore(fechaInicio) && !fechaCompra.isAfter(fechaFin);

            boolean estaPagada = compra.getEstado() instanceof StatePagada;

            if (estaEnRango && estaPagada) {

                reporte.agregarRegistro("Compra: " + compra.getId() + " | Cliente: " + compra.getCliente().getNombre() +
                        " | Fecha: " + fechaCompra + " | Total: $" + compra.getTotal()
                );

                reporte.setTotal(reporte.getTotal() + compra.getTotal());
            }
        }

        return reporte;
    }

    public void guardarReporte(Reporte reporte, String ruta) throws IOException {

        String contenido = reporte.generarTexto();

        Files.writeString(Path.of(ruta), contenido);
    }
}
