package org.example.Model;

import lombok.*;

@Getter @Setter
public class ProdusCos {
    private String produs;
    private double pret;
    private int id, cantitate;

    public ProdusCos(int id, String produs, double pret, int cantitate) {
        this.id = id;
        this.produs = produs;
        this.pret = pret;
        this.cantitate = cantitate;
    }
}
