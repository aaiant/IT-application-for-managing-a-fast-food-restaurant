package org.example.Model;

import java.sql.*;
import lombok.*;
import org.example.DBConfig;
import org.example.Interfete.I_BackEnd;
import org.example.Vizualizare.Carduri.Est.Culori;
import org.example.Vizualizare.Carduri.Est.ProdusPreluat;
import org.example.Vizualizare.Carduri.Vest.Inregistrare.Utilizator_Inregistrare;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.*;
import java.time.*;
import java.time.format.*;
import java.sql.Date;

@Getter @Setter
public class PostgreSQL {
    private String catalog = null, modelSchema = null, modelNumeColoana = null;
    private EmailBot emailBot = null;
    private Argon2_Alg argon2Alg = null;
    private Connection conexiune = null;
    private DatabaseMetaData bdMetaData = null;
    private Statement interogare = null;
    private PreparedStatement interogareAvansata = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsMetaData = null;
    private I_BackEnd interfata;
    private ArrayList<Nuanta> nuante = null;
    private ArrayList<String> coloane;
    private I_BackEnd interfataBackEnd;
    private Produse produse = null;
    private int nrProduse = 0;
    private  String
            interogareSQLPrenume = interogareSQL("prenume", "denumire", "Prenume", "utilizator_prenume", "prenume_id", "denumire", "TRUE"),
            interogareSQLNumeFamilie = interogareSQL("nume_familie", "denumire", "Nume Familie", "utilizator_nume_familie", "nume_familie_id", "denumire", "TRUE"),
            interogareSQLNrTel = interogareSQL("numere_telefon", "denumire", "NrTel", "contact_telefon", "numar_telefon_id", "denumire", "TRUE"),
            interogareSQLStradaId = "SELECT strazi.id AS \"id\" "
                    + "FROM strazi WHERE strazi.denumire = ? LIMIT 1",
            interogareSQLLocalitateId = "SELECT localitati.id AS \"id\" FROM localitati WHERE localitati.denumire = ? LIMIT 1",
            interogareSQLAdreseEmail = interogareSQL("adrese_email", "denumire", "Adresa_Email", "contact_email", "adresa_email_id", "denumire", "TRUE"),
            interogareSQNumeUtilizator = interogareSQL("nume", "denumire", "Denumire", "nume_utilizatori", "nume_id", "denumire", "TRUE"),
            interogareSQLNumeUtilizatorFalse = interogareSQL("nume", "id", "id", "nume_utilizatori", "nume_id", "denumire", "FALSE"),
            interogareSQLAdresaEmailFalse = interogareSQL("adrese_email", "id", "id", "contact_email", "adresa_email_id", "denumire", "FALSE"),
            interogareSQLNrTelFalse = interogareSQL("numere_telefon", "id", "id", "contact_telefon", "numar_telefon_id", "denumire", "FALSE"),
            interogareSQLNumeFamilieFalse = interogareSQL("nume_familie", "id", "id", "utilizator_nume_familie", "nume_familie_id", "denumire", "FALSE"),
            interogareSQLNumeFamilieTrue = interogareSQL("nume_familie", "id", "id", "utilizator_nume_familie", "nume_familie_id", "denumire", "TRUE"),
            interogareSQLPrenumeFalse = interogareSQL("prenume", "id", "id", "utilizator_prenume", "prenume_id", "denumire", "FALSE"),
            interogareSQLPrenumeTrue = interogareSQL("prenume", "id", "id", "utilizator_prenume", "prenume_id", "denumire", "TRUE");

    private void inchidereResursa(AutoCloseable resursa) {
        try {
            if (resursa != null)
                resursa.close();
        } catch(Exception eroare) {
            eroare.printStackTrace();
        }
    }

    private Connection deschidereConexiune() throws SQLException {
        return DriverManager.getConnection(DBConfig.DB_URL, DBConfig.DB_UTILIZATOR, DBConfig.DB_PAROLA);
    }

    private void inchidereResurse() {
        inchidereResursa(getRs());
        inchidereResursa(getInterogare());
        inchidereResursa(getInterogareAvansata());
        inchidereResursa(getConexiune());
    }

    //  Constructor
    public PostgreSQL(I_BackEnd interfataBackEnd) {
        this.interfataBackEnd = interfataBackEnd;
        try {
            setConexiune(deschidereConexiune());
            setBdMetaData(getConexiune().getMetaData());
            init();
        } catch(SQLException error) {
            error.printStackTrace();
        } finally {
            //inchideConexiunea(getRs(), getConexiune());
        }
    }

    //  Metode
    public void init() {
        prelucrareDateAspect();
        prelucrareDateIntrebari();
        initializareRoluriUtilizator();
        infoTari();
        argon2Alg = new Argon2_Alg();
        emailBot = new EmailBot();
    }

