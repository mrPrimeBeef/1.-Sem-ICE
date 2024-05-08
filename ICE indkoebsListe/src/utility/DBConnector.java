package utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/icedatabase";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Esn64mjy:1";
    public void dbConnect()
    {
        System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
        Connection conn = null;
        try
        {
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println ("\nDatabase Connection Established...");
        }
        catch (Exception ex)
        {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }

        finally
        {
            if (conn != null)
            {
                try
                {
                    System.out.println("\n***** Let terminate the Connection *****");
                    conn.close ();
                    System.out.println ("\nDatabase connection terminated...");
                }
                catch (Exception ex)
                {
                    System.out.println ("Error in connection termination!");
                }
            }
        }
    }
}