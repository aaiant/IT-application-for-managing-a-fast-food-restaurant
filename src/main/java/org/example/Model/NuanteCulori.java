package org.example.Model;

import lombok.*;

import java.util.ArrayList;
import java.util.EventObject;

@Getter @Setter
public class NuanteCulori extends EventObject {
    private ArrayList<Nuanta> nuante;
    private ArrayList<String> coloane;

    NuanteCulori(Object obj) {
        super(obj);
    }

    NuanteCulori(Object obj, ArrayList<Nuanta> nuante, ArrayList<String> coloane)  {
        super(obj);
        this.nuante = nuante;
        this.coloane = coloane;
    }
}
