package de.dumpeldown.dartcounter.language;

public class LanguageGerman {
    private static LanguageGerman languageGerman;

    public String punktzahl = "Runterspielen von";
    public String spielerAnzahl = "Spieleranzahl";
    public String letzer = "Letzter";
    public String durchschnitt = "Durchschnitt";
    public String hoechster = "Hoechster";
    public String anzahlWuerfe = "Gesamtwuerfe";
    public String speichern = "Eintragen";
    public String reset = "ZURUECKSETZEN";
    public String spieler = "Spieler";

    public static LanguageGerman getInstance() {
        if(languageGerman == null) languageGerman = new LanguageGerman();
        return languageGerman;
    }
}
