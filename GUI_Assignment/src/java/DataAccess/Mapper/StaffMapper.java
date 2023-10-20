package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in staff table have 7 col
 */
import Model.Staff;
import Utility.Converter;
import java.sql.*;

public class StaffMapper extends RowMapper<Staff> {

    public StaffMapper() {
        super("staff_id");
    }

    @Override
    public Staff mapRow(ResultSet result) throws SQLException {
        return new Staff(result.getInt(STAFF_ID),
                result.getString(STAFF_NAME),
                result.getString(STAFF_PASS),
                result.getString(STAFF_IC),
                result.getString(STAFF_PHNO),
                result.getString(STAFF_EMAIL),
                Converter.convertSQLDateToUtilDate(result.getDate(STAFF_BIRTHDATE)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Staff staff) throws SQLException {
        String sqlQuery = "INSERT INTO " + staff.TABLENAME
                + " (" + STAFF_NAME + ", "
                + STAFF_PASS + ", "
                + STAFF_IC + ", "
                + STAFF_PHNO + ", "
                + STAFF_EMAIL + ", "
                + STAFF_BIRTHDATE + ") "
                + "VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, staff.getStaffId());
        //because auto create so the id will no need to track
        stmt.setString(1, staff.getStaffName());
        stmt.setString(2, staff.getStaffPass());
        stmt.setString(3, staff.getStaffIc());
        stmt.setString(4, staff.getStaffPhNo());
        stmt.setString(5, staff.getStaffEmail());
        stmt.setDate(6, Converter.convertUtilDateToSQLDate(staff.getStaffBirthdate()));
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Staff staff) throws SQLException {
        String sqlQuery = "Update " + staff.TABLENAME + " SET "
                + STAFF_NAME + " = ?, "
                + STAFF_PASS + " = ?, "
                + STAFF_IC + " = ?, "
                + STAFF_PHNO + " = ?, "
                + STAFF_EMAIL + " = ?, "
                + STAFF_BIRTHDATE + " = ? WHERE " + STAFF_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(7, staff.getStaffId());
        stmt.setString(1, staff.getStaffName());
        stmt.setString(2, staff.getStaffPass());
        stmt.setString(3, staff.getStaffIc());
        stmt.setString(4, staff.getStaffPhNo());
        stmt.setString(5, staff.getStaffEmail());
        stmt.setDate(6, Converter.convertUtilDateToSQLDate(staff.getStaffBirthdate()));
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Staff staff) throws SQLException {
        String sqlQuery = "DELETE FROM " + staff.TABLENAME + " WHERE " + STAFF_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, staff.getStaffId());

        return stmt;
    }
}
