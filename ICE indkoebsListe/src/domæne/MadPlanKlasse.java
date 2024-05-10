package domæne;

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


    public MadPlanKlasse(DBConnector dbConnector, TextUI ui) {
        madplan = new ArrayList<>();


        this.dbConnector = dbConnector;
        this.ui = ui;


        madplan.add("Mandag: ");
        madplan.add("Tirsdag: ");
        madplan.add("Onsdag: ");
        madplan.add("Torsdag: ");
        madplan.add("Fredag: ");
        madplan.add("Lørdag: ");
        madplan.add("Søndag: " + "\n");

        dage = new ArrayList<>();
        dage.addAll(Arrays.asList("Mandag: ", "Tirsdag: ", "Onsdag: ", "Torsdag: ","Fredag: ","Lørdag: ","Søndag: " + "\n"));

        valg = new ArrayList<>();

        valg.add("Tilføj ret");
        valg.add("Slet ret");
        valg.add("Tilbage");

    }

    public void kørMadplan () {
       int choice;
        String ret;
        seMadplan("Sådan se ugen ud: ");

       int input = ui.promptChoice(valg, "Vælge 1 handling",1,3);
       switch (input) {
           case 1:
               ret = ui.promptText("Skriv navnet på retten").toLowerCase();
               choice = ui.promptChoice(madplan,"Hvilken dag skal retten på?",1,7);
               madplan.set(choice-1, madplan.get(choice-1) + ret);
               kørMadplan();
           break;
           case 2:
               choice = ui.promptChoice(madplan,"Hvilken dag skal retten fjernes fra?",1,7);
               madplan.set(choice-1, dage.get(choice-1));
               kørMadplan();
           break;
           case 3:
               Program program = new Program(dbConnector, ui);
               program.kørProgram();
           break;
       }
    }

    public void seMadplan(String msg){
        ui.displayList(madplan,msg + "\n");
    }
}
