import model.Program;
import model.DBConnector;
import utility.GUI;

import javax.swing.*;
/*
public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        DBConnector db = new DBConnector();

        db.dbConnect();

        program.startProgram();
    }
}*/
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                Program program = new Program(); // Pass GUI object to Program constructor
                DBConnector db = new DBConnector();

                db.dbConnect();

                program.startProgram();
            }
        });
    }
}

