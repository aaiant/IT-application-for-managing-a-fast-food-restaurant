package org.example.Interfete;

import org.example.Model.*;

import java.util.ArrayList;
import java.util.EventListener;

public interface I_BackEnd extends EventListener {
     void infoNuante(NuanteCulori clsNuante);
     void infoIntrebari(Intrebari clsIntrebari);
     void infoUtilizatoriRol(UtilizatoriRol utilizatoriRol);
     void infoProduseCos(ProduseCos produseCos);
     void stareLogare(boolean stare, String numeUtilizator);
     void infoRaspunsuri(Raspunsuri clsRaspunsuri, String codIntrebare);
     void stareRaspunsuri(boolean stare);
     void infoTari(ArrayList<String> setTari);
     void infoJudete(ArrayList<String> setJudete);
     void infoLocalitati(ArrayList<String> setLocalitati);
     void infoIntegistrare(boolean stare, String mesaj);
     void trimitereDateUtilizator(UtilizatorComplet utilizator);
     void infoProduse(Produse produse, int nrProduse);
     void infoRasp(String mesaj, boolean stare, int status);
     void infoStadiu(String mesaj);
}
