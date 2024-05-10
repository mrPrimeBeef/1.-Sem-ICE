import domæne.Program;
import domæne.DBConnector;
/*
public class Main {
    public static void main(String[] args) {
        DBConnector db = new DBConnector();
        Program program = new Program(db);

        db.dbConnect();

        program.logIndProgram();
    }
}
*/


import View.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // runs in AWT thread
        SwingUtilities.invokeLater(MainFrame::new);
    }
}


