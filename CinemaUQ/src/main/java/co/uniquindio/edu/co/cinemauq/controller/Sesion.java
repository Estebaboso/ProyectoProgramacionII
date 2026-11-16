package co.uniquindio.edu.co.cinemauq.controller;

import co.uniquindio.edu.co.cinemauq.Model.*;
import java.util.ArrayList;
import java.util.List;

// Estado temporal de la sesión mientras la aplicación esté abierta.
public class Sesion {
    public static Cliente clienteActual;
    public static Administrador administradorActual;
    public static Funcion funcionSeleccionada;
    public static List<Asiento> asientosSeleccionados = new ArrayList<>();
    public static void cerrar() {
        clienteActual = null;
        administradorActual = null;
        funcionSeleccionada = null;
        asientosSeleccionados.clear();
    }
}

