import domæne.Program;
import utility.DBConnector;

public class Main {
    public static void main(String[] args) {
        DBConnector db = new DBConnector();
        Program program = new Program(db);

        db.dbConnect();

        program.logIndProgram();
    }
}