package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.Entrada;
import co.uniquindio.edu.co.cinemauq.Model.Movimiento;
import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;
import co.uniquindio.edu.co.cinemauq.Model.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.UUID;

public class StatePagada implements StateCompra {
    private Compra compra;

    public StatePagada(Compra compra) {

        this.compra = compra;
    }

    @Override
    public boolean pagar(){
        System.out.println("Esta compra ya se encuentra pagada");
        return false;
    }

    @Override
    public void cancelar(){
        System.out.println("Solicitando cancelacion de la compra...");

        StrategyReembolso estrategia = StrategyReembolsoSelector.Seleccionar(compra, LocalDateTime.now());

        if(!estrategia.permiteCancelacion()){
            System.out.println("No se permite calcelar esta compra");
            return;
        }

        double montoReembolso = estrategia.calcularReembolso(compra.getTotal());
        compra.setPoliticaReembolso(estrategia);

        TarjetaVirtual tarjeta = compra.getCliente().getTarjeta();
        tarjeta.setSaldo(tarjeta.getSaldo() + monto);
        tarjeta.getMovimientos().add(new Movimiento(UUID.randomUUID().toString(),
                TipoMovimiento.REEMBOLSO, monto, LocalDateTime.now(), compra));

        for (Entrada entrada : compra.getEntradas()) {
            entrada.getFuncion().getAsientosReservados().remove(entrada.getAsiento().getCodigo());
        }

        compra.setEstado(new StateCancelada(compra));

        System.out.println("Reembolso realizado: " + monto);
    }
}
