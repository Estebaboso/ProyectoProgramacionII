package co.uniquindio.edu.co.cinemauq.Comportamentales;

public class StrategyReembolsoNoPermitido implements StrategyReembolso {

    @Override
    public boolean permiteCancelacion(){
        return false;
    }

    //esto aplica cuando faltan menos de 2h para la funcion el reembolso se anula
    @Override
    public double calcularReembolso(double montroPagado){
        return 0;
    }
}
