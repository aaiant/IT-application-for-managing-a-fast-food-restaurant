package org.example.Vizualizare.BaraMeniu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.example.Interfete.I_Carduri;
import org.example.Vizualizare.BaraMeniu.Bauturi.*;
import org.example.Vizualizare.BaraMeniu.Cartofi.MeniuCartofi;
import org.example.Vizualizare.BaraMeniu.Gustari.MeniuGustari;
import org.example.Interfete.I_CardTematica_CheckBoxListener;
import org.example.Vizualizare.BaraMeniu.Sosuri.MeniuSosuri;
import org.example.Vizualizare.BaraMeniu.SpecialitatiPaineLipieBlat.SpecialitatiPaineLipieBlat;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;

@Getter @Setter
public class BaraMeniu extends JMenuBar implements ActionListener, I_CardTematica_CheckBoxListener, I_Tematica {
    private JMenu cont, fereastra, afisare, meniuriProdCom, bauturi, ape, sucuri, gustari, cos;
    private JMenuItem profilulMeu, ajutor, iesire, sosuri, cartofi, burgeri_sandviciuri, inghetate,
            sucuriAcidulate, sucuriNaturale, cafele, ceaiuri, apePlate, gogosi, fursecuri, apeMinerale, comanda, portofel, deconectare, roluri;
    private JCheckBoxMenuItem meniuConectare, meniuInregistrare, aspect, pagIntroducere, gestionareCard, detaliiProdus, ghidActualizare,
    ghidInregistrare;
    private ArrayList<Object> componente;
    private I_Carduri interfata;
    private ImageIcon contIcon, profilulMeuIcon, fereastraIcon, ajutorIcon, iesireIcon, aspectIcon, afisareIcon,
    meniuriProdComIcon, bauturiIcon, apeIcon, sucuriIcon, cosCumparaturiIcon, cartofiIcon,
            gustariIcon, burgeri_sandviciuriIcon, inghetateIcon, sucuriAcidulateIcon, sucuriNaturaleIcon, cafeleIcon,
            ceaiuriIcon, apePlateIcon, apeMineraleIcon, gogosiIcon, fursecuriIcon, plasareComandaIcon, portofelIcon, deconectareIcon,
    detaliiProdusIcon, gestionareCardIcon, PagIntroducereIcon, meniuLogareIcon, meniuInregistrareIcon, ghidActualizareIcon, roluriIcon;
    private MeniuSosuri meniuSosuri;
    private I_BtnActiuni interfataBtnActiuni;
    private String rolUtilizator;

    public BaraMeniu(I_Carduri arg, I_BtnActiuni interfataBtnActiuni) {
        setInterfata(arg);
        this.interfataBtnActiuni = interfataBtnActiuni;
        initializari();
        creareMnemonice();
        creareAccelerari();
        meniuriBlocate(false);
    }

    //  Metode
    private void creareMnemonice() {
        getCont().setMnemonic(KeyEvent.VK_F); getIesire().setMnemonic(KeyEvent.VK_I); getFereastra().setMnemonic(KeyEvent.VK_W);
        getAfisare().setMnemonic(KeyEvent.VK_S); getMeniuConectare().setMnemonic(KeyEvent.VK_L);
        getMeniuInregistrare().setMnemonic(KeyEvent.VK_R); getAjutor().setMnemonic(KeyEvent.VK_A);
    }

    private void creareAccelerari() {
        getIesire().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        getMeniuConectare().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        getMeniuInregistrare().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
    }

