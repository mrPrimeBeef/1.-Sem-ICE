package domain;

import produkt.AProdukt;
import produkt.Ret;
import produkt.Vare;
import utility.TextUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IndkøbsListeKlasse {
    Program program;
    TextUI ui;
    HashSet<Ret> retter;
    HashSet<String> madplan;
    HashSet<AProdukt> fryseListe = new HashSet<>();
    HashSet<AProdukt> køleskabsListe = new HashSet<>();
    HashMap<AProdukt, String> indkøbsListe = new HashMap<>();

    public void kørIndkøbsliste() {

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
            program.kørProgram();
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
            program.kørProgram();
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
}
