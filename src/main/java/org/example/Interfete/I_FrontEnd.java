package org.example.Interfete;

import org.example.Model.*;
import org.example.Vizualizare.Carduri.Est.Culori;
import org.example.Vizualizare.Carduri.Est.ProdusPreluat;
import org.example.Vizualizare.Carduri.Intrebari_Raspunsuri.CodIntrebare;
import org.example.Vizualizare.Carduri.Vest.Inregistrare.Utilizator_Inregistrare;
import org.example.Vizualizare.Carduri.Vest.Utilizator;

import java.util.ArrayList;

public interface I_FrontEnd {
    void dateLogare(Utilizator clsUtilizator);
    void intrebareSelectata(CodIntrebare clsCodIntrebare);
    void utilizatorSelectat(UtilizatorRol utilizator);
    void produsCosSelectat(ProdusCos produs);
    void cerereTari();
    void taraSelectata(String tara);
    void judetSelectat(String judet);
    void dateUtilizatorInregistrare(Utilizator_Inregistrare utilizator);
    void stergereNuanta(String codHex);
    void stergereIntrebare(String intrebare);
    void stareCampuri(boolean stare);
    void trimitereCuloare(Culori culoare, int status);
    void trimitereIntrebare(Intrebare intrebare, int status);
    void trimitereDateRaspuns(Raspuns raspuns, String codIntrebare, String codIntrebareSelectata, int status);

    //  Actualizari profil
    void actualizareNume(int idUtlizator, String nume);
    void actualizareAdresaEmail(int idUtilizator, String adresa);
    void actualizareNrTel(int idUtilizator, String nrTel);
    void actualizareParola(int idUtilizator, String parola);
    void actualizareNumeFamilie_Prenume(int idUtilizator, String numeFamilie, String prenume);
    void actualizareNrLocuinta(int idUtilizator, int nrLocuinta);
    void actualizareStrada(int idUtilizator, String strada);
    void actualizareLocalitate(int idUtilizator, String localitate);
    void actualizareJudet(int idUtilizator, String judet);
    void actualizareCodMaxIntrebari();
    void actualizareCodMaxRaspunsuri();

    //  Inserarea produselor in cos
    void adaugareProduseCos(int idUtilizator, int idProdus, int cantitate);

    //  Umplerea JComboBox-urilor din SetariCarduri
    void comboBoxSetariCardPart1();
    void actualizareTipuriCarduriSetariCard(String banca);
    void actualizareMonedeSetariCard(String tipMoneda);
    void actualizareCarduriBancare(int idUtilizator, String numarCard, String numePosesor, String dataExpirare, String banca,
                                   String tipCard, String moneda, int stadiu);
    void actualizareIdUtilizatorSetariCard(int idUtilizator);
    void stergereCardBancar(int idUtilizator, String numarCard);

    //  Admin Produse
    void initializareUnitatiMasura();
    void initializareProducatori();
    void initializareCategorii();
    void initializareSubcategorii();
    void initializareIngrediente();
    void trimitereProdusPreluat(ProdusPreluat produs, int stadiu);
    void stergereProdusPreluat(String eticheta);


    void initializareRoluri();

    //  ROL UTILIZATOR PT BUTOANELE DE SETARI / GESTIONARE
    void actualizareRolUtilizator(String rol);
    void actualizareIdUtilizatorVal(int idUtilizator);

    //  MODIFICARE COS
    void actualizarePretTotal(double pret);
    void stergereProdusCos(int idUtilizator, int idProdusCos);
    void actualizareProdusCos(int idProdusCos, int cantitate, int idUtilizator);

    //  PLASARE COMANDA
    void actualizareCarduriBancareCombo(int idUtilizator);
    void trimitereComanda(int idUtilizator, int indexTipTransport, double pretTotal, String cardBancar,
                          ArrayList<ProdusCos> listaProduse, String abreviereMoneda);
    void trimitereProduseCos();
    void actualizareAbreviereMoneda(int idUtilizator, String nrCard);

    //  DIALOG
    void actualizareDialog(int idUtilizator, String codIntrebare);

    //  Recuperarea contului
    void recuperareCont(String adresa);
}
