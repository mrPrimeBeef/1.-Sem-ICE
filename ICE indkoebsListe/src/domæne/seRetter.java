package domæne;

import produkt.Ret;
import utility.DBConnector;
import utility.TextUI;

import java.util.ArrayList;
import java.util.Arrays;

public class seRetter {
    private DBConnector dbConnector;
    private  Program program;
    MadplanKlasse madplanKlasse;
    private  TextUI ui;
    private  ArrayList<String> valg;
    private  ArrayList<String> valg1;
    private ArrayList<String> ingredienser;
    private ArrayList<String> mængder;

    public seRetter(DBConnector dbConnector, TextUI ui, MadplanKlasse madplanKlasse){
        this.dbConnector = dbConnector;
        this.ui = ui;
        this.madplanKlasse = madplanKlasse;

        valg = new ArrayList<>();
        valg.addAll(Arrays.asList("Se retter", "Tilføj ny ret", "Føj ret til madplanen",  "Tilbage" + "\n"));
        valg1 = new ArrayList<>();
        valg1.addAll(Arrays.asList("ja", "nej" + "\n"));
        ingredienser = new ArrayList<>();
        mængder = new ArrayList<>();
    }
    public void kørRetter(){
        int input = ui.promptChoice(valg, "Vælge 1 handling",1,valg.size());
        switch (input) {
            case 1:
                ui.printRetterOgIngredienser(dbConnector.visRetter(dbConnector.getBrugerNavn()));
                kørRetter();
                break;
            case 2:
                String ingrediens;
                String mængde;
                String retNavn = ui.promptText("Hvad hedder retten?");
            int choice = 1;
                while(choice == 1){
                ingrediens = ui.promptText("ingrediens navn");
                ingredienser.add(ingrediens);
                mængde = ui.promptText("ingrediens mængde");
                mængder.add(mængde);
                choice = ui.promptChoice(valg1, "Er der flere?",1,2);
            }
                Ret ret = new Ret(retNavn.toLowerCase(), ingredienser);
                dbConnector.tilføjRet(ret, dbConnector.getBrugerNavn(),false);
                dbConnector.tilføjTilIngredienser(retNavn.toLowerCase(), ingredienser, mængder);
                kørRetter();
                break;
                case 3:
                    ui.printRetterOgIngredienser(dbConnector.visRetter(dbConnector.getBrugerNavn()));
                    String navnPåRet = ui.promptText("Skriv navnet på retten").toLowerCase();
                    madplanKlasse.tilføjTilIndskøbslisten(dbConnector.hentIngredienser(navnPåRet));
                    choice = ui.promptChoice(dbConnector.hentMadplan(dbConnector.getBrugerNavn()),"Hvilken dag skal retten på?",1,dbConnector.hentMadplan(dbConnector.getBrugerNavn()).size());
                    dbConnector.tilføjTilMadplanListe(navnPåRet,choice);
                    kørRetter();
                break;
                case 4:
                program = new Program(dbConnector, ui);
                program.kørProgram();
                break;
        }

    }
}
