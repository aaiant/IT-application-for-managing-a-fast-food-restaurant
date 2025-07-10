package org.example.Vizualizare.Carduri.Est;

import lombok.*;
import java.util.List;

@Getter @Setter
public class ProdusPreluat {
    private String denumire, linkPoza, eticheta, unitateMasura, producator, categorie, subcategorie;
    private int continut;
    private double pret;
    private List<String> ingrediente;

    public ProdusPreluat(String denumire, String linkPoza, String eticheta, int continut, double pret, List<String> ingrediente,
                         String unitateMasura, String producator, String categorie, String subcategorie) {
        this.denumire = denumire;
        this.linkPoza = linkPoza;
        this.eticheta = eticheta;
        this.continut = continut;
        this.pret = pret;
        this.ingrediente = ingrediente;
        this.unitateMasura = unitateMasura;
        this.producator = producator;
        this.categorie = categorie;
        this.subcategorie = subcategorie;
    }
}
