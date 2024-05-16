package domæne;

import produkt.Vare;
import utility.DBConnector;
import utility.TextUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Inventar {
    private DBConnector dbConnector;
    private Program program;
    private TextUI ui;
    private ArrayList<String> valg;


    public Inventar(DBConnector dbConnector, TextUI ui){
        this.dbConnector = dbConnector;
        this.ui = ui;

        valg = new ArrayList<>();
        valg.addAll(Arrays.asList("Tilføj vare", "Slet vare", "Se inventar", "Tilbage" + "\n"));

    }

    public void kørInventar(){
        HashMap<Vare, String> inventarList = dbConnector.hentInventar(dbConnector.getBrugerNavn());
        ui.displayListHashMap(inventarList);
       // inventarList.clear();
        int input = ui.promptChoice(valg, "Vælg en handling", 1, valg.size());


        switch (input) {
            case 1:
                String vareNavn = ui.promptText("Skriv varens navn").toLowerCase();
                int mængde = ui.promptNumeric("Mængde?");
                String afdeling = ui.promptText("Skriv hvilken afdeling varen befinder sig i").toLowerCase();
                Vare vare = new Vare(vareNavn, mængde, afdeling);

                dbConnector.tilføjTilInventarListe(vare, mængde);
                kørInventar();
                break;
            case 2:
                sletVareInventar();
                kørInventar();
                break;
            case 3:
                kørInventar();
                break;
            case 4:
                program = new Program(dbConnector, ui);
                program.kørProgram();
                break;

        }
    }

    public void sletVareInventar(){
        String inventarslet =  ui.promptText("Hvilken vare vil du slette?");
        int antal = ui.promptNumeric("hvor mange vil du slette?");
        dbConnector.fjernInventar(inventarslet.toLowerCase(),antal);

    }


}
