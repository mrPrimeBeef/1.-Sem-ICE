package domæne;

import produkt.Ret;
import utility.DBConnector;
import utility.TextUI;
import utility.Search;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class seRetter {
    private DBConnector dbConnector;
    private  Program program;
    private  TextUI ui;
    private  ArrayList<String> valg;
    private  ArrayList<String> valg1;
    private ArrayList<String> ingredienser;
    private Search søg;
    private Statement statement;


    public seRetter(DBConnector dbConnector, TextUI ui){
        this.dbConnector = dbConnector;
        this.ui = ui;

        valg = new ArrayList<>();
        valg.addAll(Arrays.asList("Se retter: ", "Tilføj ret", "Tilbage" + "\n"));

        valg1 = new ArrayList<>();
        valg1.addAll(Arrays.asList("ja", "nej" + "\n"));

        ingredienser = new ArrayList<>();
    }

    private void søgRet(String søgRet)
    {
        List<String> retter = new ArrayList<>();
        Connection conn = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void kørRetter(){
        String søgRet; // bruges til switch-casen
        int input = ui.promptChoice(valg, "Vælge 1 handling",1,3);
        switch (input) {
            case 1:
                søgRet = ui.promptText("Søg efter en ret: "); // spaghetti m. kødsovs



                break;
            case 2:
                String ingrediens;
                String retNavn = ui.promptText("Hvad hedder retten?");
            int choice = 1;
                while(choice == 1){
                ingrediens = ui.promptText("ingrediens navn og mængde?");
                ingredienser.add(ingrediens);
                choice = ui.promptChoice(valg1, "Er der flere?",1,2);
            }
                Ret ret = new Ret(retNavn, ingredienser);
                dbConnector.tilføjTilRetter(ret);
                dbConnector.tilføjTilIngredienser(retNavn, ingredienser);
                kørRetter();

                break;

                case 3:
                program = new Program(dbConnector, ui);
                program.kørProgram();
                break;
        }

    }
}
