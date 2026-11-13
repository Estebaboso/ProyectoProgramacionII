package co.uniquindio.edu.co.cinemauq.Model;

import co.uniquindio.edu.co.cinemauq.Comportamentales.ObserverEvento;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StateCompra;
import co.uniquindio.edu.co.cinemauq.Comportamentales.StrategyReembolso;
import co.uniquindio.edu.co.cinemauq.Estructurales.Producto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private int id;
    private Cliente cliente;
    private LocalDateTime fechaHora;
    private List<Entrada> entradas;
    private List<Producto> productos;
    private Promocion promocion;
    private double subtotal;
    private double iva;
    private double total;
    private StateCompra estado;
    private StrategyReembolso politicaReembolso;
    private List<ObserverEvento> observadores;

    public Compra(int id, Cliente cliente, LocalDateTime fechaHora, List<Entrada> entradas,
                  List<Producto> productos, Promocion promocion) {
        this.id = id;
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        this.entradas = entradas;
        this.productos = productos;
        this.promocion = promocion;
        this.observadores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public StateCompra getEstado() {
        return estado;
    }

    public void setEstado(StateCompra estado) {
        this.estado = estado;
    }

    public StrategyReembolso getPoliticaReembolso() {
        return politicaReembolso;
    }

    public void setPoliticaReembolso(StrategyReembolso politicaReembolso) {
        this.politicaReembolso = politicaReembolso;
    }

    public List<ObserverEvento> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<ObserverEvento> observadores) {
        this.observadores = observadores;
    }
}
