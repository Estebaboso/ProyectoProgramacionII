package co.uniquindio.edu.co.cinemauq.PruebaCreacional;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Creacionales.CompraBuilder;
import co.uniquindio.edu.co.cinemauq.Creacionales.Entrada2D;
import co.uniquindio.edu.co.cinemauq.Estructurales.ProductoIndividual;
import co.uniquindio.edu.co.cinemauq.Exepciones.CompraInvalidaExcepcion;
import co.uniquindio.edu.co.cinemauq.Model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class PruebaCreacionales {

    public static void main(String[] args) {


        // ===== 1. SINGLETON =====
        System.out.println("===== SINGLETON =====");
        CinemaUQ cineA = CinemaUQ.getInstance();
        CinemaUQ cineB = CinemaUQ.getInstance();
        System.out.println("¿Es la misma instancia? " + (cineA == cineB));

        // Datos de prueba (los valores de los enums se toman con values()[0];
        // cámbialos por el valor real si quieres, por ejemplo TipoFuncion.DOS_D)
        Pelicula pelicula = new Pelicula("Película de prueba", 1, "Acción", 120,
                RestriccionPelicula.values()[0]);
        Sala sala = new Sala(1);
        Asiento a1 = new Asiento("A1", 1, 1);
        Asiento a2 = new Asiento("A2", 1, 2);
        sala.getAsientos().add(a1);
        sala.getAsientos().add(a2);

        Funcion funcion = new Funcion(1, pelicula, sala, LocalDate.now().plusDays(10),
                LocalTime.of(18, 0), 10000, EstadoFuncion.ACTIVA, TipoFuncion.values()[0]);

        cineA.getFunciones().add(funcion);
        System.out.println("Funciones vistas desde la otra referencia: " + cineB.getFunciones().size());


        // ===== 2. BUILDER =====
        System.out.println("\n===== BUILDER =====");
        Cliente cliente = new Cliente("Ana", "1", "3001234567", "clave", "ana@mail.com", null);
        ProductoIndividual palomitas = new ProductoIndividual("Palomitas", 1, 8000);
        Promocion promo = new Promocion(1, "10% de descuento", 10,
                LocalDate.now().minusDays(1), LocalDate.now().plusDays(30));

        try {
            Compra compra = new CompraBuilder(1, cliente)
                    .agregarEntrada(new Entrada2D(1, funcion, a1, funcion.getPrecio()))
                    .agregarEntrada(new Entrada2D(2, funcion, a2, funcion.getPrecio()))
                    .agregarProducto(palomitas)
                    .conPromocion(promo)
                    .construir();

            System.out.printf("Subtotal (con descuento): %.2f%n", compra.getSubtotal()); // 25200.00
            System.out.printf("IVA 19%%: %.2f%n", compra.getIva());                        // 4788.00
            System.out.printf("Total: %.2f%n", compra.getTotal());                         // 29988.00
            System.out.println("Estado inicial: " + compra.getEstado().getClass().getSimpleName());
        } catch (CompraInvalidaExcepcion e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        // Simulamos que A1 ya fue pagado y reservado, e intentamos venderlo otra vez
        funcion.getAsientosReservados().add("A1");
        try {
            new CompraBuilder(2, cliente)
                    .agregarEntrada(new Entrada2D(3, funcion, a1, funcion.getPrecio()))
                    .construir();
            System.out.println("ERROR: la compra debió ser rechazada");
        } catch (CompraInvalidaExcepcion e) {
            System.out.println("Rechazada correctamente: " + e.getMessage());
        }


        // ===== 3. PROTOTYPE =====
        System.out.println("\n===== PROTOTYPE =====");
        Funcion copia = funcion.clonarPara(2, LocalDate.now().plusDays(11), LocalTime.of(20, 0));
        System.out.println("Reservas de la original: " + funcion.getAsientosReservados()); // [A1]
        System.out.println("Reservas de la copia: " + copia.getAsientosReservados());       // []
        System.out.println("¿Comparten la lista de reservas? "
                + (funcion.getAsientosReservados() == copia.getAsientosReservados()));    // false
        System.out.println("¿Comparten la película? " + (funcion.getPelicula() == copia.getPelicula())); // true
        System.out.println("Estado de la copia: " + copia.getEstadoFuncion());

        cineA.getFunciones().add(copia);
        System.out.println("Funciones registradas en el cine: " + cineB.getFunciones().size()); // 2
    }
}