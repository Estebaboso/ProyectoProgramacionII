package co.uniquindio.edu.co.cinemauq.Model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int numSala;
    private List<Asiento> asientos;

    public Sala(int numSala) {
        this.numSala = numSala;
        this.asientos = new ArrayList<>();
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }
}
