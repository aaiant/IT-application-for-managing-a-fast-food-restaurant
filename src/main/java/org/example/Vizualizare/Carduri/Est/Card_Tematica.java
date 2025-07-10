package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_BackEnd;
import org.example.Model.Nuanta;
import org.example.Model.NuanteCulori;
import org.example.Interfete.I_Culori;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;

@Getter @Setter
public class Card_Tematica extends JPanel implements ActionListener, ListSelectionListener, I_Tematica {
    private Border borduraInterioara, borduraExterioara;
    private JButton inchidereButon, editareBtn;
    private JScrollPane scroll;
    private JTable tabelTematici;
    private ListSelectionModel cursorTabel;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata;
    private ImageIcon inchidereButonIcon;
    private I_BackEnd interfataBackEnd;
    private I_Culori interfataCulori;
    private I_BtnActiuni interfataBtnActiuni;
    private String rolUtilizator;

    public Card_Tematica(I_Carduri arg, I_Culori arg2, I_BtnActiuni interfataBtnActiuni) {
        setInterfataCard(arg);
        setInterfataCulori(arg2);
        this.interfataBtnActiuni = interfataBtnActiuni;
        configurari("Tematica", "Aspect");
        initializari();
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/CardEst/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;<span style=\"font-size: 14px;\">%s</span>", textIcon));
        sb.append("</html>");
        Dimension dimensiune = getPreferredSize();
        dimensiune.width = 450;
        setPreferredSize(dimensiune);
        setLayout(new GridBagLayout());
        setBorduraInterioara(BorderFactory.createRaisedBevelBorder());
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        borduraExterioara.setTitleFont(new Font("Times New Roman", Font.PLAIN, 18));
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, borduraInterioara));
    }

    private void initializari() {
        setEditareBtn(new JButton("Editare"));
        setInchidereButon(new JButton("Închidere"));

        getEditareBtn().addActionListener(this);
        getInchidereButon().addActionListener(this);

        getEditareBtn().setFont(new Font("Times New Romans", Font.PLAIN, 18));
        getInchidereButon().setFont(new Font("Times New Romans", Font.PLAIN, 18));

        getEditareBtn().setFocusPainted(false);
        getInchidereButon().setFocusPainted(false);

        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));

        getInchidereButon().setIcon(getInchidereButonIcon());
    }

    public void actualizareRolUtilizator(String rol) {
        this.rolUtilizator = rol;
        if(rol.equals("Admin")) {
            getEditareBtn().setEnabled(true);
        } else {
            getEditareBtn().setEnabled(false);
        }
    }

    public void formatareTabel(NuanteCulori clsNuanteCulori) {
        String[] coloane = clsNuanteCulori.getColoane().toArray(new String[0]);
        DefaultTableModel model = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Nuanta clasa : clsNuanteCulori.getNuante()) {
            Object[] rowData = new Object[]{
                    clasa.getDenumireNuanta(),
                    clasa.getCod_hex(),
                    clasa.getDenumireCuloare()
            };
            model.addRow(rowData);
        }

        setTabelTematici(new JTable(model));
        getTabelTematici().setRowSelectionAllowed(true);
        getTabelTematici().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getTabelTematici().getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));

        TableColumn coduri_hex = getTabelTematici().getColumnModel().getColumn(0);
        TableColumn hex_fundal = getTabelTematici().getColumnModel().getColumn(1);
        coduri_hex.setPreferredWidth(coduri_hex.getPreferredWidth() + 100);
        hex_fundal.setPreferredWidth(hex_fundal.getPreferredWidth() + 10);

        getTabelTematici().setRowHeight(30);
        getTabelTematici().revalidate();
        getTabelTematici().repaint();

        setCursorTabel(getTabelTematici().getSelectionModel());
        getCursorTabel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getCursorTabel().addListSelectionListener(this);

        setScroll(new JScrollPane(getTabelTematici()));
        asezareElemente();
    }

    public void actualizeazaTabel(NuanteCulori clsNuanteCulori) {
        if (getTabelTematici() == null) {
            formatareTabel(clsNuanteCulori);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) getTabelTematici().getModel();

        model.setRowCount(0);

        for(Nuanta nuanta : clsNuanteCulori.getNuante()) {
            Object[] rowData = new Object[]{
                    nuanta.getDenumireNuanta(),
                    nuanta.getCod_hex(),
                    nuanta.getDenumireCuloare()
            };
            model.addRow(rowData);
        }

        getTabelTematici().setRowHeight(30);
        getTabelTematici().revalidate();
        getTabelTematici().repaint();
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets insBtn = new Insets(0, 0, 0, 0);

        int centru = GridBagConstraints.CENTER;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int gol = GridBagConstraints.NONE;
        int ambele = GridBagConstraints.BOTH;

        gc.fill = ambele;
        gc.gridy = 0;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getScroll(), gc);

        gc.fill = orizontal;
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = insBtn;
        gc.gridwidth = 1;
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        add(getEditareBtn(), gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = insBtn;
        gc.gridwidth = 1;
        gc.weightx = 0.0;
        add(getInchidereButon(), gc);
    }

    int index = 0;
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && getTabelTematici() != null) {
                int selectedRow = getTabelTematici().getSelectedRow();
                if (selectedRow != -1) {
                    Culori culori = new Culori(this, (String) getTabelTematici().getValueAt(selectedRow, 1), (
                            String) getTabelTematici().getValueAt(selectedRow, 2));
                    if(interfataCulori != null)
                        interfataCulori.transmitereCulori(culori);
                }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getInchidereButon()) {
                interfataBtnActiuni.cardTematica(false);
                interfataBtnActiuni.resetareCardProduse(true);
                interfataBtnActiuni.resetareCardPortofel(true);
            } else if(selectat == getEditareBtn()) {
                interfataBtnActiuni.activeazaCardAdminTematica(true, false);
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
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        if (borduraExterioara != null) {
            borduraExterioara.setTitleColor(culoare);
            this.revalidate();
            this.repaint();
        }
    }
}
