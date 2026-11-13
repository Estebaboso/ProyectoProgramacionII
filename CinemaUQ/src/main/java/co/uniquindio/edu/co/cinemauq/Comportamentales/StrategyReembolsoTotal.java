package co.uniquindio.edu.co.cinemauq.Comportamentales;

public class StrategyReembolsoTotal implements StrategyReembolso {
    private double porcentaje;

    public StrategyReembolsoTotal(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
