package org.example.Model;

import de.mkammerer.argon2.*;

public class Argon2_Alg {
    private Argon2 parolaStocata;

    public Argon2_Alg() {
        this.parolaStocata = Argon2Factory.create();
    }

    public String hashParola(String parolaText) {
        return parolaStocata.hash(2, 65536, 1, parolaText);
    }

    public boolean verificareParola(String parolaText, String parolaBD) {
        return parolaStocata.verify(parolaBD, parolaText);
    }
}
