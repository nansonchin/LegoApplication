package DataAccess;

import java.sql.*;

/**
 * @author LOH XIN JIE
 */
public class ConnectionDriver {

    private static final String DATABASE = "jdbc:derby://localhost:1527/GUI_Assignment";
    private static final String USER = "G1";
    private static final String PASSWORD = "G1";

    public static Connection connect() throws SQLException {
        Connection conn;

        try {
            conn = DriverManager.getConnection(DATABASE, USER, PASSWORD);
            return conn;
        } catch (SQLException ex) {
            throw new SQLException("Could not connect to database : " + ex.getMessage());
        }
    }

    public static void endConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            //close error
            System.out.println("Could not close connection : " + ex.getMessage());
        }
    }
}
