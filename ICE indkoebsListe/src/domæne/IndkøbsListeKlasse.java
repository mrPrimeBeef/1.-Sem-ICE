package domæne;

import produkt.AProdukt;
import produkt.Ret;
import produkt.Vare;
import utility.DBConnector;
import utility.TextUI;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IndkøbsListeKlasse {
    private Program program;
    private TextUI ui;
    private DBConnector dbConnector;
    private HashMap<AProdukt, String> indkøbsListe = new HashMap<>();

    public IndkøbsListeKlasse(DBConnector dbConnector, TextUI ui) {
       this.dbConnector = dbConnector;
       this.ui = ui;
    }

    public void kørIndkøbsliste() {

        ArrayList<String> listeValg = new ArrayList();
        listeValg.add("Se Indkøbsliste");
        listeValg.add("Lav ny");
        listeValg.add("tilbage");
        int choice = ui.promptChoice(listeValg, "\nvælg 1 handling", 1, 3);

        if (choice == 1) {
            brugIndkøbsliste();
        } else if (choice == 2) {
            lavIndkøbsListe();
        } else if (choice == 3) {
            program.kørProgram();
        }
    }

    public void seIndkøbsListe(HashMap<AProdukt, String> liste){
        liste = dbConnector.visIndkøbsListe(dbConnector.getBrugerNavn());
        ui.displayListHashMap(liste, "");
    }

    public void brugIndkøbsliste() {
        ArrayList<String> valg = new ArrayList();
        valg.add("Tiløj vare");
        valg.add("Slet vare");
        valg.add("Køb vare");
        valg.add("Se Indkøbsseddel");
        valg.add("tilbage");
        seIndkøbsListe(dbConnector.visIndkøbsListe(dbConnector.getBrugerNavn()));
        int input = ui.promptChoice(valg, "\nvælg 1 handling", 1, 5);


        if (input == 1) {
            føjTilIndkøbsliste(input, valg);
        } else if (input == 2) {
            sletVare();
            brugIndkøbsliste();
        } else if (input == 3) {
            købVare();
        } else if (input == 4) {
            seIndkøbsListe(dbConnector.visIndkøbsListe(dbConnector.getBrugerNavn()));
            brugIndkøbsliste();
        } else if (input == 5) {
            Program program = new Program(dbConnector, ui);
            program.kørProgram();
        }
    }

    public void føjTilIndkøbsliste(int input, ArrayList<String> valg) {
        while (input == 1) {
            String vareNavn = ui.promptText("Skrive varens navn");
            int pris = ui.promptNumeric("Skriv varens pris");
            int mængde = ui.promptNumeric("Mængde?");
            String afdeling = ui.promptText("Skriv hvilken afdeling varen befinder sig i");
            Vare vare = new Vare(vareNavn,mængde, pris, afdeling);


            dbConnector.gemTilListe(vare);


            input = ui.promptChoice(valg, "", 1, 4);
            if (input == 1) {
                føjTilIndkøbsliste(input, valg);
            } else if (input == 2) {
                sletVare();
                brugIndkøbsliste();
            } else if (input == 3) {
                købVare();
            } else if (input == 4) {
                kørIndkøbsliste();
            } else if (input == 5) {
                brugIndkøbsliste();
            }
        }
    }

    public void lavIndkøbsListe(){

    }

    public void sletVare(){
        String slet =  ui.promptText("Hvilken vare til du slette?");
        dbConnector.fjernVAre(slet);

    }

    public void  købVare(){
        String køb =  ui.promptText("Hvilken vare til du købe?");
        // der skal laves Aprodukt
        //køleskabsListe.add(køb);
        indkøbsListe.remove(køb);
    }
}
