package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Estructurales.Producto;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Entrada;
import co.uniquindio.edu.co.cinemauq.Model.LotePuntos;

import java.time.LocalDate;

public class ObserverPuntos implements ObserverEvento {


    // Este es el metodo que permite manejar los puntos
    @Override
    public void actualizar(EventoCine evento) {

        //  Primero recorre revisa el tipo de evento, si es diferente de compra_pagada lo ignora

        if (evento.getTipo() != TipoEvento.COMPRA_PAGADA) {
            return;
        }


        // Asigna compra a evento.getCompra y crea la variable puntos

            Compra compra = evento.getCompra();
            int puntos = 0;

            // Recorre entradas y suma los puntos según el metodo obtenerPuntos lo mismo en producto

            for (Entrada entrada : compra.getEntradas()) {
                puntos += entrada.obtenerPuntos();
            }

            for (Producto producto : compra.getProductos()) {
                puntos += producto.obtenerPuntos();
            }
            // Asigna la fecha actual y añade los puntos junto a la fecha actual y el plusYear es para que sume 1 año a la fecha
            // Ejemplo 100-2026-2027

            if (puntos > 0) {
                LocalDate hoy = LocalDate.now();
                compra.getCliente().getLotesPuntos().add(new LotePuntos(puntos, hoy, hoy.plusYears(1)));
            }


        }

    }
