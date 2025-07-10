package org.example.Interfete;

public interface I_CardTematica_CheckBoxListener {
    void semnalJCheckBox(boolean activ);
    void semnalJCheckBoxAdaugareCard(boolean stare);
    void semnalJCheckBoxMeniuIntro(boolean stare);
    void semnalJCheckBoxCardGhidModificare(boolean stare);
    void semnalJCheckBoxCardDetaliiProus(boolean stare);
    void semnalJCheckBoxCardInregistrare(boolean stare);
    void semnalJChecBoxCardConectare(boolean stare);

    //  Stadiu JCheckboxMenuItem
    void stadiuGestionareCard(boolean stare);
    void stadiuDetaliiProdus(boolean stare);
    void stadiuGhidActualizare(boolean stare);
    void stadiuGhidInregistrare(boolean stare);
    void stadiuMesajIntro(boolean stare);
    void stadiuAspect(boolean stare);
}
