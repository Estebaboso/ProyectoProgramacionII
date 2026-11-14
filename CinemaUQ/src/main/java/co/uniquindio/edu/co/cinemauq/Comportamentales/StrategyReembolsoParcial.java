package co.uniquindio.edu.co.cinemauq.Comportamentales;

public class StrategyReembolsoParcial implements StrategyReembolso {
    private static final double PORCENTAJE_REEMBOLSO = 80;

    @Override
    public boolean permiteCancelacion(){
        return true;
    }

    //esto es cuando es mayor de 2h y menos de las 24 h, se devolvera solo el 80% del valor
    @Override
    public double calcularReembolso(double montoPagado){
        return montoPagado * PORCENTAJE_REEMBOLSO / 100;
    }

}
