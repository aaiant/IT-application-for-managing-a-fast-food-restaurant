package org.example.Vizualizare.Carduri.Centru.Cos;


import javax.swing.*; import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent; import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*; import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_BackEnd;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;
import org.example.Model.ProdusCos;
import org.example.Model.ProduseCos;

@Getter @Setter
public class Card_CosCumparaturi extends JPanel implements ActionListener, ListSelectionListener, I_Tematica {
    private JTable tabelProduse;
    private JScrollPane scroll;
    private JLabel titluSectiune;
    private ListSelectionModel cursorTabel;
    private I_Carduri interfataCarduri;
    private JButton inchidereButon, modificareCosBtn, plasareComandaBtn;
    private Border borduraExterioara;
    private ImageIcon inchidereButonIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv, stareSetariBtn;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private I_BackEnd interfataBackEnd;
    private double pretTotal;
    private int idUtilizator;

    public Card_CosCumparaturi(I_Carduri arg, I_BtnActiuni interfataBtnActiuni, I_BackEnd interfataBackEnd, I_FrontEnd interfataFrontEnd ,Color culoareComponentaArg,
                      String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(arg);
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataBackEnd = interfataBackEnd;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("CosCumparaturi", "Coș de Cumpărături");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    //  Metode
    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Butoane/" + urlTitluIcon + ".png")).append("\">");
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
        setPretTotal(0);

        setInchidereButonIcon(creareImagineBtn("InchidereButon2"));

        setStareSetariBtn(true);

        setInchidereButon(new JButton("Închidere"));
        getInchidereButon().setIcon(getInchidereButonIcon());
        getInchidereButon().addActionListener(this);

        setModificareCosBtn(new JButton("Modificare"));
        getModificareCosBtn().addActionListener(this);
        getModificareCosBtn().setEnabled(getStareSetariBtn());

        setPlasareComandaBtn(new JButton("Plasare Comandă"));
        getPlasareComandaBtn().addActionListener(this);
        getPlasareComandaBtn().setEnabled(getStareSetariBtn());

        getInchidereButon().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getModificareCosBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getPlasareComandaBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

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

    public void formatareTabel(ProduseCos listaProduseCos) {
        String[] coloane = listaProduseCos.getColoane().toArray(new String[0]);

        DefaultTableModel model = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setPretTotal(0);
        for (ProdusCos clasa : listaProduseCos.getProduseCos()) {
            Object[] rowData = new Object[]{
                    clasa.getId(),
                    clasa.getProdus(),
                    clasa.getPret(),
                    clasa.getCantitate()
            };
            model.addRow(rowData);

            setPretTotal(getPretTotal() + (clasa.getPret() * clasa.getCantitate()));
            DecimalFormat df = new DecimalFormat("#.##");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            setPretTotal(Double.parseDouble(df.format(getPretTotal())));
            interfataFrontEnd.actualizarePretTotal(getPretTotal());
        }

        setTabelProduse(new JTable(model));
        tabelProduse.setRowSelectionAllowed(true);
        tabelProduse.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tabelProduse.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        tabelProduse.setRowSelectionAllowed(true);

        TableColumnModel columnModel = tabelProduse.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(70);

        columnModel.getColumn(1).setPreferredWidth(230);

        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(2).setMaxWidth(90);

        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(3).setMaxWidth(90);

        tabelProduse.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JScrollPane scroll = new JScrollPane(tabelProduse);
        setScroll(scroll);

        tabelProduse.revalidate();
        tabelProduse.repaint();

        setCursorTabel(tabelProduse.getSelectionModel());
        getCursorTabel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getCursorTabel().addListSelectionListener(this);

        tabelProduse.setRowHeight(30);

        asezareElemente();
    }

    public ArrayList<ProdusCos> preluareProduseCosTabel() {
        ArrayList<ProdusCos> listaProduse = new ArrayList<>();

        if (getTabelProduse() != null) {
            DefaultTableModel model = (DefaultTableModel) getTabelProduse().getModel();
            int numarRanduri = model.getRowCount();

            for (int i = 0; i < numarRanduri; i++) {
                int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                String produs = model.getValueAt(i, 1).toString();
                double pret = Double.parseDouble(model.getValueAt(i, 2).toString());
                int cantitate = Integer.parseInt(model.getValueAt(i, 3).toString());

                ProdusCos produsCos = new ProdusCos(id, produs, pret, cantitate);
                listaProduse.add(produsCos);
            }
        }

        return listaProduse;
    }

    public void actualizeazaTabel(ProduseCos listaProduseCos) {
        if (tabelProduse == null) {
            formatareTabel(listaProduseCos);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tabelProduse.getModel();

        model.setRowCount(0);

        setPretTotal(0);
        for (ProdusCos clasa : listaProduseCos.getProduseCos()) {
            Object[] rowData = new Object[]{
                    clasa.getId(),
                    clasa.getProdus(),
                    clasa.getPret(),
                    clasa.getCantitate()
            };
            model.addRow(rowData);

            setPretTotal(getPretTotal() + (clasa.getPret() * clasa.getCantitate()));
            DecimalFormat df = new DecimalFormat("#.##");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            setPretTotal(Double.parseDouble(df.format(getPretTotal())));
            interfataFrontEnd.actualizarePretTotal(getPretTotal());
        }

        tabelProduse.revalidate();
        tabelProduse.repaint();
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
        gc.gridx = 0; gc.gridwidth = 3;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getScroll(), gc);

        gc.fill = gol;
        gc.gridy++;

        gc.weightx = 1;
        gc.gridx = 0; gc.gridwidth = 1; gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getModificareCosBtn(), gc);

        gc.weightx = 1;
        gc.gridx = 1; gc.gridwidth = 1; gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getPlasareComandaBtn(), gc);

        gc.weightx = 1;
        gc.gridx = 2; gc.gridwidth = 1; gc.weighty = 0;
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
                interfataBtnActiuni.activeazaCardAdminCosCumparaturi(false);
            } else if(buton == getModificareCosBtn()) {
                interfataBtnActiuni.activeazaCardAdminCosCumparaturi(true);
                interfataFrontEnd.actualizarePretTotal(getPretTotal());

            } else if(buton == getPlasareComandaBtn()) {
                interfataBtnActiuni.activeazaCardPlasareComanda(true);
                interfataFrontEnd.actualizarePretTotal(getPretTotal());
                interfataFrontEnd.actualizareCarduriBancareCombo(getIdUtilizator());
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && getTabelProduse() != null) {
            int selectedRow = getTabelProduse().getSelectedRow();
            if (selectedRow != -1) {
                Object idProdusCosObj = getTabelProduse().getValueAt(selectedRow, 0),
                        denProdusCosObj = getTabelProduse().getValueAt(selectedRow, 1),
                        pretBucProdusObj = getTabelProduse().getValueAt(selectedRow, 2),
                        cantitateProdusCosObj = getTabelProduse().getValueAt(selectedRow, 3);
                String idProdusCos = idProdusCosObj.toString() , denProdusCos = denProdusCosObj.toString(),
                        pretBucProdus = pretBucProdusObj.toString(), cantitateProdusCos = cantitateProdusCosObj.toString();
                ProdusCos produs = new ProdusCos(Integer.parseInt(idProdusCos), denProdusCos, Double.parseDouble(pretBucProdus),
                        Integer.parseInt(cantitateProdusCos));
                if(interfataFrontEnd != null) {
                    interfataFrontEnd.produsCosSelectat(produs);
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
