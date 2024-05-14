package produkt;

public class Vare extends AProdukt{
    private int pris;
    private String vareNavn;
    private int mængde;
    private String afdeling;
    //String holdbarHed;


    public Vare(String vareNavn, int mængde, int pris, String afdeling) {
        this.pris = pris;
        this.vareNavn = vareNavn;
        this.mængde = mængde;
        this.afdeling = afdeling;
    }

    public Vare(String vareNavn, int mængde, String afdeling) {
        this.vareNavn = vareNavn;
        this.mængde = mængde;
        this.afdeling = afdeling;
    }


    public int getPris() {
        return pris;
    }

    public String getVareNavn() {
        return vareNavn;
    }

    public int getMængde() {
        return mængde;
    }

    public String getAfdeling() {
        return afdeling;
    }

    @Override
    public String toString() {
        return "" + vareNavn + ". Mængde: " + mængde;
    }
}
