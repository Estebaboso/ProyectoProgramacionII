package co.uniquindio.edu.co.cinemauq.Creacionales;

import co.uniquindio.edu.co.cinemauq.model.Cliente;
import co.uniquindio.edu.co.cinemauq.model.Entrada;
import co.uniquindio.edu.co.cinemauq.model.Estructurales.Producto;
import co.uniquindio.edu.co.cinemauq.model.Promocion;

import java.util.ArrayList;
import java.util.List;

public class CompraBuilder {
    private int id;
    private Cliente cliente;
    private List<Entrada> entradas;
    private List<Producto> productos;
    private Promocion promocion;

    public CompraBuilder(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.entradas = new ArrayList<>();
        this.productos = new ArrayList<>();
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
}
