package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in addressBook table have 8 col
 */
import Model.*;
import java.sql.*;

public class AddressBookMapper extends RowMapper<AddressBook> {

    public AddressBookMapper() {
        super("address_id");
    }

    @Override
    public AddressBook mapRow(ResultSet result) throws SQLException {
        return new AddressBook(result.getInt(ADDRESS_ID),
                result.getString(ADDRESS_NAME),
                result.getString(ADDRESS_PHONE),
                result.getString(ADDRESS_NO),
                result.getString(ADDRESS_STREET),
                result.getString(ADDRESS_STATE),
                result.getString(ADDRESS_CITY),
                result.getString(ADDRESS_POSTCODE));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, AddressBook address) throws SQLException {
        String sqlQuery = "INSERT INTO " + address.TABLENAME
                + " (" + ADDRESS_NAME + ", "
                + ADDRESS_PHONE + ", "
                + ADDRESS_NO + ", "
                + ADDRESS_STREET + ", "
                + ADDRESS_STATE + ", "
                + ADDRESS_CITY + ", "
                + ADDRESS_POSTCODE + ") "
                + "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, address.getAddressId());
        //because auto create so the id will no need to track
        stmt.setString(1, address.getAddressName());
        stmt.setString(2, address.getAddressPhone());
        stmt.setString(3, address.getAddressNo());
        stmt.setString(4, address.getAddressStreet());
        stmt.setString(5, address.getAddressState());
        stmt.setString(6, address.getAddressCity());
        stmt.setString(7, address.getAddressPostcode());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, AddressBook address) throws SQLException {
        String sqlQuery = "Update " + address.TABLENAME + " SET "
                + ADDRESS_NAME + " = ?, "
                + ADDRESS_PHONE + " = ?, "
                + ADDRESS_NO + " = ?, "
                + ADDRESS_STREET + " = ?, "
                + ADDRESS_STATE + " = ?, "
                + ADDRESS_CITY + " = ?, "
                + ADDRESS_POSTCODE + " = ? WHERE " + ADDRESS_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(8, address.getAddressId());
        stmt.setString(1, address.getAddressName());
        stmt.setString(2, address.getAddressPhone());
        stmt.setString(3, address.getAddressNo());
        stmt.setString(4, address.getAddressStreet());
        stmt.setString(5, address.getAddressState());
        stmt.setString(6, address.getAddressCity());
        stmt.setString(7, address.getAddressPostcode());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, AddressBook address) throws SQLException {
        String sqlQuery = "DELETE FROM " + address.TABLENAME + " WHERE " + ADDRESS_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, address.getAddressId());

        return stmt;
    }
}
