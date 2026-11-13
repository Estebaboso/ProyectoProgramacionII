package co.uniquindio.edu.co.cinemauq.Model;

import java.time.LocalDate;

public class LotePuntos {
    private int cantidad;
    private int cantidadRestante;
    private LocalDate fechaObtencion;
    private LocalDate fechaVencimiento;

    public LotePuntos(int cantidad, LocalDate fechaObtencion, LocalDate fechaVencimiento) {
        this.cantidad = cantidad;
        this.cantidadRestante = cantidad;
        this.fechaObtencion = fechaObtencion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(int cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    public LocalDate getFechaObtencion() {
        return fechaObtencion;
    }

    public void setFechaObtencion(LocalDate fechaObtencion) {
        this.fechaObtencion = fechaObtencion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
