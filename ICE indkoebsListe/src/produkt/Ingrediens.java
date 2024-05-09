package produkt;

public class Ingrediens extends AProdukt {
    String vareNavn;
    int mængde;
    //String holdbarHed;

    public Ingrediens(String vareNavn, int mængde) {
        this.vareNavn = vareNavn;
        this.mængde = mængde;
    }
}
