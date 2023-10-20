/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yeet
 */
public class DBaccess {
    
    //customize sql SELECT runner
    public static List<HashMap<String, Object>> customizeSqlSelect(String selectStatement) {
        Connection conn = null;
        try {
            conn = ConnectionDriver.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectStatement);

            return convertResultSetToList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DbSet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionDriver.endConnection(conn);
        }
        return null;
    }

    private static List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }
}
