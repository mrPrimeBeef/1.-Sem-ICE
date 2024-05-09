package domæne;

import produkt.AProdukt;
import produkt.Ret;
import utility.TextUI;

import java.util.*;

public class Program {
    private String navn;
    private Bruger nuværendeBruger;
    private TextUI ui;
    AProdukt produkt;
    IndkøbsListeKlasse indkøbsListeklasse;
    MadPlanKlasse madPlanKlasse = new MadPlanKlasse();
    ArrayList<Bruger> brugerList;
    ArrayList<String> startMenu;
    ArrayList<String> mainMenu;
   HashSet<Ret> retter;
   HashSet<String> madplan;
   HashSet<AProdukt> fryseListe = new HashSet<>();
   HashSet<AProdukt> køleskabsListe = new HashSet<>();
   HashMap<AProdukt, String> indkøbsListe = new HashMap<>();

    public Program() {
        this.navn = navn;

        brugerList = new ArrayList();

        // Test
        Bruger rolf = new Bruger("Rolf", "Rolf");
        brugerList.add(rolf);
        // Test

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
                this.opretBruger();
                kørProgram();

                break;
            case 2:
                if (this.logInd()) {
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


//                      Bruger Metoder
        public Bruger opretBruger () {
            while (true) {
                String brugerNavn = ui.promptText("Skriv dit bruger navn");

                if (checkCredentialAvailability(brugerNavn)) {
                    String password = ui.promptText("Skriv et kodeord");
                    Bruger nyBruger = new Bruger(brugerNavn, password);
                    brugerList.add(nyBruger);
                    setnuværendeBruger(nyBruger);
                    ui.displayMessage("Bruger oprettet");
                    return nyBruger;
                } else {
                    ui.displayMessage("bruger navn er allered i brug, vælg et andet.");

                }
            }
        }

        public boolean logInd () {
            while (true) {
                String username = ui.promptText("Skriv dit bruger navn");
                String password = ui.promptText("Skriv dit kodeord");

                for (Bruger u : brugerList) {
                    if (username.equals(u.getBrugerNavn()) && password.equals(u.getKodeOrd())) {
                        setnuværendeBruger(u);
                        ui.displayMessage("Log ind succesfuld");
                        return true;

                    }
                }
                ui.displayMessage("Forkert brugernavn eller kodeord, prøv igen");

            }
        }


        boolean checkCredentialAvailability (String credential){
            for (Bruger bruger : brugerList) {
                if (bruger.getBrugerNavn().equals(credential)) {
                    return false; // Credential exists
                }
            }
            ui.displayMessage(credential + " er ledig");
            return true; // Credential is available
        }

        public void setnuværendeBruger (Bruger nuværendeBruger){
            this.nuværendeBruger = nuværendeBruger;
        }

    }
