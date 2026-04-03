package org.example.Vizualizare.Carduri.Vest;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;

@Getter @Setter
public class Card_Logare extends JPanel implements ActionListener, I_Tematica {
    private JLabel titluEticheta, utilizatorEticheta, parolaEticheta, inregistrareEticheta, autorEticheta, contUitatEticheta;
    private JTextField utilizatorText;
    private JPasswordField parolaText;
    private JButton conectareButon, creareContButon, contUitatBtn;
    private Border borduraInterioara, borduraExterioara;
    private I_Carduri interfata;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_FrontEnd interfataFrontEnd;
    private I_BtnActiuni interfataBtnActiuni;

    public Card_Logare(I_Carduri arg, I_FrontEnd interfataFrontEnd, I_BtnActiuni interfataBtnActiuni, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                       boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfata(arg);
        this.interfataFrontEnd = interfataFrontEnd;
        this.interfataBtnActiuni = interfataBtnActiuni;
        configurari("Logare", "Meniu de autentificare");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/CardVest/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;%s", textIcon));
        sb.append("</html>");

        Dimension dimensiune = getPreferredSize();
        dimensiune.width = 250;
        setPreferredSize(dimensiune);
        setLayout(new GridBagLayout());
        setBorduraInterioara(BorderFactory.createBevelBorder(1, null, Color.WHITE, null, null));
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, borduraInterioara));
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setTitluEticheta(new JLabel("Conectare"));
        setUtilizatorEticheta(new JLabel("Utilizator: "));
        setParolaEticheta(new JLabel("Parolă: "));
        setInregistrareEticheta(new JLabel("Nu aveți cont?"));
        setContUitatEticheta(new JLabel("Cont pierdut?"));
        setAutorEticheta(new JLabel("#Autor: Antohi Andi-Ionel"));

        setUtilizatorText(new JTextField(9));
        setParolaText(new JPasswordField(9));

        setConectareButon(new JButton("Conectare")); getConectareButon().setFocusPainted(false); getConectareButon().addActionListener(this);
        setCreareContButon(new JButton("Creare")); getCreareContButon().addActionListener(this);
        setContUitatBtn(new JButton("Găsire")); getContUitatBtn().setFocusPainted(false); getContUitatBtn().addActionListener(this);

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins10 = new Insets(10, 10, 10, 10);

        int centru = GridBagConstraints.CENTER;
        int orizontal = GridBagConstraints.HORIZONTAL;

        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.3;
        gc.anchor = centru;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = ins10;
        add(getTitluEticheta(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(getUtilizatorEticheta(), gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.fill = orizontal;
        add(getUtilizatorText(), gc);
        getUtilizatorText().setPreferredSize(new Dimension(200, 25));

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(getParolaEticheta(), gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.fill = orizontal;
        add(getParolaText(), gc);
        getParolaText().setPreferredSize(new Dimension(200, 25));

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(getInregistrareEticheta(), gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(getCreareContButon(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(getContUitatEticheta(), gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(getContUitatBtn(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.3;
        gc.anchor = centru;
        gc.fill = GridBagConstraints.NONE;
        add(getConectareButon(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0;
        add(getAutorEticheta(), gc);
    }


    public void golireCampuri() {
        getUtilizatorText().setText("");
        getParolaText().setText("");
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
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getCreareContButon()) {
                interfataBtnActiuni.meniuInregistrare(true);
                interfataBtnActiuni.cardGhidInregistrare0(true);
                interfataBtnActiuni.semnalCardLogare(false);
                interfataBtnActiuni.semnalCardInregistrare(true);
            } else if(selectat == getConectareButon()) {
                String parola = new String(getParolaText().getPassword());
                Utilizator utilizator = new Utilizator(this, getUtilizatorText().getText(), parola);
                if(interfataFrontEnd != null)
                    interfataFrontEnd.dateLogare(utilizator);
            } else if(selectat == getContUitatBtn()) {
                interfataBtnActiuni.activeazaCardResetareParola(true);
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
}
