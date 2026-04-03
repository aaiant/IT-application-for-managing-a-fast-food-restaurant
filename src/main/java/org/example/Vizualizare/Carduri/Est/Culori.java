package org.example.Vizualizare.Carduri.Est;

import java.util.EventObject;
import lombok.*;

@Getter @Setter
public class Culori extends EventObject {
    //  Atribute
    private String denumireCuloare, culoareFundal, culoareText;

    public Culori(Object source) {
        super(source);
    }

    public Culori(Object sursa, String culoareFundal, String culoareText) {
        super(sursa);
        this.culoareFundal = culoareFundal;
        this.culoareText = culoareText;
    }

    public Culori(Object sursa, String denumireCuloare, String culoareFundal, String culoareText) {
        super(sursa);
        this.denumireCuloare = denumireCuloare;
        this.culoareFundal = culoareFundal;
        this.culoareText = culoareText;
    }
}
