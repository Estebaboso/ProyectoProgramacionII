package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;

import java.time.LocalDateTime;

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

        System.out.println("Reembolso calculado: " +montoReembolso);
    }
}
