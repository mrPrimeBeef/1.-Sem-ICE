package controller;

import domæne.Bruger;
import domæne.DBConnector;
import View.Form;

import javax.swing.*;

public class Controller {
    private DBConnector database;
    private Form form;

    public Controller(Form form, DBConnector database) {
        this.database = database;
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


            //this.form.reset(true);
        });

        // Kald på DBConnector
        Bruger bruger = database.opretBruger();
        if (bruger != null)
        {
            JOptionPane.showMessageDialog(this.form, "Brugeren er oprettet");
        } else
        {
            JOptionPane.showMessageDialog(this.form, "Fejl. prøv igen");
        }
        this.form.reset(true);
    }
}
