package controller;

import domæne.Bruger;
import domæne.DBConnector;
import View.LoginForm;

import javax.swing.*;

public class Controller {
    private DBConnector database;
    private LoginForm form;

    public Controller(LoginForm form, DBConnector database) {
        this.database = database;
        this.form = form;

        // opret bruger
        this.form.opretBruger(e -> {
            String brugernavn = this.form.hentBrugernavn().trim();
            String kodeord = this.form.hentKodeord().trim();

            // simple validations checking null input
            if(brugernavn.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Brugernavn er påkrævet", "Fejl",
                        JOptionPane.ERROR_MESSAGE);
            } else if(kodeord.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Kodeord er påkrævet.", "Fejl",
                        JOptionPane.ERROR_MESSAGE);
            }
            //this.form.reset(true);
        });

        // log ind bruger
        this.form.logIndBruger(e -> {
            String brugernavn = this.form.hentBrugernavn().trim();
            String kodeord = this.form.hentKodeord().trim();

            // simple validations
            if (brugernavn.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Brugernavn er påkrævet", "Fejl",
                        JOptionPane.ERROR_MESSAGE);
            } else if (kodeord.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Kodeord er påkrævet.", "Fejl",
                        JOptionPane.ERROR_MESSAGE);
            }
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
