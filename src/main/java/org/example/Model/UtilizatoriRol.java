package org.example.Model;

import java.util.ArrayList;
import lombok.*;

@Getter @Setter
public class UtilizatoriRol {
    private ArrayList<UtilizatorRol> utilizatoriRol;
    private ArrayList<String> coloane;

    UtilizatoriRol(ArrayList<UtilizatorRol> utilizatoriRol, ArrayList<String> coloane)  {
        this.utilizatoriRol = utilizatoriRol;
        this.coloane = coloane;
    }
}
