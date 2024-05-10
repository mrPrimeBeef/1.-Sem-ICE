package controller;

import domæne.DBConnector;
import View.Form;

import javax.swing.*;

public class Controller {
    // database file
    //private String databaseFile;
    private DBConnector database;
    private Form form;

    public Controller(Form form) {
        //this.database = new Database();
        this.form = form;

        // opret bruger
        this.form.opretBruger(e -> {
            String brugernavn = this.form.hentBrugernavn().trim();
            String kodeord = this.form.hentKodeord().trim();

            // simple validations
            if(brugernavn.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Brugernavn er påkrævet", "Fejl",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if(kodeord.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Kodeord er påkrævet.", "Fejl",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }



            //this.database.addUser(new User(brugernavn, kodeord));
            //his.database.saveUser(new File(databaseFile));
            this.form.reset(true);
        });
    }
}
