package utility;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import domæne.Program;


public class GUI extends JFrame {
    private JPanel mainWindow;
    private JPanel logIndPanel = new JPanel();

    private JButton opretBrugerKnap;
    private JButton logIndKnap = new JButton();

    private Program program;
    private JButton logindKnap;






    public GUI() {
        this.program = program;

        // primære vindue "container"
        mainWindow = new JPanel();
        setContentPane(mainWindow);
        mainWindow.setLayout(new FlowLayout());
        setTitle("Eating n Dreaming.");
        setSize(700,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        opretBrugerKnap = new JButton("Opret bruger");
        mainWindow.add(opretBrugerKnap);
        // to knapper til primære vindue
        logindKnap = new JButton("Log ind");
        mainWindow.add(logindKnap);


    }

          // opret en bruger knap til forsiden

    private void addListeners()
    {
        opretBrugerKnap.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                program.opretBruger();

                // POP-UP VINDUE TIL OPRETTELSE AF BRUGER
                JDialog opretBrugerVindue = new JDialog();
                opretBrugerVindue.setLayout(new FlowLayout());
                opretBrugerVindue.setSize(600,600);
                opretBrugerVindue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                opretBrugerVindue.setVisible(true);
                mainWindow.add(opretBrugerKnap);

                // LABEL
                JLabel indtastBrugernavn = new JLabel();
                JLabel indtastKodeord = new JLabel();
                opretBrugerVindue.add(indtastBrugernavn);
                opretBrugerVindue.add(indtastKodeord);
                // INPUT
                JTextField brugernavnFelt = new JTextField(20);
                JTextField kodeordsFelt = new JTextField(20 );
                opretBrugerVindue.add(brugernavnFelt);
                opretBrugerVindue.add(kodeordsFelt);
                // opret knap
                JButton opretKnap = new JButton("Opret");
                opretBrugerVindue.add(opretKnap);

                opretKnap.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String brugernavnValue = brugernavnFelt.getText();
                        String kodeordValue = kodeordsFelt.getText();

                        String demoUser = "demoUser";
                        String demoPass = "demoPass";

                        if (!brugernavnValue.isEmpty() && kodeordValue.isEmpty())
                        {
                            JOptionPane.showMessageDialog(opretBrugerVindue,"Din bruger er oprettet.");
                        }else{
                            JOptionPane.showMessageDialog(opretBrugerVindue,"Brugernavn og/eller kodeord må ikke være tomme.");
                        }
                        /*
                        if (brugernavnValue.equals(demoUser) && kodeordValue.equals(demoPass))
                        {
                            JOptionPane.showMessageDialog(opretBrugerVindue, "Din bruger er oprettet.");
                        } else {
                            JOptionPane.showMessageDialog(opretBrugerVindue, "Ugyldigt brugernavn, prøv igen.");
                        }*/

                        //System.out.println("Brugernavn: " + brugernavnValue + " er oprettet.");
                    }
                });
            }
        });
        // opret en logind knap til forsiden
        logIndKnap.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame logindVindue = new JFrame("Logind siden");
                logindVindue.setLayout(new FlowLayout());
                logindVindue.setSize(600,600);
                logindVindue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                logindVindue.add(logindKnap);
                logindVindue.setVisible(true);

                // INPUT FIELDS
                JTextField brugernavnFelt = new JTextField("Indtast brugernavn");
                JTextField kodeordsFelt = new JTextField("Indtasts kodeord");
                logindVindue.add(brugernavnFelt);
                logindVindue.add(kodeordsFelt);
            }
        });

    }


    public void displayMessage(String besked)
    {
        JOptionPane.showMessageDialog(mainWindow, besked);
    }

    public void displayList(ArrayList<String> list, String besked){
        displayMessage(besked);
        int counter = 1;
        for (String option : list) {
            //System.out.print(counter + ") ");
            JOptionPane.showMessageDialog(mainWindow, counter + ") ");
            counter++;
            //System.out.println(option);
            //JOptionPane.showMessageDialog(option);
        }
    }

    public void promptChoiceMainMenu(ArrayList<String> optionslist, String besked){
        displayList(optionslist, "");
        //int input = validerInput(min, max);
    }
    /*
    public int validerInput(int min, int max)
    {
        String regex = String.format("[%d-%d]", min, max);

        //String valg = promptText("");
        if (!valg.matches(regex))
        {
            displayMessage("Ugyldigt input. Prøv igen");
            return validerInput(min,max);
        } else {
            return Integer.parseInt(valg);
        }*/
    }
    /*
    public String promptText(){
        JOptionPane.showInputDialog("Indtast: ");

    }*/
/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}*/



