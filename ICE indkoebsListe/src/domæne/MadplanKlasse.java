package domæne;

import produkt.Ret;
import produkt.Vare;
import utility.DBConnector;
import utility.TextUI;
import java.util.ArrayList;
import java.util.Arrays;


public class MadplanKlasse {
    private DBConnector dbConnector;
    private  Program program;
    private  TextUI ui;
    private  ArrayList<String> valg;


    public MadplanKlasse(DBConnector dbConnector, TextUI ui) {
        this.dbConnector = dbConnector;
        this.ui = ui;

        valg = new ArrayList<>();
        valg.addAll(Arrays.asList("Tilføj ret", "Slet ret", "Tilbage"));

    }

    public void kørMadplan() {
       int choice;
        String ret;
        ui.displayList(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Her er planen for ugen: ");
       int input = ui.promptChoice(valg, "Vælge 1 handling",1,3);

       switch (input) {
           case 1:
               ret = ui.promptText("Skriv navnet på retten").toLowerCase();
               choice = ui.promptChoice(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Hvilken dag skal retten på?",1,dbConnector.hentMadplan(dbConnector.getBrugerNavn()).size());
               dbConnector.tilføjTilMadplanListe(ret,choice);
               tilføjTilIndskøbslisten(dbConnector.hentIngredienser(ret));
               kørMadplan();
           break;
           case 2:
               choice = ui.promptChoice(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Hvilken dag skal retten fjernes fra?",1,dbConnector.hentMadplan(dbConnector.getBrugerNavn()).size());
               String fjernedRet = dbConnector.fjernRet(choice);
               dbConnector.fjernFraMadplanListe(fjernedRet, choice);
               dbConnector.opdaterIndkøbslisten(dbConnector.hentIngredienser(fjernedRet));
               dbConnector.fjernNulMængdeVarerFraIndkøbslisten();
               kørMadplan();
           break;
           case 3:
               Program program = new Program(dbConnector, ui);
               program.kørProgram();
           break;
       }
    }

    public void tilføjTilIndskøbslisten(ArrayList<Vare> ingredienser){
        for (Vare element : ingredienser) {
            dbConnector.gemTilListe(element);
        }
    }

}
