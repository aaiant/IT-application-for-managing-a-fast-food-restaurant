package org.example.Vizualizare.Carduri.Intrebari_Raspunsuri;

import lombok.*;

import java.util.EventObject;

@Getter @Setter
public class CodIntrebare extends EventObject {
    //  Atribute
    private String codIntrebare;

    //  Constructori
    public CodIntrebare(Object source) {
        super(source);
    }

    public CodIntrebare(Object sursa, String codIntrebare) {
        super(sursa);
        this.codIntrebare = codIntrebare;
    }
}
