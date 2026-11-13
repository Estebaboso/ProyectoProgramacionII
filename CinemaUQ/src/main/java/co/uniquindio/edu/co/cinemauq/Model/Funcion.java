package co.uniquindio.edu.co.cinemauq.Model;

import co.uniquindio.edu.co.cinemauq.model.Comportamentales.ObserverEvento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {
    private int id;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private double precio;
    private EstadoFuncion estadoFuncion;
    private TipoFuncion tipoFuncion;
    private List<String> asientosReservados; // codigos de los asientos ocupados en ESTA funcion
    private List<ObserverEvento> observadores;

    public Funcion(int id, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime horaInicio, double precio,
                   EstadoFuncion estadoFuncion, TipoFuncion tipoFuncion) {
        this.id = id;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.precio = precio;
        this.estadoFuncion = estadoFuncion;
        this.tipoFuncion = tipoFuncion;
        this.asientosReservados = new ArrayList<>();
        this.observadores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoFuncion getEstadoFuncion() {
        return estadoFuncion;
    }

    public void setEstadoFuncion(EstadoFuncion estadoFuncion) {
        this.estadoFuncion = estadoFuncion;
    }

    public TipoFuncion getTipoFuncion() {
        return tipoFuncion;
    }

    public void setTipoFuncion(TipoFuncion tipoFuncion) {
        this.tipoFuncion = tipoFuncion;
    }

    public List<String> getAsientosReservados() {
        return asientosReservados;
    }

    public void setAsientosReservados(List<String> asientosReservados) {
        this.asientosReservados = asientosReservados;
    }

    public List<ObserverEvento> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<ObserverEvento> observadores) {
        this.observadores = observadores;
    }
}
