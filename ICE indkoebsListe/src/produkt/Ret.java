package produkt;

import java.util.List;

public class Ret {
    private String navn;
    private List<String> ingredienser;

    public Ret(String navn, List<String> ingredienser) {
        this.navn = navn;
        this.ingredienser = ingredienser;
    }

    public String getNavn() {
        return navn;
    }

    public List<String> getIngredienser() {
        return ingredienser;
    }

}
