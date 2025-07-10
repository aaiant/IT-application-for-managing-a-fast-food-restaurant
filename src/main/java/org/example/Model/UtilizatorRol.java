package org.example.Model;

import lombok.*;

@Getter @Setter
public class UtilizatorRol {
    private String nume, rol, rolDescriere;
    private int id;

    public UtilizatorRol(String nume, int id, String rol, String rolDescriere) {
        this.nume = nume;
        this.id = id;
        this.rol = rol;
        this.rolDescriere = rolDescriere;
    }
}
