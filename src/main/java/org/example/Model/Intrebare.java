package org.example.Model;

import lombok.*;

@Getter @Setter
public class Intrebare {
    private String cod;
    private String continut;

    public Intrebare(String cod, String continut) {
        this.cod = cod;
        this.continut = continut;
    }
}
