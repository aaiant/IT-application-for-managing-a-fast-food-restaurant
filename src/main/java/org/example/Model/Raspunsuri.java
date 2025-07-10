package org.example.Model;

import lombok.*;
import java.util.*;

@Getter @Setter
public class Raspunsuri extends EventObject{
    //  Atribute
    private ArrayList<Raspuns> raspunsuri;

    public Raspunsuri(Object source) {
        super(source);
    }

    public Raspunsuri(Object sursa, ArrayList<Raspuns> raspunsuri) {
        super(sursa);
        this.raspunsuri = raspunsuri;
    }
}
