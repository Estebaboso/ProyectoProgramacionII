package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.EstadoFuncion;
import co.uniquindio.edu.co.cinemauq.Model.Funcion;

import java.time.Duration;
import java.time.LocalDateTime;

public class StrategyReembolsoSelector {
    //esto convierte las 24 horas del dia a min
    private static final long MINUTOS_PARA_TOTAL = 24*60;
    //esto nos ayudara mas tarde para calcular si se reembolsa o no
    private static final long MINUTOS_MINIMOS = 2*60;

    public StrategyReembolso Seleccionar(Compra compra, LocalDateTime ahora){

        //esto es complejo pero basicamente este metodo no depende de entradas solo es de confiteria asi que regresa el 100%
        if(compra.getEntradas().isEmpty()){
            return new StrategyReembolsoTotal();
        }

        //esto es para obtener la funcion que se presenta
        Funcion funcion = compra.getEntradas().get(0).getFuncion();

        //esto verifica si la funcion a la cual se quiere reembolsar fue cancelada si si entonces regresa el 100%
        if(funcion.getEstadoFuncion() == EstadoFuncion.CANCELADA){
            return new StrategyReembolsoTotal();
        }

        //estas variables lo que hacen es sacar la hora de la funcion para calcular
        LocalDateTime inicio = LocalDateTime.of(funcion.getFecha(), funcion.getHoraInicio());
        long minutos = Duration.between(ahora, inicio).toMinutes();

        //aqui se utiliza la hora y se verifica si es mayor a 24h o mayor a 2h
        //en caso de 24h regresa el 100%
        //en caso de 2h regresa el 80%
        //y si es menos de 2h ya no se permite reembolsar
        if(minutos > MINUTOS_PARA_TOTAL){
            return new StrategyReembolsoTotal();
        }if(minutos >= MINUTOS_MINIMOS){
            return new StrategyReembolsoTotal();
        }
        return new StrategyReembolsoNoPermitido();
    }
}
