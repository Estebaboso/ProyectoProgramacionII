package co.uniquindio.edu.co.cinemauq.Model;

import java.time.LocalDateTime;

// Se deja como record (inmutable): un movimiento ya registrado no deberia modificarse.
// 'compra' es null cuando el movimiento es una recarga.
public record Movimiento(String id, TipoMovimiento tipo, double valor, LocalDateTime fecha, Compra compra) {
}
