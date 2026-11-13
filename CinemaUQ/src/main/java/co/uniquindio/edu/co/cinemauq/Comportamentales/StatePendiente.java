package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;

public class StatePendiente implements StateCompra {
    private Compra compra;

    public StatePendiente(Compra compra) {
        this.compra = compra;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
