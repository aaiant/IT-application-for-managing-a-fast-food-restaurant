package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.*;
import org.example.Model.UtilizatorRol;

@Getter @Setter
public class Card_Setari_UtilizatorRol extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton modificareBtn;
    private JLabel descriereRolLabel, idUtilizatorLabel, titluLabel, rolLabel;
    private JTextField idUtilizatorCamp;
    private JComboBox<String> roluriCombo;
    private JTextArea descriereRolCamp;
    private JScrollPane scroll;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private int idUtilizator;

    public Card_Setari_UtilizatorRol(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                 boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Setări Roluri</b></p></body></html>"));
        getTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTitluLabel().setOpaque(true);
        getTitluLabel().setHorizontalAlignment(JLabel.CENTER);
        getTitluLabel().setHorizontalTextPosition(JLabel.CENTER);

        setDescriereRolLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Descriere rol: </b></p></body></html>"));
        getDescriereRolLabel().setPreferredSize(new Dimension(150, 30));
        getDescriereRolLabel().setOpaque(true);

        setIdUtilizatorLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Id utilizator: </b></p></body></html>"));
        getIdUtilizatorLabel().setPreferredSize(new Dimension(150, 30));
        getIdUtilizatorLabel().setOpaque(true);

        setRolLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Rol: </b></p></body></html>"));
        getRolLabel().setPreferredSize(new Dimension(150, 30));
        getRolLabel().setOpaque(true);

        setDescriereRolCamp(new JTextArea());
        getDescriereRolCamp().setLineWrap(true);
        getDescriereRolCamp().setWrapStyleWord(true);

        setScroll(new JScrollPane(getDescriereRolCamp()));
        getScroll().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setIdUtilizatorCamp(new JTextField(3));
        setRoluriCombo(new JComboBox<>());
        getRoluriCombo().setEditable(false);

        String[] culori = {"Negru", "Alb"};
        setModificareBtn(new JButton("Modificare"));

        getModificareBtn().addActionListener(this);

        getModificareBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getIdUtilizatorCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getDescriereRolCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getRoluriCombo().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getModificareBtn().setMargin(new Insets(1, 1, 1, 1));

        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        asezareElemente();
    }

    private boolean verificareIdUtilizator(int id) {
        boolean stareObiect = true;
        if(id <= 0 || id == getIdUtilizator()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "ID-ul utilizatorului nu respectă cerințele: trebuie să fie un număr mai mare decât 0 " +
                            " și nu trebuie să fie ID-ul dvs. (nu vă puteți schimba rolul dvs, ci al altor utilizatori).", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareRol(String rol) {
        boolean stareObiect = true;
        if(rol.length() < 3) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Nu ați selectat un rol.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareDescriereRol(String descriere) {
        boolean stareObiect = true;
        if(descriere.length() < 20 || descriere.length() > 300) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Descrierea rolului nu respectă cerințele: între 50 & 300 de caractere.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    public void golireCampuri() {
        getDescriereRolCamp().setText("");
        getIdUtilizatorCamp().setText("");
        if(getRoluriCombo().getItemCount() > 0) {
            getRoluriCombo().setSelectedIndex(0);
        }
    }

    private boolean verificareSimultanaCampuri(int idUtilizator, String rol, String descriereRol) {
        boolean stare = true;

        stare = verificareIdUtilizator(idUtilizator);
        if(!stare) return false;

        stare = verificareRol(rol);
        if(!stare) return false;

        stare = verificareDescriereRol(descriereRol);
        if(!stare) return false;

        return stare;
    }

    public void actualizareIdUtilizator(int id) {
        getIdUtilizatorCamp().setText("" + id);
    }

    public void actualizareDescriere(String descriere){
        getDescriereRolCamp().setText(descriere);
    }

    public void actualizareRoluriCombo(String rol) {
        if(getRoluriCombo().getItemCount() > 0) {
            getRoluriCombo().setSelectedItem(rol);
        }
    }

    public void actualizareIdUtilizatorVal(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public void initializareRoluriCombo(ArrayList<String> roluri) {
        getRoluriCombo().removeAllItems();
        for(String rol : roluri) {
            getRoluriCombo().addItem(rol);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getModificareBtn()) {
                boolean stareObiect = true;
                String rol = Objects.requireNonNull(getRoluriCombo().getSelectedItem().toString()),
                        descriere = getDescriereRolCamp().getText().trim();
                int idUtiliator = Integer.parseInt(getIdUtilizatorCamp().getText().trim());

                stareObiect = verificareSimultanaCampuri(idUtiliator, rol, descriere);

                if(stareObiect) {
                    UtilizatorRol utilizator = new UtilizatorRol("", idUtiliator, rol, descriere);
                    interfataBtnActiuni.actualizareUtilizatorRol(utilizator);
                }
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {

    }

    @Override
    public void schimbaCuloareText(String codHex) {

    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {

    }

    @Override
    public void asezareElemente() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.insets = new Insets(5, 5, 5, 5);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getTitluLabel(), gc);

        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add(getIdUtilizatorLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.7;
        add(getIdUtilizatorCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add(getRolLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.7;
        add(getRoluriCombo(), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 1.0;
        add(getDescriereRolLabel(), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 2;
        gc.weighty = 0.6;
        gc.fill = GridBagConstraints.BOTH;
        add(getScroll(), gc);

        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weighty = 0.1;
        add(getModificareBtn(), gc);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
