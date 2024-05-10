package domæne;

import produkt.AProdukt;
import produkt.Ret;
import utility.TextUI;

import java.util.*;

public class Program {
    private String navn;
    private Bruger nuværendeBruger;
    private TextUI ui;
    private AProdukt produkt;
    private IndkøbsListeKlasse indkøbsListeklasse;
    private DBConnector dbConnector;
    private MadPlanKlasse madPlanKlasse;
    private ArrayList<String> startMenu;
    private ArrayList<String> mainMenu;
    private HashSet<Ret> retter;
    private HashSet<AProdukt> køleskabsListe = new HashSet<>();

    public Program(DBConnector dbConnector) {

        indkøbsListeklasse = new IndkøbsListeKlasse(dbConnector);
        madPlanKlasse = new MadPlanKlasse(dbConnector);

        this.navn = navn;

        this.ui = new TextUI();

        startMenu = new ArrayList<>();

        startMenu.add("Opret bruger");
        startMenu.add("Log ind");

        mainMenu = new ArrayList<>();
        mainMenu.add("Se indkøbsliste");
        mainMenu.add("Se Madplan");
        mainMenu.add("Se Køleskab");
        mainMenu.add("Se retter");
        mainMenu.add("Log ud");


    }

    //                      Start Program
    public void logIndProgram() {

        ui.displayMessage("Velkommen");

        int choice;
        choice = ui.promptChoice(startMenu, "Opret en bruger eller log ind", 1, 2);

        switch (choice) {
            case 1:
                 // Opret en instans af DBConnector-klassen
                dbConnector.opretBruger();
                kørProgram();

                break;
            case 2:

                if (dbConnector.logInd()) {
                    kørProgram();
                }
                break;
        }
    }

    public void kørProgram() {
        int menuChoice;

        menuChoice = ui.promptChoice(mainMenu, "", 1, 5);
        switch (menuChoice) {
            case 1: // se Indkøbslister
                indkøbsListeklasse.kørIndkøbsliste();
                break;
            case 2: // seMadplan
                madPlanKlasse.kørMadplan();
                break;
            case 3: // seKøleskab
                //ui.displayMessage("Oversigt af dine ingredienser i køleskabet");
                kørKøleskab();
                break;
            case 4: // se Retter
                //ui.displayMessage("Oversigt af dine tilgængelige retter");
                kørRetter();
                break;
            case 5: // log Ud
                afslutProgram();
                break;
        }
    }



        public void kørKøleskab () {
            køleskabsListe = new HashSet<>();
        }

        public void kørRetter () {
            retter = new HashSet<>();
        }

        private void afslutProgram () {
            nuværendeBruger = null;
            ui.displayMessage("Du er nu logget ud");
        }

    }