    private void initializari() {
        setContIcon(creareImagine2("Cont", "Utilizator"));        setMeniuriProdComIcon(creareImagine("Meniu2"));                           setCosCumparaturiIcon(creareImagine("CosCumparaturi"));
        setProfilulMeuIcon(creareImagine2("Cont", "Profil"));     setPlasareComandaIcon(creareImagine2("MeniuriProgram", "PlasareComanda"));                   setGestionareCardIcon(creareImagine2("Cont", "Portofel"));
        setFereastraIcon(creareImagine("Fereastra"));                    setPortofelIcon(creareImagine2("Cont", "Portofel"));               setPagIntroducereIcon(creareImagine("Acasa"));
        setAjutorIcon(creareImagine2("Cont", "Ajutor" ));         setDeconectareIcon(creareImagine2("Cont", "Deconectare"));         setMeniuLogareIcon(creareImagine2("CardVest", "Logare"));
        setIesireIcon(creareImagine2( "Cont", "Iesire"));         setAspectIcon(creareImagine2("CardEst", "Tematica"));              setMeniuInregistrareIcon(creareImagine2("CardVest", "Inregistrare"));
        setAfisareIcon(creareImagine("Afisare"));                        setDetaliiProdusIcon(creareImagine2("Meniu", "DetaliiProdus"));    setGhidActualizareIcon(creareImagine2("Cont", "Ajutor" ));
        //  Cont
        setCont(new JMenu("Cont"));                                                        /**/ getCont().setIcon(getContIcon());                           /**/
        setProfilulMeu(new JMenuItem("Profilul Meu"));                                  /**/ getProfilulMeu().setIcon(getProfilulMeuIcon());             /**/ getCont().add(getProfilulMeu());                                 /**/ getProfilulMeu().addActionListener(this);
        setPortofel(new JMenuItem("Portofel"));                                        /**/ getCont().add(getPortofel());                               /**/ getPortofel().setIcon(getPortofelIcon());                        /**/ getPortofel().addActionListener(this);
        setAjutor(new JMenuItem("Ajutor"));                                           /**/ getCont().add(getAjutor());                                 /**/ getAjutor().setIcon(getAjutorIcon());                            /**/ getAjutor().addActionListener(this);
        setDeconectare(new JMenuItem("Deconectare"));                                /**/ getCont().add(getDeconectare());                            /**/  getDeconectare().setIcon(getDeconectareIcon());                                                                     /**/ getDeconectare().addActionListener(this);
        setIesire(new JMenuItem("Ieșire"));                                         /**/ getCont().add(getIesire());                                 /**/ getIesire().setIcon(getIesireIcon());                            /**/ getIesire().addActionListener(this);
        setRoluri(new JMenuItem("Roluri"));                                        /**/   getCont().add(getRoluri());                               /**/  getRoluri().setIcon(getContIcon());                           /**/  getRoluri().addActionListener(this);
        //  Fereastra
        setFereastra(new JMenu("Fereastră"));                                        /**/ getFereastra().setIcon(getFereastraIcon());
        setAfisare(new JMenu("Afișare"));                                           /**/ getFereastra().add(getAfisare());                          /**/ getAfisare().setIcon(getAfisareIcon());
        //  Afisare
        setMeniuConectare(new JCheckBoxMenuItem("Meniu de Conectare"));             /**/    getAfisare().add(getMeniuConectare());                      /**/ getMeniuConectare().addActionListener(this);               /**/    getMeniuConectare().setSelected(true);                        /**/  getMeniuConectare().setIcon(getMeniuLogareIcon());
        setMeniuInregistrare(new JCheckBoxMenuItem("Meniu de Înregistrare"));      /**/     getAfisare().add(getMeniuInregistrare());                  /**/  getMeniuInregistrare().addActionListener(this);           /**/                                                                  /**/   getMeniuInregistrare().setIcon(getMeniuInregistrareIcon());
        setAspect(new JCheckBoxMenuItem("Aspect"));                               /**/      getAfisare().add(getAspect());                            /**/   getAspect().addActionListener(this);                     /**/      getAspect().setSelected(false);                             /**/    getAspect().setIcon(getAspectIcon());
        setPagIntroducere(new JCheckBoxMenuItem("Pagină principală"));       /**/       getAfisare().add(getPagIntroducere());                   /**/    getPagIntroducere().addActionListener(this);            /**/       getPagIntroducere().setSelected(true);                     /**/     getPagIntroducere().setIcon(getPagIntroducereIcon());
        setGestionareCard(new JCheckBoxMenuItem("Gestionare Card Bancar"));     /**/        getAfisare().add(getGestionareCard());                  /**/     getGestionareCard().addActionListener(this);           /**/        getGestionareCard().setIcon(getGestionareCardIcon());
        setDetaliiProdus(new JCheckBoxMenuItem("Detalii Produs"));             /**/         getAfisare().add(getDetaliiProdus());                  /**/      getDetaliiProdus().addActionListener(this);           /**/         getDetaliiProdus().setIcon(getDetaliiProdusIcon());
        setGhidActualizare(new JCheckBoxMenuItem("Ghid Actualizare Profil")); /**/          getAfisare().add(getGhidActualizare());               /**/       getGhidActualizare().addActionListener(this);        /**/          getGhidActualizare().setIcon(getGhidActualizareIcon());
        setGhidInregistrare(new JCheckBoxMenuItem("Ghid Înregistrare"));     /**/           getAfisare().add(getGhidInregistrare());             /**/        getGhidInregistrare().addActionListener(this);      /**/           getGhidInregistrare().setIcon(getGhidActualizareIcon());
        //  Meniuri
        setMeniuriProdCom(new JMenu("Meniu"));                             /**/ getMeniuriProdCom().setIcon(getMeniuriProdComIcon());      /**/
        setBurgeri_sandviciuri(new SpecialitatiPaineLipieBlat(getInterfata(), getInterfataBtnActiuni()));   /**/ getMeniuriProdCom().add(getBurgeri_sandviciuri());
        setMeniuSosuri(new MeniuSosuri(getInterfata(), getInterfataBtnActiuni()));                         /**/ getMeniuriProdCom().add(getMeniuSosuri());  getMeniuSosuri().addActionListener(this);
        setCartofi(new MeniuCartofi(getInterfata(), getInterfataBtnActiuni()));                           /**/ getMeniuriProdCom().add(getCartofi());
        setGustari(new MeniuGustari(getInterfata(), getInterfataBtnActiuni()));                               /**/ getMeniuriProdCom().add(getGustari());
        setBauturi(new MeniuBauturi(getInterfata(), getInterfataBtnActiuni(), "Băuturi", "Bauturi"));                                /**/ getMeniuriProdCom().add(getBauturi());
        //  Cos de Cumparaturi
        setCos(new JMenu("Coș"));                         /**/   getCos().setIcon(getCosCumparaturiIcon());               /**/
        setComanda(new JMenuItem("Plasare comandă"));  /**/    getCos().add(getComanda());                             /**/ getComanda().setIcon(getPlasareComandaIcon());          /**/    getComanda().addActionListener(this);
        //  Font
        add(getCont()); add(getFereastra()); add(getMeniuriProdCom()); add(getCos());
        aplicareStiluri(this, Color.BLACK);
    }

