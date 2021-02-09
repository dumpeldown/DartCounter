package de.dumpeldown.dartcounter.language;

public class LanguageEnglish {
    private static LanguageEnglish languageEnglish;

    public String punktzahl = "Play down from";
    public String spielerAnzahl = "Amount of players";
    public String letzer = "Last";
    public String durchschnitt = "Average";
    public String hoechster = "Highest";
    public String anzahlWuerfe = "Throws";
    public String speichern = "Save";
    public String reset = "RESET";
    public String spieler = "Player";

    public static LanguageEnglish getInstance() {
        if(languageEnglish == null) languageEnglish = new LanguageEnglish();
        return languageEnglish;
    }

}
