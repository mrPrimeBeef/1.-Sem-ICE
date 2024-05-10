package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginForm extends JPanel { // Startside

    private JTextField brugernavnFelt;
    private JTextField kodeordFelt;

    private JButton opretBrugerKnap;
    private JButton logIndBrugerKnap;

    public LoginForm() {

        JLabel firstnameLabel = new JLabel("Brugernavn: ");
        JLabel lastnameLabel = new JLabel("Kodeord: ");

        brugernavnFelt = new JTextField(25);
        kodeordFelt = new JTextField(25);

        opretBrugerKnap = new JButton("Opret bruger");
        opretBrugerKnap.setPreferredSize(new Dimension(278, 40));
        logIndBrugerKnap = new JButton("Log ind");
        logIndBrugerKnap.setPreferredSize(new Dimension(278, 40));


        // database connection


        // space between fields
        Insets fieldsInset = new Insets(0, 0, 10, 0);
        // space between buttons
        Insets buttonInset = new Insets(20,0,0,0);

        // uses Grid Bag Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = fieldsInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(firstnameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        add(brugernavnFelt, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(lastnameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;

        add(kodeordFelt, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = buttonInset;

        add(opretBrugerKnap, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = buttonInset;

        add(logIndBrugerKnap, gridBagConstraints);
    }

    // getters
    public String hentBrugernavn() {
        return brugernavnFelt.getText();
    }

    public String hentKodeord() {
        return kodeordFelt.getText();
    }

    public void opretBruger(ActionListener actionListener) {
        opretBrugerKnap.addActionListener(actionListener);
    }

    public void logIndBruger(ActionListener actionListener) {
        logIndBrugerKnap.addActionListener(actionListener);
    }

    // reset fields
    public void reset(boolean bln) {
        if(bln) {
            brugernavnFelt.setText("");
            kodeordFelt.setText("");
        }
    }
}
