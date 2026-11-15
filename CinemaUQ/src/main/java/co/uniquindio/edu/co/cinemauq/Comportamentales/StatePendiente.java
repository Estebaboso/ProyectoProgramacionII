package co.uniquindio.edu.co.cinemauq.Comportamentales;

import co.uniquindio.edu.co.cinemauq.Estructurales.ProcesadorPago;
import co.uniquindio.edu.co.cinemauq.Estructurales.ProxyPago;
import co.uniquindio.edu.co.cinemauq.Model.Compra;
import co.uniquindio.edu.co.cinemauq.Model.TarjetaVirtual;

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
    public boolean pagar(){
        System.out.println("La compra esta pendiente de pago");

        //primero se obtiene la tarjeta del cliente que esta realizando la compra
        TarjetaVirtual tarjeta = compra.getCliente().getTarjeta();
        //aqui se crea el proxy el cua lrecibe la tarjeta del usuario y no recibe un procesador pago real, ya que este se crear despues en ProxyPago
        ProcesadorPago proxy = new ProxyPago(tarjeta,null);
        //mandamos al proxy a verificar si se realiza el pago
        boolean pagoRealizado = proxy.cobrarPago(tarjeta, compra.getCliente(), compra.getTotal());
        //nos devolvera un tru o un false en caso de que sea true la compra se realizo y en caso de false no se completo la compra
        if(pagoRealizado){
            compra.setEstado(new StatePagada(compra));
            System.out.println("La compra se realizo correctamente");
            return true;
        }else{
            System.out.println("No se puedo realizar el pago.");
            return false;
        }

    }
}