    int index = 0;
    private void aplicareStiluri(Component container, Color culoare) {
        container.setFont(new Font("Arial", Font.PLAIN, 20));

        if (container instanceof JMenu meniu) {
            if(container == getCont() || container == getFereastra() || container == getMeniuriProdCom() || container == getCos()) {
                container.setForeground(Color.BLACK);
            } else { container.setForeground(culoare);}
            for (int i = 0; i < meniu.getItemCount(); i++) {
                JMenuItem componenta = meniu.getItem(i);
                if (componenta != null) {
                    aplicareStiluri(componenta, culoare);
                }
            }
        } else {
            container.setForeground(culoare);
        }

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                aplicareStiluri(subComponenta, culoare);
            }
        }
    }

    private void aplicareBackground(Component container, Color culoare) {
        if (container.getParent() != null) {
            container.setBackground(culoare);
        }

        if (container instanceof JMenu meniu) {
            for (int i = 0; i < meniu.getItemCount(); i++) {
                JMenuItem componentaItem = meniu.getItem(i);
                if (componentaItem != null) {
                    componentaItem.setOpaque(true);
                    aplicareBackground(componentaItem, culoare);
                }
            }
        }

        if (container instanceof Container subContainer) {
            Component[] subcomponente = subContainer.getComponents();
            for (Component subComponenta : subcomponente) {
                aplicareBackground(subComponenta, culoare);
            }
        }
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s.png", numeFisier))));
    }

    private ImageIcon creareImagine2(String locatie, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", locatie, numeFisier))));
    }

    public void meniuriBlocate(boolean stare) {
        getMeniuConectare().setEnabled(!stare);
        getMeniuInregistrare().setEnabled(!stare);
        getProfilulMeu().setEnabled(stare);
        getPortofel().setEnabled(stare);
        getAjutor().setEnabled(stare);
        getDeconectare().setEnabled(stare);
        getAspect().setEnabled(stare);
        getPagIntroducere().setEnabled(stare);
        getCos().setEnabled(stare);
        getMeniuriProdCom().setEnabled(stare);
        getGestionareCard().setEnabled(stare);
        getGhidActualizare().setEnabled(stare);
        getDetaliiProdus().setEnabled(stare);
        if(stare)
            stareMeniuri(100);
        else
            stareMeniuri(5);
    }

    public void stareMeniuri(int stadiu) {
        switch (stadiu) {
            case 0:
                stadiuGhidActualizare(true);
                stadiuGestionareCard(false);
                stadiuDetaliiProdus(false);
                stadiuGhidInregistrare(false);
                stadiuMesajIntro(true);
                stadiuAspect(false);
                semnalJCheckBox(false);
                break;
            case 1:
                stadiuGhidActualizare(false);
                stadiuGestionareCard(true);
                stadiuDetaliiProdus(false);
                stadiuGhidInregistrare(false);
                stadiuMesajIntro(true);
                stadiuAspect(false);
                semnalJCheckBox(false);
                break;
            case 2:
                stadiuGhidActualizare(false);
                stadiuGestionareCard(false);
                stadiuDetaliiProdus(true);
                stadiuGhidInregistrare(false);
                stadiuMesajIntro(true);
                stadiuAspect(true);
                break;
            case 3:
                stadiuGhidActualizare(false);
                stadiuGestionareCard(false);
                stadiuDetaliiProdus(false);
                stadiuGhidInregistrare(true);
                stadiuMesajIntro(true);
                break;
            case 4:
                stadiuGhidActualizare(false);
                stadiuGestionareCard(false);
                stadiuDetaliiProdus(false);
                stadiuGhidInregistrare(true);
                stadiuMesajIntro(false);
                stadiuAspect(false);
                break;
            case 5:
                stadiuGhidActualizare(false);
                stadiuGestionareCard(false);
                stadiuDetaliiProdus(false);
                stadiuGhidInregistrare(false);
                stadiuMesajIntro(true);
                stadiuAspect(false);
                semnalJCheckBox(false);
                break;
            default:
                stadiuGhidActualizare(false);
                stadiuGestionareCard(false);
                stadiuDetaliiProdus(false);
                stadiuGhidInregistrare(false);
                stadiuMesajIntro(true);
                stadiuAspect(true);
                semnalJCheckBox(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem meniuSelectat) {
            if(meniuSelectat == getIesire()) {
                interfataBtnActiuni.iesire();
            } else if(meniuSelectat == getProfilulMeu()) {
                interfataBtnActiuni.meniuProfil(true);
                interfataBtnActiuni.activeazaCardGhifModificareProfil();
                stareMeniuri(0);
            } else if(meniuSelectat == getAjutor()) {
                interfataBtnActiuni.meniuIntrebari(true);
                stareMeniuri(100);
            } else if(meniuSelectat == getDeconectare()) {
                interfataBtnActiuni.btnDeconectare(true);
                interfataBtnActiuni.deconectare();
                interfataBtnActiuni.dezactivareSemnale();
                getMeniuConectare().setSelected(true);
                getMeniuInregistrare().setSelected(false);
                getPagIntroducere().setSelected(true);
                interfataBtnActiuni.golireCampuriComboBoxuri();
                stareMeniuri(5);
            } else if(meniuSelectat == getComanda()) {
                interfataBtnActiuni.activeazaCardCosCumparaturi(true);
            } else if(meniuSelectat == getMeniuSosuri()) {
                interfataBtnActiuni.activeazaCardSosuri(true);
                interfataBtnActiuni.cardTematica(true);
                stareMeniuri(2);
                semnalJCheckBoxCardDetaliiProus(false);
            } else if(meniuSelectat == getPortofel()) {
                interfataBtnActiuni.activeazaCardPortofel(true);
                stareMeniuri(1);
            } else if(meniuSelectat == getRoluri()) {
                interfataBtnActiuni.activeazaCardRoluri(true);
            }

        }
        if(sursa instanceof JCheckBoxMenuItem checkBox) {
            if(checkBox == getMeniuConectare()) {
                if (checkBox.isSelected()) {
                    interfataBtnActiuni.meniuConectare(true);
                }
                getMeniuConectare().setSelected(checkBox.isSelected());
                getMeniuInregistrare().setSelected(!checkBox.isSelected());
                stareMeniuri(5);
                interfataBtnActiuni.meniuIntroducere(true);
                getPagIntroducere().setSelected(true);
            } else if(checkBox == getMeniuInregistrare()) {
                if (checkBox.isSelected()) {
                    interfataBtnActiuni.meniuInregistrare(true);
                }
                getMeniuInregistrare().setSelected(checkBox.isSelected());
                getMeniuConectare().setSelected(!checkBox.isSelected());
                getPagIntroducere().setSelected(false);
                stareMeniuri(4);
                interfataBtnActiuni.cardGhidInregistrare0(true);
            } else if(checkBox == getAspect()) {
                if(checkBox.isSelected())
                    interfataBtnActiuni.cardTematica(true);
                else
                    interfataBtnActiuni.cardTematica(false);
            } else if(checkBox == getPagIntroducere()) {
                interfataBtnActiuni.meniuIntroducere(true);
                interfataBtnActiuni.dezactivareContainerEst();
                stareMeniuri(100);
            } else if(checkBox == getGhidActualizare()) {
                interfataBtnActiuni.activeazaCardGhifModificareProfil();
                stareMeniuri(0);
            } else if(checkBox == getGestionareCard()) {
                interfataBtnActiuni.activeazaCardAdminCardBancar();
                stareMeniuri(1);
            } else if(checkBox == getDetaliiProdus()) {
                getDetaliiProdus().setState(!getDetaliiProdus().getState());
            } else if(checkBox == getGhidInregistrare()) {
                getGhidInregistrare().setState(!getGhidInregistrare().getState());
            }
        }
    }

    public void actualizareRolUtilizator(String rol) {
        this.rolUtilizator = rol;
        if(rol.equals("Admin")) {
            getRoluri().setEnabled(true);
        } else {
            getRoluri().setEnabled(false);
        }
    }

    @Override
    public void semnalJCheckBox(boolean stare) {
        getAspect().setSelected(stare);
    }

    @Override
    public void semnalJCheckBoxAdaugareCard(boolean stare) {
        getGestionareCard().setSelected(stare);
    }

    @Override
    public void semnalJCheckBoxMeniuIntro(boolean stare) {
        getPagIntroducere().setSelected(stare);
    }

    @Override
    public void semnalJCheckBoxCardGhidModificare(boolean stare) {
        getGhidActualizare().setSelected(stare);
    }

    @Override
    public void semnalJCheckBoxCardDetaliiProus(boolean stare) {
        getDetaliiProdus().setSelected(stare);
    }

    @Override
    public void semnalJCheckBoxCardInregistrare(boolean stare) {
        getMeniuInregistrare().setSelected(stare);
    }

    @Override
    public void semnalJChecBoxCardConectare(boolean stare) {
        getMeniuConectare().setSelected(stare);
    }

    @Override
    public void stadiuGestionareCard(boolean stare) {
        getGestionareCard().setEnabled(stare);
        getGestionareCard().setSelected(stare);
    }

    @Override
    public void stadiuDetaliiProdus(boolean stare) {
        getDetaliiProdus().setEnabled(stare);
        getDetaliiProdus().setSelected(stare);
    }

    @Override
    public void stadiuGhidActualizare(boolean stare) {
        getGhidActualizare().setEnabled(stare);
        getGhidActualizare().setSelected(stare);
    }

    @Override
    public void stadiuGhidInregistrare(boolean stare) {
        getGhidInregistrare().setEnabled(stare);
        getGhidInregistrare().setSelected(stare);
    }

    @Override
    public void stadiuMesajIntro(boolean stare) {
        getPagIntroducere().setEnabled(stare);
    }

    @Override
    public void stadiuAspect(boolean stare) {
        getAspect().setEnabled(stare);
        getAspect().setSelected(stare);
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
        aplicareBackground(this, culoare);
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        aplicareStiluri(this, culoare);
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {

    }

    @Override
    public void asezareElemente() {

    }
}
