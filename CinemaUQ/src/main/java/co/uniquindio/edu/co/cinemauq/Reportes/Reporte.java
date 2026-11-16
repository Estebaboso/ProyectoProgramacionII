
package co.uniquindio.edu.co.cinemauq.Reportes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reporte {

    private String titulo;
    private LocalDateTime fechaGeneracion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<String> registros;
    private double total;

    public Reporte(String titulo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.titulo = titulo;
        this.fechaGeneracion = LocalDateTime.now();
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.registros = new ArrayList<>();
        this.total = 0;
    }

    public void agregarRegistro(String registro) {
        registros.add(registro);
    }
// Esto es solo la estructura del reporte
public String generarTexto() {

    StringBuilder texto = new StringBuilder();

    texto.append("====================================\n");
    texto.append(titulo).append("\n");
    texto.append("====================================\n");

    texto.append("Fecha de generacion: ")
            .append(fechaGeneracion).append("\n");

    texto.append("Desde: ")
            .append(fechaInicio).append("\n");

    texto.append("Hasta: ")
            .append(fechaFin).append("\n\n");

    for (String registro : registros) {
        texto.append(registro).append("\n");
    }

    texto.append("\nRegistros encontrados: ")
            .append(registros.size());

    texto.append("\nTotal del reporte: ")
            .append(total);

    return texto.toString();
}

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public List<String> getRegistros() {
        return registros;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
