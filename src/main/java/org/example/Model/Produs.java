package org.example.Model;

import lombok.*;

import java.util.ArrayList;

@Getter @Setter
public class Produs {
    //  Atribute
    private String denProducator, denProdus, abvUM, linkPozaProdus, eticheta;
    private double pretProdus;
    private int idProdus, continutProdus;
    private ArrayList<String> ingrediente;

    //  Constructor
    public Produs(int idProdus, String denProducator, String denProdus, String abvUM, int continutProdus, double pretProdus,
                  String linkPozaProdus, String eticheta, ArrayList<String> ingrediente) {
        this.idProdus = idProdus;
        this.denProducator = denProducator;
        this.denProdus = denProdus;
        this.abvUM = abvUM;
        this.continutProdus = continutProdus;
        this.pretProdus = pretProdus;
        this.linkPozaProdus = linkPozaProdus;
        this.eticheta = eticheta;
        this.ingrediente = ingrediente;
    }
}
