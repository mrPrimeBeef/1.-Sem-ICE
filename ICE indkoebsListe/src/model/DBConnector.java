package model;

import utility.TextUI;

import java.sql.*;


public class DBConnector {
    TextUI ui;
    Bruger nuværendeBruger;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ice";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "toor";

    public DBConnector() {
        this.ui = new TextUI();
    }

    public Bruger opretBruger() {
        while (true) {
            // Indsamle brugeroplysninger fra brugeren
            String brugerNavn = ui.promptText("Skriv dit brugernavn");
            String password = ui.promptText("Skriv et kodeord");

            // Tjek om brugernavnet allerede eksisterer i databasen
            if (checkCredentialAvailability(brugerNavn)) {
                try (
                        // Opret forbindelse til databasen
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        // SQL-forespørgsel til at indsætte brugeroplysninger i databasen
                        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bruger (brugernavn, kodeord) VALUES (?, ?)")
                ) {
                    // Sæt parameterne for PreparedStatement
                    pstmt.setString(1, brugerNavn);
                    pstmt.setString(2, password);

                    // Udfør SQL-forespørgslen for at indsætte brugeren i databasen
                    pstmt.executeUpdate();

                    // Opret køleskab og indkøbsliste for den nye bruger
                    opretKøleskab(brugerNavn);
                    opretIndkøbsliste(brugerNavn);

                    ui.displayMessage("Bruger oprettet");
                    Bruger bruger = new Bruger(brugerNavn, password);
                    setnuværendeBruger(bruger);
                    return bruger;

                } catch (SQLException e) {
                    ui.displayMessage("Fejl under oprettelse af bruger: " + e.getMessage());
                    return null; // Returner null i tilfælde af en fejl
                }
            }
        }
    }


    private void opretKøleskab(String brugerNavn)  {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO koeleskab (brugernavn) VALUES (?)")
        ) {
            pstmt.setString(1, brugerNavn);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            ui.displayMessage("Fejl under oprettelse af bruger: " + e.getMessage());
        }
    }

    private void opretIndkøbsliste(String brugerNavn) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO liste (brugernavn) VALUES (?)")
        ) {
            pstmt.setString(1, brugerNavn);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            ui.displayMessage("Fejl under oprettelse af bruger: " + e.getMessage());
        }
    }

    public boolean checkCredentialAvailability(String brugerNavn) {
        try (
                // Opret forbindelse til databasen
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // SQL-forespørgsel til at kontrollere, om brugernavnet eksisterer i databasen
                PreparedStatement pstmt = conn.prepareStatement("SELECT brugernavn FROM bruger WHERE brugernavn = ?");
        ) {
            // Sæt parameteren for PreparedStatement
            pstmt.setString(1, brugerNavn);

            // Udfør SQL-forespørgslen
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Brugernavnet findes i databasen
                    ui.displayMessage(brugerNavn + " er allerede i brug");
                    return false; // Credential exists
                } else {
                    // Brugernavnet er ledigt
                    ui.displayMessage(brugerNavn + " er ledig");
                    return true; // Credential is available
                }
            }
        } catch (SQLException e) {
            ui.displayMessage("Fejl under tjek af brugernavn tilgængelighed: " + e.getMessage());
            return false; // Returner false i tilfælde af en fejl
        }
    }
    public boolean logInd() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        while (true) {
            String brugerNavn = ui.promptText("Skriv dit brugernavn");
            String kodeord = ui.promptText("Skriv dit kodeord");

            try {
                // Opret forbindelse til databasen
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // vi bruger en SQL-forespørgsel til at kontrollere brugernavn og kodeord i databasen
                pstmt = conn.prepareStatement("SELECT brugernavn FROM bruger WHERE brugernavn = ? AND kodeord = ?");
                // Sæt parameterne for PreparedStatement
                pstmt.setString(1, brugerNavn);
                pstmt.setString(2, kodeord);

                // Udfør SQL-forespørgslen
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Brugernavn og kodeord findes i databasen
                    ui.displayMessage("Log ind succesfuld");
                    Bruger bruger = new Bruger(brugerNavn, kodeord);
                    setnuværendeBruger(bruger); // Set the current user
                    return true;
                } else {
                    // Forkert brugernavn eller kodeord
                    ui.displayMessage("Forkert brugernavn eller kodeord, prøv igen");
                }
            } catch (SQLException e) {
                ui.displayMessage("Fejl under login: " + e.getMessage());
                return false; // Returner false i tilfælde af en fejl
            }
        }
    }

    public void dbConnect() {

        System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
        Connection conn = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("\nDatabase Connection Established...");
        } catch (
                Exception ex) {
            System.err.println("Cannot connect to database server");
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    System.out.println("\n***** Let terminate the Connection *****");
                    conn.close();
                    System.out.println("\nDatabase connection terminated...");
                } catch (Exception ex) {
                    System.out.println("Error in connection termination!");
                }
            }
        }
    }
    public void setnuværendeBruger (Bruger nuværendeBruger){
        this.nuværendeBruger = nuværendeBruger;
    }
}

