package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;

public class StateCancelada implements StateCompra {
    private Compra compra;

    public StateCancelada(Compra compra) {

        this.compra = compra;
    }

    @Override
    public boolean pagar(){
        System.out.println("no se puede comprar una compra cancelada");
        return false;
    }

    @Override
    public void cancelar(){
        System.out.println("La compra ya se encuentra cancelada.");
    }
}
