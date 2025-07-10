package org.example.Vizualizare.Carduri.Vest;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;
import org.example.Model.EmailBot;

@Getter @Setter
public class Card_Resetare_Parola extends JPanel implements ActionListener, I_Tematica {
    private JLabel titluEticheta, adresaEmailEticheta, autorEticheta;
    private EmailBot emailBot;
    private JTextField adresaEmailCamp;
    private JButton trimitereBtn, meniuLogareBtn;
    private Border borduraInterioara, borduraExterioara;
    private I_Carduri interfata;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_FrontEnd interfataFrontEnd;
    private I_BtnActiuni interfataBtnActiuni;

    public Card_Resetare_Parola(I_Carduri arg, I_FrontEnd interfataFrontEnd, I_BtnActiuni interfataBtnActiuni, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                       boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfata(arg);
        this.interfataFrontEnd = interfataFrontEnd;
        this.interfataBtnActiuni = interfataBtnActiuni;
        configurari("Setari", "Meniu de recuperare a contului");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Butoane/" + urlTitluIcon + ".png")).append("\">");
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
        setTitluEticheta(new JLabel("Recuperare cont"));
        setAdresaEmailEticheta(new JLabel("Adresă de E-mail:"));
        setAutorEticheta(new JLabel("#Autor: Antohi Andi-Ionel"));

        setAdresaEmailCamp(new JTextField(10));

        setTrimitereBtn(new JButton("Trimitere")); getTrimitereBtn().setFocusPainted(false); getTrimitereBtn().addActionListener(this);
        setMeniuLogareBtn(new JButton("Logare")); getMeniuLogareBtn().setFocusPainted(false); getMeniuLogareBtn().addActionListener(this);

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

        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 1;
        gc.weighty = 0.3;
        gc.anchor = centru;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = ins10;
        add(getTitluEticheta(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(getAdresaEmailEticheta(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        add(getAdresaEmailCamp(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        add(getTrimitereBtn(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        add(getMeniuLogareBtn(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        add(new JPanel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 1;
        add(getAutorEticheta(), gc);
    }

    public void golireCampuri() {
        getAdresaEmailCamp().setText("");
    }

    private boolean verificareAdresaEmail(String adresaEmail) {
        String regexAdresaEmail = "^[a-zA-Z0-9._+-]+@(gmail\\.com|yahoo\\.com|365\\.univ-ovidius\\.ro)$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexAdresaEmail);
        Matcher matcher = pattern.matcher(adresaEmail);
        if(adresaEmail.length() < 4 || adresaEmail.length() > 50 || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Adresa de E-mail nu respectă cerințele: între 3 și 50 caractere. În plus, ea trebuie să conțină minuscule, " +
                    "majuscule, cifre, punct, +, -, _ și să se termine cu: @gmail.com, @yahoo.com, @365.univ-ovidius.ro.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == trimitereBtn) {
                String adresaEmail = getAdresaEmailCamp().getText().trim();
                boolean stare = verificareAdresaEmail(adresaEmail);
                if(stare) {
                    interfataFrontEnd.recuperareCont(adresaEmail);
                }
                golireCampuri();

            } else if(selectat == getMeniuLogareBtn()) {
                interfataBtnActiuni.meniuConectare(true);
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
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
