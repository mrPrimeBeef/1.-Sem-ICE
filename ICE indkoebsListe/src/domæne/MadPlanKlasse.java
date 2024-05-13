package domæne;

import produkt.Ret;
import utility.DBConnector;
import utility.TextUI;
import java.util.ArrayList;
import java.util.Arrays;


public class MadPlanKlasse {
    private DBConnector dbConnector;
    private  Program program;
    private  TextUI ui;
    private  ArrayList<String> madplan;
    private  ArrayList<String> dage;
    private  ArrayList<String> valg;
    private ArrayList<String> retter;


    public MadPlanKlasse(DBConnector dbConnector, TextUI ui) {
        madplan = new ArrayList<>();
        madplan.addAll(Arrays.asList("Mandag: ", "Tirsdag: ", "Onsdag: ", "Torsdag: ","Fredag: ","Lørdag: ","Søndag: " + "\n"));

        dage = new ArrayList<>();
        dage.addAll(Arrays.asList("Mandag: ", "Tirsdag: ", "Onsdag: ", "Torsdag: ","Fredag: ","Lørdag: ","Søndag: " + "\n"));

        this.dbConnector = dbConnector;
        this.ui = ui;

        valg = new ArrayList<>();
        valg.addAll(Arrays.asList("Tilføj ret", "Slet ret", "Tilbage"));



    }

    public void kørMadplan () {
       int choice;
        String ret;
        ui.displayList(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Her er planen for ugen");

       int input = ui.promptChoice(valg, "Vælge 1 handling",1,3);
       switch (input) {
           case 1:
               ret = ui.promptText("Skriv navnet på retten").toLowerCase();
               choice = ui.promptChoice(madplan,"Hvilken dag skal retten på?",1,7);
               dbConnector.tilføjTilMadplanListe(ret,choice);
               kørMadplan();
           break;
           case 2:
               choice = ui.promptChoice(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Hvilken dag skal retten fjernes fra?",1,7);
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
