package co.uniquindio.edu.co.cinemauq.Comportamentales;

public interface SubjectEvento {
    void subscribir(ObserverEvento observer);
    void desuscribrir(ObserverEvento observer);
    void notificarObservadores(EventoCine event);
}

