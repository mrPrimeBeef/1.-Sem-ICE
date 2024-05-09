package domæne;

import produkt.AProdukt;
import produkt.Ret;
import produkt.Vare;
import utility.TextUI;

import java.util.*;

public class Program {
    private String navn;
    private Bruger nuværendeBruger;
    private TextUI ui;
    AProdukt produkt;
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
                kørIndkøbsliste();
                break;
            case 2: // seMadplan
                //ui.displayMessage("Oversigt af din madplan");
                kørMadplan();
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

    private void kørIndkøbsliste() {

        // Test
        Vare vare1 = new Vare(10, "argurk", 1);
        Vare vare2 = new Vare(2, "æble",1);
        Vare vare3 = new Vare(12, "mælk",1);
        indkøbsListe.put(vare1, "Grønt");
        indkøbsListe.put(vare2, "Grønt");
        indkøbsListe.put(vare3, "Mejeri");
        // Test

        ArrayList<String> liste = new ArrayList();
        liste.add("Se Indkøbsliste");
        liste.add("Lav ny");
        liste.add("tilbage");
        int choice = ui.promptChoice(liste, "\nvælg 1 handling", 1, 3);

        if (choice == 1) {
            brugIndkøbsliste(indkøbsListe);
        } else if (choice == 2) {
            lavIndkøbsListe();
        } else if (choice == 3) {
            kørProgram();
        }
    }

    public void seIndkøbsListe(HashMap<AProdukt, String> indkøbsliste){
        ui.displayListHashMap(indkøbsliste, "");
    }

    public void brugIndkøbsliste(HashMap<AProdukt, String> indkøbsliste) {
        ArrayList<String> valg = new ArrayList();
        valg.add("Tiløj vare");
        valg.add("Slet vare");
        valg.add("Køb vare");
        valg.add("Se Indkøbsseddel");
        valg.add("tilbage");
        seIndkøbsListe(indkøbsliste);
        int input = ui.promptChoice(valg, "\nvælg 1 handling", 1, 5);


        if (input == 1) {
            føjTilIndkøbsliste(input, valg, indkøbsliste);
        } else if (input == 2) {
            sletVare();
        } else if (input == 3) {
            købVare();
        } else if (input == 4) {
            seIndkøbsListe(indkøbsliste);
            brugIndkøbsliste(indkøbsliste);
        } else if (input == 5) {
            kørProgram();
        }
    }

    public void føjTilIndkøbsliste(int input, ArrayList<String> valg,HashMap<AProdukt, String> indkøbsliste) {
        while (input == 1) {
            String vareNavn = ui.promptText("Skrive varens navn");
            int pris = ui.promptNumeric("Skriv varens pris");
            int mængde = ui.promptNumeric("Mængde?");
            Vare vare = new Vare(pris, vareNavn,mængde);
            String afdeling = ui.promptText("Skriv hvilken afdeling varen befinder sig i");

            indkøbsliste.put(vare, afdeling);

            input = ui.promptChoice(valg, "", 1, 4);
            if (input == 1) {
                føjTilIndkøbsliste(input, valg, indkøbsliste);
            } else if (input == 2) {
                sletVare();
            } else if (input == 3) {
                købVare();
            } else if (input == 4) {
                kørIndkøbsliste();
            } else if (input == 5) {
                brugIndkøbsliste(indkøbsliste);
            }
        }
    }

   public void lavIndkøbsListe(){

   }

   public void sletVare(){
       String slet =  ui.promptText("Hvilken vare til du slette?");
       // find vare i listen, så den kan slettes
       indkøbsListe.remove(slet);
   }

   public void  købVare(){
        String køb =  ui.promptText("Hvilken vare til du købe?");
       // der skal laves Aprodukt
        //køleskabsListe.add(køb);
        indkøbsListe.remove(køb);
   }


        public void kørMadplan () {
            madplan = new HashSet<>();
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
