package domæne;

import utility.DBConnector;
import utility.TextUI;
import java.util.ArrayList;
import java.util.Arrays;


public class MadplanKlasse {
    private DBConnector dbConnector;
    private  Program program;
    private  TextUI ui;
    private  ArrayList<String> madplan;
    private  ArrayList<String> dage;
    private  ArrayList<String> valg;
    private ArrayList<String> retter;


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
               kørMadplan();
           break;
           case 2:
               choice = ui.promptChoice(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Hvilken dag skal retten fjernes fra?",1,dbConnector.hentMadplan(dbConnector.getBrugerNavn()).size());
               dbConnector.fjernRet(choice);
               kørMadplan();
           break;
           case 3:
               Program program = new Program(dbConnector, ui);
               program.kørProgram();
           break;
       }
    }

}
