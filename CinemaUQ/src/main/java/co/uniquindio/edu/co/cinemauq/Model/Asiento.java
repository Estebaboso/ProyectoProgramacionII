package co.uniquindio.edu.co.cinemauq.Model;

public class Asiento {
    private String codigo;
    private int fila;
    private int columna;

    public Asiento(String codigo, int fila, int columna) {
        this.codigo = codigo;
        this.fila = fila;
        this.columna = columna;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
