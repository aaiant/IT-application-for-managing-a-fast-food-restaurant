package org.example.Model;

import lombok.*;

import java.util.EventObject;

@Getter @Setter
public class Nuanta{
    private String denumireNuanta;
    private String cod_hex;
    private String denumireCuloare;


    Nuanta(String denumireNuantaArg, String cod_hexArg, String denumireCuloareArg)  {
        this.denumireNuanta = denumireNuantaArg;
        this.cod_hex = cod_hexArg;
        this.denumireCuloare = denumireCuloareArg;
    }
}
