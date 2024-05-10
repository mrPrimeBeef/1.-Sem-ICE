package controller;

import model.DBConnector;
import model.Bruger;
//import View.Form;

import javax.swing.*;
import java.io.File;

public class UserController {
    // database file
    private String databaseFile;
    private DBConnector database;

    public UserController(Form form) {
        this.database = new Database();
        this.form = form;

        // submit user
        this.form.submitUsers(e -> {
            String firstname = this.form.getFirstname().trim();
            String password = this.form.getLastname().trim();

            // simple validations
            if(firstname.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "First Name Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if(password.isEmpty()) {
                JOptionPane.showMessageDialog(this.form, "Last Name Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.database.addUser(new User(firstname, password));
            this.database.saveUser(new File(databaseFile));
            this.form.reset(true);
        });

        // load users
        /*
        this.form.viewUsers(e -> {
            this.userDetails.getUsers(this.database.loadUsers(new File(databaseFile)));
        });*/
    }
}
