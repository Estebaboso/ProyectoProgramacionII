package co.uniquindio.edu.co.cinemauq.Comportamentales;

public class StrategyReembolsoTotal implements StrategyReembolso {

        //esto hace que se permita el reembolso osea es antes de las 2 horas de la funcion y mayor a 24h de la funcion
        @Override
        public boolean permiteCancelacion() {
            return true;
        }

        // esto se supone que regresa el valor Total en el reembolso osea el 100%
        @Override
        public double calcularReembolso(double montoPagado) {
            return montoPagado;
        }
    }
