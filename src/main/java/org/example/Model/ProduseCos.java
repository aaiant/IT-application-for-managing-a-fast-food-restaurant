package org.example.Model;

import java.util.ArrayList;
import lombok.*;

@Getter @Setter
public class ProduseCos {
    private ArrayList<ProdusCos> produseCos;
    private ArrayList<String> coloane;

    ProduseCos(ArrayList<ProdusCos> produseCos, ArrayList<String> coloane)  {
        this.produseCos = produseCos;
        this.coloane = coloane;
    }
}
