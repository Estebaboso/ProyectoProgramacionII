package co.uniquindio.edu.co.cinemauq;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;
import co.uniquindio.edu.co.cinemauq.Model.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HelloAplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Obtener instancia de CinemaUQ
        CinemaUQ cinema = CinemaUQ.getInstance();

        // Crear administrador por defecto
        Administrador adminDefault = new Administrador(
                "Admin Principal",
                "01N",
                "3001234567",
                "Admin123",
                "admin@cinemauq.com"
        );

        cinema.getAdministradores().add(adminDefault);

        // Crear tarjeta virtual para el cliente
        TarjetaVirtual tarjetaDefault = new TarjetaVirtual(
                "TV001",
                100000,
                EstadoTarjetaVirtual.ACTIVA
        );

        // Crear cliente por defecto
        Cliente clienteDefault = new Cliente(
                "Cliente Prueba",
                "02N",
                "3009876543",
                "Cliente123",
                "cliente@cinemauq.com",
                tarjetaDefault
        );

        // Asignar puntos de prueba al cliente
        LotePuntos puntosDefault = new LotePuntos(
                500,
                LocalDate.now(),
                LocalDate.now().plusYears(1)
        );

        clienteDefault.getLotesPuntos().add(puntosDefault);

        // Registrar cliente
        cinema.getClientes().add(clienteDefault);


        // Abrir pantalla inicial
        FXMLLoader loader = new FXMLLoader(
                HelloAplication.class.getResource(
                        "/co.uniquindio.edu.co.cinemauq/inicio-view.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("CinemaUQ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}