package org.example.Controlor;

import org.example.Interfete.I_Carduri;
import org.example.Model.*;
import org.example.Interfete.I_BackEnd;
import org.example.Vizualizare.BaraMeniu.BaraMeniu;
import org.example.Interfete.I_CardTematica_CheckBoxListener;
import org.example.Vizualizare.Carduri.Centru.Cos.Card_CosCumparaturi;
import org.example.Vizualizare.Carduri.Centru.Ghid.Card_Ghid_Inregistrare0;
import org.example.Vizualizare.Carduri.Centru.Meniuri.Card_Lista_Produse;
import org.example.Vizualizare.Carduri.Centru.Meniuri.Card_Produse;
import org.example.Vizualizare.Carduri.Centru.Mesaje.Card_Explicatii;
import org.example.Vizualizare.Carduri.Centru.Mesaje.Card_MesajConexiune;
import org.example.Vizualizare.Carduri.Centru.Mesaje.Card_MesajIntroducere;
import org.example.Vizualizare.Carduri.Centru.Mesaje.Card_MesajPlati;
import org.example.Vizualizare.Carduri.Centru.Portofel.Card_Bancar;
import org.example.Vizualizare.Carduri.Centru.Portofel.Card_Portofel;
import org.example.Vizualizare.Carduri.Centru.Profil.Campuri_JScroll;
import org.example.Vizualizare.Carduri.Centru.Profil.Card_Profil;
import org.example.Vizualizare.Carduri.Centru.Profil.JScroll_Profil;
import org.example.Vizualizare.Carduri.Centru.Roluri.CardRoluri;
import org.example.Vizualizare.Carduri.Est.*;
import org.example.Interfete.I_Culori;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import lombok.*;
import org.example.Interfete.I_Tematica;
import org.example.Vizualizare.Carduri.Intrebari_Raspunsuri.Card_Intrebari;
import org.example.Vizualizare.Carduri.Intrebari_Raspunsuri.Card_Raspunsuri;
import org.example.Vizualizare.Carduri.Intrebari_Raspunsuri.CodIntrebare;
import org.example.Vizualizare.Carduri.Vest.Card_Resetare_Parola;
import org.example.Vizualizare.Carduri.Vest.Inregistrare.Card_Inregistrare;
import org.example.Vizualizare.Carduri.Vest.Card_Logare;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;
import org.example.Vizualizare.Carduri.Vest.Inregistrare.Utilizator_Inregistrare;
import org.example.Vizualizare.Carduri.Vest.Utilizator;

@Getter @Setter
public class Controlor_Carduri implements I_Carduri, ActionListener {
    private Card_Logare cardLogare;
    private Card_Inregistrare cardInregistrare;
    private Card_Resetare_Parola cardResetareParola;
    private Card_Tematica cardTematica;
    private Card_Intrebari cardIntrebari;
    private Card_Portofel cardPortofel;
    private Card_Bancar cardBancar;
    private Card_Explicatii cardExplicatii;
    private Card_MesajPlati cardPrincipal;
    private Card_MesajIntroducere cardMesajIntroducere;
    private Card_CosCumparaturi cardCosCumparaturi;
    private Card_Profil cardProfil; private JScroll_Profil scrollProfil; private Campuri_JScroll campuriJscroll;
    private Card_MesajConexiune cardMesajConDa, cardMesajConNu, cardMesajRegDa, cardMesajRegNu;
    private Card_Raspunsuri cardRaspunsuri;
    private Card_Ghid_Inregistrare0 cardGhidInregistrare0, cardGhidModificareProfil;
    private Card_Produse cardProduse;
    private Card_Lista_Produse cardListaProduse;
    private Card_Admin_Tematica cardAdminTematica;
    private Card_Setari_Tematica cardSetariTematica;
    private Card_Admin_Intrebari cardAdminIntrebari;
    private Card_Setari_Intrebare cardSetariIntrebare;
    private Card_Admin_Raspunsuri cardAdminRaspunsuri;
    private Card_Setari_Raspuns cardSetariRaspuns;
    private Card_Detalii_Produs cardDetaliiProdus;
    private Card_Setari_CardBancar cardSetariCard;
    private Card_Admin_CardBancar cardAdminCardBancar;
    private Card_Setari_Produse cardSetariProduse;
    private Card_Admin_Produse cardAdminProduse;
    private Card_Setari_UtilizatorRol cardSetariUtilizatorRol;
    private Card_Admin_UtilizatorRol cardAdminUtilizatorRol;
    private Card_Admin_CosCumparaturi cardAdminCosCumparaturi;
    private Card_Setari_CosCumparaturi cardSetariCosCumparaturi;
    private Card_PlasareComanda cardPlasareComanda;
    private Card_Setari_PlasareComanda cardSetariPlasareComanda;
    private CardRoluri cardRoluri;
    private BaraMeniu baraMeniu;
    private CardLayout cardLayoutVest, cardLayoutEst, cardLayoutCentru;
    private JPanel containerVest, containerEst, containerCentru;
    private I_CardTematica_CheckBoxListener intefata;
    private String textBtnInapoi, textBtnInainte, urlBtnInapoiIcon, urlBtnInainteIcon;
    private Map<String, I_Tematica> interfeteColectie;
    private PostgreSQL bazaDate;
    private I_Culori interfataCulori;
    private I_FrontEnd interfataFrontEnd;
    private I_BackEnd interfataBackEnd;
    private I_BtnActiuni interfataBtnActiuni;
    private String meniu, categorie, subcategorie, um, urlTitluIcon, titlu, culoareText, urlTitluCardPortofel, titluCardPortofel;
    private int offset;
    private boolean stareContainerEst;

