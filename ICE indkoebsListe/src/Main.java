import domæne.Program;
import utility.DBConnector;
import utility.TextUI;
// Denne opgave er lavet med en lokal mySQL database, og vil nok ikke virke uden
public class Main {
    public static void main(String[] args) {
        DBConnector db = new DBConnector();
        TextUI ui = new TextUI();
        Program program = new Program(db,ui);

        db.dbConnect();

        program.logIndProgram();
    }
}