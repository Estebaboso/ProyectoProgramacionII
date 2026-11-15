package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Model.EstadoTarjetaVirtual;
import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;
import co.uniquindio.edu.co.cinemauq.Model.Usuario;

public class ProxyPago implements ProcesadorPago {
    private TarjetaVirtual tarjeta;
    private ProcesadorPago procesadorReal;

    public ProxyPago(TarjetaVirtual tarjeta, ProcesadorPago procesadorReal) {
        this.tarjeta = tarjeta;
        this.procesadorReal = procesadorReal;
    }

    // Puede estar separado pero realmente no interesa, si se incumple cualquiera de las dos no se puede hacer la transaccion
    @Override
    public boolean cobrarPago(TarjetaVirtual tarjeta, Usuario usuario, double monto) {
        System.out.println("[REPORTE AUDITORIA PARA] " + usuario.getNombre() + " por el saldo de " + tarjeta.getSaldo());

        if (tarjeta.getEstadoTarjeta() == EstadoTarjetaVirtual.NO_ACTIVA || tarjeta.getSaldo() < 0) { //El 0 es temporal luego se cambia
            System.out.println("Transacción denegada para el usuario " + usuario.getNombre() + ". No cumple con las condiciones");
            return false;
        }

        //Revisa si no existe un procesador de pago todavia y lo crea en PagoTarjetaVirtual porque ahi se realiza el cobro
        if (procesadorReal == null) {
            System.out.println("\n[PROXY] creando nuevo procesador de pago");
            procesadorReal = new PagoTarjetaVirtual();
        }

        return procesadorReal.cobrarPago(tarjeta, usuario, monto);
    }
        public TarjetaVirtual getTarjeta () {
            return tarjeta;
        }

        public void setTarjeta (TarjetaVirtual tarjeta){
            this.tarjeta = tarjeta;
        }

        public ProcesadorPago getProcesadorReal () {
            return procesadorReal;
        }

        public void setProcesadorReal (ProcesadorPago procesadorReal){
            this.procesadorReal = procesadorReal;
        }
    }
