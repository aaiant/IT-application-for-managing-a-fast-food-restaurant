package org.example.Model;

import lombok.*;

import java.util.ArrayList;
import java.util.EventObject;

@Getter @Setter
public class UtilizatorComplet extends EventObject {
    //  Atribute
    private String rol, numeUtilizator, parola, numeFamilie,
            prenume, nrTel, adresaEmail, tara, judet,
            localitate, strada;
    private int idUtilizator, nrLocuinta;
    private ArrayList<CardBancar> carduriBancare;

    //  Constructor
    public UtilizatorComplet(Object source) {
        super(source);
    }

    public UtilizatorComplet(Object sursa, int idUtilizator, String rol, String numeUtilizator, String parola, String numeFamilie,
                                   String prenume, String nrTel, String adresaEmail, String tara,
                                   String localitate, String strada, String judet, int nrLocuinta, ArrayList<CardBancar> carduriBancare){
        super(sursa);
        this.idUtilizator = idUtilizator;
        this.rol = rol;
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.numeFamilie = numeFamilie;
        this.prenume = prenume;
        this.nrTel = nrTel;
        this.adresaEmail = adresaEmail;
        this.tara = tara;
        this.localitate = localitate;
        this.strada = strada;
        this.judet = judet;
        this.nrLocuinta = nrLocuinta;
        this.carduriBancare = carduriBancare;
    }
}