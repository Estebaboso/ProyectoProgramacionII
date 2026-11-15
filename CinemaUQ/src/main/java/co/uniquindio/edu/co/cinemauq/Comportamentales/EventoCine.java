package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Funcion;

public class EventoCine {

    private TipoEvento tipo;
    private Compra compra;
    private Funcion funcion;



    public EventoCine(TipoEvento tipo, Compra compra) {
        this.tipo = tipo;
        this.compra = compra;
    }

    public EventoCine(TipoEvento tipo, Funcion funcion) {
        this.tipo = tipo;
        this.funcion = funcion;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
}