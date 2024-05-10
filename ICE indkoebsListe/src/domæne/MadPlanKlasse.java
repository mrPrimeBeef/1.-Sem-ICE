package domæne;

import produkt.AProdukt;
import produkt.Ret;
import utility.TextUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MadPlanKlasse {
    Program program;
    TextUI ui = new TextUI();
    HashSet<Ret> retter;
    ArrayList<String> madplan;
    ArrayList<String> dage;
    ArrayList<String> valg;
    HashSet<AProdukt> fryseListe = new HashSet<>();
    HashSet<AProdukt> køleskabsListe = new HashSet<>();
    HashMap<AProdukt, String> indkøbsListe = new HashMap<>();


    public MadPlanKlasse(DBConnector dbConnector) {
        madplan = new ArrayList<>();

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
               program.kørProgram();
           break;
       }
    }

    public void seMadplan(String msg){
        ui.displayList(madplan,msg + "\n");
    }
}
