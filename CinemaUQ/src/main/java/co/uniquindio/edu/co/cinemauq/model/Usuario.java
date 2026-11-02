package co.uniquindio.edu.co.cinemauq.model;

public abstract class Usuario {

    private String nombre;
    private String id;
    private String telefono;
    private String contrasena;
    private String correoE;

    public Usuario(String nombre, String id, String telefono, String contrasena, String correoE) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.correoE = correoE;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

}
