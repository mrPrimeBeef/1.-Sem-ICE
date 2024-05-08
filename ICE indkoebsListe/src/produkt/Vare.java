package produkt;

public class Vare extends AProdukt{
    private int pris;
    String vareNavn;
    //String holdbarHed;

    public Vare(int pris, String vareNavn) {
        this.pris = pris;
        this.vareNavn = vareNavn;
        //this.holdbarHed = holdbarHed;
    }

    @Override
    public String toString() {
        return "" + vareNavn;
    }
}