    public Controlor_Carduri(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg,
                             String textBtnInapoi, String textBtnInainte, String urlBtnInapoiIcon, String urlBtnInainteIcon) {
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg, textBtnInapoi, textBtnInainte, urlBtnInapoiIcon, urlBtnInainteIcon);
    }

    private void mesajStare(boolean stare, String mesaj) {
        if (stare) {
            JOptionPane.showMessageDialog(null, mesaj, "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, mesaj, "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Metode
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg, String textBtnInapoi, String textBtnInainte, String urlBtnInapoiIcon, String urlBtnInainteIcon) {

        interfeteColectie = new HashMap<>();

        setCardLayoutVest(new CardLayout());        /**/    setContainerVest(new JPanel(getCardLayoutVest()));          /**/    setTextBtnInapoi(textBtnInapoi);            /**/        setUrlBtnInainteIcon(urlBtnInainteIcon);
        setCardLayoutEst(new CardLayout());        /**/     setContainerEst(new JPanel(getCardLayoutEst()));           /**/     setTextBtnInainte(textBtnInainte);         /**/
        setCardLayoutCentru(new CardLayout());    /**/      setContainerCentru(new JPanel(getCardLayoutCentru()));    /**/      setUrlBtnInapoiIcon(urlBtnInapoiIcon);    /**/
        getContainerVest().setPreferredSize(new Dimension(350, 300));

        interfataCulori = clsCulori -> {
            for (Map.Entry<String, I_Tematica> element : interfeteColectie.entrySet()) {
                I_Tematica component = element.getValue();
                component.schimbaTematica(clsCulori.getCuloareFundal());
                component.schimbaCuloareText(clsCulori.getCuloareText());
            }
        };

        interfataBtnActiuni = new I_BtnActiuni() {
            @Override
            public void meniuInregistrare(boolean stare) {
                if(stare) {
                    getContainerVest().setPreferredSize(new Dimension(750, 300));
                    schimbareCard(getCardInregistrare().getName());
                    getIntefata().stadiuMesajIntro(false);
                    getIntefata().semnalJCheckBoxMeniuIntro(false);
                }
            }

            @Override
            public void meniuConectare(boolean stare) {
                if(stare) {
                    getContainerVest().setPreferredSize(new Dimension(350, 300));
                    schimbareCard(getCardLogare().getName());
                    getIntefata().stadiuMesajIntro(true);
                    getIntefata().semnalJCheckBoxMeniuIntro(true);
                    getIntefata().stadiuGhidInregistrare(false);
                }
            }

            @Override
            public void btnDeconectare(boolean stare) {
                if(stare) {
                    schimbareCard(getCardMesajConDa().getName());
                    schimbareCard(getCardMesajIntroducere().getName());
                    getBaraMeniu().meniuriBlocate(false);
                    getContainerVest().setVisible(true);
                }
            }

            @Override
            public void cardTematica(boolean stare) {
                dezactivareSemnale();
                getContainerEst().setVisible(stare);
                getIntefata().semnalJCheckBox(stare);
                schimbareCard(cardTematica.getName());
                setStareContainerEst(stare);
            }

            @Override
            public void activeazaCardAdminTematica(boolean stare, boolean dezactivare) {
                if(stare) {
                    schimbareCard(getCardAdminTematica().getName());
                    setStareContainerEst(true);
                } else if(!stare && !dezactivare) {
                    schimbareCard(getCardTematica().getName());
                } else if(!stare && dezactivare){
                    getContainerEst().setVisible(false);
                    getIntefata().semnalJCheckBox(false);
                    setStareContainerEst(false);
                }
            }

            @Override
            public void meniuIntroducere(boolean stare) {
                if(stare)
                    schimbareCard(getCardMesajIntroducere().getName());
            }

            @Override
            public void meniuPrincipal(boolean stare) {
                if(stare)
                    schimbareCard(getCardPrincipal().getName());
            }

            @Override
            public void meniuIntrebari(boolean stare) {
                dezactivareSemnale();
                if(stare) {
                    schimbareCard(getCardIntrebari().getName());
                    getContainerEst().setVisible(false);
                    setStareContainerEst(false);
                }
            }

            @Override
            public void activeazaCardPortofel(boolean stare) {
                if(stare) {
                    setareCardPortofel(cardPortofel, "/Imagini/Cont/Portofel", "Portofel");
                }
                interfataBtnActiuni.activeazaCardAdminCardBancar();
                getIntefata().semnalJCheckBoxMeniuIntro(false);
                getIntefata().semnalJCheckBox(false);
            }

            @Override
            public void activeazaCardCosCumparaturi(boolean stare) {
                if(stare) {
                    dezactivareContainerEst();
                    schimbareCard(getCardCosCumparaturi().getName());
                }
            }

            @Override
            public void meniuProfil(boolean stare) {
                if(stare) {
                    schimbareCard(getCardProfil().getName());
                }
                getIntefata().semnalJCheckBoxMeniuIntro(false);
                getIntefata().stadiuGestionareCard(false);
            }

            @Override
            public void meniuRaspunsuri(boolean stare) {
                schimbareCard(getCardRaspunsuri().getName());
            }

            @Override
            public void cardGhidInregistrare0(boolean stare) {
                if(stare) {
                    schimbareCard(getCardGhidInregistrare0().getName());
                    getIntefata().stadiuGhidInregistrare(true);
                }
            }

            @Override
            public void activeazaCardGhifModificareProfil() {
                dezactivareSemnale();
                schimbareCard(getCardGhidModificareProfil().getName());
                getContainerEst().setVisible(true);
                setStareContainerEst(true);
            }

            @Override
            public void activeazaCardSucuriCarbogazoase(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Sucuri", "Carbogazoase", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Sucuri/SucCarbogazos", "Sucuri Carbogazoase");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardSucuriNeCarbogazoase(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Sucuri", "Necarbogazoase", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Sucuri/SucNatural", "Sucuri Necarbogazoase");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardPizze(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Aluaturi", "Pizze", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/SpecialitatiPaineLipieBlat/Pizza", "Pizze");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardBurgeri(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Aluaturi", "Burgeri", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/SpecialitatiPaineLipieBlat/Burger2", "Burgeri");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardSandvisuri(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Aluaturi", "Sandvisuri", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/SpecialitatiPaineLipieBlat/Sandvici", "Șandvișuri");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardCartofiPrajiti(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Cartofi", "Prajiti", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/Cartofi/CartofiPai", "Cartofi Prăjiți");
            }

            @Override
            public void activeazaCardChipsuri(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Cartofi", "Chipsuri", "Procesate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/Cartofi/Chipsuri", "Chipsuri");
            }

            @Override
            public void activeazaCardNachos(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Cartofi", "Nachos", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/Cartofi/Nachos", "Nachos");
            }

            @Override
            public void activeazaCardShaorme(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Aluaturi", "Shaorme", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/SpecialitatiPaineLipieBlat/Shaorma", "Shaorme");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardCafeaCofeina(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Cafele", "Cafeinate", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Cafele/Cofeina", "Cafea cu cofeină");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardCafeaFaraCofeina(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Cafele", "Non-Cafeinate", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Cafele/NonCofeina", "Cafea fără cofeină");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardApaCarbogazoasa(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Ape", "Carbogazoase", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Ape/ApaMinerala", "Ape minerale");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardApaNecarbogazoasa(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Ape", "Necarbogazoase", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Ape/ApaPlata", "Ape plate");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardCeaiIndulcit(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Ceaiuri", "Indulcite", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Ceaiuri/CeaiIndulcit", "Ceaiuri îndulcite");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardCeaiNatural(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Bauturi", "Ceaiuri", "Naturale", "ml", 0, 0, false, true,
                        "/Imagini/Meniu/Bauturi/Ceaiuri/CeaiNatural", "Ceaiuri naturale");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardGogosi(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Gustari", "Gogosi", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/Gustari/Gogoasa", "Gogoși");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardFursecuri(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Gustari", "Fursecuri", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/Gustari/Fursec", "Fursecuri");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardInghetata(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Gustari", "Inghetate", "Preparate", "g", 0, 0, false, true,
                        "/Imagini/Meniu/Gustari/Inghetata", "Înghețate");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardSosuri(boolean stare) {
                schimbareDate(bazaDate, cardProduse, "Sosuri", "Clasice", "Preparate", "ml", 0, 0, false, true,
                        "/Imagini/Sosuri/Sosuri", "Sosuri");
                if(getIntefata() != null) {
                    getIntefata().stadiuGestionareCard(false);
                }
            }

            @Override
            public void activeazaCardAdminIntrebari(boolean stare) {
                if(stare) {
                    schimbareCard(getCardAdminIntrebari().getName());
                }
                stadiuContainerEst(stare);
            }

            @Override
            public void activeazaCardAdminUtilizatorRol(boolean stare) {
                if(stare) {
                    schimbareCard(getCardAdminUtilizatorRol().getName());
                }
                stadiuContainerEst(stare);
            }

            @Override
            public void activeazaCardResetareParola(boolean stare) {
                if(stare) {
                    if(cardResetareParola != null) {
                        getContainerVest().setPreferredSize(new Dimension(350, 300));
                        schimbareCard(getCardResetareParola().getName());
                    }
                }
            }

            @Override
            public void activeazaCardDetaliiProdus(boolean stare) {
                dezactivareSemnale();
                if (cardDetaliiProdus != null) {
                    schimbareCard(getCardDetaliiProdus().getName());
                }
                stadiuContainerEst(stare);
                getIntefata().semnalJCheckBoxCardDetaliiProus(stare);
            }


            @Override
            public void activeazaCardAdminCardBancar() {
                dezactivareSemnale();
                if(cardSetariCard != null && cardPortofel != null) {
                    schimbareCard(getCardAdminCardBancar().getName());
                    interfataFrontEnd.comboBoxSetariCardPart1();
                    setStareContainerEst(true);
                    getContainerEst().setVisible(true);
                }
            }

            @Override
            public void activeazaCardAdminRaspunsuri(boolean stare) {
                if(stare) {
                    schimbareCard(getCardAdminRaspunsuri().getName());
                } else {
                    schimbareCard(getCardTematica().getName());
                }
                getIntefata().semnalJCheckBoxAdaugareCard(stare);
                setStareContainerEst(stare);
                getIntefata().semnalJCheckBox(stare);
                getContainerEst().setVisible(stare);
            }

            @Override
            public void activeazaCardAdminProduse(boolean stare) {
                if(cardAdminProduse != null) {
                    schimbareCard(getCardAdminProduse().getName());
                    interfataFrontEnd.initializareUnitatiMasura();
                    interfataFrontEnd.initializareProducatori();
                    interfataFrontEnd.initializareCategorii();
                    interfataFrontEnd.initializareSubcategorii();
                    interfataFrontEnd.initializareIngrediente();
                }

            }

            @Override
            public void activeazaCardRoluri(boolean stare) {
                dezactivareSemnale();
                if(stare && cardRoluri != null) {
                    schimbareCard(getCardRoluri().getName());
                    getContainerEst().setVisible(false);
                    setStareContainerEst(false);
                }
            }

            @Override
            public void activeazaCardAdminCosCumparaturi(boolean stare) {
                dezactivareSemnale();
                if(stare && cardRoluri != null) {
                    schimbareCard(getCardAdminCosCumparaturi().getName());
                }
                getContainerEst().setVisible(stare);
                setStareContainerEst(stare);
            }

            @Override
            public void activeazaCardPlasareComanda(boolean stare) {
                dezactivareSemnale();
                if(stare && cardPortofel != null) {
                    schimbareCard(getCardPlasareComanda().getName());
                }
                getContainerEst().setVisible(stare);
                setStareContainerEst(stare);
            }

            @Override
            public void resetareCardProduse(boolean stare) {
                if(getCardProduse() != null) {
                    resetareBordura_Asezare(getCardProduse(), getUrlTitluIcon(), getTitlu());
                }
            }

            @Override
            public void resetareCardPortofel(boolean stare) {
                if(getCardPortofel() != null && stare) {
                    resetareBordura_AsezareCardPortofel(getCardPortofel(), getUrlTitluCardPortofel(), getTitluCardPortofel());
                }
            }

            @Override
            public void actualizareDetaliiProdus(int idProdus, String denumire, String ingrediente, String pret) {
                if(cardDetaliiProdus != null) {
                    cardDetaliiProdus.golireCampuri();
                    cardDetaliiProdus.adaugareText(idProdus, denumire, ingrediente, pret);
                }
            }

            @Override
            public void deconectare() {
                if(cardSetariCard != null) {
                    //cardSetariCard.golireComboBoxuri();
                }

                dezactivareSemnale();
                schimbareCard(getCardMesajIntroducere().getName());
                schimbareCard(getCardTematica().getName());
                getIntefata().semnalJCheckBoxMeniuIntro(true);
                getIntefata().stadiuGestionareCard(false);
                getIntefata().stadiuDetaliiProdus(false);
                getContainerEst().setVisible(false);
                getIntefata().semnalJCheckBox(false);
                setStareContainerEst(false);
            }

            @Override
            public void golireCampuriComboBoxuri() {
                if(cardSetariPlasareComanda != null) {cardSetariPlasareComanda.golireCampuri();}
            }

            @Override
            public void dezactivareSemnale() {
                if(getIntefata() != null) {
                    getIntefata().semnalJCheckBox(false);
                    getIntefata().semnalJCheckBoxAdaugareCard(false);
                    getIntefata().semnalJCheckBoxMeniuIntro(false);
                    getIntefata().semnalJCheckBoxCardGhidModificare(false);
                    getIntefata().semnalJCheckBoxCardDetaliiProus(false);
                }
            }

            @Override
            public void dezactivareContainerEst() {
                if(cardTematica != null) {
                    schimbareCard(getCardTematica().getName());
                }
                setStareContainerEst(false);
                getContainerEst().setVisible(false);
                getIntefata().semnalJCheckBox(false);
            }

            @Override
            public void trecereContMeniu(boolean stare, int stadiu) {
                interfataBtnActiuni.cardTematica(stare);
                if(baraMeniu != null) {
                    baraMeniu.stareMeniuri(stadiu);
                }
                if(getIntefata() != null) {
                    getIntefata().semnalJCheckBoxCardDetaliiProus(false);
                }
            }

            @Override
            public void actualizareUtilizatorRol(UtilizatorRol utilizator) {
                if(bazaDate != null && cardSetariUtilizatorRol != null) {
                    int stadiu = bazaDate.actualizareRoluriUtilizator(utilizator);
                    if(stadiu > 0) {
                        bazaDate.initializareRoluriUtilizator();
                        cardSetariUtilizatorRol.golireCampuri();
                        mesajStare(true, "Ați reușit să modificați rolul utilizatorului.");
                    } else {
                        mesajStare(false, "Nu ați reușit să modificați rolul utilizatorului.");
                    }
                }
            }

            @Override
            public void actualizareDateProfil(boolean stare) {
                if(stare) {
                    if(campuriJscroll != null) {
                        campuriJscroll.actualizareDateProfil();
                    }
                }
            }

            @Override
            public void refreshCarduriBancare(int idUtilizator) {
                if(bazaDate != null) {
                    String numeUtilizator = bazaDate.gasireNume_IdUtilizator(idUtilizator);
                    bazaDate.dateUtilizator(numeUtilizator);
                }
                if(cardPortofel != null) {
                    cardPortofel.resetareVizualizareCarduri();
                }
            }

            @Override
            public void semnalCardMeniu(String meniu, String categorii, String subcategorii, String um, int offset) {
                bazaDate.produse(meniu, categorii, subcategorii, um, offset);
                bazaDate.nrProduse(meniu, categorii, subcategorii, um);
                bazaDate.trimitereDateProduse();
            }

            @Override
            public void semnalCardLogare(boolean stare) {
                getIntefata().semnalJChecBoxCardConectare(stare);
            }

            @Override
            public void semnalCardInregistrare(boolean stare) {
                getIntefata().semnalJCheckBoxCardInregistrare(stare);
            }

        };


        interfataFrontEnd = new I_FrontEnd() {
            @Override
            public void dateLogare(Utilizator clsUtilizator) {
                getBazaDate().dateConectare(clsUtilizator.getNumeUtilizator(), clsUtilizator.getParolaUtilizator());
            }

            @Override
            public void intrebareSelectata(CodIntrebare clsCodIntrebare) {
                getBazaDate().prelucrareDateRaspunsuri(clsCodIntrebare.getCodIntrebare());
            }

            @Override
            public void utilizatorSelectat(UtilizatorRol utilizator) {
                if(cardSetariUtilizatorRol != null) {
                    cardSetariUtilizatorRol.actualizareIdUtilizator(utilizator.getId());
                    cardSetariUtilizatorRol.actualizareRoluriCombo(utilizator.getRol());
                    cardSetariUtilizatorRol.actualizareDescriere(utilizator.getRolDescriere());
                }
            }

            @Override
            public void produsCosSelectat(ProdusCos produs) {
                if(cardSetariCosCumparaturi != null) {
                    cardSetariCosCumparaturi.actualizareIdProdusStocat(produs.getId());
                    cardSetariCosCumparaturi.actualizareCantitateProdus(produs.getCantitate());
                }
            }

            @Override
            public void cerereTari() {
                if(bazaDate != null)
                    bazaDate.infoTari();
            }

            @Override
            public void taraSelectata(String tara) {
                if (bazaDate != null) {
                    bazaDate.infoJudete(tara);
                }}

            @Override
            public void judetSelectat(String judet) {
                if(bazaDate != null) {
                    bazaDate.infoLocalitati(judet);
                }
            }

            @Override
            public void dateUtilizatorInregistrare(Utilizator_Inregistrare utilizator) {
                if(bazaDate != null) {
                    getBazaDate().prelucrareDateInregistrare(utilizator);
                    getBazaDate().initializareRoluriUtilizator();
                }
            }

            @Override
            public void stergereNuanta(String codHex) {
                bazaDate.stergereNuanta(codHex);
            }

            @Override
            public void stergereIntrebare(String intrebare) {
                bazaDate.stergereIntrebare(intrebare);
            }

            @Override
            public void stareCampuri(boolean stare) {
                campuriJscroll.setStare(stare);
                campuriJscroll.stareCampuri(stare);
            }

            @Override
            public void trimitereCuloare(Culori culoare, int status) {
                if(bazaDate != null) {
                    if(status == 0)
                        bazaDate.adaugareNuanta(culoare);
                    else if(status == 1)
                        bazaDate.modificareNuanta(culoare);
                }
            }

            @Override
            public void trimitereIntrebare(Intrebare intrebare, int status) {
                if(bazaDate != null) {
                    if(status == 0)
                        bazaDate.adaugareIntrebare(intrebare);
                    else if(status == 1)
                        bazaDate.modificareIntrebare(intrebare);
                }
            }

            @Override
            public void trimitereDateRaspuns(Raspuns raspuns, String codIntrebare, String codIntrebareSelectata, int status) {
                if(bazaDate != null) {
                    if(status == 0)
                        bazaDate.adaugareRaspuns(raspuns, codIntrebare);
                    else if(status == 1)
                        bazaDate.modificareCompletaRaspuns(raspuns, codIntrebare, codIntrebareSelectata);
                    else if(status == 2)
                        bazaDate.stergereRaspuns(raspuns.getCod(), codIntrebare);
                }
            }

            @Override
            public void actualizareNume(int idUtilizator, String nume) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdNume(idUtilizator, nume);
                    String numeGasit = "";
                    if(id != 0) {
                        numeGasit = bazaDate.gasireNume(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareNume(numeGasit);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Numele de utilizator este deja folosit.", "Atenție", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            @Override
            public void actualizareAdresaEmail(int idUtilizator, String adresa) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdAdresaEmail(idUtilizator, adresa);
                    String adresaGasita = "";
                    if(id != 0) {
                        adresaGasita = bazaDate.gasireAdresaEmail(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareAdresaEmail(adresaGasita);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Adresa de E-mail este deja folosită.", "Atenție", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            @Override
            public void actualizareNrTel(int idUtilizator, String nrTel) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdNrTel(idUtilizator, nrTel);
                    String nrTelGasit = "";
                    if(id != 0) {
                        nrTelGasit = bazaDate.gasireNrTel(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareNrTel(nrTelGasit);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Numărul de telefon este deja folosit.", "Atenție", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            @Override
            public void actualizareParola(int idUtilizator, String parola) {
                if(bazaDate != null) {
                    String parolaHash = bazaDate.prelucrareParolaUtilizator(idUtilizator, parola);
                    if(campuriJscroll != null) {
                        campuriJscroll.actualizareParola(parolaHash);
                    } else {
                        JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void actualizareNumeFamilie_Prenume(int idUtilizator, String numeFamilie, String prenume) {
                boolean combinatie = true;
                if(bazaDate != null) {
                    combinatie = bazaDate.verificareNumeFamilie_Prenume(numeFamilie, prenume);
                    if(combinatie) {
                        int idNumeFamilie = bazaDate.preluareIdNumeFamilie(idUtilizator, numeFamilie),
                                idPrenume = bazaDate.preluareIdPrenume(idUtilizator, prenume);
                        String nume_familie = bazaDate.gasireNumeFamilie(idNumeFamilie),
                                prenume_utilizator = bazaDate.gasirePrenume(idPrenume);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareNumeFamilie(nume_familie);
                            campuriJscroll.actualizarePrenume(prenume_utilizator);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void actualizareNrLocuinta(int idUtilizator, int nrLocuinta) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdLocuinta(idUtilizator, nrLocuinta),
                            nr_locuinta = 0;
                    if(id != 0) {
                        nr_locuinta = bazaDate.gasireNrLocuinta(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareIdLocuinta(id);
                            campuriJscroll.actualizareNrLocuinta(nr_locuinta);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void actualizareStrada(int idUtilizator, String strada) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdStrada(idUtilizator, strada);
                    String stradaGasita = "";
                    if(id != 0) {
                        stradaGasita = bazaDate.gasireStrada(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareStrada(stradaGasita);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void actualizareLocalitate(int idUtilizator, String localitate) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdLocalitate(idUtilizator, localitate);
                    String localitateGasita = "";
                    if(id != 0) {
                        localitateGasita = bazaDate.gasireLocalitate(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareLocalitate(localitateGasita);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void actualizareJudet(int idUtilizator, String judet) {
                if(bazaDate != null) {
                    int id = bazaDate.preluareIdJudet(judet);
                    String judetGasit = "";
                    if(id != 0) {
                        judetGasit = bazaDate.gasireJudet(id);
                        if(campuriJscroll != null) {
                            campuriJscroll.actualizareJudet(judetGasit);
                        } else {
                            JOptionPane.showMessageDialog(null, "Eroare internă (500)", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void actualizareCodMaxIntrebari() {
                if(cardSetariIntrebare != null && bazaDate != null) {
                    cardSetariIntrebare.actualizareCodMax(bazaDate.codMaximIntrebari());
                }
            }

            @Override
            public void actualizareCodMaxRaspunsuri() {
                if(cardSetariRaspuns != null && bazaDate != null) {
                    cardSetariRaspuns.actualizareCodMax(bazaDate.codMaximRaspunsuri());
                }
            }

            @Override
            public void adaugareProduseCos(int idUtilizator, int idProdus, int cantitate) {
                if(bazaDate != null) {
                    bazaDate.adaugareProduseCos(idUtilizator, idProdus, cantitate);
                    bazaDate.initializareCosCumparaturiTabel(idUtilizator);
                }
            }

            @Override
            public void comboBoxSetariCardPart1() {
                if(bazaDate != null && cardSetariCard != null) {
                    //cardSetariCard.golireBanci();
                    cardSetariCard.actualizareBanci(bazaDate.preluareBanci());
                    cardSetariCard.actualizareTipuriMonede(bazaDate.preluareTipMonede());
                }
            }

            @Override
            public void actualizareTipuriCarduriSetariCard(String banca) {
                if(bazaDate != null && cardSetariCard != null) {
                    ArrayList<String> tipCarduri = bazaDate.preluareTipCarduri(banca);
                    cardSetariCard.golireTipCarduriCombo();
                    cardSetariCard.actualizareTipuriCarduri(tipCarduri);
                }
            }

            @Override
            public void actualizareMonedeSetariCard(String tipMoneda) {
                if(bazaDate != null && cardSetariCard != null) {
                    ArrayList<String> monede = bazaDate.preluareMoneda(tipMoneda);
                    cardSetariCard.golireMonedaCombo();
                    cardSetariCard.actualizareMoneda(monede);
                }
            }

            @Override
            public void actualizareCarduriBancare(int idUtilizator, String numarCard, String numePosesor, String dataExpirare,
                                                  String banca, String tipCard, String moneda, int stadiu) {
                if(bazaDate != null && cardSetariCard != null) {
                    if (stadiu == 0) {
                        bazaDate.inserareCardNou(idUtilizator, numarCard, numePosesor, dataExpirare, banca, tipCard, moneda);
                    } else if (stadiu == 1) {
                        bazaDate.modificareCardBancar(idUtilizator, numarCard, numePosesor, dataExpirare, banca, tipCard, moneda);
                    }
                }
            }

            @Override
            public void actualizareIdUtilizatorSetariCard(int idUtilizator) {
                if(cardSetariCard != null) {
                    cardSetariCard.actualizareIdUtilizator(idUtilizator);
                }
            }

            @Override
            public void stergereCardBancar(int idUtilizator, String numarCard) {
                if(bazaDate != null) {
                    bazaDate.stergereCardBancar(idUtilizator, numarCard);
                }
            }

            @Override
            public void initializareUnitatiMasura() {
                if(bazaDate != null && cardSetariProduse != null) {
                    ArrayList<String> unitatiMasura = bazaDate.preluareUnitatiMasura();
                    if(unitatiMasura != null) {
                        cardSetariProduse.initializareUnitatiMasuraCamp(unitatiMasura);
                    }
                }
            }

            @Override
            public void initializareProducatori() {
                if(bazaDate != null && cardSetariProduse != null) {
                    ArrayList<String> producatori = bazaDate.preluareProducatori();
                    if(producatori != null) {
                        cardSetariProduse.initializareProducatori(producatori);
                    }
                }
            }

            @Override
            public void initializareCategorii() {
                if(bazaDate != null && cardSetariProduse != null) {
                    ArrayList<String> categorii = bazaDate.preluareCategorii();
                    if(categorii != null) {
                        cardSetariProduse.initializareCategorii(categorii);
                    }
                }
            }

            @Override
            public void initializareSubcategorii() {
                if(bazaDate != null && cardSetariProduse != null) {
                    ArrayList<String> subcategorii = bazaDate.preluareSubcategorii();
                    if(subcategorii != null) {
                        cardSetariProduse.initializareSubcategorii(subcategorii);
                    }
                }
            }

            @Override
            public void initializareIngrediente() {
                if(bazaDate != null && cardSetariProduse != null) {
                    ArrayList<String> ingrediente = bazaDate.preluareIngrediente();
                    if(ingrediente != null) {
                        cardSetariProduse.initializarePopupIngrediente(ingrediente);
                    }
                }
            }

            @Override
            public void trimitereProdusPreluat(ProdusPreluat produs, int stadiu) {
                if(bazaDate != null && cardSetariProduse != null && cardProduse != null) {
                    if(stadiu == 0) {
                        int stare = bazaDate.gestionareProdus(produs);
                        if (stare > 0) {
                            cardSetariProduse.golireCampuri();
                            cardProduse.getInapoiBtn().doClick();
                        }
                    } else if(stadiu == 1) {
                        int stare = bazaDate.modificareProdus(produs);
                        if (stare > 0) {
                            cardSetariProduse.golireCampuri();
                            cardProduse.getInapoiBtn().doClick();
                        } else if(stare == -1) {
                            mesajStare(false, String.format("Nu există un produs cu eticheta %s.", produs.getEticheta()));
                        }
                    }
                }

            }

            @Override
            public void stergereProdusPreluat(String eticheta) {
                if(bazaDate != null && cardSetariProduse != null && cardProduse != null) {
                    int stare = bazaDate.stergereProdus(eticheta);
                    switch (stare){
                        case -1:
                            mesajStare(false, "Produsul nu există.");
                            break;
                        case 0:
                            mesajStare(false, "Produsul nu a putut fi șters.");
                            break;
                        case 1:
                            mesajStare(true, "Ați șters produsul.");
                            cardSetariProduse.golireCampuri();
                            cardProduse.getInapoiBtn().doClick();
                            break;
                    }
                }
            }

            @Override
            public void initializareRoluri() {
                if(bazaDate != null && cardSetariUtilizatorRol != null) {
                    ArrayList<String> roluriGasite = bazaDate.preluareRoluri();
                    if(roluriGasite != null) {
                        cardSetariUtilizatorRol.initializareRoluriCombo(roluriGasite);
                    } else {
                        mesajStare(false, "Ne pare rău, dar momentan nu există roluri în baza de date.");
                    }
                }
            }

            @Override
            public void actualizareRolUtilizator(String rol) {
                if (rol != null) {
                    if(cardIntrebari != null) {
                        cardIntrebari.actualizareRolUtilizator(rol);
                    }

                    if(cardRaspunsuri != null) {
                        cardRaspunsuri.actualizareRolUtilizator(rol);
                    }

                    if(cardDetaliiProdus != null) {
                        cardDetaliiProdus.actualizareRolUtilizator(rol);
                    }

                    if(cardTematica != null) {
                        cardTematica.actualizareRolUtilizator(rol);
                    }

                    if(baraMeniu != null) {
                        baraMeniu.actualizareRolUtilizator(rol);
                    }
                }
            }

            @Override
            public void actualizareIdUtilizatorVal(int idUtilizator) {
                if(cardSetariUtilizatorRol != null && idUtilizator > 0) {
                    cardSetariUtilizatorRol.actualizareIdUtilizatorVal(idUtilizator);
                }
            }

            @Override
            public void actualizarePretTotal(double pret) {
                if(cardSetariCosCumparaturi != null) {
                    cardSetariCosCumparaturi.actualizarePretTotal(pret);
                }
                if(cardSetariPlasareComanda != null) {
                    cardSetariPlasareComanda.actualizarePretTotal(pret);
                }
            }

            @Override
            public void stergereProdusCos(int idUtilizator, int idProdusCos) {
                if(bazaDate != null) {
                    System.out.println(idProdusCos);
                    bazaDate.stergereProdusCosCumparaturi(idUtilizator, idProdusCos);

                }
            }

            @Override
            public void actualizareProdusCos(int idProdusCos, int cantitate, int idUtilizator) {
                if(bazaDate != null && cardSetariCosCumparaturi != null) {
                    int stadiu = bazaDate.actualizareCosCumparaturiTabel(idProdusCos, cantitate);
                    if(stadiu > 0) {
                        bazaDate.initializareCosCumparaturiTabel(idUtilizator);
                        cardSetariCosCumparaturi.golireCampuri();
                        mesajStare(true, "Ați reușit să modificați produsul din coșul de cumpărături.");
                    }
                }
            }

            @Override
            public void actualizareCarduriBancareCombo(int idUtilizator) {
                ArrayList<String> listaCarduri;
                if(bazaDate != null && cardSetariPlasareComanda != null) {
                    listaCarduri = bazaDate.initializareCarduriBancare(idUtilizator);
                    cardSetariPlasareComanda.actualizareCarduriBancareCombo(listaCarduri);
                }
            }

            @Override
            public void trimitereComanda(int idUtilizator, int indexTipTransport, double pretTotal, String cardBancar,
                                         ArrayList<ProdusCos> listaProduse, String abreviereMoneda) {
                if(bazaDate != null) {
                    int rezultat = bazaDate.plasareComanda(idUtilizator, indexTipTransport, pretTotal, cardBancar, listaProduse, abreviereMoneda);
                    if(rezultat == 1) {
                        if(cardSetariPlasareComanda != null) {
                            cardSetariPlasareComanda.actualizarePretTotal(0.0);
                        }
                    }
                }
            }

            @Override
            public void trimitereProduseCos() {
                if(cardSetariPlasareComanda != null && cardCosCumparaturi != null) {
                    ArrayList<ProdusCos> listaProduse = cardCosCumparaturi.preluareProduseCosTabel();
                    if(!listaProduse.isEmpty()) {
                        cardSetariPlasareComanda.setListaProduseCos(listaProduse);
                    }
                }
            }

            @Override
            public void actualizareAbreviereMoneda(int idUtilizator, String nrCard) {
                if(bazaDate != null && cardSetariPlasareComanda != null) {
                    String abreviere = bazaDate.preluareAbreviereMonedaCard(idUtilizator, nrCard);
                    if(abreviere.length() >= 2) {
                        cardSetariPlasareComanda.actualizareAbreviereMoneda(abreviere);
                    }
                }
            }

            @Override
            public void actualizareDialog(int idUtilizator, String codIntrebare) {
                if(bazaDate != null) {
                    bazaDate.inserareDateDialog(idUtilizator, codIntrebare);
                }
            }

            @Override
            public void recuperareCont(String adresa) {
                if(bazaDate != null) {
                    bazaDate.recuperareUtilizator(adresa);
                }
            }
        };

        setCardTematica(new Card_Tematica(this, interfataCulori, interfataBtnActiuni));
        setCardSetariTematica(new Card_Setari_Tematica(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariIntrebare(new Card_Setari_Intrebare(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariRaspuns(new Card_Setari_Raspuns(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariCard(new Card_Setari_CardBancar(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariProduse(new Card_Setari_Produse(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariUtilizatorRol(new Card_Setari_UtilizatorRol(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariCosCumparaturi(new Card_Setari_CosCumparaturi(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardSetariPlasareComanda(new Card_Setari_PlasareComanda(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));

        setCardAdminTematica(new Card_Admin_Tematica(this, cardSetariTematica, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardAdminIntrebari(new Card_Admin_Intrebari(this, cardSetariIntrebare, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardAdminRaspunsuri(new Card_Admin_Raspunsuri(this, cardSetariRaspuns, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardAdminCardBancar(new Card_Admin_CardBancar(this, cardSetariCard, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardAdminProduse(new Card_Admin_Produse(this, cardSetariProduse, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardAdminUtilizatorRol(new Card_Admin_UtilizatorRol(this, cardSetariUtilizatorRol, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardAdminCosCumparaturi(new Card_Admin_CosCumparaturi(this, cardSetariCosCumparaturi, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardPlasareComanda(new Card_PlasareComanda(this, cardSetariPlasareComanda, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));

        setCardCosCumparaturi(new Card_CosCumparaturi(this, interfataBtnActiuni, interfataBackEnd, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardRoluri(new CardRoluri(this, interfataBtnActiuni, interfataBackEnd, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardDetaliiProdus(new Card_Detalii_Produs(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardBancar(new Card_Bancar(this, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardPortofel(new Card_Portofel(this, interfataBtnActiuni, interfataFrontEnd, cardBancar, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg, "Portofel", "Portofel"));
        setCardIntrebari(new Card_Intrebari(this, interfataBtnActiuni, interfataBackEnd, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardRaspunsuri(new Card_Raspunsuri(this, interfataBtnActiuni, interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg));
        setCardInregistrare(new Card_Inregistrare(this, interfataFrontEnd, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardResetareParola(new Card_Resetare_Parola(this, interfataFrontEnd, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCampuriJscroll(new Campuri_JScroll(interfataFrontEnd, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setScrollProfil(new JScroll_Profil(getCampuriJscroll()));
        setCardProfil(new Card_Profil(this, interfataFrontEnd, interfataBtnActiuni ,getScrollProfil(), culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));

        interfataBackEnd = new I_BackEnd() {

            @Override
            public void infoNuante(NuanteCulori clsNuante) {
                if(cardTematica.getTabelTematici() != null)
                    cardTematica.actualizeazaTabel(clsNuante);
                else
                    cardTematica.formatareTabel(clsNuante);
            }

            @Override
            public void infoIntrebari(Intrebari clsIntrebari) {
                if(cardIntrebari.getTabelIntrebari() != null)
                    cardIntrebari.actualizeazaTabel(clsIntrebari);
                else
                    cardIntrebari.formatareTabel(clsIntrebari);
            }

            @Override
            public void infoUtilizatoriRol(UtilizatoriRol utilizatoriRol) {
                if(cardRoluri.getTabelUtilizatori() != null)
                    cardRoluri.actualizeazaTabel(utilizatoriRol);
                else
                    cardRoluri.formatareTabel(utilizatoriRol);
            }

            @Override
            public void infoProduseCos(ProduseCos produseCos) {
                if(cardCosCumparaturi.getTabelProduse() != null) {
                    cardCosCumparaturi.actualizeazaTabel(produseCos);
                } else {
                    cardCosCumparaturi.formatareTabel(produseCos);
                }
            }

            @Override
            public void stareLogare(boolean stare, String numeUtilizator) {
                if(stare) {
                    schimbareCard(getCardMesajConDa().getName());
                    getCardLogare().golireCampuri();
                    getBaraMeniu().meniuriBlocate(true);
                    getContainerVest().setVisible(false);
                } else {
                    schimbareCard(getCardMesajConNu().getName());
                }
            }

            @Override
            public void infoRaspunsuri(Raspunsuri clsRaspunsuri, String codIntrebare) {
                interfataBtnActiuni.meniuRaspunsuri(true);
                getCardRaspunsuri().setareText("");
                getCardRaspunsuri().getCodIntrebareCamp().setText(codIntrebare);
                if(cardSetariRaspuns != null)
                    cardSetariRaspuns.setCodIntrebareSelectata(codIntrebare);
                int index = 0;
                for(Raspuns raspuns : clsRaspunsuri.getRaspunsuri()) {
                    getCardRaspunsuri().adaugareText(String.format("Răspuns %d (COD: %s):\n%s\n\n", index, raspuns.getCod(), raspuns.getContinut()));
                    index++;
                }
            }

            @Override
            public void stareRaspunsuri(boolean stare) {
                if (!stare)
                    JOptionPane.showMessageDialog(null, "Întrebarea nu are răspunsuri asociate.", "Atenție", JOptionPane.WARNING_MESSAGE);
            }

            @Override
            public void infoTari(ArrayList<String> setTari) {
                if(setTari != null) {
                    cardInregistrare.formatareTariCombo(setTari);
                    if(campuriJscroll.getStare())
                        campuriJscroll.formatareTariCombo(setTari);
                }
            }

            @Override
            public void infoJudete(ArrayList<String> setJudete) {
                if(setJudete != null) {
                    cardInregistrare.formatareJudeteCombo(setJudete);
                    if(campuriJscroll.getStare())
                        campuriJscroll.formatareJudeteCombo(setJudete);
                }
            }

            @Override
            public void infoLocalitati(ArrayList<String> setLocalitati) {
                if(setLocalitati != null) {
                    cardInregistrare.formatareLocalitatiCombo(setLocalitati);
                    if(campuriJscroll.getStare())
                        campuriJscroll.formatareLocalitatiCombo(setLocalitati);
                }
            }

            @Override
            public void infoIntegistrare(boolean stare, String mesaj) {
                if(stare) {
                    schimbareCard(getCardMesajConDa().getName());
                    if(cardInregistrare != null) {
                        cardInregistrare.golireCampuri();
                    }
                } else {
                    schimbareCard(getCardMesajRegNu().getName());
                    JOptionPane.showMessageDialog(null, mesaj, "Atenție", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void trimitereDateUtilizator(UtilizatorComplet utilizator) {
                interfataBtnActiuni.dezactivareSemnale();
                if(getIntefata() != null) {
                    getIntefata().semnalJCheckBoxMeniuIntro(true);
                }
                baraMeniu.stareMeniuri(10);
                campuriJscroll.primireDateUtilizator(utilizator);
                if(utilizator.getCarduriBancare() != null) {
                    cardPortofel.primireDateCarduri(utilizator.getCarduriBancare());
                }

                if(cardDetaliiProdus != null) {
                    cardDetaliiProdus.actualizareIdUtilizator(utilizator.getIdUtilizator());
                }

                if(cardSetariCard != null) {
                    cardSetariCard.actualizareIdUtilizator(utilizator.getIdUtilizator());
                }
                if(cardSetariUtilizatorRol != null) {
                    interfataFrontEnd.actualizareRolUtilizator(utilizator.getRol());
                    interfataFrontEnd.actualizareIdUtilizatorVal(utilizator.getIdUtilizator());
                }

                if(cardCosCumparaturi != null) {
                    bazaDate.initializareCosCumparaturiTabel(utilizator.getIdUtilizator());
                    cardCosCumparaturi.setIdUtilizator(utilizator.getIdUtilizator());
                }

                if(cardSetariCosCumparaturi != null) {
                    cardSetariCosCumparaturi.actualizareIdUtilizatorVal(utilizator.getIdUtilizator());
                }

                if(cardSetariPlasareComanda != null) {
                    cardSetariPlasareComanda.actualizareIdUtilizatorVal(utilizator.getIdUtilizator());
                }

                if(cardIntrebari != null) {
                        cardIntrebari.setIdUtilizator(utilizator.getIdUtilizator());
                }
            }

            @Override
            public void infoProduse(Produse produse, int nrProduse) {
                cardListaProduse.primireProduse(produse);
                cardProduse.setNrMaxPagini(nrProduse);
            }

            @Override
            public void infoRasp(String mesaj, boolean stare, int status) {
                if(stare) {
                    JOptionPane.showMessageDialog(null, mesaj, "Succes", JOptionPane.INFORMATION_MESSAGE);
                    if(status == 0)
                        getCardSetariIntrebare().golireCampuri();
                    else if(status == 1)
                        getCardSetariRaspuns().golireCampuri();
                    else if(status == 2)
                        getCardResetareParola().golireCampuri();
                    else if(status == 3) {
                        if(cardSetariCard != null) {
                            cardSetariCard.golireCampuri();
                        }
                    }
                } else {
                    infoStadiu("Numele de utilizator este deja folosit.");
                }
            }

            @Override
            public void infoStadiu(String mesaj) {
                JOptionPane.showMessageDialog(null, mesaj, "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        };
        setBazaDate(new PostgreSQL(interfataBackEnd));
        setCardListaProduse(new Card_Lista_Produse(this, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardProduse(new Card_Produse(this, interfataBtnActiuni, cardListaProduse, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg,
                getMeniu(), getCategorie(), getSubcategorie(), getUm(), getUrlTitluIcon(), getTitlu()));
        setCardGhidInregistrare0(new Card_Ghid_Inregistrare0(this, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg,
                "Ghid Înregistrare"));
        setCardGhidModificareProfil(new Card_Ghid_Inregistrare0(this, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg,
                "Ghid Actualizare"));
        setCardLogare(new Card_Logare(this, interfataFrontEnd, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardMesajIntroducere(new Card_MesajIntroducere(this, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg)); getCardMesajIntroducere().setName("Introducere");
        setCardMesajConDa(new Card_MesajConexiune(this, interfataBtnActiuni, "SuccesInfo", "Conectare",
                "Felicitări! Tocmai ce v-aţi conectat cu succes.", "/Imagini/Personal_IT.png",
                "<html>&emsp;Acum că v-ați conectat, puteți accesa absolut toate meniurile care vă sunt puse la dispoziție.</html>",
                culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardMesajConNu(new Card_MesajConexiune(this, interfataBtnActiuni,"EroareInfo", "Conectare",
                "Conectare eşuată", "/Imagini/EroareLogare.png",
                "<html>&emsp;Ne pare rău, dar conectarea a eşuat. " +
                        "Vă rugăm să verificaţi toate informaţiile introduse şi să încercaţi din nou.</html>",
                culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardMesajRegDa(new Card_MesajConexiune(this, interfataBtnActiuni,"SuccesInfo", "Înregistrare",
                "Felicitări! Tocmai ce v-aţi înregistrat cu succes.", "/Imagini/CardVest/Inregistrare.png",
                "<html> Vă rugăm să vă asiguraţi că toate informaţiile introduse în cadrul înregistrării sunt corecte. Pentru " +
                        "acest lucru, accesaţi secţiunea <b>Profilul Meu</b> din meniul intitulat <b>Cont</b>." +
                        "<br> Acum, aveţi posibilitatea de a accesa toate meniurile care vă sunt puse la dispoziţie. Nu " +
                        "uitaţi să accesaţi şi secţiunea care conţine cele mai delicioase gogosi şi îngheţate!" +
                        "<br> Mulţumim pentru alegerea făcută!</html>",
                culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardMesajRegNu(new Card_MesajConexiune(this, interfataBtnActiuni,"EroareInfo", "Înregistrare",
                "Înregistrare eşuată", "/Imagini/Personal_IT.png",
                "<html>&emsp;Ne pare rău, dar nu aţi reuşit să vă înregistraţi.</html>",
                culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));
        setCardPrincipal(new Card_MesajPlati(this, interfataBtnActiuni, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,  focusareDesenButonArg));

        setareNume(getCardProduse(), "Bauturi");
        setareNume(getCardGhidInregistrare0(), "GhidInregistrare0");
        setareNume(getCardGhidModificareProfil(), "GhidModificare");
        setareNume(getCardLogare(), "Logare");
        setareNume(getCardInregistrare(),"Inregistrare");
        setareNume(getCardResetareParola(), "ResetareParola");
        setareNume(getCardAdminTematica(), "AdminTematica");
        setareNume(getCardAdminRaspunsuri(), "AdminRaspunsuri");
        setareNume(getCardAdminIntrebari(), "AdminIntrebari");
        setareNume(getCardDetaliiProdus(), "DetaliiProdus");
        setareNume(getCardAdminProduse(), "AdminProduse");
        setareNume(getCardAdminCardBancar(), "AdminCard");
        setareNume(getCardAdminCosCumparaturi(), "AdminCosCumparaturi");
        setareNume(getCardPlasareComanda(), "PlasareComanda");
        setareNume(getCardTematica(),"Tematica");
        setareNume(getCardIntrebari(),"Intrebari");
        setareNume(getCardRoluri(), "Roluri");
        setareNume(getCardAdminUtilizatorRol(), "AdminRoluri");
        setareNume(getCardPortofel(),"Portofel");
        setareNume(getCardMesajConDa(),"Mesaj Con Da");
        setareNume(getCardMesajConNu(),"Mesaj Con Nu");
        setareNume(getCardMesajRegDa(),"Mesaj Reg Da");
        setareNume(getCardMesajRegNu(),"Mesaj Reg Nu");
        setareNume(getCardPrincipal(),"Principal");
        setareNume(getCardProfil(),"Profil");
        setareNume(getCardCosCumparaturi(),"CosCumparaturi");
        setareNume(getCardMesajIntroducere(),"Introducere");
        setareNume(getCampuriJscroll(),"CampuriJScroll");
        setareNume(getCardRaspunsuri(), "Raspunsuri");

        adaugareInContainerVest(getCardLogare());
        adaugareInContainerVest(getCardInregistrare());
        adaugareInContainerVest(getCardResetareParola());

        adaugareInContainerEst(getCardTematica());
        adaugareInContainerEst(getCardAdminTematica());
        adaugareInContainerEst(getCardAdminIntrebari());
        adaugareInContainerEst(getCardAdminRaspunsuri());
        adaugareInContainerEst(getCardGhidModificareProfil());
        adaugareInContainerEst(getCardDetaliiProdus());
        adaugareInContainerEst(getCardAdminCardBancar());
        adaugareInContainerEst(getCardAdminProduse());
        adaugareInContainerEst(getCardAdminUtilizatorRol());
        adaugareInContainerEst(getCardAdminCosCumparaturi());
        adaugareInContainerEst(getCardPlasareComanda());

        adaugareInContainerCentru(getCardMesajIntroducere());
        adaugareInContainerCentru(getCardPortofel());
        adaugareInContainerCentru(getCardProduse());
        adaugareInContainerCentru(getCardGhidInregistrare0());
        adaugareInContainerCentru(getCardMesajConNu());
        adaugareInContainerCentru(getCardPrincipal());
        adaugareInContainerCentru(getCardMesajRegDa());
        adaugareInContainerCentru(getCardMesajRegNu());
        adaugareInContainerCentru(getCardProfil());
        adaugareInContainerCentru(getCardIntrebari());
        adaugareInContainerCentru(getCardRoluri());
        adaugareInContainerCentru(getCardMesajConDa());
        adaugareInContainerCentru(getCardCosCumparaturi());
        adaugareInContainerCentru(getCardRaspunsuri());

        setBaraMeniu(new BaraMeniu(this, interfataBtnActiuni)); setIntefata(getBaraMeniu());
        try {
            adaugaInColectie(getCardProduse().getName(), getCardProduse());
            adaugaInColectie(getCardGhidInregistrare0().getName(), getCardGhidInregistrare0());
            adaugaInColectie(getCardGhidModificareProfil().getName(), getCardGhidModificareProfil());
            adaugaInColectie(getCardLogare().getName(), getCardLogare());
            adaugaInColectie(getCardInregistrare().getName(), getCardInregistrare());
            adaugaInColectie(getCardResetareParola().getName(), getCardResetareParola());
            adaugaInColectie(getCardTematica().getName(), getCardTematica());
            adaugaInColectie(getCardAdminTematica().getName(), getCardAdminTematica());
            adaugaInColectie(getCardAdminIntrebari().getName(), getCardAdminIntrebari());
            adaugaInColectie(getCardAdminRaspunsuri().getName(), getCardAdminRaspunsuri());
            adaugaInColectie(getCardDetaliiProdus().getName(), getCardDetaliiProdus());
            adaugaInColectie(getCardAdminCardBancar().getName(), getCardAdminCardBancar());
            adaugaInColectie(getCardAdminUtilizatorRol().getName(), getCardAdminUtilizatorRol());
            adaugaInColectie(getCardAdminProduse().getName(), getCardAdminProduse());
            adaugaInColectie(getCardAdminCosCumparaturi().getName(), getCardAdminCosCumparaturi());
            adaugaInColectie(getCardPlasareComanda().getName(), getCardPlasareComanda());
            adaugaInColectie(getCardIntrebari().getName(), getCardIntrebari());
            adaugaInColectie(getCardRoluri().getName(), getCardRoluri());
            adaugaInColectie(getCardPortofel().getName(), getCardPortofel());
            adaugaInColectie(getCardMesajConDa().getName(), getCardMesajConDa());
            adaugaInColectie(getCardMesajConNu().getName(), getCardMesajConNu());
            adaugaInColectie(getCardMesajRegDa().getName(), getCardMesajRegDa());
            adaugaInColectie(getCardMesajRegNu().getName(), getCardMesajRegNu());
            adaugaInColectie(getCardPrincipal().getName(), getCardPrincipal());
            adaugaInColectie(getCardProfil().getName(), getCardProfil());
            adaugaInColectie(getCardCosCumparaturi().getName(), getCardCosCumparaturi());
            adaugaInColectie(getCardMesajIntroducere().getName(), getCardMesajIntroducere());
            adaugaInColectie(getCampuriJscroll().getName(), getCampuriJscroll());
            adaugaInColectie(getBaraMeniu().getName(), getBaraMeniu());
            adaugaInColectie(getCardRaspunsuri().getName(), getCardRaspunsuri());
        } catch(IllegalStateException eroare) {eroare.printStackTrace();}

    }

    private void stadiuContainerEst(boolean stare) {
        getContainerEst().setVisible(stare);
        setStareContainerEst(stare);
    }

    private boolean verificareCard(JPanel container, String card) {
        for(Component componenta : container.getComponents())
            if(componenta.getName() != null && componenta.getName().equals(card))
                return true;
        return false;
    }

    private void adaugaInColectie(String key, I_Tematica value) {
        if (value != null) {
            interfeteColectie.put(key, value);
        } else {
            throw new IllegalStateException(key + " este null.");
        }
    }

    private void setareNume(JPanel card, String denumire) {card.setName(denumire);}

    private void adaugareInContainerCentru(JPanel card) {getContainerCentru().add(card, card.getName());}
    private void adaugareInContainerVest(JPanel card) {getContainerVest().add(card, card.getName());}
    private void adaugareInContainerEst(JPanel card) {getContainerEst().add(card, card.getName());}

    @Override
    public void schimbareCard(String numeCard) {
        if(verificareCard(getContainerVest(), numeCard)) {
            getCardLayoutVest().show(getContainerVest(), numeCard);
        } else if(verificareCard(getContainerEst(), numeCard)) {
            getCardLayoutEst().show(getContainerEst(), numeCard);
        } else if(verificareCard(getContainerCentru(), numeCard)) {
            getCardLayoutCentru().show(getContainerCentru(), numeCard);
        }
    }

    @Override
    public void actionListenerInterfata(int id) {

    }

    @Override
    public JMenuBar creareJMenuBar() {
        return getBaraMeniu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
    }

    public void schimbareDate(PostgreSQL bd, Card_Produse card, String meniu, String categorie, String subcategorie, String um, int offset, int index, boolean stadiuInapoiBtn, boolean stadiuInainteBtn,
                              String urlTitluIcon, String titlu) {
        schimbareCard(getCardProduse().getName());
        setMeniu(meniu); setCategorie(categorie); setSubcategorie(subcategorie); setOffset(offset);
        bd.produse(meniu, categorie, subcategorie, um, offset);
        bd.nrProduse(meniu, categorie, subcategorie, um);
        bd.trimitereDateProduse();
        card.setMeniu(meniu);
        card.setCategorie(categorie);
        card.setSubcategorie(subcategorie);
        card.setUm(um);
        card.setIndexLista(index);
        card.getInainteBtn().setEnabled(stadiuInainteBtn);
        card.getInapoiBtn().setEnabled(stadiuInapoiBtn);
        setUrlTitluIcon(urlTitluIcon);
        setTitlu(titlu);
        card.setUrlTitluIcon(urlTitluIcon);
        card.setTitlu(titlu);
        resetareBordura_Asezare(card, urlTitluIcon, titlu);
    }

    public void setareCardPortofel(Card_Portofel card, String urlTitluIcon, String titlu) {
        schimbareCard(getCardPortofel().getName());
        setUrlTitluCardPortofel(urlTitluIcon);
        setTitluCardPortofel(titlu);
        card.setUrlTitluIcon(urlTitluIcon);
        card.setTitlu(titlu);
        resetareBordura_AsezareCardPortofel(card, urlTitluIcon, titlu);
    }

    public void resetareBordura_Asezare(Card_Produse card, String urlTitluIcon, String titlu) {
        card.configurari(urlTitluIcon, titlu);
        card.asezareElemente();
    }

    public void resetareBordura_AsezareCardPortofel(Card_Portofel card, String urlTitluIcon, String titlu) {
        card.configurari(urlTitluIcon, titlu);
        card.asezareElemente();
    }

    public boolean getStareContainerEst() {
        return stareContainerEst;
    }
}
