package org.example.Model;

import lombok.*;

@Getter @Setter
public class UtilizatorRecuperat {
    private String numeUtilizator, parola, numeFamilie, prenume;
    private int id;

    public UtilizatorRecuperat(int id, String numeUtilizator, String parola, String numeFamilie, String prenume) {
        this.id = id;
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.numeFamilie = numeFamilie;
        this.prenume = prenume;
    }
}
