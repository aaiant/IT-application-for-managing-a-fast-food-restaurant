package org.example.Model;

import lombok.*;

import java.util.ArrayList;
import java.util.EventObject;

@Getter @Setter
public class Intrebari extends EventObject {
    private ArrayList<Intrebare> intrebari;
    private ArrayList<String> coloane;

    Intrebari(Object obj) {
        super(obj);
    }

    Intrebari(Object obj, ArrayList<Intrebare> intrebari, ArrayList<String> coloane)  {
        super(obj);
        this.intrebari = intrebari;
        this.coloane = coloane;
    }
}
