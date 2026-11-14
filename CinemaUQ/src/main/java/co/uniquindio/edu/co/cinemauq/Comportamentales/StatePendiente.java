package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;

public class StatePendiente implements StateCompra {
    private Compra compra;

    public StatePendiente(Compra compra) {
        this.compra = compra;
    }

    @Override
    public void cancelar(){
        System.out.println("La compra pendiente ha sido cancelada.");

        compra.setEstado(new StateCancelada(compra));
    }

    @Override
    public void pagar(){
        System.out.println("La compra esta pendiente de pago");

        //aqui iria codigo para conectar con el proxy i guess antes de que pase a pagada

        compra.setEstado(new StatePagada(compra));
        System.out.println("La compra se realizo correctamente");
    }
}
