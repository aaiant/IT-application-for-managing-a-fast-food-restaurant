package org.example.Interfete;

import org.example.Model.UtilizatorRol;

import javax.swing.*;
import java.util.EventListener;

public interface I_BtnActiuni extends EventListener {
    void meniuInregistrare(boolean stare);
    void meniuConectare(boolean stare);
    void btnDeconectare(boolean stare);
    void cardTematica(boolean stare);
    void activeazaCardAdminTematica(boolean stare, boolean dezactivare);
    void meniuIntroducere(boolean stare);
    void meniuPrincipal(boolean stare);
    void meniuIntrebari(boolean stare);
    void activeazaCardPortofel(boolean stare);
    void activeazaCardCosCumparaturi(boolean stare);
    void meniuProfil(boolean stare);
    void meniuRaspunsuri(boolean stare);
    void cardGhidInregistrare0(boolean stare);
    void activeazaCardGhifModificareProfil();
    void activeazaCardSucuriCarbogazoase(boolean stare);
    void activeazaCardSucuriNeCarbogazoase(boolean stare);
    void activeazaCardPizze(boolean stare);
    void activeazaCardBurgeri(boolean stare);
    void activeazaCardSandvisuri(boolean stare);
    void activeazaCardCartofiPrajiti(boolean stare);
    void activeazaCardChipsuri(boolean stare);
    void activeazaCardNachos(boolean stare);
    void activeazaCardShaorme(boolean stare);
    void activeazaCardCafeaCofeina(boolean stare);
    void activeazaCardCafeaFaraCofeina(boolean stare);
    void activeazaCardApaCarbogazoasa(boolean stare);
    void activeazaCardApaNecarbogazoasa(boolean stare);
    void activeazaCardCeaiIndulcit(boolean stare);
    void activeazaCardCeaiNatural(boolean stare);
    void activeazaCardGogosi(boolean stare);
    void activeazaCardFursecuri(boolean stare);
    void activeazaCardInghetata(boolean stare);
    void activeazaCardSosuri(boolean stare);
    void activeazaCardAdminIntrebari(boolean stare);
    void activeazaCardAdminUtilizatorRol(boolean stare);
    void activeazaCardResetareParola(boolean stare);
    void activeazaCardDetaliiProdus(boolean stare);
    void activeazaCardAdminCardBancar();
    void activeazaCardAdminRaspunsuri(boolean stare);
    void activeazaCardAdminProduse(boolean stare);
    void activeazaCardRoluri(boolean stare);
    void activeazaCardAdminCosCumparaturi(boolean stare);
    void activeazaCardPlasareComanda(boolean stare);

    void resetareCardProduse(boolean stare);
    void resetareCardPortofel(boolean stare);


    void actualizareDateProfil(boolean stare);

    //  Portofel
    void refreshCarduriBancare(int idUtilizator);

    //  Detalii produs
    void actualizareDetaliiProdus(int idProdus, String denumire, String ingrediente, String pret);

    //  Deconectare
    void deconectare();
    void golireCampuriComboBoxuri();

    //  Carduri EST
    void dezactivareSemnale();
    void dezactivareContainerEst();
    void trecereContMeniu(boolean stare, int stadiu);

    //  Card UtilizatorRoluri
    void actualizareUtilizatorRol(UtilizatorRol utilizator);

    void semnalCardMeniu(String meniu, String categorii, String subcategorii, String um, int offset);
    void semnalCardLogare(boolean stare);
    void semnalCardInregistrare(boolean stare);
    default void iesire() {
        int raspuns = JOptionPane.showConfirmDialog(null,
                "Doriti sa iesiti din aplicatie?", "Confirmare", JOptionPane.YES_NO_OPTION);
        if(raspuns == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
