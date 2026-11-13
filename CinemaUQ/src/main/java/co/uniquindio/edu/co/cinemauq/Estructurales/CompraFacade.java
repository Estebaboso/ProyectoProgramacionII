package co.uniquindio.edu.co.cinemauq.Estructurales;

import co.uniquindio.edu.co.cinemauq.Creacionales.CinemaUQ;

public class CompraFacade {
    private CinemaUQ cinemaUQ;

    public CompraFacade(CinemaUQ cinemaUQ) {
        this.cinemaUQ = cinemaUQ;
    }

    public CinemaUQ getCinemaUQ() {
        return cinemaUQ;
    }

    public void setCinemaUQ(CinemaUQ cinemaUQ) {
        this.cinemaUQ = cinemaUQ;
    }
}
