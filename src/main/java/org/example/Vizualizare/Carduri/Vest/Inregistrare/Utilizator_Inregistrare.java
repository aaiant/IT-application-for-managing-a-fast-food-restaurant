package org.example.Vizualizare.Carduri.Vest.Inregistrare;

import lombok.*;

import java.util.EventObject;

@Getter @Setter
public class Utilizator_Inregistrare extends EventObject {
    //  Atribute
    private String numeUtilizator, parola, numeFamilie,
            prenume, nrTel, adresaEmail, tara, judet,
            localitate, strada;
    private int nrLocuinta;

    //  Constructor
    public Utilizator_Inregistrare(Object source) {
        super(source);
    }

    public Utilizator_Inregistrare(Object sursa, String numeUtilizator, String parola, String numeFamilie,
                                   String prenume, String nrTel, String adresaEmail, String tara,
                                   String judet, String localitate, String strada, int nrLocuinta){
        super(sursa);
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.numeFamilie = numeFamilie;
        this.prenume = prenume;
        this.nrTel = nrTel;
        this.adresaEmail = adresaEmail;
        this.tara = tara;
        this.judet = judet;
        this.localitate = localitate;
        this.strada = strada;
        this.nrLocuinta = nrLocuinta;
    }
}
