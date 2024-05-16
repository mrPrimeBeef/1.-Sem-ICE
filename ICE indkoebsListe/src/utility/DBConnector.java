package utility;

import produkt.Ret;
import produkt.Vare;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class DBConnector {
    TextUI ui;
    String brugerNavn;
    HashMap<Vare, String> vareMap;


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/icedatabase";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Esn64mjy:1";

    public DBConnector() {
        this.ui = new TextUI();
        this.vareMap = new HashMap<>();

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

    public void opretBruger() {

            // Indsamle brugeroplysninger fra brugeren
            String brugerNavn = ui.promptText("Skriv dit brugernavn");
            String password = ui.promptText("Skriv et kodeord");

            // Tjek om brugernavnet allerede eksisterer i databasen
            if (checkCredentialAvailability(brugerNavn)) {
                try{
                        // Opret forbindelse til databasen
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        // SQL-forespørgsel til at indsætte brugeroplysninger i databasen
                        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bruger (brugernavn, kodeord) VALUES (?, ?)");

                    // Sæt parameterne for PreparedStatement
                    pstmt.setString(1, brugerNavn);
                    pstmt.setString(2, password);

                    // Udfør SQL-forespørgslen for at indsætte brugeren i databasen
                    pstmt.executeUpdate();

                    // Opret køleskab og indkøbsliste for den nye bruger
                  //  opretKøleskab(brugerNavn);
                   // opretIndkøbsliste(brugerNavn);

                    ui.displayMessage("Bruger oprettet");
                    setBrugerNavn(brugerNavn);


                } catch (SQLException e) {
                    ui.displayMessage("Fejl under oprettelse af bruger: " + e.getMessage());
                }
            }
            else {
                opretBruger();
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

    public void setBrugerNavn(String brugerNavn) {
        this.brugerNavn = brugerNavn;
    }

    public String getBrugerNavn() {
        return brugerNavn;
    }


    public void tilføjTilInventarListe(Vare vare, int mængde) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO inventar (brugernavn, varer, mængde, afdeling) VALUES (?, ?, ?, ?)");

            pstmt.setString(1, this.brugerNavn);
            pstmt.setString(2, vare.getVareNavn());
            pstmt.setInt(3, mængde);
            pstmt.setString(4, vare.getAfdeling());

            ui.displayMessage("Din vare er blevet tilføjet \n");
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under tilføjelse til inventar: " + e.getMessage());
        }
    }
    public void fjernInventar(String vareNavn, int antal) {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement to check the quantity of the item
            String checkQuantitySql = "SELECT mængde FROM inventar WHERE varer = ?";
            PreparedStatement checkQuantityPstmt = conn.prepareStatement(checkQuantitySql);

            // Set the parameter for the PreparedStatement
            checkQuantityPstmt.setString(1, vareNavn);

            // Execute the query and get the result
            ResultSet rs = checkQuantityPstmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("mængde");

                if (currentQuantity <= antal) {
                    // Prepare the SQL statement to delete the item
                    String deleteSql = "DELETE FROM inventar WHERE varer = ?";
                    PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);

                    // Set the parameter for the PreparedStatement
                    deletePstmt.setString(1, vareNavn);

                    // Execute the delete statement
                    int rowsAffected = deletePstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Varen er slettet.");
                    } else {
                        System.out.println("Varen blev ikke fundet.");
                    }

                    // Close the delete PreparedStatement
                    deletePstmt.close();
                } else {
                    // Prepare the SQL statement to update the quantity
                    String updateSql = "UPDATE inventar SET mængde = mængde - ? WHERE varer = ?";
                    PreparedStatement updatePstmt = conn.prepareStatement(updateSql);

                    // Set the parameters for the PreparedStatement
                    updatePstmt.setInt(1, antal);
                    updatePstmt.setString(2, vareNavn);

                    // Execute the update statement
                    int rowsAffected = updatePstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Mængden er nedjusteret med " + antal + ".");
                    } else {
                        System.out.println("Varen blev ikke fundet.");
                    }

                    // Close the update PreparedStatement
                    updatePstmt.close();
                }
            } else {
                System.out.println("Varen blev ikke fundet.");
            }

            // Close ResultSet, PreparedStatement, and Connection
            rs.close();
            checkQuantityPstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int hentMængde(String vareNavn) {
        int mængde = -1; // Standard værdi, hvis varen ikke findes

        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement to select the quantity of the item
            String sql = "SELECT mængde FROM liste WHERE varer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the parameter for the PreparedStatement
            pstmt.setString(1, vareNavn);

            // Execute the query and get the result
            ResultSet rs = pstmt.executeQuery();

            // Check if a result is returned
            if (rs.next()) {
                mængde = rs.getInt("mængde"); // Get the quantity from the result
            }

            // Close ResultSet, PreparedStatement, and Connection
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mængde;
    }


    public HashMap<Vare, String> hentInventar(String brugerNavn) {
        HashMap<Vare, String> inventarMap = new HashMap<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM inventar WHERE brugernavn = ?");

            pstmt.setString(1, brugerNavn);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String vareNavn = rs.getString("varer");
                int mængde = rs.getInt("mængde");
                String afdeling = rs.getString("afdeling");

                Vare vare = new Vare(vareNavn, mængde, afdeling);
                inventarMap.put(vare, afdeling);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventarMap;
    }


    public void gemTilListe(Vare vare) {
        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // SQL-forespørgsel til at kontrollere, om varenavnet allerede findes
            String checkIfExistsQuery = "SELECT mængde FROM liste WHERE brugernavn = ? AND Varer = ?";
            PreparedStatement checkIfExistsStmt = conn.prepareStatement(checkIfExistsQuery);
            checkIfExistsStmt.setString(1, this.brugerNavn);
            checkIfExistsStmt.setString(2, vare.getVareNavn());

            // Udfør SQL-forespørgslen for at kontrollere, om varenavnet allerede findes
            ResultSet rs = checkIfExistsStmt.executeQuery();

            // Hvis varenavnet allerede findes
            if (rs.next()) {
                int currentQuantity = rs.getInt("mængde");

                // Opdater mængden i databasen med 1
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE liste SET mængde = ? WHERE brugernavn = ? AND Varer = ?");
                updateStmt.setInt(1, currentQuantity + 1);
                updateStmt.setString(2, this.brugerNavn);
                updateStmt.setString(3, vare.getVareNavn());
                updateStmt.executeUpdate();

                System.out.println("Mængden for varen " + vare.getVareNavn() + " er øget med 1.");
            } else {
                // Luk resultatsættet og forberedt udsagnet
                rs.close();
                checkIfExistsStmt.close();

                // Indsæt varen i databasen, da den ikke findes endnu
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO liste (brugernavn, Varer, mængde, pris, afdeling) VALUES (?, ?, ?, ?, ?)");
                insertStmt.setString(1, this.brugerNavn);
                insertStmt.setString(2, vare.getVareNavn());
                insertStmt.setInt(3, vare.getMængde());
                insertStmt.setInt(4, vare.getPris());
                insertStmt.setString(5, vare.getAfdeling());

                // Udfør SQL-forespørgslen for at indsætte varen i databasen
                insertStmt.executeUpdate();

                // Vis en bekræftelsesbesked
                System.out.println("Varen " + vare.getVareNavn() + " er blevet tilføjet til listen.");

                // Luk PreparedStatement
                insertStmt.close();
            }

            // Luk resultatsættet og forbindelsen
            rs.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under indsæt af vare: " + e.getMessage());
        }
    }


    public Vare hentVare(String brugernavn, String vareNavnFraBruger) {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement to fetch the item
            String sql = "SELECT * FROM liste WHERE brugernavn = ? AND varer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the parameters for the PreparedStatement
            pstmt.setString(1, brugernavn);
            pstmt.setString(2, vareNavnFraBruger);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Check if the ResultSet has any rows
            if (rs.next()) {
                // If a row is found, fetch item details
                String vareNavn = rs.getString("varer");
                int mængde = rs.getInt("mængde");
                int pris = rs.getInt("pris");
                String afdeling = rs.getString("afdeling");

                // Close ResultSet, PreparedStatement, and Connection
                rs.close();
                pstmt.close();
                conn.close();

                // Create and return the Vare object
                return new Vare(vareNavn, mængde, pris, afdeling);
            } else {
                // Close ResultSet, PreparedStatement, and Connection
                rs.close();
                pstmt.close();
                conn.close();

                // If no item is found, return null
                return null;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            return null;
        }
    }


    public HashMap<Vare, String> hentIndkøbsListe(String brugernavn){
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

    public void fjernVare(String vareNavn, int antal) {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement to check the quantity of the item
            String checkQuantitySql = "SELECT mængde FROM liste WHERE varer = ?";
            PreparedStatement checkQuantityPstmt = conn.prepareStatement(checkQuantitySql);

            // Set the parameter for the PreparedStatement
            checkQuantityPstmt.setString(1, vareNavn);

            // Execute the query and get the result
            ResultSet rs = checkQuantityPstmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("mængde");

                if (currentQuantity <= antal) {
                    // Prepare the SQL statement to delete the item
                    String deleteSql = "DELETE FROM liste WHERE varer = ?";
                    PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);

                    // Set the parameter for the PreparedStatement
                    deletePstmt.setString(1, vareNavn);

                    // Execute the delete statement
                    int rowsAffected = deletePstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Varen er slettet.");
                    } else {
                        System.out.println("Varen blev ikke fundet.");
                    }

                    // Close the delete PreparedStatement
                    deletePstmt.close();
                } else {
                    // Prepare the SQL statement to update the quantity
                    String updateSql = "UPDATE liste SET mængde = mængde - ? WHERE varer = ?";
                    PreparedStatement updatePstmt = conn.prepareStatement(updateSql);

                    // Set the parameters for the PreparedStatement
                    updatePstmt.setInt(1, antal);
                    updatePstmt.setString(2, vareNavn);

                    // Execute the update statement
                    int rowsAffected = updatePstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Mængden er nedjusteret med " + antal + ".");
                    } else {
                        System.out.println("Varen blev ikke fundet.");
                    }

                    // Close the update PreparedStatement
                    updatePstmt.close();
                }
            } else {
                System.out.println("Varen blev ikke fundet.");
            }

            // Close ResultSet, PreparedStatement, and Connection
            rs.close();
            checkQuantityPstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fjernVareKøb(String vareNavn) {
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


    public void tilføjRet(Ret ret, String brugerNavn, boolean isGlobal) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO retter (brugernavn, navn, global) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (isGlobal) {
                pstmt.setNull(1, java.sql.Types.VARCHAR);  // NULL for globale retter
            } else {
                pstmt.setString(1, brugerNavn);
            }
            pstmt.setString(2, ret.getNavn().toLowerCase());
            pstmt.setBoolean(3, isGlobal);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tilføjTilIngredienser(String retNavn, ArrayList<String> ingredienser, ArrayList<String> mængder) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Ingredienser (navn, mængde, ret_navn) VALUES (?, ?, ?)");

            // Gennemløb ingredienslisten og indsæt hver ingrediens i databasen
            for (int i = 0; i < ingredienser.size(); i++) {
                String navn = ingredienser.get(i);
                String mængde = mængder.get(i);
                pstmt.setString(1, navn); // Indstil navnet på ingrediensen
                pstmt.setString(2, mængde); // Indstil mængden af ingrediensen
                pstmt.setString(3, retNavn); // Indstil navnet på retten
                pstmt.executeUpdate(); // Udfør indsættelsen
            }

            // Luk PreparedStatement og Connection
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // Håndter fejl
            e.printStackTrace();
        }
    }

    public ArrayList<Vare> hentIngredienser(String retNavn) {
        ArrayList<Vare> ingredienser = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement checkRetPstmt = conn.prepareStatement("SELECT COUNT(*) FROM retter WHERE navn = ?");
             PreparedStatement getIngredienserPstmt = conn.prepareStatement(
                     "SELECT v.navn, i.mængde, v.pris, v.afdeling " +
                             "FROM ingredienser i " +
                             "JOIN varer v ON i.navn = v.navn " +
                             "WHERE i.ret_navn = ?")) {

            // Check if the dish exists
            checkRetPstmt.setString(1, retNavn);
            try (ResultSet rs = checkRetPstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Dish exists, fetch ingredients
                    getIngredienserPstmt.setString(1, retNavn);
                    try (ResultSet ingredienserRs = getIngredienserPstmt.executeQuery()) {
                        while (ingredienserRs.next()) {
                            String navn = ingredienserRs.getString("navn");
                            int mængde = ingredienserRs.getInt("mængde");
                            int pris = ingredienserRs.getInt("pris");
                            String afdeling = ingredienserRs.getString("afdeling");
                            ingredienser.add(new Vare(navn, mængde, pris, afdeling));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the error
        }
        return ingredienser;
    }

    public void tilføjTilMadplanListe(String retnavn, int indexTal) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Forbered en SELECT-forespørgsel for at hente den nuværende ret
            PreparedStatement selectPstmt = conn.prepareStatement("SELECT ret FROM madplan WHERE brugernavn = ? AND dag = ?");
            selectPstmt.setString(1, this.brugerNavn);
            selectPstmt.setInt(2, indexTal);

            // Udfør SELECT-forespørgslen
            ResultSet rs = selectPstmt.executeQuery();

            String nuværendeRet = null;

            // Hvis der findes en nuværende ret, gem navnet på den
            if (rs.next()) {
                nuværendeRet = rs.getString("ret");
            }

            // Luk ResultSet og PreparedStatement for SELECT-forespørgslen
            rs.close();
            selectPstmt.close();

            // Forbered opdateringsforespørgslen til at ændre retten
            PreparedStatement updatePstmt = conn.prepareStatement("UPDATE madplan SET ret = ? WHERE brugernavn = ? AND dag = ?");
            updatePstmt.setString(1, retnavn);
            updatePstmt.setString(2, this.brugerNavn);
            updatePstmt.setInt(3, indexTal);

            // Udfør opdateringen og gem antallet af berørte rækker
            int rowsAffected = updatePstmt.executeUpdate();

            // Hvis ingen eksisterende ret blev opdateret, skal vi indsætte en ny ret
            if (rowsAffected == 0) {
                updatePstmt.close(); // Luk PreparedStatement for opdateringsforespørgslen

                // Forbered indsættelsesforespørgslen
                PreparedStatement insertPstmt = conn.prepareStatement("INSERT INTO madplan (brugernavn, dag, ret) VALUES (?, ?, ?)");
                insertPstmt.setString(1, this.brugerNavn);
                insertPstmt.setInt(2, indexTal);
                insertPstmt.setString(3, retnavn);

                // Udfør indsættelsen
                insertPstmt.executeUpdate();

                // Luk PreparedStatement for indsættelsesforespørgslen
                insertPstmt.close();
            }

            // Luk PreparedStatement for opdateringsforespørgslen
            updatePstmt.close();

            // Luk forbindelsen til databasen
            conn.close();

            // Hvis der var en nuværende ret
            if (nuværendeRet != null) {
                ArrayList<Vare> vareListe = hentIngredienser(nuværendeRet);
                opdaterIndkøbslisten(vareListe);
                fjernNulMængdeVarerFraIndkøbslisten();
            }
        } catch (SQLException e) {
            ui.displayMessage("Fejl under tilføjelse til madplanen: " + e.getMessage());
        }
    }


    public void fjernFraMadplanListe(String retnavn, int indexTal) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM madplan WHERE brugernavn = ? AND dag = ? AND ret = ?");

            pstmt.setString(1, this.brugerNavn);
            pstmt.setInt(2, indexTal); // Index + 1 for at matche med 1-baseret dagnummer
            pstmt.setString(3, retnavn);

            pstmt.executeUpdate(); // Udfør indsættelsen

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under fjernelse fra madplanen: " + e.getMessage());
        }
    }

    public void opdaterIndkøbslisten(ArrayList<Vare> vareListe) {
        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // For hver vare i arraylisten
            for (Vare vare : vareListe) {
                // Forbered SQL-forespørgslen til at opdatere mængden af varen
                String updateSql = "UPDATE liste SET mængde = mængde - 1 WHERE Varer = ? AND mængde > 0";
                PreparedStatement updatePstmt = conn.prepareStatement(updateSql);

                // Indstil parameteren for PreparedStatement med navnet på varen
                updatePstmt.setString(1, vare.getVareNavn());

                // Udfør opdateringsforespørgslen
                int rowsAffected = updatePstmt.executeUpdate();

                // Tjek om der blev berørt nogen rækker (dvs. om mængden blev opdateret)
                if (rowsAffected > 0) {
                    System.out.println("Mængden af varen \"" + vare.getVareNavn() + "\" er blevet opdateret.");
                } else {
                    System.out.println("Varen \"" + vare.getVareNavn() + "\" blev ikke fundet i indkøbslisten " +
                            "eller mængden er allerede nul.");
                }

                // Luk PreparedStatement for opdatering
                updatePstmt.close();
            }

            // Luk forbindelsen til databasen
            conn.close();
        } catch (SQLException e) {
            // Log fejlen eller vis en fejlbesked
            System.err.println("Fejl under opdatering af indkøbslisten: " + e.getMessage());
        }
    }

    public void fjernNulMængdeVarerFraIndkøbslisten() {
        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Forbered SQL-forespørgslen til at slette varer med nulmængde fra indkøbslisten
            String deleteSql = "DELETE FROM liste WHERE mængde = 0";
            PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);

            // Udfør sletningsforespørgslen
            deletePstmt.executeUpdate();

            // Luk PreparedStatement for sletning
            deletePstmt.close();

            // Luk forbindelsen til databasen
            conn.close();
        } catch (SQLException e) {
            // Log fejlen eller vis en fejlbesked
            System.err.println("Fejl under fjernelse af varer med nul mængde fra indkøbslisten: " + e.getMessage());
        }
    }

    public ArrayList<String> hentMadplan(String brugernavn) {
        ArrayList<String> madplan = new ArrayList<>();
        madplan.addAll(Arrays.asList("Mandag: ", "Tirsdag: ", "Onsdag: ", "Torsdag: ","Fredag: ","Lørdag: ","Søndag: " + "\n"));
        ArrayList<String> madplan2 = new ArrayList<>();
        madplan2.addAll(Arrays.asList("Mandag: ", "Tirsdag: ", "Onsdag: ", "Torsdag: ","Fredag: ","Lørdag: ","Søndag: " + "\n"));

        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // SQL-forespørgsel til at hente madplanen for den angivne bruger
            PreparedStatement pstmt = conn.prepareStatement("SELECT dag, ret FROM madplan WHERE brugernavn = ?");

            // Sæt parameteren for PreparedStatement
            pstmt.setString(1, brugernavn);

            // Udfør SQL-forespørgslen
            ResultSet rs = pstmt.executeQuery();
            int rDag = 0;
            int count = 0;
            // Iterér gennem resultatet og tilføj hver ret til den passende dag i ArrayList
            while (rs.next()) {
                int dag = rs.getInt("dag");
                String ret = rs.getString("ret");
                rDag = dag - 1;
                StringBuilder sb = new StringBuilder();
                if (madplan.get(rDag).equals(madplan2.get(rDag))) {
                    sb.append(madplan.get(rDag) + ret);
                    madplan.set(rDag, madplan.get(rDag) + ret);
                } else {
                    if(count < 1) {
                        ui.displayMessage("Der er noget på dagen");
                        count++;
                    }
                }
            }

            // Luk ResultSet, PreparedStatement og Connection
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under hentning af madplan for ugen: " + e.getMessage());
        }

        return madplan;
    }


    public String fjernRet(int index) {
        String removedRetNavn = null; // Variabel til at gemme navnet på den fjernede ret

        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Forbered SQL-forespørgslen til at hente rettens navn baseret på indeks
            String selectSql = "SELECT ret FROM madplan WHERE dag = ?";
            PreparedStatement selectPstmt = conn.prepareStatement(selectSql);
            selectPstmt.setInt(1, index);

            // Udfør forespørgslen for at hente rettens navn
            ResultSet rs = selectPstmt.executeQuery();

            // Hvis der blev returneret en række, gemmes rettens navn før sletning
            if (rs.next()) {
                removedRetNavn = rs.getString("ret");
            }

            // Luk ResultSet og PreparedStatement for at hente rettens navn
            rs.close();
            selectPstmt.close();

            // Hvis rettens navn blev fundet og gemt, fortsættes med at slette den
            if (removedRetNavn != null) {
                // Forbered SQL-forespørgslen til at slette retten
                String deleteSql = "DELETE FROM madplan WHERE dag = ?";
                PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);

                // Indstil parameteren for PreparedStatement
                deletePstmt.setInt(1, index);

                // Udfør sletningsforespørgslen
                int rowsAffected = deletePstmt.executeUpdate();

                // Tjek om der blev berørt nogen rækker (dvs. om retten blev slettet succesfuldt)
                if (rowsAffected > 0) {
                    System.out.println("Retten \"" + removedRetNavn + "\" er blevet fjernet fra madplanen.");
                } else {
                    System.out.println("Der blev ikke fundet nogen ret på den angivne dag.");
                }

                // Luk PreparedStatement for sletning
                deletePstmt.close();
            }

            // Luk forbindelsen til databasen
            conn.close();
        } catch (SQLException e) {
            // Log fejlen eller vis en fejlbesked
            System.err.println("Fejl under fjernelse af ret fra madplan: " + e.getMessage());
        }

        // Returner navnet på den fjernede ret (kan være null, hvis ingen ret blev fjernet)
        return removedRetNavn;
    }

    public HashMap<String, ArrayList<String>> visRetter(String brugernavn) {
        HashMap<String, ArrayList<String>> retterOgIngredienser = new HashMap<>();

        ResultSet rs = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT r.navn AS ret_navn, i.navn AS ingrediens_navn, i.mængde AS ingrediens_mængde " +
                    "FROM retter r " +
                    "JOIN ingredienser i ON r.navn = i.ret_navn " +
                    "WHERE r.brugernavn = ? OR r.global = TRUE";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Indstil parameteren for PreparedStatement
            pstmt.setString(1, brugernavn);

            // Udfør SQL-forespørgslen
            rs = pstmt.executeQuery();

            // Iterér gennem resultatsættet og opret en mappe med retter og deres ingredienser
            while (rs.next()) {
                String retNavn = rs.getString("ret_navn");
                String ingrediensNavn = rs.getString("ingrediens_navn");
                int ingrediensMængde = rs.getInt("ingrediens_mængde");

                // Hent eller opret en ArrayList for ingredienserne for den aktuelle ret
                ArrayList<String> ingredienser = retterOgIngredienser.getOrDefault(retNavn, new ArrayList<>());

                // Tilføj den aktuelle ingrediens til ArrayListen
                ingredienser.add(ingrediensNavn + " (" + ingrediensMængde + ")");

                // Opdater HashMap med retter og ingredienser
                retterOgIngredienser.put(retNavn, ingredienser);
            }

            // Luk ResultSet, PreparedStatement og Connection
            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            ui.displayMessage("Der er fejl i at vise retterne: " + e.getMessage());
        }
        return retterOgIngredienser;
    }
    }

