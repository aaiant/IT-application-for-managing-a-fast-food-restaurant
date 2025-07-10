package org.example;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String DB_URL = dotenv.get("DB_URL");
    public static final String DB_UTILIZATOR = dotenv.get("DB_UTILIZATOR");
    public static final String DB_PAROLA = dotenv.get("DB_PAROLA");
    public static final String DB_ADRESA_EXPEDITOR = dotenv.get("DB_ADRESA_EXPREDITOR");
    public static final String DB_PAROLA_EXPEDITOR = dotenv.get("DB_PAROLA_EXPEDITOR");
    public static final String CHEIE_VIGENERE = dotenv.get("CHEIE_VIGENERE");
    public static final String CHEIE_AES = dotenv.get("CHEIE_AES");
}
