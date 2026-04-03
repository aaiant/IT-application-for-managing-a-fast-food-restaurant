package org.example.Vizualizare.Carduri.Intrebari_Raspunsuri;

import javax.swing.*; import javax.swing.border.*;
import java.awt.*; import java.awt.event.*;
import java.util.Objects;
import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;

@Getter @Setter
public class Card_Raspunsuri extends JPanel implements ActionListener, I_Tematica {
    private JScrollPane scroll;
    private JTextArea zonaText;
    private JTextField codIntrebareCamp;
    private JLabel codIntrebare;
    private ListSelectionModel cursorTabel;
    private I_Carduri interfataCarduri;
    private JButton inchidereButon, inapoiBtn, setariBtn;
    private Border borduraExterioara;
    private ImageIcon inchidereButonIcon, inapoiBtnIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv, stareSetariBtn;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private String rolUtilizator;

    public Card_Raspunsuri(I_Carduri arg, I_BtnActiuni interfataBtnActiuni,  I_FrontEnd interfataFrontEnd ,Color culoareComponentaArg,
                          String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(arg);
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("Ajutor2", "Meniu de Răspunsuri");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
        asezareElemente();
    }

    //  Metode
    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Cont/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;<span style=\"font-size: 14px;\">%s</span>", textIcon));
        sb.append("</html>");
        setLayout(new GridBagLayout());
        Dimension dim = getPreferredSize();
        dim.width = 150;
        setPreferredSize(dim);
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, BorderFactory.createRaisedBevelBorder()));
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInchidereButonIcon(creareImagine("InchidereButon2"));
        setInapoiBtnIcon(creareImagine("InapoiButon"));

        setStareSetariBtn(true);

        setInchidereButon(new JButton("Închidere"));
        getInchidereButon().setIcon(getInchidereButonIcon());
        getInchidereButon().addActionListener(this);

        setZonaText(new JTextArea());
        getZonaText().setEditable(false);
        getZonaText().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getZonaText().setLineWrap(true);
        getZonaText().setWrapStyleWord(true);

        setScroll(new JScrollPane(getZonaText()));
        getScroll().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getScroll().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setSetariBtn(new JButton("Setări"));
        getSetariBtn().setEnabled(getStareSetariBtn());
        getSetariBtn().addActionListener(this);

        setInapoiBtn(new JButton("Înapoi"));
        getInapoiBtn().setIcon(getInapoiBtnIcon());
        getInapoiBtn().addActionListener(this);

        setCodIntrebare(new JLabel("Codul Întrebării: "));
        getCodIntrebare().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        setCodIntrebareCamp(new JTextField(3));
        getCodIntrebareCamp().setEditable(false);
        getCodIntrebareCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getInchidereButon().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getInapoiBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getSetariBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        getZonaText().setFont(new Font(getFontSv(), getTipTextSv() ,getDimensiuneTextSv()));
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins5 = new Insets(5, 5, 5, 5);

        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int centru = GridBagConstraints.CENTER;
        int ambele = GridBagConstraints.BOTH;
        int inceput = GridBagConstraints.LINE_START;
        int sfarsit = GridBagConstraints.LINE_END;

        gc.fill = ambele;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridwidth = 7;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getScroll(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weighty = 0;
        gc.anchor = inceput;
        gc.insets = ins5;
        add(getInapoiBtn(), gc);

        gc.weightx = 1;
        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins5;
        add(getSetariBtn(), gc);

        gc.weightx = 1;
        gc.gridx = 2;
        gc.gridwidth = 1;
        gc.weighty = 0;
        gc.anchor = sfarsit;
        gc.insets = ins5;
        add(getCodIntrebare(), gc);

        gc.weightx = 0.5;
        gc.gridx = 3;
        gc.gridwidth = 1;
        gc.weighty = 0;
        gc.fill = orizontal;
        gc.anchor = inceput;
        gc.insets = ins5;
        add(getCodIntrebareCamp(), gc);

        gc.fill = gol;
        gc.weightx = 1;
        gc.gridx = 4;
        gc.gridwidth = 1;
        gc.weighty = 0;
        gc.anchor = sfarsit;
        gc.insets = ins5;
        add(getInchidereButon(), gc);
    }

    public void actualizareRolUtilizator(String rol) {
        this.rolUtilizator = rol;
        if(rol.equals("Admin")) {
            getSetariBtn().setEnabled(true);
        } else {
            getSetariBtn().setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if(buton == getInchidereButon()) {
                interfataBtnActiuni.meniuIntroducere(true);
                interfataBtnActiuni.activeazaCardAdminRaspunsuri(false);
            } else if(buton == getInapoiBtn()) {
                interfataBtnActiuni.meniuIntrebari(true);
                interfataBtnActiuni.activeazaCardAdminRaspunsuri(false);
            } else if(buton == getSetariBtn()) {
                interfataBtnActiuni.activeazaCardAdminRaspunsuri(true);
                interfataFrontEnd.actualizareCodMaxRaspunsuri();
            }
         }
    }

    private void aplicareStiluri2(Component container, Color culoare) {
        container.setBackground(culoare);

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                if(!(subComponenta instanceof JTextField) && !(subComponenta instanceof JButton) && !(subComponenta instanceof JComboBox<?>)
                        && !(subComponenta instanceof JTextArea))
                    aplicareStiluri2(subComponenta, culoare);
            }
        }
    }

    private void aplicareStiluri3(Component container, Color culoare) {
        container.setForeground(culoare);

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                if(!(subComponenta instanceof JTextField) && !(subComponenta instanceof JButton) && !(subComponenta instanceof JTextArea))
                    aplicareStiluri3(subComponenta, culoare);
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        aplicareStiluri2(this, culoare);
        this.setBackground(culoare);
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(componenta instanceof JLabel eticheta)
                eticheta.setOpaque(true);
            if(!(componenta instanceof JTextField) && !(componenta instanceof JButton) && !(componenta instanceof JTextArea))
                componenta.setBackground(culoare);
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        aplicareStiluri3(this, culoare);
        setCuloareComponentaSv(culoare);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Butoane/%s.png", numeFisier))));
    }

    public void setareText(String text) {
        getZonaText().setText(text);
    }

    public void adaugareText(String text) {
        getZonaText().append(text);
    }

    public boolean getStareSetariBtn() {return stareSetariBtn;}
}
