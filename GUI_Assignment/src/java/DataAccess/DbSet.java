package DataAccess;

/**
 * @author LOH XIN JIE
 */
import DataAccess.Mapper.*;
import Utility.Converter;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbSet<T extends DBModel> {

    public T t;
    private Connection conn;
    private Logger logger;

    public DbSet(T t) {
        this.t = t;
        this.logger = Logger.getLogger(DbSet.class.getName());
    }

    //get all data in corresponding table
    public ArrayList<T> getData(RowMapper<T> mapper) throws SQLException {
        ArrayList<T> tList = new ArrayList<>();
        try {
            conn = ConnectionDriver.connect();
            String sqlQuery = "SELECT * FROM " + t.TABLENAME;

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery();

            while (rs != null && rs.next()) {
                tList.add(mapper.mapRow(rs));
            }

            return tList;
        } catch (SQLException ex) {
            /*connection and execute error*/
            logger.log(Level.SEVERE, ex.getMessage());
            conn.rollback();

            /**
             * this throw is for checking to know have error occurs when during
             * db execute normally will be stmt or conn error
             */
            throw new SQLException(ex.getMessage());
        } finally {
            ConnectionDriver.endConnection(conn);
        }
    }

    //SELECT * FROM TABLENAME WHERE mapper.id = ?
    public ArrayList<T> getData(RowMapper<T> mapper, int rowID) throws SQLException {
        ArrayList<T> tList = new ArrayList<>();
        try {
            conn = ConnectionDriver.connect();
            String sqlQuery = "SELECT * FROM " + t.TABLENAME + " WHERE " + mapper.TABLE_PRIMARY + " = ?";

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, rowID);

            ResultSet rs = stmt.executeQuery();

            while (rs != null && rs.next()) {
                tList.add(mapper.mapRow(rs));
            }

            return tList;
        } catch (SQLException ex) {
            /*connection and execute error*/
            logger.log(Level.SEVERE, ex.getMessage());
            conn.rollback();

            /**
             * this throw is for checking to know have error occurs when during
             * db execute normally will be stmt or conn error
             */
            throw new SQLException(ex.getMessage());
        } finally {
            ConnectionDriver.endConnection(conn);
        }
    }

    //passing sql and List<Object>
    public ArrayList<T> getData(RowMapper<T> mapper, ArrayList<Object> condition, String sqlQuery) throws SQLException {
        ArrayList<T> tList = new ArrayList<>();
        try {
            conn = ConnectionDriver.connect();

            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            for (int i = 1; i <= condition.size(); i++) {
                setParamenter(stmt, condition.get(i - 1), i);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs != null && rs.next()) {
                tList.add(mapper.mapRow(rs));
            }

            return tList;
        } catch (SQLException ex) {
            /*connection and execute error*/
            logger.log(Level.SEVERE, ex.getMessage());
            conn.rollback();

            /**
             * this throw is for checking to know have error occurs when during
             * db execute normally will be stmt or conn error
             */
            throw new SQLException(ex.getMessage());
        } finally {
            ConnectionDriver.endConnection(conn);
        }
    }

    //set ? paramenter
    private void setParamenter(PreparedStatement stmt, Object condition, int index) throws SQLException {
        if (condition instanceof Integer) {
            stmt.setInt(index, (Integer) condition);
        } else if (condition instanceof String) {
            stmt.setString(index, (String) condition);
        } else if (condition instanceof Character) {
            stmt.setString(index, String.valueOf((Character) condition));
        } else if (condition instanceof Double) {
            stmt.setDouble(index, (Double) condition);
        } else if (condition instanceof java.util.Date) {
            stmt.setDate(index, Converter.convertUtilDateToSQLDate((java.util.Date) condition));
        }
    }

    //Insert
    public boolean Add(RowMapper<T> mapper, T t) throws SQLException {
        try {
            conn = ConnectionDriver.connect();

            PreparedStatement stmt = mapper.prepareAdd(conn, t);

            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            /*connection and execute error*/
            logger.log(Level.SEVERE, ex.getMessage());
            conn.rollback();

            /**
             * this throw is for checking to know have error occurs when during
             * db execute normally will be stmt or conn error
             */
            throw new SQLException(ex.getMessage());
        } finally {
            ConnectionDriver.endConnection(conn);
        }
    }

    /**
     * <h1>Update<h1/><br/>
     * for <b>member_address</b> didn't provide update function because inside
     * only contain two primary key <br/>
     * if need to make change please using add and delete function to process it
     */
    public boolean Update(RowMapper<T> mapper, T t) throws SQLException {
        try {
            conn = ConnectionDriver.connect();

            PreparedStatement stmt = mapper.prepareUpdate(conn, t);

            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            /*connection and execute error*/
            logger.log(Level.SEVERE, ex.getMessage());
            conn.rollback();

            /**
             * this throw is for checking to know have error occurs when during
             * db execute normally will be stmt or conn error
             */
            throw new SQLException(ex.getMessage());
        } finally {
            ConnectionDriver.endConnection(conn);
        }
    }

    //Delete
    public boolean Delete(RowMapper<T> mapper, T t) throws SQLException {
        try {
            conn = ConnectionDriver.connect();

            PreparedStatement stmt = mapper.prepareDelete(conn, t);

            return stmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            /*connection and execute error*/
            logger.log(Level.SEVERE, ex.getMessage());
            conn.rollback();

            /**
             * this throw is for checking to know have error occurs when during
             * db execute normally will be stmt or conn error
             */
            throw new SQLException(ex.getMessage());
        } finally {
            ConnectionDriver.endConnection(conn);
        }
    }

}
