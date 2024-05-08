import utility.DBConnector;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        DBConnector db = new DBConnector();

        db.dbConnect();

        program.logIndProgram();
    }
}