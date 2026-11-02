package co.uniquindio.edu.co.cinemauq.model;

public class Cliente extends Usuario {
    private int numTarjeta;

    public Cliente(int numTarjeta) {
        super(nombre, id, telefono, contrasena, correoE);

        this.numTarjeta = numTarjeta;
    }
}