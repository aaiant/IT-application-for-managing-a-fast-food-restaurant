package org.example.Model;

import lombok.*;

@Getter @Setter
public class CardBancar {
    String numeBanca, tipCard, tipMoneda, moneda, numarCard, nume_posesor, dataExpirareCard, link_poza;

    public CardBancar(String numeBanca, String tipCard, String tipMoneda, String moneda, String numarCard,
                      String nume_posesor, String dataExpirareCard, String link_poza) {
        this.numeBanca = numeBanca;
        this.tipCard = tipCard;
        this.tipMoneda = tipMoneda;
        this.moneda = moneda;
        this.numarCard = numarCard;
        this.nume_posesor = nume_posesor;
        this.dataExpirareCard = dataExpirareCard;
        this.link_poza = link_poza;
    }
}
