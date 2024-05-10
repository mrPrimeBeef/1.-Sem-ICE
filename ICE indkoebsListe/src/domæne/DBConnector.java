package domæne;

import produkt.AProdukt;
import produkt.Vare;
import utility.TextUI;
import View.Form;
import javax.swing.*;
import java.sql.*;
import java.util.HashMap;


public class DBConnector {
    TextUI ui;
    Bruger nuværendeBruger;
    String brugerNavn;
    String password;
    Vare vare;
    private Form form;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/icedatabase";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Esn64mjy:1";

    public DBConnector() {
        this.ui = new TextUI();
    }

    public Bruger opretBruger() {
        while (true) {
            // Indsamle brugeroplysninger fra brugeren
            //String brugerNavn = JOptionPane.showMessageDialog(this.form, "Skriv dit brugernavn");
            //String password = JOptionPane.showMessageDialog(this.form, "Skriv dit kodeord");


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
                  //  opretKøleskab(brugerNavn);
                   // opretIndkøbsliste(brugerNavn);

                    //ui.displayMessage("Bruger oprettet");
                    Bruger bruger = new Bruger(brugerNavn, password);
                    setBrugerNavn(brugerNavn);

                    return bruger;

                } catch (SQLException e) {
                    //ui.displayMessage("Fejl under oprettelse af bruger: " + e.getMessage());
                    return null; // Returner null i tilfælde af en fejl
                }
            }
        }
    }


    private void opretKøleskab(String brugerNavn) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO koeleskab (brugernavn) VALUES (?)")
        ) {
            pstmt.setString(1, brugerNavn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this.form, "Fejl under oprettelse af bruger" + e.getMessage());
        }
    }

    private void opretIndkøbsliste(String brugerNavn) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO liste (brugernavn) VALUES (?)")
        ) {
            pstmt.setString(1, brugerNavn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this.form, "Fejl under oprettelse af bruger " + e.getMessage());
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
            JOptionPane.showMessageDialog(this.form, "Fejl under tjek af brugernavn tilgængellighed " + e.getMessage());
            return false; // Returner false i tilfælde af en fejl
        }
    }

    public boolean logInd() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        while (true) {
            brugerNavn = ui.promptText("Skriv dit brugernavn");
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
                    setBrugerNavn(brugerNavn);// Set the current user

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

    public void setBrugerNavn(String brugerNavn) {
        this.brugerNavn = brugerNavn;
    }

    public String getBrugerNavn() {
        return brugerNavn;
    }

    public void gemTilListe(Vare vare) {


        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // SQL-forespørgsel til at indsætte brugeroplysninger i databasen

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO liste (brugernavn, Varer, mængde, pris, afdeling) VALUES (?, ?, ?, ?, ?)");

            // Sæt parameterne for PreparedStatement
           pstmt.setString(1, this.brugerNavn);
            pstmt.setString(2, vare.getVareNavn());
            pstmt.setInt(3, vare.getMængde());
            pstmt.setInt(4, vare.getPris());
            pstmt.setString(5, vare.getAfdeling());

            // Udfør SQL-forespørgslen for at indsætte brugeren i databasen
            pstmt.executeUpdate();


        } catch (SQLException e) {
            ui.displayMessage("Fejl under indsæt af vare: " + e.getMessage());

        }
    }

    public HashMap<AProdukt, String> visIndkøbsListe(String brugernavn){
            HashMap<AProdukt, String> vareMap = new HashMap<>();
            try {
                // Opret forbindelse til databasen
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // SQL-forespørgsel til at hente vareoplysninger for den angivne bruger
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM liste WHERE brugernavn = ?");
                // Sæt parameteren for PreparedStatement
                pstmt.setString(1, brugernavn);
                // Udfør SQL-forespørgslen
                ResultSet rs = pstmt.executeQuery();
                // Iterér gennem resultatet og opret Vare-objekter
                while (rs.next()) {
                    String vareNavn = rs.getString("Varer");
                    int mængde = rs.getInt("mængde");
                    int pris = rs.getInt("pris");
                    String afdeling = rs.getString("afdeling");
                    // Opret Vare-objekt
                    Vare vare = new Vare(vareNavn, mængde, pris, afdeling);
                    // Tilføj Vare-objektet til HashMap med varenavnet som nøgle
                    vareMap.put(vare, afdeling);
                }
                // Luk ResultSet, PreparedStatement og Connection
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                ui.displayMessage("Fejl under hentning af vareliste: " + e.getMessage());
            }
            return vareMap;
    }

    public void fjernVAre(String vareNavn) {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement to delete the item
            String sql = "DELETE FROM liste WHERE varer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the parameter for the PreparedStatement
            pstmt.setString(1, vareNavn);

            // Execute the delete statement
            int rowsAffected = pstmt.executeUpdate();

            // Check if any rows were affected (i.e., if the item was deleted successfully)
            if (rowsAffected > 0) {
                System.out.println("varen er slettet.");
            } else {
                System.out.println("varen er ikke fundet.");
            }

            // Close PreparedStatement and Connection
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


