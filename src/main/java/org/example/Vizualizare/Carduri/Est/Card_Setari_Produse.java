package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.*;
import org.example.Interfete.*;

@Getter @Setter
public class Card_Setari_Produse extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton adaugareBtn, modificareBtn, stergereBtn, meniuTematicaBtn, linkPozaProdusBtn, tabelIngredienteBtn;
    private JLabel continutProdusLabel, linkPozaLabel, denumireProduseLabel, pretProdusLabel, etichetaProdusLabel, UnitateMasuraProdusLabel,
            producatorLabel, CategorieProdusLabel, subcategorieProdusLabel, ingredienteLabel;
    private JTextField denumireProdusCamp, linkPozaProdusCamp, pretProdusCamp, continutProdusCamp, etichetaProdusCamp, ingredienteProdusCamp;
    private JComboBox<String> unitatiMasuraCombo, producatoriCombo, categoriiProduseCombo, subcategoriiProduseCombo;
    private File proiectRoot;
    private List<JCheckBox> checkBox_uri = new ArrayList<>();
    private List<String> item_uriSelectate = new ArrayList<>();
    private JPanel fereastraIngredienteProdus;
    private JScrollPane scroll;
    private JPopupMenu popup;
    private ImageIcon meniuTematicaIcon;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private String link_poza, rolUtilizator;
    private int idUtilizator;

    public Card_Setari_Produse(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                  boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        try {
            proiectRoot = new File(".").getCanonicalFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setFereastraIngredienteProdus(new JPanel());
        getFereastraIngredienteProdus().setLayout(new BoxLayout(getFereastraIngredienteProdus(), BoxLayout.Y_AXIS));

        setScroll(new JScrollPane(getFereastraIngredienteProdus()));
        getScroll().setPreferredSize(new Dimension(240, 200));

        setPopup(new JPopupMenu());
        getPopup().add(getScroll());


        setContinutProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Conținut: </b></p></body></html>"));
        getContinutProdusLabel().setOpaque(true);

        setDenumireProduseLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Denumire produs: </b></p></body></html>"));
        getDenumireProduseLabel().setOpaque(true);

        setLinkPozaLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Link poză: </b></p></body></html>"));
        getLinkPozaLabel().setOpaque(true);

        setPretProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Preț: </b></p></body></html>"));
        getPretProdusLabel().setOpaque(true);

        setEtichetaProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Etichetă: </b></p></body></html>"));
        getEtichetaProdusLabel().setOpaque(true);

        setUnitateMasuraProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Unitate de masură: </b></p></body></html>"));
        getUnitateMasuraProdusLabel().setOpaque(true);

        setProducatorLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Producător: </b></p></body></html>"));
        getProducatorLabel().setOpaque(true);

        setCategorieProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Categorie: </b></p></body></html>"));
        getCategorieProdusLabel().setOpaque(true);

        setSubcategorieProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Subcategorie: </b></p></body></html>"));
        getSubcategorieProdusLabel().setOpaque(true);

        setIngredienteLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Ingrediente: </b></p></body></html>"));
        getIngredienteLabel().setOpaque(true);

        setMeniuTematicaBtn(new JButton(""));
        getMeniuTematicaBtn().addActionListener(this);
        getMeniuTematicaBtn().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
        getMeniuTematicaBtn().setFont(new Font("Times New Roman", Font.PLAIN, 14));
        getMeniuTematicaBtn().setOpaque(true);
        setMeniuTematicaIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/CardEst/Tematica.png"))));
        getMeniuTematicaBtn().setIcon(getMeniuTematicaIcon());

        setLinkPozaProdusBtn(new JButton("Link Poză"));
        getLinkPozaProdusBtn().addActionListener(this);
        getLinkPozaProdusBtn().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
        getLinkPozaProdusBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getLinkPozaProdusBtn().setOpaque(true);

        setTabelIngredienteBtn(new JButton("Ingrediente"));
        getTabelIngredienteBtn().addActionListener(this);
        getTabelIngredienteBtn().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
        getTabelIngredienteBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getTabelIngredienteBtn().setOpaque(true);

        setDenumireProdusCamp(new JTextField(18));
        setLinkPozaProdusCamp(new JTextField(15));
        setPretProdusCamp(new JTextField(3));
        setContinutProdusCamp(new JTextField(4));
        setEtichetaProdusCamp(new JTextField(4));
        setIngredienteProdusCamp(new JTextField(18));

        getIngredienteProdusCamp().setEditable(false);
        getLinkPozaProdusCamp().setEditable(false);

        setUnitatiMasuraCombo(new JComboBox<>());
        setProducatoriCombo(new JComboBox<>());
        setCategoriiProduseCombo(new JComboBox<>());
        setSubcategoriiProduseCombo(new JComboBox<>());

        String[] culori = {"Negru", "Alb"};
        setAdaugareBtn(new JButton("Adăugare"));
        setStergereBtn(new JButton("Ștergere"));
        setModificareBtn(new JButton("Modificare"));

        getAdaugareBtn().addActionListener(this);
        getStergereBtn().addActionListener(this);
        getModificareBtn().addActionListener(this);

        int dimensiuneFont = 16; String tipFont = "Times New Roman";

        getAdaugareBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getStergereBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getModificareBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getDenumireProdusCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getLinkPozaProdusCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getPretProdusCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getContinutProdusCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getEtichetaProdusCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getIngredienteProdusCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getUnitatiMasuraCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getProducatoriCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getCategoriiProduseCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getSubcategoriiProduseCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));

        getDenumireProdusCamp().setPreferredSize(new Dimension(180, 25));
        getLinkPozaProdusCamp().setPreferredSize(new Dimension(180, 25));
        getPretProdusCamp().setPreferredSize(new Dimension(40, 25));
        getContinutProdusCamp().setPreferredSize(new Dimension(40, 25));
        getEtichetaProdusCamp().setPreferredSize(new Dimension(40, 25));
        getIngredienteProdusCamp().setPreferredSize(new Dimension(40, 25));

        getUnitatiMasuraCombo().setPreferredSize(new Dimension(180, 25));
        getProducatoriCombo().setPreferredSize(new Dimension(180, 25));
        getCategoriiProduseCombo().setPreferredSize(new Dimension(180, 25));
        getSubcategoriiProduseCombo().setPreferredSize(new Dimension(180, 25));

        getDenumireProduseLabel().setPreferredSize(new Dimension(120, 25));
        getLinkPozaLabel().setPreferredSize(new Dimension(120, 25));
        getPretProdusLabel().setPreferredSize(new Dimension(120, 25));
        getEtichetaProdusLabel().setPreferredSize(new Dimension(120, 25));
        getUnitateMasuraProdusLabel().setPreferredSize(new Dimension(120, 25));
        getProducatorLabel().setPreferredSize(new Dimension(120, 25));

        getMeniuTematicaBtn().setPreferredSize(new Dimension(120, 30));
        getTabelIngredienteBtn().setPreferredSize(new Dimension(120, 30));
        getLinkPozaProdusBtn().setPreferredSize(new Dimension(120, 30));

        getAdaugareBtn().setMargin(new Insets(2, 8, 2, 8));
        getModificareBtn().setMargin(new Insets(2, 8, 2, 8));
        getStergereBtn().setMargin(new Insets(2, 8, 2, 8));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        getMeniuTematicaBtn().setVisible(true);
        getTabelIngredienteBtn().setVisible(true);
        getLinkPozaProdusBtn().setVisible(true);
        asezareElemente();
    }

    private boolean verificareLinkPozaProdus(String link) {
        String regexLinkPoza = "^/Imagini_Produse/[a-zA-Z0-9/_-]+\\.png$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile(regexLinkPoza);
        matcher = pattern.matcher(link);
        if(link.length() < 22 || link.length() > 255 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Link-ul asociat pozei nu respectă cerințele: începe cu \"/Imagini/\", trebuie să aibă " +
                            "între 22 & 255 de caractere și se termină cu .png.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareDenumireProdus(String denumire) {
        String regexDenumire = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ0-9\\s\\-/,&']*$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile(regexDenumire);
        matcher = pattern.matcher(denumire);
        if(denumire.length() <  4 || denumire.length() > 60 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Denumirea produsului nu respectă cerințele: conține între 4 și 60 de caractere sau cifre, cât și spațiu sau simbolurile: \"&\", " +
                    "\",\", \"'\", \"/\", \" \".", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareIngredienteProdus(String listaIngrediente) {
        boolean stareObiect = true;
        if(listaIngrediente.length() < 2) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Trebuie să alegeți ingredientele pentru produs: Apăsați butonul \"Ingrediente\" de mai jos!", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareContinutProdus(int continut) {
        boolean stareObiect = true;
        if(continut < 1 || continut > 1000) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Conținutul asociat produsului nu respectă cerințele: trebuie să aibă valori cuprinse între 1 și 1000.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareEtichetaProdus(String eticheta) {
        String regexEtichetaProdus = "^[A-Z][a-zA-Z0-9\\-]*$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile(regexEtichetaProdus);
        matcher = pattern.matcher(eticheta);
        if(eticheta.length() < 2 || eticheta.length() > 30 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Eticheta asociată produsului nu respectă cerințele: începe cu majusculă, conține litere, cifre și simbolul \"-\" și trebuie să aibă " +
                            "între 2 & 30 de caractere.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificarePretProdus(double pret) {
        boolean stareObiect = true;
        if(pret < 0.05 || pret > 9999.99) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Prețul asociat produsului nu respectă cerințele: trebuie să aibă valori cuprinse între 0.05 și 9999.99.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareUnitateMasuraProdus(String unitateMasura) {
        boolean stareObiect = true;
        if(unitateMasura == null || unitateMasura.equals(" ")) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Nu ați selectat o unitate de măsură.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareProducatorProdus(String producator) {
        boolean stareObiect = true;
        if(producator == null || producator.equals(" ")) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Nu ați selectat un producător.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareCategorieProdus(String categorie) {
        boolean stareObiect = true;
        if(categorie == null || categorie.equals(" ")) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Nu ați selectat o categorie.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareSubcategorieProdus(String subcategorie) {
        boolean stareObiect = true;
        if(subcategorie == null || subcategorie.equals(" ")) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Nu ați selectat o subcategorie.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareListaIngredienteProdus(List ingrediente) {
        boolean stareObiect = true;
        if(ingrediente.isEmpty()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Nu ați selectat ingredientele necesare sau există o eroare de sistem la preluarea acestora.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private void verficareSimultanaCampuri(int continutProdus, double pretProdus, String denumireProdus,
                                              String linkPozaProdus, String ingredienteProdus, String etichetaProdus,
                                              String unitateMasuraProdus, String producatorProdus,
                                              String categorieProdus, String subcategorieProdus,
                                           List<String> listaIngredienteProdus) throws Exception {
        boolean stare = true;

        stare = verificareContinutProdus(continutProdus);
        if (!stare) throw new Exception();

        stare = verificarePretProdus(pretProdus);
        if (!stare) throw new Exception();

        stare = verificareDenumireProdus(denumireProdus);
        if (!stare) throw new Exception();

        stare = verificareLinkPozaProdus(linkPozaProdus);
        if (!stare) throw new Exception();

        stare = verificareIngredienteProdus(ingredienteProdus);
        if (!stare) throw new Exception();

        stare = verificareEtichetaProdus(etichetaProdus);
        if (!stare) throw new Exception();

        stare = verificareUnitateMasuraProdus(unitateMasuraProdus);
        if (!stare) throw new Exception();

        stare = verificareProducatorProdus(producatorProdus);
        if (!stare) throw new Exception();

        stare = verificareCategorieProdus(categorieProdus);
        if (!stare) throw new Exception();

        stare = verificareSubcategorieProdus(subcategorieProdus);
        if (!stare) throw new Exception();

        stare = verificareListaIngredienteProdus(listaIngredienteProdus);
        if (!stare) throw new Exception();
    }


    public void initializareUnitatiMasuraCamp(ArrayList<String> unitatiMasura) {
        if(getUnitatiMasuraCombo() != null) {
            for(String unitateMasura : unitatiMasura) {
                getUnitatiMasuraCombo().addItem(unitateMasura);
            }
        }
    }

    public void initializareProducatori(ArrayList<String> producatori) {
        if(getUnitatiMasuraCombo() != null) {
            for(String producator : producatori) {
                getProducatoriCombo().addItem(producator);
            }
        }
    }

    public void initializareCategorii(ArrayList<String> categorii) {
        if(getCategoriiProduseCombo() != null) {
            for(String categorie : categorii) {
                getCategoriiProduseCombo().addItem(categorie);
            }
        }
    }

    public void initializareSubcategorii(ArrayList<String> subcategorii) {
        if(getSubcategoriiProduseCombo() != null) {
            for(String subcategorie : subcategorii) {
                getSubcategoriiProduseCombo().addItem(subcategorie);
            }
        }
    }

    public void golireCampuri() {
        getDenumireProdusCamp().setText("");
        getPretProdusCamp().setText("");
        getContinutProdusCamp().setText("");
        getLinkPozaProdusCamp().setText("");
        getIngredienteProdusCamp().setText("");
        getEtichetaProdusCamp().setText("");
        getUnitatiMasuraCombo().setSelectedIndex(0);
        getProducatoriCombo().setSelectedIndex(0);
        getCategoriiProduseCombo().setSelectedIndex(0);
        getSubcategoriiProduseCombo().setSelectedIndex(0);

        for(JCheckBox checkBox : getCheckBox_uri()) {
            checkBox.setSelected(false);
        }
    }


    public void actualizareRolUtilizator(String rol) {
        this.rolUtilizator = rol;
    }

    public void actualizareIdUtilizator(int id) {
        this.idUtilizator = id;
    }

    private void actualizareSelectieFereastraIngredienteProdus() {
        getItem_uriSelectate().clear();
        for (JCheckBox checkBox : getCheckBox_uri()) {
            if (checkBox.isSelected()) {
                getItem_uriSelectate().add(checkBox.getText());
            }
        }
        getIngredienteProdusCamp().setText(String.join(", ", getItem_uriSelectate()));
    }

    public List<String> obtinereItem_uriSelectate() {
        return new ArrayList<>(getItem_uriSelectate());
    }

    public void initializarePopupIngrediente(ArrayList<String> ingrediente) {
        for (String item : ingrediente) {
            JCheckBox checkBox = new JCheckBox(item);
            checkBox.addActionListener(e -> actualizareSelectieFereastraIngredienteProdus());
            getCheckBox_uri().add(checkBox);
            getFereastraIngredienteProdus().add(checkBox);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getMeniuTematicaBtn()) {
                interfataBtnActiuni.cardTematica(true);
            } else if(selectat == getTabelIngredienteBtn()) {
                getPopup().show(this, 0, getHeight());
            } else if(selectat == getLinkPozaProdusBtn()) {
                JFileChooser fileChooser = new JFileChooser();
                try {
                    File resourcesFolder = new File(proiectRoot, "src/main/resources/Imagini_Produse/");
                    if (resourcesFolder.exists()) {
                        fileChooser.setCurrentDirectory(resourcesFolder);
                    } else {
                        resourcesFolder = new File(proiectRoot, "resources/Imagini_Produse/");
                        if (resourcesFolder.exists()) {
                            fileChooser.setCurrentDirectory(resourcesFolder);
                        } else {
                            fileChooser.setCurrentDirectory(proiectRoot);
                        }
                    }
                } catch (Exception ex) {
                    fileChooser.setCurrentDirectory(proiectRoot);
                    ex.printStackTrace();
                }

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Fișiere imagine", "png");
                fileChooser.setFileFilter(filter);

                int rezultat = fileChooser.showOpenDialog(this);
                if (rezultat == JFileChooser.APPROVE_OPTION) {
                    File fisierSelectat = fileChooser.getSelectedFile();
                    String caleAbsoluta = fisierSelectat.getAbsolutePath();

                    int indexImagini = caleAbsoluta.indexOf("Imagini_Produse");

                    if (indexImagini != -1) {
                        String caleRelativa = caleAbsoluta.substring(indexImagini);
                        caleRelativa = "/" + caleRelativa.replace("\\", "/");

                        getLinkPozaProdusCamp().setText(caleRelativa);
                    } else {
                        Path pathAbsolut = Paths.get(caleAbsoluta);
                        Path pathProiect = Paths.get(proiectRoot.getAbsolutePath());
                        Path caleRelativa = pathProiect.relativize(pathAbsolut);

                        String caleFisier = caleRelativa.toString().replace("\\", "/");
                        int indexResources = caleFisier.indexOf("resources/");
                        if (indexResources != -1) {
                            caleFisier = caleFisier.substring(indexResources + 10);
                        }

                        getLinkPozaProdusCamp().setText("/" + caleFisier);
                    }
                }
            } else if(selectat == getAdaugareBtn()) {
                String denumireProdus = getDenumireProdusCamp().getText().trim(),
                        linkPozaProdus = getLinkPozaProdusCamp().getText().trim(),
                        ingredienteProdus = getIngredienteProdusCamp().getText().trim(),
                        etichetaProdus = getEtichetaProdusCamp().getText().trim(),
                        unitateMasuraProdus = "",
                        producatorProdus = "",
                        categorieProdus = "",
                        subcategorieProdus = "";
                int continutProdus = 0;

                try {
                    subcategorieProdus = Objects.requireNonNull(getSubcategoriiProduseCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați subcategoria produsului.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    categorieProdus = Objects.requireNonNull(getCategoriiProduseCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați categoria produsului.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    producatorProdus = Objects.requireNonNull(getProducatoriCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați producatorul bunului alimentar.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    unitateMasuraProdus = Objects.requireNonNull(getUnitatiMasuraCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați unitatea de masură a produsului.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    continutProdus = Integer.parseInt(getContinutProdusCamp().getText().trim());
                } catch(NumberFormatException eroare) {
                    JOptionPane.showMessageDialog(null, "Trebuie să introduceți un număr pentru conținut.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }

                double pretProdus = 0.0;

                try {
                    pretProdus = Double.parseDouble(getPretProdusCamp().getText().trim());
                } catch (NumberFormatException eroare) {
                    JOptionPane.showMessageDialog(null, "Trebuie să introduceți un număr cu zecimale pentru preț.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }

                List<String> listaIngredienteProdus;

                try {
                    listaIngredienteProdus = obtinereItem_uriSelectate();
                    verficareSimultanaCampuri(continutProdus, pretProdus, denumireProdus, linkPozaProdus, ingredienteProdus, etichetaProdus,
                            unitateMasuraProdus,producatorProdus, categorieProdus, subcategorieProdus, listaIngredienteProdus);
                    ProdusPreluat produsPreluat = new ProdusPreluat(denumireProdus, linkPozaProdus, etichetaProdus, continutProdus, pretProdus,
                            listaIngredienteProdus, unitateMasuraProdus, producatorProdus, categorieProdus, subcategorieProdus);
                    interfataFrontEnd.trimitereProdusPreluat(produsPreluat, 0);
                } catch (Exception eroare) {
                    JOptionPane.showMessageDialog(null, "Există o eroare la adăugarea produsului.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else if(selectat == getStergereBtn()) {
                String etichetaProdus = getEtichetaProdusCamp().getText().trim();
                boolean stare = true;

                try {
                    stare = verificareEtichetaProdus(etichetaProdus);
                    if (!stare) throw new Exception();

                    interfataFrontEnd.stergereProdusPreluat(etichetaProdus);
                } catch (Exception eroare) {
                    System.out.println(eroare.getMessage());
                }
            } else if(selectat == getModificareBtn()) {
                String denumireProdus = getDenumireProdusCamp().getText().trim(),
                        linkPozaProdus = getLinkPozaProdusCamp().getText().trim(),
                        ingredienteProdus = getIngredienteProdusCamp().getText().trim(),
                        etichetaProdus = getEtichetaProdusCamp().getText().trim(),
                        unitateMasuraProdus = "",
                        producatorProdus = "",
                        categorieProdus = "",
                        subcategorieProdus = "";
                int continutProdus = 0;

                try {
                    subcategorieProdus = Objects.requireNonNull(getSubcategoriiProduseCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați subcategoria produsului.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    categorieProdus = Objects.requireNonNull(getCategoriiProduseCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați categoria produsului.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    producatorProdus = Objects.requireNonNull(getProducatoriCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați producatorul bunului alimentar.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    unitateMasuraProdus = Objects.requireNonNull(getUnitatiMasuraCombo().getSelectedItem()).toString();
                } catch (NullPointerException eroare ) {
                    JOptionPane.showMessageDialog(null, "Trebuie să selectați unitatea de masură a produsului.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    continutProdus = Integer.parseInt(getContinutProdusCamp().getText().trim());
                } catch(NumberFormatException eroare) {
                    JOptionPane.showMessageDialog(null, "Trebuie să introduceți un număr pentru conținut.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }

                double pretProdus = 0.0;

                try {
                    pretProdus = Double.parseDouble(getPretProdusCamp().getText().trim());
                } catch (NumberFormatException eroare) {
                    JOptionPane.showMessageDialog(null, "Trebuie să introduceți un număr cu zecimale pentru preț.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }

                List<String> listaIngredienteProdus;

                try {
                    listaIngredienteProdus = obtinereItem_uriSelectate();
                    verficareSimultanaCampuri(continutProdus, pretProdus, denumireProdus, linkPozaProdus, ingredienteProdus, etichetaProdus,
                            unitateMasuraProdus,producatorProdus, categorieProdus, subcategorieProdus, listaIngredienteProdus);
                    ProdusPreluat produsPreluat = new ProdusPreluat(denumireProdus, linkPozaProdus, etichetaProdus, continutProdus, pretProdus,
                            listaIngredienteProdus, unitateMasuraProdus, producatorProdus, categorieProdus, subcategorieProdus);
                    interfataFrontEnd.trimitereProdusPreluat(produsPreluat, 1);
                } catch (Exception eroare) {
                    System.out.println(eroare.getMessage());
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

        gc.insets = new Insets(3, 5, 3, 5);

        gc.ipadx = 0;
        gc.ipady = 0;


        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.1;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        add(getDenumireProduseLabel(), gc);

        gc.gridy = 1;
        add(getLinkPozaLabel(), gc);

        gc.gridy = 2;
        add(getContinutProdusLabel(), gc);

        gc.gridy = 3;
        add(getPretProdusLabel(), gc);

        gc.gridy = 4;
        add(getEtichetaProdusLabel(), gc);

        gc.gridy = 5;
        add(getIngredienteLabel(), gc);

        gc.gridy = 6;
        add(getUnitateMasuraProdusLabel(), gc);

        gc.gridy = 7;
        add(getProducatorLabel(), gc);

        gc.gridy = 8;
        add(getCategorieProdusLabel(), gc);

        gc.gridy = 9;
        add(getSubcategorieProdusLabel(), gc);


        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.9;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 2;
        add(getDenumireProdusCamp(), gc);

        gc.gridy = 1;
        add(getLinkPozaProdusCamp(), gc);

        gc.gridy = 2;
        gc.gridwidth = 1;
        add((getContinutProdusCamp()), gc);

        gc.gridy = 3;
        add(getPretProdusCamp(), gc);

        gc.gridy = 4;
        add(getEtichetaProdusCamp(), gc);

        gc.gridy = 5;
        gc.gridwidth = 2;
        add(getIngredienteProdusCamp(), gc);

        gc.gridy = 6;
        gc.gridwidth = 2;
        add(getUnitatiMasuraCombo(), gc);

        gc.gridy = 7;
        add(getProducatoriCombo(), gc);

        gc.gridy = 8;
        add(getCategoriiProduseCombo(), gc);

        gc.gridy = 9;
        add(getSubcategoriiProduseCombo(), gc);


        gc.gridy = 10;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(8, 3, 3, 3);
        add(getAdaugareBtn(), gc);

        gc.gridx = 1;
        add(getModificareBtn(), gc);

        gc.gridx = 2;
        add(getStergereBtn(), gc);

        gc.gridy = 11;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(8, 3, 3, 3);
        add(getMeniuTematicaBtn(), gc);

        gc.gridx = 1;
        add(getLinkPozaProdusBtn(), gc);

        gc.gridx = 2;
        add(getTabelIngredienteBtn(), gc);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
