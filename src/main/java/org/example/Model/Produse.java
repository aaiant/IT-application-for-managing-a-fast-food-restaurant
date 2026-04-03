package org.example.Model;

import java.util.ArrayList;
import java.util.EventObject;
import lombok.*;
import java.util.*;

@Getter @Setter
public class Produse extends EventObject {
    //  Atribute
    private ArrayList<Produs> produse;

    public Produse(Object source) {
        super(source);
    }

    public Produse(Object sursa, ArrayList<Produs> produse) {
        super(sursa);
        this.produse = produse;
    }
}
