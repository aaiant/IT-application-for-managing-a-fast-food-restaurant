package org.example.Vizualizare.Carduri.Vest;

import lombok.*;

import java.util.EventObject;

@Getter @Setter
public class Utilizator extends EventObject {
    private String numeUtilizator;
    private String parolaUtilizator;

    //  Constructor
    Utilizator(Object obj) {
        super(obj);
    }

    Utilizator(Object obj, String numeUtlizator, String parolaUtilizator)  {
        super(obj);
        this.numeUtilizator = numeUtlizator;
        this.parolaUtilizator = parolaUtilizator;
    }
}