    private void prelucrareDateAspect() {
        try {
            setNuante(new ArrayList<>());
            setColoane(new ArrayList<>());
            setInterogare(getConexiune().createStatement());
            setRs(interogare.executeQuery("SELECT N1.denumire AS \"Nuante\", N1.cod_hex AS \"Hex Fundal\", N2.cod_hex AS \"Hex Text\" " +
                    "FROM nuante AS N1 INNER JOIN nuante AS N2 ON N1.culoaretext_id = N2.id WHERE N1.denumire <> 'Negru' ORDER BY N1.denumire"
            ));
            while(getRs().next()) {
                Nuanta nuanta = new Nuanta(getRs().getString("Nuante"),
                        getRs().getString("Hex Fundal"), getRs().getString("Hex Text"));
                getNuante().add(nuanta);
            }

            setRsMetaData(getRs().getMetaData());
            int nrCol = getRsMetaData().getColumnCount();
            for(int i = 1; i <= nrCol; i++)
                getColoane().add(getRsMetaData().getColumnName(i));
            NuanteCulori nuanteCulori = new NuanteCulori(this, getNuante(), getColoane());
            if (interfataBackEnd != null) {
                interfataBackEnd.infoNuante(nuanteCulori);
            }

        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogare());
        }
    }

    private void prelucrareDateIntrebari() {
        try {
            ArrayList<Intrebare> setIntrebari = new ArrayList<>();
            setColoane(new ArrayList<>());
            setInterogare(getConexiune().createStatement());
            setRs(interogare.executeQuery("SELECT intrebari.cod AS \"Cod\", intrebari.continut AS \"Continut\" FROM intrebari ORDER BY intrebari.cod"));
            while(getRs().next()) {
                Intrebare intrebare = new Intrebare(getRs().getString("Cod"),
                        getRs().getString("Continut"));
                setIntrebari.add(intrebare);
            }

            setRsMetaData(getRs().getMetaData());
            int nrCol = getRsMetaData().getColumnCount();
            for(int i = 1; i <= nrCol; i++)
                getColoane().add(getRsMetaData().getColumnName(i));
            Intrebari setCompletIntrebari = new Intrebari(this, setIntrebari, getColoane());
            if (interfataBackEnd != null) {
                interfataBackEnd.infoIntrebari(setCompletIntrebari);
            }

        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogare());
        }
    }

    public void prelucrareDateRaspunsuri(String codIntrebare) {
        try {
            ArrayList<Raspuns> setRaspusnuri = new ArrayList<>();
            setColoane(new ArrayList<>());
            String interogareSQL = "SELECT raspunsuri.cod AS \"Cod\", raspunsuri.continut AS \"Continut\" " +
                    "FROM (intrebari INNER JOIN intrebari_raspunsuri ON intrebari_raspunsuri.intrebare_id = intrebari.id) " +
                    "INNER JOIN raspunsuri ON intrebari_raspunsuri.raspuns_id = raspunsuri.id WHERE intrebari.cod = ? ORDER BY intrebari.cod";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, codIntrebare);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                Raspuns raspuns = new Raspuns(getRs().getString("Cod"),
                        getRs().getString("Continut"));
                setRaspusnuri.add(raspuns);
            }

            Raspunsuri setCompletRaspunsuri = new Raspunsuri(this, setRaspusnuri);
            if(getRs().first()) {
                if (interfataBackEnd != null) {
                    interfataBackEnd.infoRaspunsuri(setCompletRaspunsuri, codIntrebare);
                    interfataBackEnd.stareRaspunsuri(true);
                }
            } else {
                setCompletRaspunsuri = new Raspunsuri(this, new ArrayList<>());
                interfataBackEnd.infoRaspunsuri(setCompletRaspunsuri, codIntrebare);
                interfataBackEnd.stareRaspunsuri(false);
            }


        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    //  VERIFICARI LOGARE

    private boolean verificareDate(String interogareSQL, String atribut) {
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setString(1, atribut);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                return false;
            }
        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return true;
    }

    private boolean verificareDateIntString(String interogareSQL, int atribut1, String atribut2) {
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, atribut1);
            getInterogareAvansata().setString(2, atribut2);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                return false;
            }
        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return true;
    }

    private int verificareDateIntInt(String interogareSQL, int atribut1, int atribut2) {
        int rezultat = 0;

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, atribut1);
            getInterogareAvansata().setInt(2, atribut2);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                rezultat = getRs().getInt("id");
            }
        } catch(SQLException eroare) {
            return rezultat;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return rezultat;
    }

    private int verificareDateIntString2(String interogareSQL, int atribut1, String atribut2) {
        int rezultat = 0;

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, atribut1);
            getInterogareAvansata().setString(2, atribut2);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                rezultat = getRs().getInt("id");
            }
        } catch(SQLException eroare) {
            return rezultat;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return rezultat;
    }

    private boolean verificareDateInt(String interogareSQL, int atribut) {
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, atribut);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                return false;
            }
        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return true;
    }

    private int verificareDateFalse(String interogareSQL, String atributCautat) {
        int rezultat = 0;

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setString(1, atributCautat);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                rezultat = getRs().getInt("id");
            }
        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return rezultat;
    }

    private int verificareDateFalseInt(String interogareSQL, int atributCautat) {
        int rezultat = 0;

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, atributCautat);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                rezultat = getRs().getInt("id");
            }
        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return rezultat;
    }

    private String interogareSQL(String tabel1, String atribut1, String prescurtareAtribut1, String tabel2, String atribut3, String atribut4, String valBoolean) {
        return String.format("SELECT %s.%s AS \"%s\", %s.activ AS \"Activ\" FROM %s INNER JOIN %s ON %s.%s = %s.id WHERE %s.%s = ? AND %s.activ = %s LIMIT 1",
                tabel1, atribut1, prescurtareAtribut1, tabel2, tabel1, tabel2, tabel2, atribut3, tabel1, tabel1, atribut4, tabel2, valBoolean);
    }


    //  METODE PENTRU GASIREA ATRIBUTELOR

    public String gasireNume(int id) {
       String interogareSQL = "SELECT denumire FROM nume WHERE id = ?", nume = "";
       try {
           setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
           getInterogareAvansata().setInt(1, id);
           setRs(getInterogareAvansata().executeQuery());
           if (getRs().next()) {
               nume = getRs().getString("denumire");
           }
       } catch (SQLException eroare) {
           interfataBackEnd.infoStadiu("Numele de utilizator nu există.");
       } finally {
           inchidereResursa(getInterogareAvansata());
           inchidereResursa(getRs());
       }
       return nume;
    }

    public String gasireNume_IdUtilizator(int idUtilizator) {
        String interogareSQL = "SELECT nume.denumire FROM (nume INNER JOIN nume_utilizatori ON nume_utilizatori.nume_id = nume.id) " +
                "INNER JOIN utilizatori ON nume_utilizatori.utilizator_id = utilizatori.id WHERE utilizatori.id = ?", nume = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, idUtilizator);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                nume = getRs().getString("denumire");
            }
            return nume;
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Numele de utilizator nu există.");
            return nume;
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
    }

    public String gasireAdresaEmail(int id) {
        String interogareSQL = "SELECT denumire FROM adrese_email WHERE id = ?", adresa_email = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                adresa_email = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Adresa de E-mail nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return adresa_email;
    }

    public String gasireNrTel(int id) {
        String interogareSQL = "SELECT denumire FROM numere_telefon WHERE id = ?", numar_telefon = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                numar_telefon = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Numărul de telefon nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return numar_telefon;
    }

    public String gasireNumeFamilie(int id) {
        String interogareSQL = "SELECT denumire FROM nume_familie WHERE id = ?", nume_familie = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                nume_familie = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Numele de familie nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return nume_familie;
    }

    public String gasirePrenume(int id) {
        String interogareSQL = "SELECT denumire FROM prenume WHERE id = ?", prenume = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                prenume = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Prenumele nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return prenume;
    }

    public int gasireNrLocuinta(int id) {
        String interogareSQL = "SELECT numar FROM locuinte WHERE id = ?";
        int nrLocuinta = 0;
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                nrLocuinta = getRs().getInt("numar");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Locuința nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return nrLocuinta;
    }

    public String gasireStrada(int id) {
        String interogareSQL = "SELECT denumire FROM strazi WHERE id = ?", strada = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                strada = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Strada nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return strada;
    }

    public String gasireLocalitate(int id) {
        String interogareSQL = "SELECT denumire FROM localitati WHERE id = ?", localitate = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                localitate = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Localitatea nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return localitate;
    }

    public String gasireJudet(int id) {
        String interogareSQL = "SELECT denumire FROM judete WHERE id = ?", judet = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, id);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next()) {
                judet = getRs().getString("denumire");
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Județul nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return judet;
    }

    //  METODE PENTRU VALIDARI & ID

    private boolean stare_mesaj(boolean stare, String mesaj) {
        interfataBackEnd.infoStadiu(mesaj);
        return stare;
    }

    public boolean verificareNumeFamilie_Prenume(String numeFamilie, String prenume) {
        if(!verificareDate(getInterogareSQLNumeFamilie(), numeFamilie)
                && !verificareDate(getInterogareSQLPrenume(), prenume)) {
            interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar există deja persoana %s %s.", numeFamilie,
                    prenume));
            return false;
        }
        return true;
    }

    private boolean validare(String interogareSQL, String atribut, String mesajEroare) {
        if(!verificareDate(interogareSQL, atribut) ) {
            return stare_mesaj(false, mesajEroare);
        };

        return true;
    }

    private int preluareIdRol() {
        int idRol = 0;
        try {
            String interogareSQL1 = "SELECT roluri.id AS id FROM roluri WHERE roluri.denumire = ? LIMIT 1";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, "Client");
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                idRol = getRs().getInt("id");
            }
            return idRol;
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Ne pare rău, dar rolul utilizatorului nu a putut fi asociat.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idRol;
    }

    public void trimitereEmail(UtilizatorComplet utilizator) {
        EmailBot.verificaSiTrimiteEmail(DBConfig.DB_ADRESA_EXPEDITOR, DBConfig.DB_PAROLA_EXPEDITOR, utilizator.getAdresaEmail(),
                String.format("Întregistrare %s", utilizator.getNumeUtilizator()),
                formatareMesajEmail(utilizator.getNumeUtilizator(), utilizator.getNumeFamilie(), utilizator.getPrenume(),
                        utilizator.getNrTel(), utilizator.getAdresaEmail(), utilizator.getTara(), utilizator.getJudet(),
                        utilizator.getLocalitate(), utilizator.getStrada(), utilizator.getNrLocuinta()));
    }

    //  SPECIFIC PT INREGISTRARE

    public int preluareIdLocuinta(int nrLocuinta) {
        String
                interogareSQL0 = "INSERT INTO locuinte (numar) VALUES (?) RETURNING id",
                interogareSQL2 = "SELECT id FROM locuinte WHERE numar = ?";

        int idNrLocuinta = verificareDateFalseInt(interogareSQL2, nrLocuinta);
        try {
            if (idNrLocuinta == 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setInt(1, nrLocuinta);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idNrLocuinta = getRs().getInt("id");
                }
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la inserarea locuinței în baza de date.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idNrLocuinta;
    }

    public int preluareIdStrada(String strada) {
        String
                interogareSQL0 = "INSERT INTO strazi (denumire) VALUES (?) RETURNING id",
                interogareSQL2 = "SELECT id FROM strazi WHERE denumire = ?";

        int idNrLocuinta = verificareDateFalse(interogareSQL2, strada);
        try {
            if (idNrLocuinta == 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, strada);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idNrLocuinta = getRs().getInt("id");
                }
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la inserarea străzii în baza de date.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idNrLocuinta;
    }

    public int preluareIdLocaliatate(String localitate) {
        String interogareSQL2 = "SELECT id FROM localitati WHERE denumire = ?";
        int idLocalitate = verificareDateFalse(interogareSQL2, localitate);
        return idLocalitate;
    }

    public int preluareIdJudet(String judet) {
        String interogareSQL2 = "SELECT id FROM judete WHERE denumire = ?";
        int idJudet = verificareDateFalse(interogareSQL2, judet);
        return idJudet;
    }
    //  --------------------------------

    public int preluareIdLocuinta(int idUtilizator, int nrLocuinta) {
        String
                interogareSQL0 = "INSERT INTO locuinte (numar) VALUES (?) RETURNING id",
                interogareSQL1 = "UPDATE utilizatori SET locuinta_id = ? WHERE id = ?",
                interogareSQL2 = "SELECT id FROM locuinte WHERE numar = ?";

        int idNrLocuinta = verificareDateFalseInt(interogareSQL2, nrLocuinta);
        try {
            if (idNrLocuinta != 0 && idUtilizator != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setInt(1, idNrLocuinta);
                getInterogareAvansata().setInt(2, idUtilizator);
                getInterogareAvansata().executeUpdate();
            } else if (idNrLocuinta == 0 && idUtilizator != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setInt(1, nrLocuinta);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idNrLocuinta = getRs().getInt("id");
                }
                interogareAutomata2(interogareSQL1, 1, idNrLocuinta, 2, idUtilizator);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la nivelul locuințelor.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idNrLocuinta;
    }

    public int preluareIdStrada(int idUtilizator, String strada) {
        String
                interogareSQL0 = "INSERT INTO strazi (denumire) VALUES (?) RETURNING id",
                interogareSQL1 = "UPDATE utilizatori SET strada_id = ? WHERE id = ?",
                interogareSQL2 = "SELECT id FROM strazi WHERE denumire = ?";

        int idStrada = verificareDateFalse(interogareSQL2, strada);
        try {
            if (idStrada != 0 && idUtilizator != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setInt(1, idStrada);
                getInterogareAvansata().setInt(2, idUtilizator);
                getInterogareAvansata().executeUpdate();
            } else if (idStrada == 0 && idUtilizator != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, strada);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idStrada = getRs().getInt("id");
                }
                interogareAutomata2(interogareSQL1, 1, idStrada, 2, idUtilizator);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la nivelul străzilor.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idStrada;
    }

    public int preluareIdLocalitate(int idUtilizator, String localitate) {
        String
                interogareSQL1 = "UPDATE utilizatori SET localitate_id = ? WHERE id = ?",
                interogareSQL2 = "SELECT id FROM localitati WHERE denumire = ?";

        int idLocalitate = verificareDateFalse(interogareSQL2, localitate);
        try {
            if (idLocalitate != 0 && idUtilizator != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setInt(1, idLocalitate);
                getInterogareAvansata().setInt(2, idUtilizator);
                getInterogareAvansata().executeUpdate();
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la nivelul localităților.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idLocalitate;
    }

    private int preluareIdUtilizator(String utilizatorParola, int idRolAsociat, int idLocuinta, int idStrada, int idLocalitate) {
        int idUtilizatorInserat = 0;
        try {
            String interogareSQL2 = "INSERT INTO utilizatori (parola, rol_id, locuinta_id, strada_id, localitate_id) VALUES(?, ?, ?, ?, ?) RETURNING id";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, argon2Alg.hashParola(utilizatorParola));
            getInterogareAvansata().setInt(2, idRolAsociat);
            getInterogareAvansata().setInt(3, idLocuinta);
            getInterogareAvansata().setInt(4, idStrada);
            getInterogareAvansata().setInt(5, idLocalitate);
            setRs(getInterogareAvansata().executeQuery());
            if (getRs().next())
                idUtilizatorInserat = getRs().getInt("id");
            return idUtilizatorInserat;
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Utilizatorul nu a putut fi inserat în baza de date.");
        } finally {
        inchidereResursa(getInterogareAvansata());
        inchidereResursa(getRs());
        }
        return idUtilizatorInserat;
    }

    public String prelucrareParolaUtilizator(int idUtilizator, String parola) {
        String parolaHash = argon2Alg.hashParola(parola);
        try {
            String interogareSQL2 = "UPDATE utilizatori SET parola = ? WHERE id = ?";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, parolaHash);
            getInterogareAvansata().setInt(2, idUtilizator);
            getInterogareAvansata().executeUpdate();
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Parola nu a putut fi actualizată.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return parolaHash;
    }

    public int preluareIdNume(int idUtilizatorInserat, String numeUtilizator) {
        int idNume = verificareDateFalse(getInterogareSQLNumeUtilizatorFalse(), numeUtilizator);
        String
                interogareSQL0 = "INSERT INTO nume (denumire) VALUES (?) RETURNING id",
                interogareSQL1 = "INSERT INTO nume_utilizatori (utilizator_id, nume_id, activ) VALUES (? ,?, ?)",
                interogareSQL2 = "UPDATE nume_utilizatori SET utilizator_id = ?, activ = ? WHERE nume_id = ?",
                interogareSQL3 = "UPDATE nume_utilizatori SET activ = ? WHERE utilizator_id = ?";
        try {
            if (idNume != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL2, 1, idUtilizatorInserat, 3, idNume, 2, true);
            } else if (idNume == 0 && idUtilizatorInserat != 0 && verificareDate(getInterogareSQNumeUtilizator(), numeUtilizator)) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, numeUtilizator);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idNume = getRs().getInt("id");
                }

                interogareAutomata1(interogareSQL1, 1, idUtilizatorInserat, 2, idNume, 3, true);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Numele de utilizator este deja folosit.");

        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idNume;
    }

    public int preluareIdAdresaEmail(int idUtilizatorInserat, String adresaEmail) {
        int idAdresaEmail = verificareDateFalse(getInterogareSQLAdresaEmailFalse(), adresaEmail);
        String
                interogareSQL1 = "INSERT INTO adrese_email (denumire) VALUES (?) RETURNING id",
                interogareSQL2 = "INSERT INTO contact_email (utilizator_id, adresa_email_id, activ) VALUES (? ,?, ?)",
                interogareSQL3 = "UPDATE contact_email SET utilizator_id = ?, activ = ? WHERE adresa_email_id = ?",
                interogareSQL4 = "UPDATE contact_email SET activ = ? WHERE utilizator_id = ?";
        try {
            if (idAdresaEmail != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL4, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL3, 1, idUtilizatorInserat, 3, idAdresaEmail, 2, true);
            } else if (idAdresaEmail == 0 && idUtilizatorInserat != 0 && verificareDate(getInterogareSQLAdreseEmail(), adresaEmail)) {
                interogareAutomata3(interogareSQL4, 2, idUtilizatorInserat, 1, false);
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setString(1, adresaEmail);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idAdresaEmail = getRs().getInt("id");
                }

                interogareAutomata1(interogareSQL2, 1, idUtilizatorInserat, 2, idAdresaEmail, 3, true);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Adresa de E-mail este deja folosită.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idAdresaEmail;
    }

    public int preluareIdNrTel(int idUtilizatorInserat, String nrTel) {
        int idNrTel = verificareDateFalse(getInterogareSQLNrTelFalse(), nrTel);
        String
                interogareSQL0 = "INSERT INTO numere_telefon (denumire) VALUES (?) RETURNING id",
                interogareSQL1 = "INSERT INTO contact_telefon (utilizator_id, numar_telefon_id, activ) VALUES (? ,?, ?)",
                interogareSQL2 = "UPDATE contact_telefon SET utilizator_id = ?, activ = ? WHERE numar_telefon_id = ?",
                interogareSQL3 = "UPDATE contact_telefon SET activ = ? WHERE utilizator_id = ?";
        try {
            if (idNrTel != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL2, 1, idUtilizatorInserat, 3, idNrTel, 2, true);
            } else if (idNrTel == 0 && idUtilizatorInserat != 0 && verificareDate(getInterogareSQLNrTel(), nrTel)) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, nrTel);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idNrTel = getRs().getInt("id");
                }
                interogareAutomata1(interogareSQL1, 1, idUtilizatorInserat, 2, idNrTel, 3, true);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Numărul de telefon este deja folosit.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idNrTel;
    }

    public int preluareIdNumeFamilie(int idUtilizatorInserat, String numeFamilie) {
        int rezNumeFamilieFalse = verificareDateFalse(getInterogareSQLNumeFamilieFalse(), numeFamilie);
        int rezNumeFamilieTrue = verificareDateFalse(getInterogareSQLNumeFamilieTrue(), numeFamilie);
        boolean stadiuNumeFamilie = verificareDate(getInterogareSQLNumeFamilie(), numeFamilie);
        int idNumeFamilie = 0;
        String
                interogareSQL0 = "INSERT INTO nume_familie (denumire) VALUES (?) RETURNING id",
                interogareSQL1 = "INSERT INTO utilizator_nume_familie (utilizator_id, nume_familie_id, activ) VALUES (? ,?, ?)",
                interogareSQL2 = "UPDATE utilizator_nume_familie SET utilizator_id = ?, activ = ? WHERE nume_familie_id = ?",
                interogareSQL3 = "UPDATE utilizator_nume_familie SET activ = ? WHERE utilizator_id = ?";
        try {
            if (rezNumeFamilieFalse != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL2, 1, idUtilizatorInserat, 3, rezNumeFamilieFalse, 2, true);
                idNumeFamilie = rezNumeFamilieFalse;
            } else if (rezNumeFamilieFalse == 0 && rezNumeFamilieTrue != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL1, 1, idUtilizatorInserat, 2, rezNumeFamilieTrue, 3, true);
                idNumeFamilie = rezNumeFamilieTrue;
            } else if (rezNumeFamilieFalse == 0 && idUtilizatorInserat != 0 && stadiuNumeFamilie) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, numeFamilie);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idNumeFamilie = getRs().getInt("id");
                }
                interogareAutomata1(interogareSQL1, 1, idUtilizatorInserat, 2, idNumeFamilie, 3, true);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la nivelul numelui de familie.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idNumeFamilie;
    }

    public int preluareIdPrenume(int idUtilizatorInserat, String prenume) {
        int rezPrenumeFalse = verificareDateFalse(getInterogareSQLPrenumeFalse(), prenume);
        int rezPrenumeTrue = verificareDateFalse(getInterogareSQLPrenumeTrue(), prenume);
        boolean stadiuPrenume = verificareDate(getInterogareSQLPrenume(), prenume);
        int idPrenume = 0;
        String
                interogareSQL15 = "INSERT INTO prenume (denumire) VALUES (?) RETURNING id",
                interogareSQL16 = "INSERT INTO utilizator_prenume (utilizator_id, prenume_id, activ) VALUES (? ,?, ?)",
                interogareSQL17 = "UPDATE utilizator_prenume SET utilizator_id = ?, activ = ? WHERE prenume_id = ?",
                interogareSQL3 = "UPDATE utilizator_prenume SET activ = ? WHERE utilizator_id = ?";
        try {
            if (rezPrenumeFalse != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL17, 1, idUtilizatorInserat, 3, rezPrenumeFalse, 2, true);
                idPrenume = rezPrenumeFalse;
            } else if (rezPrenumeFalse == 0 && rezPrenumeTrue != 0 && idUtilizatorInserat != 0) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                interogareAutomata1(interogareSQL16, 1, idUtilizatorInserat, 2, rezPrenumeTrue, 3, true);
                idPrenume = rezPrenumeTrue;
            } else if (rezPrenumeFalse == 0 && idUtilizatorInserat != 0 && stadiuPrenume) {
                interogareAutomata3(interogareSQL3, 2, idUtilizatorInserat, 1, false);
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL15));
                getInterogareAvansata().setString(1, prenume);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idPrenume = getRs().getInt("id");
                }
                interogareAutomata1(interogareSQL16, 1, idUtilizatorInserat, 2, idPrenume, 3, true);
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoIntegistrare(false, "Există o eroare la nivelul prenumelui.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idPrenume;
    }

    //  FINALUL SECTIUNII METODELOR PENTRU VALIDARI & ID

    //  INCEPUTUL ZONEI DE RECUPERARE CONT

    private int actualizareParola(int id, String parola) {
        String interogareSQL0 = "UPDATE utilizatori SET parola = ? WHERE id = ?",
        interogareSQL1 = "SELECT id FROM utilizatori WHERE id = ?";
        int idUtilizator = verificareDateFalseInt(interogareSQL1, id);
        try {
            if(idUtilizator != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, argon2Alg.hashParola(parola));
                getInterogareAvansata().setInt(2, idUtilizator);
                getInterogareAvansata().executeUpdate();
            }
            return idUtilizator;
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Utilizatorul nu există.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
        return idUtilizator;
    }

    private String formatareMesajEmail(String numeUtilizator, String numeFamilie, String prenume,
                                       String nrTel, String adresaEmailUtilizator, String tara, String judet,
                                       String localitate, String strada, int nrLocuinta) {
        return String.format("Bun venit la aplicația noastră de Fast-Food, %s %s!\nTocmai ce ați finalizat procesul de înregistrare! "
                + "Mai jos veți avea o confirmare cu datele despre contul dvs:\n"
                + "Nume de utilizator: %s\n"
                + "Nume de familie: %s\n"
                + "Prenume: %s\n"
                + "Număr de telefon: %s\n"
                + "Adresă de E-mail: %s\n"
                + "Adresă: %s, %s, %s, %s, %d\n",
                numeFamilie, prenume, numeUtilizator, numeFamilie, prenume, nrTel, adresaEmailUtilizator, tara, judet, localitate, strada, nrLocuinta);
    }

    private String generareParola() {
        SecureRandom aleatoriu = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for(int i = 0; i < 6; i++) {
            int cifra = aleatoriu.nextInt(10);
            sb.append(cifra);
        }
        return sb.toString();
    }

    private String formatareMesajRecuperareCont(String numeUtilizator, String parolaNoua, String numeFamilie, String prenume) {
        return String.format("Bună %s %s! Mai jos vei avea date de logare pentru cont:\n"
                + "Nume de utilizator: %s\n"
                + "Parolă nouă: %s\n"
                + "Vă recomandăm să schimbați parola de îndată cum vă conectați! Vă mulțumim pentru că ați apelat la serviciile noastre!",
                numeFamilie, prenume, numeUtilizator, parolaNoua);
    }

    public int recuperareUtilizator(String adresaEmail) {
        String interogareSQL0 = "SELECT utilizatori.id AS id, nume.denumire AS nume_utilizator, nume_familie.denumire AS nume_familie, prenume.denumire AS prenume " +
                "FROM (((((((utilizatori INNER JOIN contact_email ON contact_email.utilizator_id = utilizatori.id) " +
                "INNER JOIN adrese_email ON contact_email.adresa_email_id = adrese_email.id) " +
                "INNER JOIN nume_utilizatori ON nume_utilizatori.utilizator_id = utilizatori.id) " +
                "INNER JOIN nume ON nume_utilizatori.nume_id = nume.id) " +
                "INNER JOIN utilizator_nume_familie ON utilizator_nume_familie.utilizator_id = utilizatori.id) " +
                "INNER JOIN nume_familie ON utilizator_nume_familie.nume_familie_id = nume_familie.id) " +
                "INNER JOIN utilizator_prenume ON utilizator_prenume.utilizator_id = utilizatori.id) " +
                "INNER JOIN prenume ON utilizator_prenume.prenume_id = prenume.id " +
                "WHERE adrese_email.denumire = ?",
        interogareSQL1 = "SELECT utilizatori.id FROM (adrese_email INNER JOIN contact_email ON contact_email.adresa_email_id = " +
                "adrese_email.id) INNER JOIN utilizatori ON " +
                "contact_email.utilizator_id = utilizatori.id WHERE adrese_email.denumire = ?",
        numeUtilizator = null, numeFamilie = null, prenume = null, parola = generareParola();
        int idUtilizator = 0,  idAdresaUtilizator_AdresEmail = verificareDateFalse(interogareSQL1, adresaEmail), stadiu = 0;

        try {
            if(idAdresaUtilizator_AdresEmail != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setString(1, adresaEmail);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    idUtilizator = getRs().getInt("id");
                    numeUtilizator = getRs().getString("nume_utilizator");
                    numeFamilie = getRs().getString("nume_familie");
                    prenume = getRs().getString("prenume");
                }

                EmailBot.EmailResult rezultat = null;
                try {
                    rezultat =  EmailBot.verificaSiTrimiteEmail(DBConfig.DB_ADRESA_EXPEDITOR, DBConfig.DB_PAROLA_EXPEDITOR, adresaEmail, "Recuperarea contului",
                            formatareMesajRecuperareCont(numeUtilizator, parola, numeFamilie, prenume));
                } catch (Exception eroare) {
                    interfataBackEnd.infoStadiu("Nu a putut fi trimis E-mailul din cauza unei probleme. Vă rugăm să încercați mai târziu.");
                    return 0;
                }

                if(!rezultat.isSuccess()) {
                    interfataBackEnd.infoStadiu("Nu a putut fi trimis E-mailul din cauza unei probleme. Vă rugăm să încercați mai târziu.");
                    return 0;
                }

                stadiu = actualizareParola(idUtilizator, parola);
                if(stadiu == 0) throw new SQLException();

                interfataBackEnd.infoRasp("Contul a fost recuperat cu succes. Vă rugăm să vă verificați E-mailul!", true, 2);
                return 1;
            } else {
                interfataBackEnd.infoStadiu("Adresa de E-mail nu există.");
                return 0;
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eraore la nivelul adresei de E-mail.");
            return 0;
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
    }

    //  SFARSITUL ZONEI DE RECUPERARE CONT

    public boolean prelucrareDateInregistrare(Utilizator_Inregistrare utilizator) {
        boolean stare = true;

        stare = validare(interogareSQNumeUtilizator, utilizator.getNumeUtilizator(), "Ne pare rău, dar numele de utilizator este deja folosit!");
        if(!stare) return false;

        stare = validare(interogareSQLAdreseEmail, utilizator.getAdresaEmail(), "Ne pare rău, dar adresa de E-mail este deja folosită!");
        if(!stare) return false;

        stare = validare(interogareSQLNrTel, utilizator.getNrTel(), "Ne pare rău, dar numărul de telefon este deja folosit!");
        if(!stare) return false;

        if(!verificareDate(interogareSQLNumeFamilie, utilizator.getNumeFamilie())
                && !verificareDate(interogareSQLPrenume, utilizator.getPrenume())) {
            stare = false;
            return stare_mesaj(stare, String.format("Ne pare rău, dar există deja persoana %s %s.", utilizator.getNumeFamilie(),
                    utilizator.getPrenume()));
        }

        try {
            int idRol = preluareIdRol();
            if(idRol == 0) throw new SQLException("Eroare la roluri");

            int idNrLocuinta = preluareIdLocuinta(utilizator.getNrLocuinta());
            if(idNrLocuinta == 0) throw new SQLException("Eroare la locuinte");

            int idStrada = preluareIdStrada(utilizator.getStrada());
            if(idStrada == 0) throw new SQLException("Eroare la strada");

            int idLocalitate = preluareIdLocaliatate(utilizator.getLocalitate());
            if(idLocalitate == 0) throw new SQLException("Eroare la locuinta");

            int idUtilizatorInserat = preluareIdUtilizator(utilizator.getParola(), idRol, idNrLocuinta, idStrada, idLocalitate);
            if(idUtilizatorInserat == 0) throw new SQLException("Eroare la inserarea utilizatorului");

            int idNume = preluareIdNume(idUtilizatorInserat, utilizator.getNumeUtilizator());
            if(idNume == 0) throw new SQLException("Eroare la numele de utilizator");

            int idAdresaEmail = preluareIdAdresaEmail(idUtilizatorInserat, utilizator.getAdresaEmail());
            if(idAdresaEmail == 0) throw new SQLException("Eroare la adresa de email.");

            int idNrTel = preluareIdNrTel(idUtilizatorInserat, utilizator.getNrTel());
            if(idNrTel == 0) throw  new SQLException("Eroare la nr. de tel");

            int idNumeFamilie = preluareIdNumeFamilie(idUtilizatorInserat, utilizator.getNumeFamilie());
            if(idNumeFamilie == 0) throw new SQLException("Eroare la numele de familie");

           int idPrenume = preluareIdPrenume(idUtilizatorInserat, utilizator.getPrenume());
           if(idPrenume == 0) throw new SQLException("Eroare la prenume");

           interfataBackEnd.infoIntegistrare(true, "Întregistrare reușită!");
          EmailBot.EmailResult rezultat = EmailBot.verificaSiTrimiteEmail(DBConfig.DB_ADRESA_EXPEDITOR, DBConfig.DB_PAROLA_EXPEDITOR, utilizator.getAdresaEmail(),
                   String.format("Întregistrare %s", utilizator.getNumeUtilizator()),
                   formatareMesajEmail(utilizator.getNumeUtilizator(), utilizator.getNumeFamilie(), utilizator.getPrenume(),
                            utilizator.getNrTel(), utilizator.getAdresaEmail(), utilizator.getTara(), utilizator.getJudet(),
                            utilizator.getLocalitate(), utilizator.getStrada(), utilizator.getNrLocuinta()));
          if(!rezultat.isSuccess()) {
              JOptionPane.showMessageDialog(null, "Nu a putut fi trimis și un E-mail de confirmare din cauza unor probleme.",
                      "Atenție", JOptionPane.WARNING_MESSAGE);
          }
        } catch (SQLException eroare) {
            stare = false;
            //interfataBackEnd.infoIntegistrare(false, eroare.getMessage());
        } finally {
            inchidereResursa(getInterogareAvansata());
        }
        return stare;
    }

    private void interogareAutomata1(String interogareSQL, int oridineArg1, int arg1,
                                       int ordineArg2, int arg2, int ordineArg3, boolean arg3) {
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(oridineArg1, arg1);
            getInterogareAvansata().setInt(ordineArg2, arg2);
            getInterogareAvansata().setBoolean(ordineArg3, arg3);
            getInterogareAvansata().executeUpdate();
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getInterogareAvansata());
        }
    }

    private void interogareAutomata2(String interogareSQL, int oridineArg1, int arg1,
                                     int ordineArg2, int arg2) {
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(oridineArg1, arg1);
            getInterogareAvansata().setInt(ordineArg2, arg2);
            getInterogareAvansata().executeUpdate();
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getInterogareAvansata());
        }
    }

    private void interogareAutomata3(String interogareSQL, int oridineArg1, int arg1,
                                     int ordineArg2, boolean arg2) {
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(oridineArg1, arg1);
            getInterogareAvansata().setBoolean(ordineArg2, arg2);
            getInterogareAvansata().executeUpdate();
        } catch (SQLException eroare) {
            eroare.printStackTrace();
            System.out.println("EROARE IN INTERO3");
        } finally {
            inchidereResursa(getInterogareAvansata());
        }
    }

    private int interogareAutomata5(String interogareSQL, int ordineArg1, String arg1,
                                     int ordineArg2, String arg2) {
        int id = 0;
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setString(ordineArg1, arg1);
            getInterogareAvansata().setString(ordineArg2, arg2);
            setRs(getInterogareAvansata().executeQuery());
                while (getRs().next()) {
                    id = getRs().getInt("id");
                }
            return id;
        } catch (SQLException eroare) {
            return 0;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void infoTari() {
        try {
            ArrayList<String> setTari = new ArrayList<>();
            String interogareSQL = "SELECT tari.denumire AS \"Tara\" FROM tari ORDER BY tari.denumire";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                setTari.add(getRs().getString("Tara"));
            }
            if(getRs().first()) {
                if (interfataBackEnd != null) {
                    interfataBackEnd.infoTari(setTari);
                }
            } else {
                //interfataBackEnd.stareRaspunsuri(false);
            }


        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void infoJudete(String tara) {
        try {
            ArrayList<String> setJudete = new ArrayList<>();
            String interogareSQL = "SELECT judete.denumire AS \"Judet\" " +
                    "FROM tari INNER JOIN judete ON judete.tara_id = tari.id " +
                    " WHERE tari.denumire = ? ORDER BY judete.denumire";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, tara);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                setJudete.add(getRs().getString("Judet"));
            }
            if(getRs().first()) {
                if (interfataBackEnd != null) {
                    interfataBackEnd.infoJudete(setJudete);
                }
            } else {
                //interfataBackEnd.stareRaspunsuri(false);
            }


        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void infoLocalitati(String judet) {
        try {
            ArrayList<String> setLocalitati = new ArrayList<>();
            String interogareSQL = "SELECT localitati.denumire AS \"Localitate\" " +
                    "FROM judete INNER JOIN localitati ON localitati.judet_id = judete.id " +
                    " WHERE judete.denumire = ? ORDER BY localitati.denumire";
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, judet);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                setLocalitati.add(getRs().getString("Localitate"));
            }
            if(getRs().first()) {
                if (interfataBackEnd != null) {
                    interfataBackEnd.infoLocalitati(setLocalitati);
                }
            } else {
                //interfataBackEnd.stareRaspunsuri(false);
            }

        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void dateConectare(String numeUtilizator, String parolaUtilizator) {
        String interogareSQL = "SELECT utilizatori.parola AS parola_utilizator FROM " +
                "(nume INNER JOIN nume_utilizatori ON nume_utilizatori.nume_id = nume.id)" +
                " INNER JOIN utilizatori ON nume_utilizatori.utilizator_id = utilizatori.id" +
                " WHERE nume.denumire = ? AND nume_utilizatori.activ = ?";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, numeUtilizator);
            getInterogareAvansata().setBoolean(2, true);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().first()) {
                if (interfataBackEnd != null) {
                    String parolaHash = getRs().getString("parola_utilizator");
                    if(argon2Alg.verificareParola(parolaUtilizator, parolaHash)) {
                        dateUtilizator(numeUtilizator);
                        interfataBackEnd.stareLogare(true, numeUtilizator);
                    } else {
                        interfataBackEnd.stareLogare(false, numeUtilizator);
                    }
                }
            } else {
                interfataBackEnd.stareLogare(false, numeUtilizator);
            }
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }

    }

    private UtilizatorComplet trasmitereDateProfil(String numeUtilizator) {
        UtilizatorComplet utilizator = null;
        String interogareSQL = "SELECT " +
                "utilizatori.id as id, " +
                "roluri.denumire AS rol, " +
        "nume.denumire AS nume_utilizator, " +
                "utilizatori.parola AS parola, " +
        "adrese_email.denumire AS adresa_email, " +
                "numere_telefon.denumire AS numar_telefon, " +
        "nume_familie.denumire AS nume_familie, " +
                "prenume.denumire AS prenume, " +
        "locuinte.numar AS numar_locuinta, " +
                "strazi.denumire AS strada, " +
        "localitati.denumire AS localitate, " +
                "judete.denumire AS judet, " +
        "tari.denumire AS tara " +
        "FROM (((((((((((((((nume " +
                "INNER JOIN nume_utilizatori ON nume_utilizatori.nume_id = nume.id) " +
        "INNER JOIN utilizatori ON nume_utilizatori.utilizator_id = utilizatori.id) " +
                "INNER JOIN roluri ON utilizatori.rol_id = roluri.id) " +
        "INNER JOIN contact_email ON contact_email.utilizator_id = utilizatori.id) " +
        "INNER JOIN adrese_email ON contact_email.adresa_email_id = adrese_email.id) " +
        "INNER JOIN contact_telefon ON contact_telefon.utilizator_id = utilizatori.id) " +
        "INNER JOIN numere_telefon ON contact_telefon.numar_telefon_id = numere_telefon.id) " +
        "INNER JOIN utilizator_nume_familie ON utilizator_nume_familie.utilizator_id = utilizatori.id) " +
        "INNER JOIN nume_familie ON utilizator_nume_familie.nume_familie_id = nume_familie.id) " +
        "INNER JOIN utilizator_prenume ON utilizator_prenume.utilizator_id = utilizatori.id) " +
        "INNER JOIN prenume ON utilizator_prenume.prenume_id = prenume.id) " +
        "INNER JOIN locuinte ON utilizatori.locuinta_id = locuinte.id) " +
        "INNER JOIN strazi ON utilizatori.strada_id = strazi.id) " +
        "INNER JOIN localitati ON utilizatori.localitate_id = localitati.id) " +
        "INNER JOIN judete ON localitati.judet_id = judete.id) " +
        "INNER JOIN tari ON judete.tara_id = tari.id " +
        "WHERE " +
        "nume.denumire = ? AND contact_email.activ = ? AND contact_telefon.activ = ? " +
                "AND utilizator_nume_familie.activ = ? AND utilizator_prenume.activ = ? " +
        "LIMIT 1; ";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, numeUtilizator);
            getInterogareAvansata().setBoolean(2, true);
            getInterogareAvansata().setBoolean(3, true);
            getInterogareAvansata().setBoolean(4, true);
            getInterogareAvansata().setBoolean(5, true);
            setRs(getInterogareAvansata().executeQuery());
            String rol, nume_utilizator, parola, numar_telefon, adresa_email, nume_familie, prenume, strada, localitate, judet, tara;
            int id_utilizator, numar_locuinta;
            if(getRs().next()) {
                id_utilizator = getRs().getInt("id");
                rol = getRs().getString("rol");
                nume_utilizator = getRs().getString("nume_utilizator");
                parola = getRs().getString("parola");
                numar_telefon = getRs().getString("numar_telefon");
                adresa_email = getRs().getString("adresa_email");
                nume_familie = getRs().getString("nume_familie");
                prenume = getRs().getString("prenume");
                numar_locuinta = getRs().getInt("numar_locuinta");
                strada = getRs().getString("strada");
                localitate = getRs().getString("localitate");
                judet = getRs().getString("judet");
                tara = getRs().getString("tara");
                 utilizator = new UtilizatorComplet(this, id_utilizator, rol, nume_utilizator, parola, nume_familie, prenume, numar_telefon,
                        adresa_email, tara, localitate, strada, judet, numar_locuinta, null);
            }
            return utilizator;
        } catch (SQLException eroare) {
            eroare.printStackTrace();
            return utilizator;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    private ArrayList<CardBancar> transmitereDateCarduri(String numeUtilizator) {
        ArrayList<CardBancar> listaCarduri = new ArrayList<>();
        String interogareSQL = "SELECT " +
                "banci.denumire AS nume_banca, " +
                "tipuri_carduri.denumire AS tip_card, " +
        "tipuri_monede.denumire AS tip_moneda, " +
                "monede.denumire AS moneda, " +
        "carduri.numar AS numar_card, " +
                "carduri.nume_posesor AS nume_posesor, " +
                "carduri.data_expirare AS data_expirare_card, " +
                "carduri_continut.link_poza AS link_poza " +
        "FROM (((((((nume " +
                "INNER JOIN nume_utilizatori ON nume_utilizatori.nume_id = nume.id) " +
        "INNER JOIN utilizatori ON nume_utilizatori.utilizator_id = utilizatori.id) " +
        "INNER JOIN carduri ON carduri.utilizator_id = utilizatori.id) " +
                "INNER JOIN carduri_continut ON carduri.card_continut_id = carduri_continut.id )" +
        "INNER JOIN tipuri_carduri ON carduri_continut.tip_card_id = tipuri_carduri.id) " +
        "INNER JOIN banci ON carduri_continut.banca_id = banci.id) " +
        "INNER JOIN monede ON carduri.moneda_id = monede.id) " +
        "INNER JOIN tipuri_monede ON monede.tip_moneda_id = tipuri_monede.id " +
        "WHERE " +
            "nume.denumire = ? ;";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, numeUtilizator);
            setRs(getInterogareAvansata().executeQuery());
            String numeBanca, tipCard, tipMoneda, moneda, numarCard, nume_posesor, dataExpirareCard, link_poza;
            if(!getRs().first()) return new ArrayList<CardBancar>();
            getRs().beforeFirst();
            while(getRs().next()) {
                numeBanca = getRs().getString("nume_banca");
                tipCard = getRs().getString("tip_card");
                tipMoneda = getRs().getString("tip_moneda");
                moneda = getRs().getString("moneda");
                numarCard = getRs().getString("numar_card");
                nume_posesor = getRs().getString("nume_posesor");
                dataExpirareCard = getRs().getString("data_expirare_card");
                link_poza = getRs().getString("link_poza");

                DateTimeFormatter formatterOriginal = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate data = LocalDate.parse(dataExpirareCard, formatterOriginal);
                DateTimeFormatter formatterNou = DateTimeFormatter.ofPattern("MM/yy");
                String dataFormatata = data.format(formatterNou);

                listaCarduri.add(new CardBancar(numeBanca, tipCard, tipMoneda, moneda, numarCard, nume_posesor, dataFormatata,
                        link_poza));
            }
            return listaCarduri;
        } catch (SQLException eroare) {
            eroare.printStackTrace();
            return listaCarduri;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void dateUtilizator(String numeUtilizator) {
        UtilizatorComplet utilizator = trasmitereDateProfil(numeUtilizator);
        ArrayList<CardBancar> carduriBancare = transmitereDateCarduri(numeUtilizator);
        if(carduriBancare != null) {
            utilizator.setCarduriBancare(carduriBancare);
        }
        if (interfataBackEnd != null) {
            interfataBackEnd.trimitereDateUtilizator(utilizator);
        }
    }

    private ArrayList<String> ingredienteProdus(int idProdus) {
        ArrayList<String> ingrediente = new ArrayList<>();
        String interogareSQL0 = "SELECT ingrediente.denumire AS ingredient " +
                "FROM ingrediente INNER JOIN produse_ingrediente ON produse_ingrediente.ingredient_id = ingrediente.id " +
                "WHERE produse_ingrediente.produs_id = ?";

        PreparedStatement interogare = null;
        ResultSet rezultat = null;

        try {
            interogare = getConexiune().prepareStatement(interogareSQL0,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            interogare.setInt(1, idProdus);
            rezultat = interogare.executeQuery();

            while(rezultat.next()) {
                ingrediente.add(rezultat.getString("ingredient"));
            }
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            try {
                if (rezultat != null) rezultat.close();
                if (interogare != null) interogare.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ingrediente;
    }

    public void produse(String meniu, String categorie, String subcategorie, String unitateMasura, int offset) {
        ArrayList<Produs> listaProduse = new ArrayList<>();
        ArrayList<String> ingrediente = new ArrayList<>();
        String interogareSQL0 = "SELECT " +
        "producatori.denumire AS denumire_producator, " +
                "produse.denumire AS denumire_produs, " +
        "produse.continut AS continut_produs, " +
                "abrevieri_um.denumire AS abreviere_unitate_masura, " +
                "produse.id AS id_produs, " +
        "produse.pret AS pret_produs, " +
                "produse.link_poza AS link_poza_produs, " +
                "produse.eticheta AS eticheta " +
        "FROM " +
                "(((((((produse " +
                        "INNER JOIN unitati_masura ON produse.unitate_masura_id = unitati_masura.id) " +
                "INNER JOIN prescurtare_um ON prescurtare_um.um_id = unitati_masura.id) " +
                "INNER JOIN abrevieri_um ON prescurtare_um.abreviere_um_id = abrevieri_um.id) " +
        "INNER JOIN producatori ON produse.producator_id = producatori.id) " +
        "INNER JOIN categorii_subcategorii ON produse.categorie_subcategorie_id = categorii_subcategorii.id) " +
        "INNER JOIN subcategorii ON categorii_subcategorii.subcategorie_id = subcategorii.id) " +
        "INNER JOIN categorii ON categorii_subcategorii.categorie_id = categorii.id) " +
        "INNER JOIN meniuri ON categorii.meniu_id = meniuri.id " +
        "WHERE " +
        "meniuri.denumire = ? " +
        "AND " +
        "categorii.denumire = ? " +
        "AND " +
        "subcategorii.denumire = ? " +
        "AND " +
        "abrevieri_um.denumire = ? " +
                "LIMIT 2 " +
                "OFFSET ?";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, meniu);
            getInterogareAvansata().setString(2, categorie);
            getInterogareAvansata().setString(3, subcategorie);
            getInterogareAvansata().setString(4, unitateMasura);
            getInterogareAvansata().setInt(5, offset);
            setRs(getInterogareAvansata().executeQuery());
            String denProducator = "", denProdus = "", abvUM = "", linkPozaProdus = "", eticheta;
            double pretProdus = 0.00;
            int continutProdus = 0, idProdus = 0;
            while(getRs().next()) {
                idProdus = getRs().getInt("id_produs");
                denProducator = getRs().getString("denumire_producator");
                denProdus = getRs().getString("denumire_produs");
                abvUM = getRs().getString("abreviere_unitate_masura");
                continutProdus = getRs().getInt("continut_produs");
                pretProdus = getRs().getDouble("pret_produs");
                linkPozaProdus = getRs().getString("link_poza_produs");
                eticheta = getRs().getString("eticheta");
                ingrediente = ingredienteProdus(idProdus);
                listaProduse.add(new Produs(idProdus, denProducator, denProdus, abvUM, continutProdus, pretProdus, linkPozaProdus,
                        eticheta, ingrediente));
            }
            setProduse(new Produse(this, listaProduse));

        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void nrProduse(String meniu, String categorie, String subcategorie, String unitateMasura) {
        String interogareSQL1 = "SELECT COUNT(PRODUSE.ID) AS NR_PRODUSE " +
                "FROM " +
                "(((((((produse " +
                "INNER JOIN unitati_masura ON produse.unitate_masura_id = unitati_masura.id) " +
                "INNER JOIN prescurtare_um ON prescurtare_um.um_id = unitati_masura.id) " +
                "INNER JOIN abrevieri_um ON prescurtare_um.abreviere_um_id = abrevieri_um.id) " +
                "INNER JOIN producatori ON produse.producator_id = producatori.id) " +
                "INNER JOIN categorii_subcategorii ON produse.categorie_subcategorie_id = categorii_subcategorii.id) " +
                "INNER JOIN subcategorii ON categorii_subcategorii.subcategorie_id = subcategorii.id) " +
                "INNER JOIN categorii ON categorii_subcategorii.categorie_id = categorii.id) " +
                "INNER JOIN meniuri ON categorii.meniu_id = meniuri.id " +
                "WHERE " +
                "meniuri.denumire = ? " +
                "AND " +
                "categorii.denumire = ? " +
                "AND " +
                "subcategorii.denumire = ? " +
                "AND " +
                "abrevieri_um.denumire = ?; ";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setString(1, meniu);
            getInterogareAvansata().setString(2, categorie);
            getInterogareAvansata().setString(3, subcategorie);
            getInterogareAvansata().setString(4, unitateMasura);
            setRs(getInterogareAvansata().executeQuery());
            int rez = 0;
            while(getRs().next()) {
                rez = getRs().getInt("nr_produse");
                rez = getRs().getInt("nr_produse");
            }
            if(rez % 2 == 1) {rez++;}
            setNrProduse(rez);
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public ArrayList<String> preluareUnitatiMasura() {
        ArrayList<String> unitatiMasura = new ArrayList<>();
        String interogareSQL = "SELECT unitati_masura.denumire AS unitate_masura FROM unitati_masura";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            if(!getRs().first()) return new ArrayList<>();
            String unitateMasura = "";
            getRs().beforeFirst();
            while(getRs().next()) {
                unitateMasura = getRs().getString("unitate_masura");
                unitatiMasura.add(unitateMasura);
            }
            return unitatiMasura;
        } catch (SQLException eroare) {
            return unitatiMasura;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public ArrayList<String> preluareProducatori() {
        ArrayList<String> producatori = new ArrayList<>();
        String interogareSQL = "SELECT producatori.denumire AS producatori FROM producatori";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            if(!getRs().first()) return new ArrayList<>();
            String producator = "";
            getRs().beforeFirst();
            while(getRs().next()) {
                producator = getRs().getString("producatori");
                producatori.add(producator);
            }
            return producatori;
        } catch (SQLException eroare) {
            return producatori;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public ArrayList<String> preluareCategorii() {
        ArrayList<String> categorii = new ArrayList<>();
        String interogareSQL = "SELECT categorii.denumire AS categorii FROM categorii";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            if(!getRs().first()) return new ArrayList<>();
            String categorie = "";
            getRs().beforeFirst();
            while(getRs().next()) {
                categorie = getRs().getString("categorii");
                categorii.add(categorie);
            }
            return categorii;
        } catch (SQLException eroare) {
            return categorii;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public ArrayList<String> preluareSubcategorii() {
        ArrayList<String> subcategorii = new ArrayList<>();
        String interogareSQL = "SELECT subcategorii.denumire AS subcategorii FROM subcategorii";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            if(!getRs().first()) return new ArrayList<>();
            String subcategorie = "";
            getRs().beforeFirst();
            while(getRs().next()) {
                subcategorie = getRs().getString("subcategorii");
                subcategorii.add(subcategorie);
            }
            return subcategorii;
        } catch (SQLException eroare) {
            return subcategorii;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public ArrayList<String> preluareIngrediente() {
        ArrayList<String> ingrediente = new ArrayList<>();
        String interogareSQL = "SELECT ingrediente.denumire AS ingrediente FROM ingrediente";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            if(!getRs().first()) return new ArrayList<>();
            String ingredient = "";
            getRs().beforeFirst();
            while(getRs().next()) {
                ingredient = getRs().getString("ingrediente");
                ingrediente.add(ingredient);
            }
            return ingrediente;
        } catch (SQLException eroare) {
            return ingrediente;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public ArrayList<String> preluareRoluri() {
        ArrayList<String> roluri = new ArrayList<>();
        String interogareSQL = "SELECT roluri.denumire AS roluri FROM roluri";

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            if(!getRs().first()) return new ArrayList<>();
            String rol = "";
            getRs().beforeFirst();
            while(getRs().next()) {
                rol = getRs().getString("roluri");
                roluri.add(rol);
            }
            return roluri;
        } catch (SQLException eroare) {
            return roluri;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void trimitereDateProduse() {
        interfataBackEnd.infoProduse(getProduse(), getNrProduse());
    }

    //  CRUD Nuante, Intrebari, Raspunsuri

    public void adaugareNuanta(Culori culoare){
        String interogareSQL0 = "INSERT INTO nuante (denumire, cod_hex, culoaretext_id) VALUES (?, ?, (SELECT id FROM nuante WHERE cod_hex = ?));",
        interogareSQL1 = "SELECT id FROM nuante WHERE cod_hex = ?";
        if(verificareDate(interogareSQL1, culoare.getCuloareFundal())) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, culoare.getDenumireCuloare());
                getInterogareAvansata().setString(2, culoare.getCuloareFundal());
                getInterogareAvansata().setString(3, culoare.getCuloareText());
                getInterogareAvansata().executeUpdate();

                interfataBackEnd.infoRasp(String.format("Ați adăugat nuanța %s cu codul hex %s pentru fundal și codul hex %s pentru text!",
                        culoare.getDenumireCuloare(), culoare.getCuloareFundal(), culoare.getCuloareText()), true, 2);
                prelucrareDateAspect();
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp("Nuanța deja există în baza de date! Vă rugăm să introduceți alt cod hex.", false, 2);
        }
    }

    public void adaugareIntrebare(Intrebare intrebare){
        String interogareSQL0 = "INSERT INTO intrebari (cod, continut) VALUES (?, ?);",
                interogareSQL1 = "SELECT id FROM intrebari WHERE cod = ?";
        if(verificareDate(interogareSQL1, intrebare.getCod())) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, intrebare.getCod());
                getInterogareAvansata().setString(2, intrebare.getContinut());
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați adăugat întrebare cu codul %s și cu textul \"%s\".",
                        intrebare.getCod(), intrebare.getContinut()), true, 0);
                prelucrareDateIntrebari();
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp("Întrebarea deja există în baza de date! Vă rugăm să introduceți alt cod pentru întrebare.", false, 0);
        }
    }

    private int adaugareRaspunsInitial(Raspuns raspuns){
        String interogareSQL0 = "INSERT INTO raspunsuri (cod, continut) VALUES (?, ?) RETURNING id",
                interogareSQL1 = "SELECT id FROM raspunsuri WHERE cod = ?";
        int idRaspunsAdaugat = 0;

        if(verificareDate(interogareSQL1, raspuns.getCod())) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, raspuns.getCod());
                getInterogareAvansata().setString(2, raspuns.getContinut());
                setRs(getInterogareAvansata().executeQuery());
                while(getRs().next()) {
                    idRaspunsAdaugat = getRs().getInt("id");
                }
                return idRaspunsAdaugat;
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp("Răspunsul deja există în baza de date! Vă rugăm să introduceți alt cod pentru răspuns.", false, 1);
        }
        return idRaspunsAdaugat;
    }

    private int asociereIntrebareRaspuns(int idRaspuns, String codIntrebare) {
        String interogareSQL0 = "INSERT INTO intrebari_raspunsuri (raspuns_id, intrebare_id) VALUES (?, (SELECT id FROM intrebari WHERE cod = ?)) RETURNING id",
                interogareSQL1 = "SELECT id FROM intrebari_raspunsuri WHERE raspuns_id = ? AND intrebare_id = (SELECT id FROM intrebari WHERE cod = ?)",
                interogareSQL2 = "SELECT id FROM intrebari WHERE cod = ?";

        int idRaspunsAdaugat = 0;

        if(verificareDateIntString(interogareSQL1, idRaspuns, codIntrebare) && !verificareDate(interogareSQL2, codIntrebare)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setInt(1, idRaspuns);
                getInterogareAvansata().setString(2, codIntrebare);
                setRs(getInterogareAvansata().executeQuery());
                while(getRs().next()) {
                    idRaspunsAdaugat = getRs().getInt("id");
                }
                return idRaspunsAdaugat;
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Răspunsul a fost deja asociat cu întrebarea pentru care ați introdus codul / " +
                    "întrebarea %s nu există.", codIntrebare), false, 1);
        }
        return idRaspunsAdaugat;
    }

    public void adaugareRaspuns(Raspuns raspuns, String codIntrebare) {
        int idRaspunsAdaugat = adaugareRaspunsInitial(raspuns), idAsociereIntrebareRaspuns = 0;
        if(idRaspunsAdaugat != 0)
            idAsociereIntrebareRaspuns =  asociereIntrebareRaspuns(idRaspunsAdaugat, codIntrebare);
        if(idAsociereIntrebareRaspuns != 0) {
            interfataBackEnd.infoRasp(String.format("Ați adăugat răspunsul %s pentru întrebarea %s.",
                    raspuns.getCod(), codIntrebare), true, 1);
            prelucrareDateRaspunsuri(codIntrebare);
        }
    }

    public int gestionareProdus(ProdusPreluat produs) {
        int idProdusInserat = adaugareProdus(produs);
        int stadiu = 0;
        for(String ingredient : produs.getIngrediente()) {
            stadiu = adaugareIngredienteProdus(idProdusInserat, ingredient);
            if(stadiu == 0) {
                interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar ingredientul %s nu a putut fi asociat produsului.", ingredient));
                break;
            } else if(stadiu == -1) {
                interfataBackEnd.infoStadiu("Asocierea dintre produs & ingredient există deja în baza de date! Vă rugăm să introduceți un alt ingredient!");
                break;
            }
        }
        return stadiu;
    }

    private int adaugareProdus(ProdusPreluat produs) {
        String interogareSQL0 = "SELECT id FROM produse WHERE eticheta = ?",
                interogareSQL1 = "INSERT INTO produse (denumire, unitate_masura_id, categorie_subcategorie_id, pret, link_poza, continut, " +
                        "producator_id, eticheta) VALUES (?, (SELECT id FROM unitati_masura WHERE denumire = ?), " +
                        "(SELECT categorii_subcategorii.id FROM categorii_subcategorii WHERE categorie_id = (SELECT id FROM categorii WHERE denumire = ?) AND " +
                        "subcategorie_id = (SELECT id FROM subcategorii WHERE denumire = ?)), ?, ?, ?, (SELECT id FROM producatori WHERE denumire = ?), " +
                        "?) RETURNING id",
        interogareSQL2 = "SELECT categorii_subcategorii.id AS id FROM categorii_subcategorii WHERE categorie_id = (SELECT id FROM categorii WHERE denumire = ?) AND " +
                         "subcategorie_id = (SELECT id FROM subcategorii WHERE denumire = ?)";
        int idProdusAdaugat = verificareDateFalse(interogareSQL0, produs.getEticheta()),
        idCategorieSubcategorie = interogareAutomata5(interogareSQL2, 1, produs.getCategorie(), 2, produs.getSubcategorie());

        if(idCategorieSubcategorie == 0) {
            interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar nu există o asociere pentru: %s și %s.",
                    produs.getCategorie(), produs.getSubcategorie()));
            return 0;
        }

        if(idProdusAdaugat == 0) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, produs.getDenumire());
                getInterogareAvansata().setString(2, produs.getUnitateMasura());
                getInterogareAvansata().setString(3, produs.getCategorie());
                getInterogareAvansata().setString(4, produs.getSubcategorie());
                getInterogareAvansata().setDouble(5, produs.getPret());
                getInterogareAvansata().setString(6, produs.getLinkPoza());
                getInterogareAvansata().setInt(7, produs.getContinut());
                getInterogareAvansata().setString(8, produs.getProducator());
                getInterogareAvansata().setString(9, produs.getEticheta());

                setRs(getInterogareAvansata().executeQuery());
                while(getRs().next()) {
                    idProdusAdaugat = getRs().getInt("id");
                }
                return idProdusAdaugat;
            } catch (SQLException eroare) {
                System.out.println(eroare.getMessage());
                interfataBackEnd.infoStadiu("Ne pare rău, dar produsul nu a putut fi adăugat.");
                return 0;
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoStadiu("Produsul deja există în baza de date! Vă rugăm să introduceți o altă etichetă!");
        }
        return 0;
    }

    private int adaugareIngredienteProdus(int idProdus, String ingredient) {
        String interogareSQL0 = "SELECT id FROM produse_ingrediente WHERE produs_id = ? AND ingredient_id = (SELECT id FROM ingrediente WHERE denumire = ?)",
        interogareSQL1 = "INSERT INTO produse_ingrediente (produs_id, ingredient_id) VALUES (?, (SELECT id FROM ingrediente WHERE denumire = ?)) RETURNING ID";

        int idProdusIngredientAdaugat = verificareDateIntString2(interogareSQL0, idProdus, ingredient);
        if(idProdusIngredientAdaugat == 0) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setInt(1, idProdus);
                getInterogareAvansata().setString(2, ingredient);
                setRs(getInterogareAvansata().executeQuery());
                while(getRs().next()) {
                    idProdusIngredientAdaugat = getRs().getInt("id");
                }
                return idProdusIngredientAdaugat;
            } catch (SQLException eroare) {
                return 0;
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            return -1;
        }
    }

    public void stergereNuanta(String codHex) {
        String interogareSQL0 = "DELETE FROM nuante WHERE cod_hex = ?;",
        interogareSQL1 = "SELECT id FROM nuante WHERE cod_hex = ?";
        if(!verificareDate(interogareSQL1, codHex)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, codHex);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați șters nuanța cu codul hex %s pentru fundal!", codHex), true, 2);
                prelucrareDateAspect();
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Nu ați reușit să ștergeți intrebarea cu codul %s.", codHex), false, 2);
        }
    }

    public void stergereIntrebare(String codIntrebare) {
        String interogareSQL0 = "DELETE FROM intrebari WHERE cod = ?;",
                interogareSQL1 = "SELECT id FROM intrebari WHERE cod = ?";
        if(!verificareDate(interogareSQL1, codIntrebare)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, codIntrebare);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați șters întrebarea cu codul %s.", codIntrebare), true, 0);
                prelucrareDateIntrebari();
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Întrebarea nu există.", codIntrebare), false, 0);
        }
    }

    public void stergereRaspuns(String codRaspuns, String codIntrebare) {
        String interogareSQL0 = "DELETE FROM raspunsuri WHERE cod = ?;",
                interogareSQL1 = "SELECT id FROM raspunsuri WHERE cod = ?";
        if(!verificareDate(interogareSQL1, codRaspuns)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, codRaspuns);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați șters răspunsul care are codul %s.", codRaspuns), true, 2);
                prelucrareDateRaspunsuri(codIntrebare);
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Nu ați reușit să ștergeți răspunsul care are codul %s.", codRaspuns), false, 2);
        }
    }

    public int stergereProdus(String eticheta) {
        String interogareSQL1 = "DELETE FROM produse WHERE eticheta = ?;",
                interogareSQL0 = "SELECT id FROM produse WHERE eticheta = ?";
        if(!verificareDate(interogareSQL0, eticheta) && !verificareDate(interogareSQL0, eticheta)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, eticheta);
                getInterogareAvansata().executeUpdate();
                return 1;
            } catch (SQLException eroare) {
                return 0;
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            return -1;
        }
    }

    public void modificareNuanta(Culori culoare) {
        String interogareSQL0 = "SELECT id FROM nuante WHERE cod_hex = ?",
                interogareSQL1 = "UPDATE nuante SET denumire = ?, culoaretext_id = (SELECT id FROM nuante WHERE cod_hex = ?) WHERE cod_hex = ?",
        codHexFundal = culoare.getCuloareFundal(), codHexText = culoare.getCuloareText(), denumire = culoare.getDenumireCuloare();
        if(!verificareDate(interogareSQL0, codHexFundal)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, denumire);
                getInterogareAvansata().setString(2, codHexText);
                getInterogareAvansata().setString(3, codHexFundal);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați reușit să actualizați datele pentru" +
                        " nuanța care are codul hex %s ca și fundal!", codHexFundal), true, 2);
                prelucrareDateAspect();
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Nu ați reușit să actualizați datele " +
                    "pentru nuanța care are codul hex %s ca și fundal!", codHexFundal), false, 2);
        }
    }

    public void modificareIntrebare(Intrebare intrebare) {
        String interogareSQL0 = "SELECT id FROM intrebari WHERE cod = ?",
                interogareSQL1 = "UPDATE intrebari SET continut = ? WHERE cod = ?",
                codIntrebare = intrebare.getCod(), intrebareContinut = intrebare.getContinut();
        if(!verificareDate(interogareSQL0, codIntrebare)) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, intrebareContinut);
                getInterogareAvansata().setString(2, codIntrebare);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați reușit să actualizați datele pentru" +
                        " întrebarea care are codul %s!", codIntrebare), true, 0);
                prelucrareDateIntrebari();
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Nu ați reușit să actualizați datele " +
                    "pentru întrebarea care are codul %s!", codIntrebare), false, 0);
        }
    }

    private int verificareIntrebareRaspuns(int idRaspuns, int idIntrebare) {
        String interogareSQL0 = "SELECT id FROM intrebari_raspunsuri WHERE intrebare_id = ? AND raspuns_id = ?";
        int idIntrebareRaspuns = 0;
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            getInterogareAvansata().setInt(1, idIntrebare);
            getInterogareAvansata().setInt(2, idRaspuns);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next())
                idIntrebareRaspuns = getRs().getInt("id");
            return idIntrebareRaspuns;
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return idIntrebareRaspuns;
    }

    public void modificareCompletaRaspuns(Raspuns raspuns, String codIntrebare, String codIntrebareSelectata) {
        String interogareSQL0 = "SELECT id FROM intrebari WHERE cod = ?",
                interogareSQL1= "UPDATE intrebari_raspunsuri SET intrebare_id = ? WHERE id = ?",
                codRaspuns = raspuns.getCod();
        int idRaspunsModificat = modificareRaspuns(raspuns),
        idIntrebareAsociata = verificareDateFalse(interogareSQL0, codIntrebareSelectata),
                idIntrebareSchimbata = verificareDateFalse(interogareSQL0, codIntrebare),
        idIntrebareRaspuns = 0;
        if(idRaspunsModificat != 0 && idIntrebareAsociata != 0)
            idIntrebareRaspuns = verificareIntrebareRaspuns(idRaspunsModificat, idIntrebareAsociata);
        else
            interfataBackEnd.infoRasp(String.format("Răspunsul %s sau întrebarea %s nu există.",codRaspuns , codIntrebare), false, 0);
        if(idIntrebareRaspuns != 0) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setInt(1, idIntrebareSchimbata);
                getInterogareAvansata().setInt(2, idIntrebareRaspuns);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați reușit să actualizați datele pentru" +
                        " răspunsul %s.", codRaspuns), true, 0);
                prelucrareDateRaspunsuri(codIntrebare);
            } catch (SQLException eroare) {
                eroare.printStackTrace();
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Răspunsul %s nu a fost asociat cu întrebarea %s.", codRaspuns, codIntrebare), false, 0);
        }
    }

    private int modificareRaspuns(Raspuns raspuns) {
        String interogareSQL1 = "UPDATE raspunsuri SET continut = ? WHERE cod = ?",
                interogareSQL0 = "SELECT id from raspunsuri WHERE cod = ?",
                codRaspuns = raspuns.getCod(), continutRaspuns = raspuns.getContinut();
        int idRaspunsModificat = verificareDateFalse(interogareSQL0, codRaspuns);
        if(idRaspunsModificat != 0) {
            try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
                getInterogareAvansata().setString(1, continutRaspuns);
                getInterogareAvansata().setString(2, codRaspuns);
                getInterogareAvansata().executeUpdate();
                return idRaspunsModificat;
            } catch (SQLException eroare) {
                eroare.printStackTrace();
                return idRaspunsModificat;
            } finally {
                inchidereResursa(getRs());
                inchidereResursa(getInterogareAvansata());
            }
        } else {
            interfataBackEnd.infoRasp(String.format("Nu ați reușit să actualizați datele " +
                    "pentru răspunsul care are codul %s!", codRaspuns), false, 0);
        }
        return idRaspunsModificat;
    }

    public int modificareProdus(ProdusPreluat produs) {
        String interogareSQL2 = "SELECT categorii_subcategorii.id AS id FROM categorii_subcategorii WHERE categorie_id = (SELECT id FROM categorii WHERE denumire = ?) AND " +
                "subcategorie_id = (SELECT id FROM subcategorii WHERE denumire = ?)";
        int idCategorieSubcategorie = interogareAutomata5(interogareSQL2, 1, produs.getCategorie(), 2, produs.getSubcategorie());

        if(idCategorieSubcategorie == 0) {
            interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar nu există o asociere pentru: %s și %s.",
                    produs.getCategorie(), produs.getSubcategorie()));
            return 0;
        }

        int stadiu = stergereProdus(produs.getEticheta()), stadiuSecundar = 0;
        if(stadiu > 0) {
            stadiu = adaugareProdus(produs);
        } else if(stadiu < 0) {
            return -1;
        }

        if(stadiu != 0) {
            for (String ingredient : produs.getIngrediente()) {
                stadiuSecundar = adaugareIngredienteProdus(stadiu, ingredient);
                if (stadiuSecundar == 0) {
                    interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar ingredientul %s nu a putut fi asociat produsului.", ingredient));
                    break;
                } else if (stadiuSecundar == -1) {
                    interfataBackEnd.infoStadiu("Asocierea dintre produs & ingredient există deja în baza de date! Vă rugăm să introduceți un alt ingredient!");
                    break;
                }
            }
        }
        return stadiu;
    }

    //  FUNCTII PENTRU INTREBARI & RASPUNSURI

    public String codMaximRaspunsuri() {
        String interogareSQL0 = "SELECT MAX(cod) AS cod_maxim FROM raspunsuri;",
                cod = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                cod = getRs().getString("cod_maxim");
            }
            return cod;
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return cod;
    }

    public String codMaximIntrebari() {
        String interogareSQL0 = "SELECT MAX(cod) AS cod_maxim FROM intrebari;",
                cod = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                cod = getRs().getString("cod_maxim");
            }
            return cod;
        } catch (SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return cod;
    }

    //  INTEROGARE AUTOMATA PENTRU MULTIPLE FUNCTII

    private int interogareAutomata4(String interogareSQL, int atribut1, String elementCautat) {
        int rezultat = 0;

        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setInt(1, atribut1);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                rezultat = getRs().getInt(elementCautat);
            }
        } catch(SQLException eroare) {
            return rezultat;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return rezultat;
    }

    //  VERFICAREA APARITIEI PRODUSULUI IN COSUL UTILIZATORULUI
    public boolean verificareProdusCos(int idUtilizator, int idProdus, int cantitate) {
        String interogareSQL0 = "SELECT id FROM cos WHERE utilizator_id = ? AND produs_id = ?",
                interogareSQL1 = "SELECT cantitate FROM cos WHERE id = ?",
                interogareSQL2 = "UPDATE cos SET cantitate = ? WHERE id = ?";

        int idCos = verificareDateIntInt(interogareSQL0, idUtilizator, idProdus);
        if(idCos != 0) {
            int cantitateGasita = interogareAutomata4(interogareSQL1, idCos, "cantitate"),
                cantitateNoua = cantitateGasita + cantitate;
            interogareAutomata2(interogareSQL2, 1, cantitateNoua, 2, idCos);
            return true;
        } else {
            return false;
        }
    }

    //  ADAUGAREA PRODUSELOR IN COS

    public void adaugareProduseCos(int idUtilizator, int idProdus, int cantitate) {
        String interogareSQL0 = "INSERT INTO cos (utilizator_id, produs_id, cantitate) VALUES (?, ?, ?)";
        try {
            if(!verificareProdusCos(idUtilizator, idProdus, cantitate)) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setInt(1, idUtilizator);
                getInterogareAvansata().setInt(2, idProdus);
                getInterogareAvansata().setInt(3, cantitate);
                getInterogareAvansata().executeUpdate();
            }
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Produsul nu a putut fi achiziționat.");
        }
    }

    //  ZONA DE SETARI PENTRU CARDURI BANCARE

    public ArrayList<String> preluareBanci() {
        ArrayList<String> banci = new ArrayList<>();
        String interogareSQL = "SELECT denumire FROM banci",
        banca = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                banca = getRs().getString("denumire");
                banci.add(banca);
            }
            return banci;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la găsirea bancilor.");
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return new ArrayList<>();
    }

    public ArrayList<String> preluareTipCarduri(String banca) {
        ArrayList<String> tipuriCarduri = new ArrayList<>();
        String interogareSQL = "SELECT tipuri_carduri.denumire FROM (tipuri_carduri INNER JOIN carduri_continut ON carduri_continut.tip_card_id = tipuri_carduri.id) " +
                "INNER JOIN banci ON carduri_continut.banca_id = banci.id WHERE banci.denumire = ? ORDER BY tipuri_carduri.denumire",
                tipCard = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setString(1, banca);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                tipCard = getRs().getString("denumire");
                tipuriCarduri.add(tipCard);
            }
            return tipuriCarduri;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la găsirea tipurilor de carduri bancare.");
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return new ArrayList<>();
    }

    public ArrayList<String> preluareTipMonede() {
        ArrayList<String> tipuriMonede = new ArrayList<>();
        String interogareSQL = "SELECT denumire FROM tipuri_monede",
                tipMoneda = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                tipMoneda = getRs().getString("denumire");
                tipuriMonede.add(tipMoneda);
            }
            return tipuriMonede;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la găsirea tipurilor de monede.");
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return new ArrayList<>();
    }

    public ArrayList<String> preluareMoneda(String tipMoneda) {
        ArrayList<String> monede = new ArrayList<>();
        String interogareSQL = "SELECT monede.denumire FROM monede INNER JOIN tipuri_monede ON monede.tip_moneda_id = tipuri_monede.id WHERE tipuri_monede.denumire = ? " +
                "ORDER BY monede.denumire",
                moneda = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setString(1, tipMoneda);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                moneda = getRs().getString("denumire");
                monede.add(moneda);
            }
            return monede;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la găsirea monedelor.");
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return new ArrayList<>();
    }

    public String preluarePozaCard(String banca, String tipCard) {
        String interogareSQL = "SELECT carduri_continut.link_poza AS link_poza " +
                "FROM (tipuri_carduri INNER JOIN carduri_continut ON carduri_continut.tip_card_id = tipuri_carduri.id) " +
                "INNER JOIN banci ON carduri_continut.banca_id = banci.id " +
                "WHERE banca.denumire = ? AND tipuri_carduri.denumire = ?",
                link_poza = "";
        try {
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
            getInterogareAvansata().setString(1, banca);
            getInterogareAvansata().setString(2, tipCard);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                link_poza = getRs().getString("link_poza");
            }
            return link_poza;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la găsirea pozei pentru cardul bancar.");
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
        return "";
    }


    public int inserareCardNou(int idUtilizator, String numarCard, String numePosesor,
                                String dataExpirare, String banca, String tipCard, String moneda) {
        String interogareSQL0 = "SELECT id FROM carduri WHERE numar = ?",
                interogareSQL1 = "INSERT INTO carduri (utilizator_id, numar, nume_posesor, data_expirare, moneda_id, card_continut_id)  VALUES " +
                "(?, ?, ?, ?, (SELECT monede.id FROM monede WHERE denumire = ?), (SELECT carduri_continut.id FROM " +
                "(tipuri_carduri INNER JOIN carduri_continut ON carduri_continut.tip_card_id = tipuri_carduri.id) " +
                "INNER JOIN banci ON carduri_continut.banca_id = banci.id WHERE banci.denumire = ? AND tipuri_carduri.denumire = ?)) " +
                "RETURNING id";
        int idCardInserat = 0, idCardGasit = verificareDateFalse(interogareSQL0, numarCard);
        try {
            if(idCardGasit == 0) {
                DateTimeFormatter dataIntrare = DateTimeFormatter.ofPattern("MM/yyyy");
                YearMonth anLuna = YearMonth.parse(dataExpirare, dataIntrare);
                LocalDate data = anLuna.atDay(30);
                DateTimeFormatter dataIesire = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataFormatata = data.format(dataIesire);

                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setInt(1, idUtilizator);
                getInterogareAvansata().setString(2, numarCard);
                getInterogareAvansata().setString(3, numePosesor);
                getInterogareAvansata().setDate(4, Date.valueOf(dataFormatata));
                getInterogareAvansata().setString(5, moneda);
                getInterogareAvansata().setString(6, banca);
                getInterogareAvansata().setString(7, tipCard);
                setRs(getInterogareAvansata().executeQuery());
                while(getRs().next()) {
                    idCardInserat = getRs().getInt("id");
                }
                interfataBackEnd.infoRasp(String.format("Ați reușit să adăugați cardul bancar cu numărul %s.", numarCard), true, 3);
                return 1;
            } else {
                interfataBackEnd.infoStadiu(String.format("Există deja un card cu numărul %s.", numarCard));
                return 0;
            }

        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la adăugarea cardului bancar.");
            return 0;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public void stergereCardBancar(int idUtilizator, String numarCard) {
        String interogareSQL0 = "SELECT id FROM carduri WHERE numar = ?",
                interogareSQL1 = "DELETE FROM carduri WHERE numar = ?",
                interogareSQL2 = "SELECT utilizator_id AS id FROM carduri WHERE numar = ?";
        int idCardGasit = verificareDateFalse(interogareSQL0, numarCard),
        idUtilizatorGasit = verificareDateFalse(interogareSQL2, numarCard);
        try {
            if(idCardGasit != 0 && (idUtilizatorGasit == idUtilizator)) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setString(1, numarCard);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp("Ați reușit să ștergeți cardul bancar.", true, 3);
            } else {
                interfataBackEnd.infoStadiu(String.format("Nu există cardul bancar cu numărul %s sau nu vă aparține.", numarCard));
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu(String.format("Nu a putut fi șters cardul bancar cu numărul %s.", numarCard));
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public int modificareCardBancar(int idUtilizator, String numarCard, String numePosesor,
                                     String dataExpirare, String banca, String tipCard, String moneda) {
        String interogareSQL0 = "SELECT id FROM carduri WHERE numar = ?",
                interogareSQL1 = "UPDATE carduri SET numar = ?, nume_posesor = ?, data_expirare = ?, " +
                        "moneda_id = (SELECT monede.id FROM monede WHERE denumire = ?), " +
                        "card_continut_id = (SELECT carduri_continut.id FROM " +
                        "(tipuri_carduri INNER JOIN carduri_continut ON carduri_continut.tip_card_id = tipuri_carduri.id) " +
                        "INNER JOIN banci ON carduri_continut.banca_id = banci.id WHERE banci.denumire = ? AND tipuri_carduri.denumire = ?) " +
                        "WHERE carduri.id = ?",
                interogareSQL2 = "SELECT utilizator_id AS id FROM carduri WHERE numar = ?";
        int idCardGasit = verificareDateFalse(interogareSQL0, numarCard),
         idUtilizatorGasit = verificareDateFalse(interogareSQL2, numarCard);
        try {
            if(idCardGasit != 0 && (idUtilizator == idUtilizatorGasit)) {
                DateTimeFormatter dataIntrare = DateTimeFormatter.ofPattern("MM/yyyy");
                YearMonth anLuna = YearMonth.parse(dataExpirare, dataIntrare);
                LocalDate data = anLuna.atDay(30);
                DateTimeFormatter dataIesire = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dataFormatata = data.format(dataIesire);

                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setString(1, numarCard);
                getInterogareAvansata().setString(2, numePosesor);
                getInterogareAvansata().setDate(3, Date.valueOf(dataFormatata));
                getInterogareAvansata().setString(4, moneda);
                getInterogareAvansata().setString(5, banca);
                getInterogareAvansata().setString(6, tipCard);
                getInterogareAvansata().setInt(7, idCardGasit);
                getInterogareAvansata().executeUpdate();
                interfataBackEnd.infoRasp(String.format("Ați reușit să actualizați cardul bancar cu numărul %s.", numarCard), true, 3);
                return 1;
            } else {
                interfataBackEnd.infoStadiu(String.format("Nu există cardul bancar cu numărul %s sau nu vă aparține.", numarCard));
                return 0;
            }

        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Ne pare rău, dar există o eroare la actualizarea cardului bancar.");
            return 0;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    //  ROLURI UTILIZATORI
    public void initializareRoluriUtilizator() {
        String interogareSQL0 = "SELECT nume.denumire AS nume, utilizatori.id AS id, roluri.denumire AS rol, roluri.descriere AS descriere " +
                "FROM ((utilizatori INNER JOIN roluri ON utilizatori.rol_id = roluri.id) " +
                "INNER JOIN nume_utilizatori ON nume_utilizatori.utilizator_id = utilizatori.id) " +
                "INNER JOIN nume ON nume_utilizatori.nume_id = nume.id";
        ArrayList<UtilizatorRol> setUtilizatori = new ArrayList<>();
        try {
            setColoane(new ArrayList<>());
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                UtilizatorRol utilizator = new UtilizatorRol(getRs().getString("nume"),
                        getRs().getInt("id"), getRs().getString("rol"),
                        getRs().getString("descriere"));
                setUtilizatori.add(utilizator);
            }

            setRsMetaData(getRs().getMetaData());
            int nrCol = getRsMetaData().getColumnCount();
            for(int i = 1; i <= nrCol; i++)
                getColoane().add(getRsMetaData().getColumnName(i));
            UtilizatoriRol setCompletUtilizatoriRol = new UtilizatoriRol(setUtilizatori, getColoane());
            if (interfataBackEnd != null) {
                interfataBackEnd.infoUtilizatoriRol(setCompletUtilizatoriRol);
            }

        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogare());
        }
    }

    private int actualizareUtilizatori(UtilizatorRol utilizator) {
        String interogareSQL0 = "SELECT id FROM utilizatori WHERE id = ?",
                interogareSQL1 = "UPDATE utilizatori SET rol_id = (SELECT id FROM roluri WHERE denumire = ?) WHERE utilizatori.id = ?";
        int idUtilizatorGasit = verificareDateFalseInt(interogareSQL0, utilizator.getId());
        try {
            if(idUtilizatorGasit != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setString(1, utilizator.getRol());
                getInterogareAvansata().setInt(2, utilizator.getId());
                getInterogareAvansata().executeUpdate();
            } else {
                interfataBackEnd.infoStadiu(String.format("Nu există un utilizator cu id-ul %d.", utilizator.getId()));
            }
            return idUtilizatorGasit;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar există o eroare la actualizarea rolului pentru utilizatorul %s.", utilizator.getNume()));
            return idUtilizatorGasit;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    private int actualizareRoluri(UtilizatorRol utilizator) {
        String interogareSQL0 = "SELECT id FROM roluri WHERE denumire = ?",
                interogareSQL1 = "UPDATE roluri SET descriere = ? WHERE denumire = ?";
        int idRolGasit = verificareDateFalse(interogareSQL0, utilizator.getRol());
        try {
            if(idRolGasit != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setString(1, utilizator.getRolDescriere());
                getInterogareAvansata().setString(2, utilizator.getRol());
                getInterogareAvansata().executeUpdate();
            } else {
                interfataBackEnd.infoStadiu(String.format("Nu există un rol cu denumirea %s.", utilizator.getRol()));
            }
            return idRolGasit;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu(String.format("Ne pare rău, dar există o eroare la actualizarea rolului cu denumirea %s.", utilizator.getRol()));
            return 0;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public int actualizareRoluriUtilizator(UtilizatorRol utilizator) {
        int stadiuActualizareUtilizator = actualizareUtilizatori(utilizator),
        stadiuActualizareRoluri = actualizareRoluri(utilizator);
        if(stadiuActualizareRoluri != 0 && stadiuActualizareUtilizator != 0) {
            return 1;
        }
        return 0;
    }

    //  COS DE CUMPARATURI & MODIFICARE COS

    public void initializareCosCumparaturiTabel(int idUtilizator) {
        String interogareSQL0 = "SELECT cos.id AS \"ID\", CONCAT(producatori.denumire || ' ' || produse.denumire || ' ' || produse.continut " +
                "|| ' ' || abrevieri_um.denumire) AS \"Produs\", produse.pret AS \"Pret Buc\", cos.cantitate AS \"Cantitate\" " +
                "FROM (((((unitati_masura INNER JOIN produse ON produse.unitate_masura_id = unitati_masura.id) " +
                "INNER JOIN prescurtare_um ON prescurtare_um.um_id = unitati_masura.id) " +
                "INNER JOIN abrevieri_um ON prescurtare_um.abreviere_um_id = abrevieri_um.id) " +
                "INNER JOIN producatori ON produse.producator_id = producatori.id) " +
                "INNER JOIN cos ON cos.produs_id = produse.id) " +
                "INNER JOIN utilizatori ON cos.utilizator_id = utilizatori.id " +
                "WHERE utilizatori.id = ?";
        ArrayList<ProdusCos> setProduse = new ArrayList<>();
        int cantitate = 0, idProdusCos = 0;
        double pretBuc = 0;
        String denProdus = "";
        try {
            setColoane(new ArrayList<>());
            setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
            getInterogareAvansata().setInt(1, idUtilizator);
            setRs(getInterogareAvansata().executeQuery());
            while(getRs().next()) {
                idProdusCos = getRs().getInt("ID");
                denProdus = getRs().getString("Produs");
                pretBuc = getRs().getDouble("Pret Buc");
                cantitate = getRs().getInt("Cantitate");
                ProdusCos produs = new ProdusCos(idProdusCos, denProdus, pretBuc, cantitate);
                setProduse.add(produs);
            }

            setRsMetaData(getRs().getMetaData());
            int nrCol = getRsMetaData().getColumnCount();
            for(int i = 1; i <= nrCol; i++)
                getColoane().add(getRsMetaData().getColumnName(i));
            ProduseCos ProduseCos = new ProduseCos(setProduse, getColoane());
            if (interfataBackEnd != null) {
                interfataBackEnd.infoProduseCos(ProduseCos);
            }

        } catch(SQLException eroare) {
            eroare.printStackTrace();
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogare());
        }
    }

    public void stergereProdusCosCumparaturi(int idUtilizator, int idProdusCos) {
        String interogareSQL0 = "SELECT id FROM cos WHERE id = ?", 
                interogareSQL1 = "DELETE FROM cos WHERE id = ?";
        try {
            System.out.println(idProdusCos);
            int idProdusCosGasit = verificareDateFalseInt(interogareSQL0, idProdusCos);
            System.out.println(idProdusCosGasit);
            if(idProdusCosGasit != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setInt(1, idProdusCos);
                getInterogareAvansata().executeUpdate();
                initializareCosCumparaturiTabel(idUtilizator);
            } else {
                interfataBackEnd.infoStadiu("Produsul nu a putut fi găsit în coșul de cumpărături.");
            }
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eroare la ștergerea produsului din coșul de cumpărături.");
        } finally {
            inchidereResursa(getInterogare());
        }
    }

    public int actualizareCosCumparaturiTabel(int idProdusCos, int cantitate) {
        String interogareSQL0 = "SELECT id FROM cos WHERE id = ?",
                interogareSQL1 = "UPDATE cos SET cantitate = ? " +
                "WHERE cos.id = ?";
        try {
            int idProdusCosGasit = verificareDateFalseInt(interogareSQL0, idProdusCos);
            if(idProdusCosGasit != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL1));
                getInterogareAvansata().setInt(1, cantitate);
                getInterogareAvansata().setInt(2, idProdusCos);
                getInterogareAvansata().executeUpdate();
                return 1;
            } else {
                interfataBackEnd.infoStadiu("Nu există produsul în coșul de cumpărături.");
                return 0;
            }
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eroare la actualizarea produsului din coșul de cumpărături.");
            return 0;
        } finally {
            inchidereResursa(getInterogare());
        }
    }

    //  PLASARE COMANDA

    public ArrayList<String> initializareCarduriBancare(int idUtilizator) {
        String interogareSQL0 = "SELECT numar FROM carduri WHERE utilizator_id = ?";
        ArrayList<String> listaCarduri = new ArrayList<>();
        try {
            setInterogareAvansata(getConexiune().prepareStatement(
                    interogareSQL0,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ));
            getInterogareAvansata().setInt(1, idUtilizator);
            setRs(getInterogareAvansata().executeQuery());
            if(getRs().next()) {
                getRs().beforeFirst();
                while(getRs().next()) {
                    listaCarduri.add(getRs().getString("numar"));
                }
            } else {
                interfataBackEnd.infoStadiu("Nu există carduri bancare înregistrate. Vă rugăm să vă adăugați un card bancar!");
            }
            return listaCarduri;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eroare la preluarea cardurilor bancare.");
            return listaCarduri;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    public String preluareAbreviereMonedaCard(int idUtilizator, String nrCard) {
        String interogareSQL2 = "SELECT abrevieri.denumire AS abreviere FROM " +
                "((( utilizatori INNER JOIN carduri ON carduri.utilizator_id = utilizatori.id) " +
                "INNER JOIN monede ON carduri.moneda_id = monede.id) " +
                "INNER JOIN monede_abrevieri ON monede_abrevieri.moneda_id = monede.id) " +
                "INNER JOIN abrevieri ON monede_abrevieri.abreviere_id = abrevieri.id " +
                "WHERE utilizatori.id = ? AND carduri.numar = ?",
                interogareSQL1 = "SELECT id FROM carduri WHERE numar = ?",
        interogareSQL0 = "SELECT id FROM utilizatori WHERE id = ?",
        abreviere = "";
        try {
            int idUtilizatorGasit = verificareDateFalseInt(interogareSQL0, idUtilizator),
                    idCardGasit = verificareDateFalse(interogareSQL1, nrCard);
            if(idUtilizatorGasit != 0 && idCardGasit != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(
                        interogareSQL2,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                ));
                getInterogareAvansata().setInt(1, idUtilizator);
                getInterogareAvansata().setString(2, nrCard);
                setRs(getInterogareAvansata().executeQuery());
                if(getRs().next()) {
                    getRs().beforeFirst();
                    while(getRs().next()) {
                        abreviere = getRs().getString("abreviere");
                    }
                } else {
                    interfataBackEnd.infoStadiu("Nu a putut fi găsită o abreviere pentru moneda cardului.");
                }
            } else {
                interfataBackEnd.infoStadiu("Nu a putut fi găsit cardul sau ID-ul dvs. de utilizator din cauza unei erori interne.");
            }
            return abreviere;
        } catch(SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eroare la preluarea abrevierii monedei.");
            return abreviere;
        } finally {
            inchidereResursa(getRs());
            inchidereResursa(getInterogareAvansata());
        }
    }

    private String formatareMesajPlasareComanda(String numeFamilie, String prenume, int nrLocuinta,
                                                String strada, String localitate, String judet,
                                                String tara, int indexTipTransport, double pretTotal,
                                                String nrCard, ArrayList<ProdusCos> listaProduse,
                                                String abreviereMoneda) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Vă mulțumim pentru comanda plasată, %s %s!\n", numeFamilie, prenume));
        if(indexTipTransport == 1) {
            sb.append(String.format("Mai jos sunt afișate produsele achiziționate cu cardul %s, care pot fi ridicate din magazinul nostru.\n\n",
                    nrCard));
        } else if(indexTipTransport == 2) {
            sb.append(String.format("Mai jos sunt afișate produsele achiziționate cu cardul %s, care vor fi livrate la adresa %s, %s, %s, %s, numărul %d.\n\n",
                    nrCard, tara, judet, localitate, strada, nrLocuinta));
        }
        for(ProdusCos produs : listaProduse) {
            sb.append(String.format("Produs: %s, cantitate: %d, pret: %.2f %s.\n", produs.getProdus(), produs.getCantitate(),
                    produs.getCantitate() * produs.getPret(), abreviereMoneda));
        }
        sb.append(String.format("Preț total: %.2f %s.\n", pretTotal, abreviereMoneda));
        return sb.toString();
    }

    private void stergereDateCos(int idUtilizator) {
        String interogareSQL = "DELETE FROM cos WHERE utilizator_id = ?";
        try {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL));
                getInterogareAvansata().setInt(1, idUtilizator);
                getInterogareAvansata().executeUpdate();
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Coșul de cumpărături nu a putut fi golit");
        } finally {
            inchidereResursa(getInterogareAvansata());
        }
    }

    public int plasareComanda(int idUtilizator, int indexTipTransport, double pretTotal, String nrCard, ArrayList<ProdusCos> listaProduse,
            String abreviereMoneda) {
        String interogareSQL0 = "SELECT nume_familie.denumire AS nume_familie, prenume.denumire AS prenume, " +
                "adrese_email.denumire AS adresa_email, locuinte.numar AS numar, " +
                "strazi.denumire AS strada, localitati.denumire AS localitate, " +
                "judete.denumire AS judet, tari.denumire AS tara " +
                "FROM ((((((((((utilizatori INNER JOIN contact_email ON contact_email.utilizator_id = utilizatori.id) " +
                "INNER JOIN adrese_email ON contact_email.adresa_email_id = adrese_email.id) " +
                "INNER JOIN utilizator_nume_familie ON utilizator_nume_familie.utilizator_id = utilizatori.id) " +
                "INNER JOIN nume_familie ON utilizator_nume_familie.nume_familie_id = nume_familie.id) " +
                "INNER JOIN utilizator_prenume ON utilizator_prenume.utilizator_id = utilizatori.id) " +
                "INNER JOIN prenume ON utilizator_prenume.prenume_id = prenume.id) " +
                "INNER JOIN locuinte ON utilizatori.locuinta_id = locuinte.id) " +
                "INNER JOIN strazi ON utilizatori.strada_id = strazi.id)" +
                "INNER JOIN localitati ON utilizatori.localitate_id = localitati.id) " +
                "INNER JOIN judete ON localitati.judet_id = judete.id)" +
                "INNER JOIN tari ON judete.tara_id = tari.id "+
                "WHERE utilizatori.id = ?",
                interogareSQL1 = "SELECT id FROM utilizatori WHERE id = ?",
                numeFamilie = null, prenume = null, adresaEmail = null, strada = null, localitate = null,
        judet = null, tara = null;
        int nrLocuinta = 0,  idUtilizatorGasit = verificareDateFalseInt(interogareSQL1, idUtilizator), stadiu = 0;
        try {
            if(idUtilizatorGasit != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setInt(1, idUtilizator);
                setRs(getInterogareAvansata().executeQuery());
                if (getRs().next()) {
                    numeFamilie = getRs().getString("nume_familie");
                    prenume = getRs().getString("prenume");
                    adresaEmail = getRs().getString("adresa_email");
                    nrLocuinta= getRs().getInt("numar");
                    strada = getRs().getString("strada");
                    localitate = getRs().getString("localitate");
                    judet = getRs().getString("judet");
                    tara = getRs().getString("tara");
                }
                EmailBot.EmailResult rezultat = EmailBot.verificaSiTrimiteEmail(DBConfig.DB_ADRESA_EXPEDITOR, DBConfig.DB_PAROLA_EXPEDITOR, adresaEmail, "Comandă plasată",
                        formatareMesajPlasareComanda(numeFamilie, prenume, nrLocuinta, strada, localitate, judet, tara, indexTipTransport,
                                pretTotal, nrCard, listaProduse, abreviereMoneda));
                if(!rezultat.isSuccess()) {
                    JOptionPane.showMessageDialog(null, "Nu a putut fi trimis și un E-mail de confirmare din cauza unor probleme.",
                            "Atenție", JOptionPane.WARNING_MESSAGE);
                }
                interfataBackEnd.infoRasp("Vă mulțumim pentru achiziționarea produselor!", true, 2);
                stergereDateCos(idUtilizatorGasit);
                initializareCosCumparaturiTabel(idUtilizatorGasit);
                return 1;
            } else {
                interfataBackEnd.infoStadiu("Comanda nu a putut fi plasată.");
                return 0;
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eroare la plasarea comenzii.");
            return 0;
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
    }

    //  DIALOG

    public void inserareDateDialog(int idUtilizator, String codIntrebare) {
        String interogareSQL0 = "INSERT INTO DIALOG (utilizator_id, intrebare_id) VALUES (?, (SELECT id FROM intrebari WHERE cod = ?))",
                interogareSQL1 = "SELECT id FROM utilizatori WHERE id = ?",
                interogareSQL2 = "SELECT id FROM intrebari WHERE cod = ?";
        int idIntrebareGasita = verificareDateFalse(interogareSQL2, codIntrebare),  idUtilizatorGasit = verificareDateFalseInt(interogareSQL1, idUtilizator);
        try {
            if(idUtilizatorGasit != 0 && idIntrebareGasita != 0) {
                setInterogareAvansata(getConexiune().prepareStatement(interogareSQL0));
                getInterogareAvansata().setInt(1, idUtilizator);
                getInterogareAvansata().setString(2, codIntrebare);
                getInterogareAvansata().executeUpdate();
            }
        } catch (SQLException eroare) {
            interfataBackEnd.infoStadiu("Există o eroare la inserarea dialogului.");
        } finally {
            inchidereResursa(getInterogareAvansata());
            inchidereResursa(getRs());
        }
    }
}


