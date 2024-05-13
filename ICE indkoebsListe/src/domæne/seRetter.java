package domæne;

import produkt.Ret;
import utility.DBConnector;
import utility.TextUI;

import java.util.ArrayList;
import java.util.Arrays;

public class seRetter {
    private DBConnector dbConnector;
    private  Program program;
    private  TextUI ui;
    private  ArrayList<String> valg;
    private  ArrayList<String> valg1;
    private ArrayList<String> ingredienser;

    public seRetter(DBConnector dbConnector, TextUI ui){
        this.dbConnector = dbConnector;
        this.ui = ui;

        valg = new ArrayList<>();
        valg.addAll(Arrays.asList("Se retter: ", "Tilføj ret", "Tilbage" + "\n"));
        valg1 = new ArrayList<>();
        valg1.addAll(Arrays.asList("ja", "nej" + "\n"));
        ingredienser = new ArrayList<>();
    }
    public void kørRetter(){
        int input = ui.promptChoice(valg, "Vælge 1 handling",1,3);
        switch (input) {
            case 1:

                break;
            case 2:
                String ingrediens;
                String retNavn = ui.promptText("Hvad hedder retten?");
            int choice = 1;
                while(choice == 1){
                ingrediens = ui.promptText("ingrediens navn og mængde?");
                ingredienser.add(ingrediens);
                choice = ui.promptChoice(valg1, "Er der flere?",1,2);
            }
                Ret ret = new Ret(retNavn, ingredienser);
                dbConnector.tilføjTilRetter(ret);
                dbConnector.tilføjTilIngredienser(retNavn, ingredienser);
                kørRetter();

                break;

                case 3:
                program = new Program(dbConnector, ui);
                program.kørProgram();
                break;
        }

    }
}
