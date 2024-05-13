package domæne;

import produkt.Vare;
import utility.DBConnector;
import utility.TextUI;


import java.util.ArrayList;
import java.util.HashMap;

public class IndkøbsListeKlasse {
    private Program program;
    private TextUI ui;
    private DBConnector dbConnector;

    public IndkøbsListeKlasse(DBConnector dbConnector, TextUI ui) {
       this.dbConnector = dbConnector;
       this.ui = ui;


    }

    public void kørIndkøbsliste(ArrayList<String> listeValg, ArrayList<String> valg) {
        Program program = new Program(dbConnector, ui);
        brugIndkøbsliste(valg, listeValg, program);
    }


    public void brugIndkøbsliste(ArrayList<String> valg, ArrayList<String> listeValg, Program program) {

        HashMap<Vare, String> list = dbConnector.hentIndkøbsListe(dbConnector.getBrugerNavn());
        ui.displayListHashMap(list);
        list.clear();


        int input = ui.promptChoice(valg, "\nvælg 1 handling", 1, 5);

        switch (input) {
            case 1:
                føjTilIndkøbsliste(input, valg, listeValg, program);
                break;
            case 2:
                sletVare();
                brugIndkøbsliste(valg, listeValg, program);
                break;
            case 3:
                købVare(valg, listeValg, program);
                break;
            case 4:
                brugIndkøbsliste(valg, listeValg, program);
                break;
            case 5:
                program.kørProgram();
                break;
            default:
                // Handle invalid input
                break;
        }
    }


    public void føjTilIndkøbsliste(int input, ArrayList<String> valg, ArrayList<String> listeValg, Program program) {
        do {
            String vareNavn = ui.promptText("Skriv varens navn").toLowerCase();
            int pris = ui.promptNumeric("Skriv varens pris");
            int mængde = ui.promptNumeric("Mængde?");
            String afdeling = ui.promptText("Skriv hvilken afdeling varen befinder sig i").toLowerCase();
            Vare vare = new Vare(vareNavn, mængde, pris, afdeling);

            dbConnector.gemTilListe(vare);

            input = ui.promptChoice(valg, "", 1, 4);

            if (input == 1) {

            } else if (input == 2) {
                sletVare();
                brugIndkøbsliste(valg, listeValg, program);
            } else if (input == 3) {
                købVare(valg, listeValg, program);
            } else if (input == 4) {
                kørIndkøbsliste(listeValg, valg);
            } else if (input == 5) {
                brugIndkøbsliste(valg, listeValg, program);
            }
        } while (input == 1);
    }


    //public void lavIndkøbsListe(ArrayList<String> valg,ArrayList<String> listeValg){
//        String listeNavn = ui.promptText("Skriv navnet på den nye indkøbsliste:");
//
//        kørIndkøbsliste(listeValg, valg);
   // }

    public void sletVare(){
        String slet =  ui.promptText("Hvilken vare til du slette?");
        dbConnector.fjernVare(slet.toLowerCase());

    }

    public void  købVare(ArrayList<String> valg,ArrayList<String> listeValg, Program program){
        String køb =  ui.promptText("Hvilken vare har du købt?").toLowerCase();
        Vare vare = dbConnector.hentVare(dbConnector.getBrugerNavn(), køb);
        dbConnector.tilføjTilInventarListe(vare);
        dbConnector.fjernVare(køb);
        brugIndkøbsliste(valg, listeValg, program);
    }
}
