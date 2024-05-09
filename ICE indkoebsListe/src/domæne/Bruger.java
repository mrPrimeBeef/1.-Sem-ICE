package domæne;

public class Bruger {
    private String brugerNavn;
    private String kodeOrd;


    public Bruger(String brugerNavn, String kodeOrd) {
        this.brugerNavn = brugerNavn;
        this.kodeOrd = kodeOrd;

    }
    public String getBrugerNavn() {
        return brugerNavn;
    }
    public String getKodeOrd() {
        return kodeOrd;
    }

}
