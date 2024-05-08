import produkt.AProdukt;
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
    Collection<HashSet<Ret>> retter;
    Collection<HashSet<String>> madplan;
    Collection<HashSet<AProdukt>> fryseListe = new HashSet<>();
    Collection<HashSet<AProdukt>> køleskabsListe;
    Collection<TreeMap<AProdukt, String>> indkøbsliste;
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
    choice = ui.promptChoice(startMenu, "Opret en bruger eller log ind",1,2);

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



    menuChoice  = ui.promptChoice(mainMenu,"",1,5);
    switch(menuChoice){
        case 1: // seIndkøbsliste
            ui.displayMessage("Oversigt af din indkøbsliste");
            kørIndkøbsliste();
            break;
        case 2: // seMadplan
            ui.displayMessage("Oversigt af din madplan");
            kørMadplan();
            break;
        case 3: // seKøleskab
            ui.displayMessage("Oversigt af dine ingredienser i køleskabet");
            kørKøleskab();
            break;
        case 4: // se Retter
            ui.displayMessage("Oversigt af dine tilgængelige retter");
            kørRetter();
            break;
        case 5: // log Ud
            afslutProgram();
            break;
    }
}

    private void kørIndkøbsliste() {

        // Test
        HashMap<AProdukt, String> prøve = new HashMap<>();
        Vare vare1 = new Vare(10,"argurk");
        Vare vare2 = new Vare(2,"æble");
        Vare vare3 = new Vare(12,"mælk");
        prøve.put(vare1, "Grønt");
        prøve.put(vare2, "Grønt");
        prøve.put(vare3, "Mejeri");
        // Test

        ArrayList<String> lister = new ArrayList();
        lister.add("Indkøbsliste");
        lister.add("Lav ny");
        int choice = ui.promptChoice(lister, "vælg 1",1,2);

        if (choice == 1) {
            // Group the keys by their values
            Map<String, List<AProdukt>> groupedMap = new HashMap<>();
            for (Map.Entry<AProdukt, String> entry : prøve.entrySet()) {
                String value = entry.getValue();
                groupedMap.computeIfAbsent(value, k -> new ArrayList<>()).add(entry.getKey());
            }

            // Display the grouped map
            for (Map.Entry<String, List<AProdukt>> group : groupedMap.entrySet()) {
                System.out.println(group.getKey() + ":");
                for (AProdukt produkt : group.getValue()) {
                    System.out.println(produkt);
                }
            }
        } else if (choice == 2) {

        }
    }
    public void kørMadplan(){
        madplan = new HashSet<>();
    }
    public void kørKøleskab(){
        køleskabsListe = new HashSet<>();
    }

    public void kørRetter(){
        retter = new HashSet<>();
    }

private void afslutProgram(){
    nuværendeBruger = null;
    ui.displayMessage("Du er nu logget ud");
}


//                      Bruger Metoder
    public Bruger opretBruger(){
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
                    ui.displayMessage("Log ind succesfuld");
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
