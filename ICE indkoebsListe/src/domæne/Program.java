package domæne;

import produkt.AProdukt;
import produkt.Ret;
import produkt.Vare;
import utility.DBConnector;
import utility.TextUI;

import java.util.*;

public class Program {
    private IndkøbsListeKlasse indkøbsListeklasse;
    private DBConnector dbConnector;
    private TextUI ui;
    private Program program;
    private MadPlanKlasse madPlanKlasse;
    private ArrayList<String> startMenu;
    private ArrayList<String> mainMenu;
    private ArrayList<String> valg;
    private ArrayList<String> listeValg;
    private HashSet<Ret> retter;
    private HashSet<AProdukt> køleskabsListe = new HashSet<>();

    public Program(DBConnector dbConnector, TextUI ui) {

        indkøbsListeklasse = new IndkøbsListeKlasse(dbConnector,ui);
        madPlanKlasse = new MadPlanKlasse(dbConnector,ui);


        this.dbConnector = dbConnector;
        this.ui = ui;

        startMenu = new ArrayList<>();
        startMenu.addAll(Arrays.asList("Opret bruger", "Log ind"));

        mainMenu = new ArrayList<>();
        mainMenu.addAll(Arrays.asList("Se indkøbsliste", "Se Madplan","Se Køleskab", "Se retter","Log ud"));

        ArrayList<String> valg = new ArrayList();
        valg.addAll(Arrays.asList("Tiløj vare","Slet vare", "Køb vare", "Se Indkøbsseddel","tilbage"));

        ArrayList<String> listeValg = new ArrayList();
        listeValg.addAll(Arrays.asList("Se: Indkøbsliste", "Lav ny","tilbage" ));
    }

    //                      Start Program
    public void logIndProgram() {

        ui.displayMessage("Velkommen");

        int choice;
        choice = ui.promptChoice(startMenu, "Opret en bruger eller log ind", 1, 2);

        switch (choice) {
            case 1:
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
                indkøbsListeklasse.kørIndkøbsliste(listeValg, valg);
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
            ui.displayMessage("Du er nu logget ud");

        }

    }
