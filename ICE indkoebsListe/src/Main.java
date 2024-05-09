import domain.Program;
import utility.GUI;
import utility.DBConnector;

import javax.swing.*;
/*
public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        DBConnector db = new DBConnector();

        db.dbConnect();

        program.startProgram();
    }
}/*
public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            GUI gui = new GUI();
            Program program = new Program(gui);
            DBConnector db = new DBConnector();
        }
    });

}
}*/
