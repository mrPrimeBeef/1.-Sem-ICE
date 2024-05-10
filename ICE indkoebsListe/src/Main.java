import domæne.Program;
import utility.DBConnector;
import utility.TextUI;

public class Main {
    public static void main(String[] args) {
        DBConnector db = new DBConnector();
        TextUI ui = new TextUI();
        Program program = new Program(db,ui);

        db.dbConnect();

        program.logIndProgram();
    }
}