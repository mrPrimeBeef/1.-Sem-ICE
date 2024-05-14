package domæne;

import produkt.Ret;
import utility.DBConnector;
import utility.TextUI;

import java.util.*;

public class Program {
    private IndkøbsListeKlasse indkøbsListeklasse;
    private DBConnector dbConnector;
    private Inventar inventar;
    private TextUI ui;
    private Program program;
    private MadPlanKlasse madPlanKlasse;
    private domæne.seRetter seRetter;
    private ArrayList<String> startMenu;
    private ArrayList<String> mainMenu;
    private ArrayList<String> valg;
    private ArrayList<String> listeValg;
    private HashSet<Ret> retter;

    public Program(DBConnector dbConnector, TextUI ui) {

        indkøbsListeklasse = new IndkøbsListeKlasse(dbConnector,ui);
        madPlanKlasse = new MadPlanKlasse(dbConnector,ui);
        inventar = new Inventar(dbConnector,ui);
        seRetter = new seRetter(dbConnector,ui);

        this.dbConnector = dbConnector;
        this.ui = ui;

        startMenu = new ArrayList<>();
        startMenu.addAll(Arrays.asList("Opret bruger", "Log ind"));

        mainMenu = new ArrayList<>();
        mainMenu.addAll(Arrays.asList("Se indkøbsliste", "Se Madplan","Se inventar", "Se retter","Log ud"));

        valg = new ArrayList();
        valg.addAll(Arrays.asList("Tiløj vare","Slet vare", "Køb vare", "Se Indkøbsseddel","tilbage"));

        listeValg = new ArrayList();
        listeValg.addAll(Arrays.asList("Se: Indkøbsliste","tilbage" ));
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
            case 3: // seInventar
                //kørInventar();
                break;
            case 4: // se seRetter
                seRetter.kørRetter();
                break;
            case 5: // log Ud
                afslutProgram();
                break;
        }
    }


        private void afslutProgram () {
            ui.displayMessage("Du er nu logget ud");

        }



    }
