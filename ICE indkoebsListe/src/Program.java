import produkt.AProdukt;
import utility.TextUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Collection;

public class Program {
    private String navn;
    private Bruger nuværendeBruger;
    private TextUI ui;
    AProdukt produkt;
    ArrayList<Bruger> brugerList;
    ArrayList<String> startMenu;
    ArrayList<String> mainMenu;
    Collection<HashSet<Ret>> retter = new HashSet<>();
    Collection<HashSet<AProdukt>> fryseListe = new HashSet<>();
    Collection<HashSet<AProdukt>> køleskabsListe = new HashSet<>();
    Collection<TreeMap<AProdukt, String>> indkøbsliste = new ArrayList<>();
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
    mainMenu.add("1) Se indkøbsliste");
    mainMenu.add("2) Se Madplan");
    mainMenu.add("3) Se Køleskab");
    mainMenu.add("4) Se retter");
    mainMenu.add("5) Log ud");

    menuChoice = ui.promptChoice(startMenu, "Vælg fra menuen");
    switch(menuChoice){
        case 1: // seIndkøbsliste
            ui.displayMessage("Oversigt af din indkøbsliste");
            break;
        case 2: // seMadplan
            ui.displayMessage("Oversigt af din madplan");
            break;
        case 3: // seKøleskab
            ui.displayMessage("Oversigt af dine ingrendienser i køleskabet");
            break;
        case 4: // se Retter
            ui.displayMessage("Oversigt af dine tilgængelige retter");
            break;
        case 5: // log Ud
            afslutProgram();
            break;

    }

}

private void afslutProgram()
{
    ui.displayMessage("Du er nu logget ud");
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

    public void logUd(){
        nuværendeBruger = null;
        ui.displayMessage("Du er nu logget ud \n \n \n ");
    }

}
