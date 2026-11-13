package co.uniquindio.edu.co.cinemauq.Comportamentales;

public class StrategyReembolsoParcial implements StrategyReembolso {
    private double porcentaje;

    public StrategyReembolsoParcial(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
