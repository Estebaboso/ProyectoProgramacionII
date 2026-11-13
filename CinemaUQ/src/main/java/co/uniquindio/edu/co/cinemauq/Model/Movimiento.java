package co.uniquindio.edu.co.cinemauq.Model;

import java.time.LocalDateTime;

public record Movimiento(String id, TipoMovimiento tipo, double valor, LocalDateTime fecha, Compra compra) {
}
