package org.example.Model;

import lombok.*;

@Getter @Setter
public class Raspuns {
    //  Atribute
    private String cod;
    private String continut;

    //  Constructor
    public Raspuns(String cod, String continut) {
        this.cod = cod;
        this.continut = continut;
    }
}
