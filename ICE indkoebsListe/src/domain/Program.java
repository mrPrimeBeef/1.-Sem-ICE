package domain;

import produkt.AProdukt;
import produkt.Ret;
import utility.DBConnector;
import utility.GUI;

import java.util.*;

public class Program {
    private String navn;
    private Bruger nuværendeBruger;
    private GUI gui;
    private DBConnector dbConnector = new DBConnector();
    private AProdukt produkt;
    private IndkøbsListeKlasse indkøbsListeklasse;
    private MadPlanKlasse madPlanKlasse = new MadPlanKlasse();
    private ArrayList<String> startMenu;
    private ArrayList<String> mainMenu;
    private HashSet<Ret> retter;
    private HashSet<AProdukt> køleskabsListe = new HashSet<>();

    public Program() {
        this.navn = navn;

        this.gui = gui;

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
    public void startProgram() {

        gui.displayMessage("Velkommen");

        int choice;
        choice = gui.promptChoice(startMenu, "Opret en bruger eller log ind", 1, 2);

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

        menuChoice = gui.promptChoice(mainMenu, "", 1, 5);
        switch (menuChoice) {
            case 1: // se Indkøbslister
                indkøbsListeklasse.kørIndkøbsliste();
                break;
            case 2: // seMadplan
                madPlanKlasse.kørMadplan();
                break;
            case 3: // seKøleskab
                kørKøleskab();
                break;
            case 4: // se Retter
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
            gui.displayMessage("Du er nu logget ud");
        }
        
        public void opretBruger()
        {
            dbConnector.opretBruger();
            gui.displayMessage("Brugeren er oprettet");
            
        }

    }
