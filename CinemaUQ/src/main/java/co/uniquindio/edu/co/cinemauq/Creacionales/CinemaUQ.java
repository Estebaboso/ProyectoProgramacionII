package co.uniquindio.edu.co.cinemauq.Creacionales;

import co.uniquindio.edu.co.cinemauq.Model.Administrador;
import co.uniquindio.edu.co.cinemauq.Model.Cliente;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Funcion;
import co.uniquindio.edu.co.cinemauq.Model.Pelicula;
import co.uniquindio.edu.co.cinemauq.Model.Promocion;
import co.uniquindio.edu.co.cinemauq.Model.Sala;
import co.uniquindio.edu.co.cinemauq.Estructurales.Producto;

import java.util.ArrayList;
import java.util.List;

public class CinemaUQ {
    private static CinemaUQ instancia;

    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Funcion> funciones;
    private List<Cliente> clientes;
    private List<Administrador> administradores;
    private List<Producto> productos;
    private List<Promocion> promociones;
    private List<Compra> compras;
    private int puntosEntrada = 300;
    private int puntosProducto = 200;
    private int puntosCombo = 400;

    private CinemaUQ() {
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.promociones = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    //este metodo pertenece solo a la clase nadie puede crear el objeto desde afuera
    //el cine con ese condicional solo se crea una sola vez luego es reutilizable
    //la palabra synchronized lo que hace es crear un afila de espera si se nesesita dos llamados por interfaz o por reportes
    //solo se aplica una sola instancea
    public static synchronized CinemaUQ getInstance() {
        if (instancia == null) {
            instancia = new CinemaUQ();
        }
        return instancia;
    }

    // Los controladores de inicio de sesión consultan estos usuarios registrados.
    public Administrador autenticarAdministrador(String correo, String clave) {
        if (correo == null || clave == null) return null;
        for (Administrador admin : administradores) {
            if (admin.getCorreoE().equalsIgnoreCase(correo.trim())
                    && admin.getContrasena().equals(clave)) return admin;
        }
        return null;
    }

    public Cliente autenticarCliente(String correo, String clave) {
        if (correo == null || clave == null) return null;
        for (Cliente cliente : clientes) {
            if (cliente.getCorreoE().equalsIgnoreCase(correo.trim())
                    && cliente.getContrasena().equals(clave)) return cliente;
        }
        return null;
    }

    public boolean registrarCliente(Cliente nuevo) {
        if (nuevo == null || nuevo.getId() == null || nuevo.getCorreoE() == null || nuevo.getId().isBlank() || nuevo.getCorreoE().isBlank()) {
            return false;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getId().equalsIgnoreCase(nuevo.getId()) || cliente.getCorreoE().equalsIgnoreCase(nuevo.getCorreoE())) {
                return false;
            }
        }

        for (Administrador admin : administradores) {
            if (admin.getId().equalsIgnoreCase(nuevo.getId()) || admin.getCorreoE().equalsIgnoreCase(nuevo.getCorreoE())) {
                return false;
            }
        }

        clientes.add(nuevo);
        return true;
    }

    public boolean registrarProducto(Producto producto) {
        if (producto == null || productos.contains(producto)) {
            return false;
        }

        productos.add(producto);
        return true;
    }

    public boolean registrarPromocion(Promocion promocion) {
        if (promocion == null) {
            return false;
        }

        for (Promocion p : promociones) {
            if (p.getId() == promocion.getId()) {
                return false;
            }
        }

        promociones.add(promocion);
        return true;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Promocion> getPromociones() {
        return promociones;
    }

    public void setPromociones(List<Promocion> promociones) {
        this.promociones = promociones;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
    public int getPuntosEntrada() { return puntosEntrada; }
    public int getPuntosProducto() { return puntosProducto; }
    public int getPuntosCombo() { return puntosCombo; }

    public void setPuntosEntrada(int puntosEntrada) { this.puntosEntrada = puntosEntrada; }
    public void setPuntosProducto(int puntosProducto) { this.puntosProducto = puntosProducto; }
    public void setPuntosCombo(int puntosCombo) { this.puntosCombo = puntosCombo; }
}
