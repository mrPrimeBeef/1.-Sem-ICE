import produkt.AProdukt;
import utility.TextUI;

import java.util.ArrayList;
import java.util.HashSet;

public class Program {
    private String navn;
    private Bruger nuværendeBruger;
    private TextUI ui;
    AProdukt produkt;
    ArrayList<Bruger> brugerList;
    ArrayList<String> startMenu;
    ArrayList<String> mainMenu;
    HashSet<Ret> retter;
    HashSet<AProdukt> fryserListe;
    HashSet<AProdukt> køleskabsListe;
    // TreeMap<AProdukt, sted> indkøbsliste;

    Program(){
        this.navn = navn;

        brugerList = new ArrayList();
                        // Test
        Bruger rolf = new Bruger("Rolf","Rolf");
        brugerList.add(rolf);
                        // Test

        this.ui = new TextUI();

        startMenu = new ArrayList<>();

        startMenu.add("Opret bruger");
        startMenu.add("Log ind");
    }

//                      Start Program
public void logIndProgram(){

    ui.displayMessage("Velkommen");

    int choice;
    choice = ui.promptChoiceLogin(startMenu, "Opret en bruger eller log ind");

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

public void kørProgram(){

    int menuChoice;

    mainMenu = new ArrayList<>();
    mainMenu.add("Se indkøbsliste");
    mainMenu.add("Se Madplan");
    mainMenu.add("Se Køleskab");
    mainMenu.add("Se retter");
    mainMenu.add("Log ud");

    menuChoice = ui.promptChoiceMenu(mainMenu, "Vælg fra menuen");
    switch(menuChoice){
    case 1:
        indkøbsListe();
    break;
    case 2:
        madPlan();
    break;
    case 3:
        køleskab();
    break;
    case 4:
        retter();
    break;
    case 5:
        logUd();
    break;
    default:
    break;
    }

}

    private void indkøbsListe() {
    }
    private void madPlan() {
    }
    private void køleskab() {
    }
    private void retter() {
    }
    public void logUd(){
        nuværendeBruger = null;
        ui.displayMessage("Du er nu logget ud \n \n \n ");
    }






    //                      Bruger Metoder
    public Bruger opretBruger() {
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

    public boolean logInd() {
        while (true) {
            String username = ui.promptText("Skriv dit bruger navn");
            String password = ui.promptText("Skriv dit kodeord");

            for (Bruger u : brugerList) {
                if (username.equals(u.getBrugerNavn()) && password.equals(u.getKodeOrd())) {
                    setnuværendeBruger(u);
                    ui.displayMessage("Login successful");
                    return true;

                }
            }
            ui.displayMessage("Forkert brugernavn eller kodeord, prøv igen");

        }
    }


    boolean checkCredentialAvailability(String credential) {
        for (Bruger bruger : brugerList) {
            if (bruger.getBrugerNavn().equals(credential)) {
                return false; // Credential exists
            }
        }
        ui.displayMessage(credential + " er ledig");
        return true; // Credential is available
    }

    public void setnuværendeBruger(Bruger nuværendeBruger) {
        this.nuværendeBruger = nuværendeBruger;
    }

}