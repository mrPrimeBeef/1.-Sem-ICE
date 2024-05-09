package produkt;

public class Vare extends AProdukt{
    private int pris;
    private String vareNavn;
    private int mængde;
    //String holdbarHed;


    public Vare(String vareNavn, int mængde, int pris) {
        this.pris = pris;
        this.vareNavn = vareNavn;
        this.mængde = mængde;
    }

    @Override
    public String toString() {
        return "" + vareNavn + ". Mængde: " + mængde;
    }
}
