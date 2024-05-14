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
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/icer";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "atx33xmw!";

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


    public void tilføjTilInventarListe(Vare vare) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO inventar (brugernavn, varer, mængde, afdeling) VALUES (?, ?, ?, ?)");

            pstmt.setString(1, this.brugerNavn);
            pstmt.setString(2, vare.getVareNavn());
            pstmt.setInt(3, vare.getMængde());
            pstmt.setString(4, vare.getAfdeling());

            ui.displayMessage("Din vare er blevet tilføjet \n");
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under tilføjelse til inventar: " + e.getMessage());
        }
    }
    public void fjernInventar(String vareNavn) {
        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement to delete the item
            String sql = "DELETE FROM inventar WHERE varer = ?";
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


    public HashMap<Vare, String> hentInventar(String brugernavn) {
        HashMap<Vare, String> inventarMap = new HashMap<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM inventar WHERE brugernavn = ?");

            pstmt.setString(1, brugernavn);
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

            pstmt.close();
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

    public void fjernVare(String vareNavn) {
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

    public void tilføjTilRetter(Ret ret) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO retter (brugernavn, navn) VALUES (?, ?)");

            pstmt.setString(1, this.brugerNavn);
            pstmt.setString(2, ret.getNavn().toLowerCase());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under tilføjelse til retter: " + e.getMessage());
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
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO madplan (brugernavn, dag, ret) VALUES (?, ?, ?)");

            pstmt.setString(1, this.brugerNavn);
            pstmt.setInt(2, indexTal); // Index + 1 for at matche med 1-baseret dagnummer
            pstmt.setString(3, retnavn);

            pstmt.executeUpdate(); // Udfør indsættelsen

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under tilføjelse til madplanen: " + e.getMessage());
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


    public void fjernRet(int index) {
        try {
            // Opret forbindelse til databasen
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Forbered SQL-forespørgslen til at slette posten
            String sql = "DELETE FROM madplan WHERE dag = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Indstil parameteren for PreparedStatement
            pstmt.setInt(1, index);

            // Udfør sletningsforespørgslen
            int rowsAffected = pstmt.executeUpdate();

            // Tjek om der blev berørt nogen rækker (dvs. om retten blev slettet succesfuldt)
            if (rowsAffected > 0) {
                System.out.println("Retten er blevet fjernet fra madplanen.");
            } else {
                System.out.println("Der blev ikke fundet nogen ret på den angivne dag.");
            }

            // Luk PreparedStatement og Connection
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // Log fejlen eller vis en fejlbesked
            System.err.println("Fejl under fjernelse af ret fra madplan: " + e.getMessage());
        }
    }

    public HashMap<String, ArrayList<String>> visRetter(String brugernavn) {
        HashMap<String, ArrayList<String>> retterOgIngredienser = new HashMap<>();

        ResultSet rs = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT r.navn AS ret_navn, i.navn AS ingrediens_navn, i.mængde AS ingrediens_mængde " +
                    "FROM retter r " +
                    "JOIN ingredienser i ON r.navn = i.ret_navn " +
                    "WHERE r.brugernavn = ?";

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



    public ArrayList<String> hentAlleRetter() {
        ArrayList<String> retterListe = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("SELECT navn FROM retter");
            ResultSet rs = pstmt.executeQuery();

            // Iterér gennem resultatsættet og tilføj navnene på retterne til ArrayList
            while (rs.next()) {
                String retNavn = rs.getString("navn");
                retterListe.add(retNavn);
            }

            // Luk ResultSet, PreparedStatement og Connection
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            ui.displayMessage("Fejl under hentning af retter: " + e.getMessage());
        }

        return retterListe;
    }



}

