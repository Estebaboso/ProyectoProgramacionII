package co.uniquindio.edu.co.cinemauq.Comportamentales;

public interface StrategyReembolso {

    boolean permiteCancelacion();

    double calcularReembolso(double montoPagado);
}
