package org.example.Vizualizare.Carduri.Intrebari_Raspunsuri;

import javax.swing.*; import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent; import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*; import java.awt.event.*;
import java.io.ObjectStreamException;
import java.util.Objects;
import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_BackEnd;
import org.example.Model.Intrebare;
import org.example.Model.Intrebari;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;

@Getter @Setter
public class Card_Intrebari extends JPanel implements ActionListener, ListSelectionListener, I_Tematica {
    private JTable tabelIntrebari;
    private JScrollPane scroll;
    private JLabel titluSectiune;
    private ListSelectionModel cursorTabel;
    private I_Carduri interfataCarduri;
    private JButton inchidereButon, setariBtn;
    private Border borduraExterioara;
    private ImageIcon inchidereButonIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv, stareSetariBtn;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private I_BackEnd interfataBackEnd;
    private String rolUtilizator;
    private int idUtilizator;

    public Card_Intrebari(I_Carduri arg, I_BtnActiuni interfataBtnActiuni, I_BackEnd interfataBackEnd, I_FrontEnd interfataFrontEnd ,Color culoareComponentaArg,
                          String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(arg);
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataBackEnd = interfataBackEnd;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("Ajutor", "Meniu de Întrebări");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
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
        setInchidereButonIcon(creareImagineBtn("InchidereButon2"));

        setStareSetariBtn(true);

        setInchidereButon(new JButton("Închidere"));
        getInchidereButon().setIcon(getInchidereButonIcon());
        getInchidereButon().addActionListener(this);

        setSetariBtn(new JButton("Setări"));
        getSetariBtn().addActionListener(this);
        getSetariBtn().setEnabled(getStareSetariBtn());

        getInchidereButon().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getSetariBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    private ImageIcon creareImagineBtn(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Butoane/%s.png", numeFisier))));
    }

    public void formatareTabel(Intrebari clsIntrebari) {
        String[] coloane = clsIntrebari.getColoane().toArray(new String[0]);
        DefaultTableModel model = new DefaultTableModel(coloane, 0);

        for (Intrebare clasa : clsIntrebari.getIntrebari()) {
            Object[] rowData = new Object[]{
                    clasa.getCod(),
                    clasa.getContinut()
            };
            model.addRow(rowData);
        }

        setTabelIntrebari(new JTable(model));
        tabelIntrebari.setRowSelectionAllowed(true);
        tabelIntrebari.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tabelIntrebari.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        tabelIntrebari.setRowSelectionAllowed(true);

        TableColumn coduri = tabelIntrebari.getColumnModel().getColumn(0);
        coduri.setMinWidth(50);
        coduri.setMaxWidth(100);

        tabelIntrebari.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JScrollPane scroll = new JScrollPane(tabelIntrebari);
        setScroll(scroll);

        tabelIntrebari.revalidate();
        tabelIntrebari.repaint();

        setCursorTabel(tabelIntrebari.getSelectionModel());
        getCursorTabel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getCursorTabel().addListSelectionListener(this);

        tabelIntrebari.setRowHeight(30);

        asezareElemente();
    }

    public void actualizeazaTabel(Intrebari clsIntrebari) {
        DefaultTableModel model = (DefaultTableModel) tabelIntrebari.getModel();

        model.setRowCount(0);

        for (Intrebare clasa : clsIntrebari.getIntrebari()) {
            Object[] rowData = new Object[]{
                    clasa.getCod(),
                    clasa.getContinut()
            };
            model.addRow(rowData);
        }

        tabelIntrebari.revalidate();
        tabelIntrebari.repaint();
    }

    public void actualizareRolUtilizator(String rol) {
        this.rolUtilizator = rol;
        if(rol.equals("Admin")) {
            getSetariBtn().setEnabled(true);
        } else {
            getSetariBtn().setEnabled(false);
        }
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins5A = new Insets(5, 5, 5,  5);
        Insets ins5D = new Insets(5, 0, 5, 5);
        Insets ins5S = new Insets(5, 5, 5, 0);

        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int centru = GridBagConstraints.CENTER;
        int ambele = GridBagConstraints.BOTH;
        int inceput = GridBagConstraints.LINE_START;
        int sfarsit = GridBagConstraints.LINE_END;

        gc.fill = ambele;
        gc.gridy = 0;
        gc.weightx = 1; gc.weighty = 1;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getScroll(), gc);

        gc.fill = gol;
        gc.gridy++;

        gc.weightx = 1;
        gc.gridx = 0; gc.gridwidth = 1; gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getSetariBtn(), gc);

        gc.weightx = 1;
        gc.gridx = 1; gc.gridwidth = 1; gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getInchidereButon(), gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if(buton == getInchidereButon()) {
                interfataBtnActiuni.meniuIntroducere(true);
                interfataBtnActiuni.activeazaCardAdminIntrebari(false);
            } else if(buton == getSetariBtn()) {
                interfataBtnActiuni.activeazaCardAdminIntrebari(true);
                interfataFrontEnd.actualizareCodMaxIntrebari();
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && getTabelIntrebari() != null) {
            int selectedRow = getTabelIntrebari().getSelectedRow();

            if (selectedRow != -1) {
                Object objIdIntrebare = getTabelIntrebari().getValueAt(selectedRow, 0);
                String codIntrebareText = objIdIntrebare.toString();

                CodIntrebare codIntrebare = new CodIntrebare(this, codIntrebareText);
                if(interfataFrontEnd != null) {
                    if(getIdUtilizator() != 0 && codIntrebareText.length() == 3) {
                        interfataFrontEnd.actualizareDialog(getIdUtilizator(), codIntrebareText);
                    }
                    interfataFrontEnd.intrebareSelectata(codIntrebare);
                    interfataBtnActiuni.activeazaCardAdminIntrebari(false);

                    getTabelIntrebari().getSelectionModel().removeListSelectionListener(this);
                    getTabelIntrebari().clearSelection();
                    getTabelIntrebari().getSelectionModel().addListSelectionListener(this);
                }
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        Component[] componente = this.getComponents();
        this.setBackground(culoare);
        for(Component componenta : componente) {
            if(!(componenta instanceof JButton)) {
                componenta.setBackground(culoare);
                if(componenta instanceof JScrollPane scroll) {
                    Component[] componenteNivel2 = scroll.getComponents();
                    for(Component componentaNivel3 : componenteNivel2)
                        componentaNivel3.setBackground(culoare);
                }
            }
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        setCuloareComponentaSv(culoare);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
         getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private boolean getFocusareDesenBtnSv() {return focusareDesenBtnSv;}
    private boolean getFocusareBtnSv() {return focusareBtnSv;}
    private boolean getStareSetariBtn() {return stareSetariBtn;}
}
