package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in member_address table have 2 col
 */
import Model.*;
import java.sql.*;

public class MemberAddressMapper extends RowMapper<MemberAddress> {

    public MemberAddressMapper() {
        super("member_id");
    }

    @Override
    public MemberAddress mapRow(ResultSet result) throws SQLException {
        return new MemberAddress(new AddressBook(result.getInt(ADDRESS_ID)),
                new Member(result.getInt(MEMBER_ID)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, MemberAddress ma) throws SQLException {
        String sqlQuery = "INSERT INTO " + ma.TABLENAME + " VALUES(?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, ma.getAddress().getAddressId());
        stmt.setInt(2, ma.getMember().getMemberId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, MemberAddress ma) throws SQLException {
        /**
         * because both are primary key so cannot be update<br/>
         * please use delete and add to implements it
         */
        throw new SQLException("member_address table cannot be update, because both are primary key \n Please using add and delete function to change it");
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, MemberAddress ma) throws SQLException {
        String sqlQuery = "DELETE FROM " + ma.TABLENAME + " WHERE " + MEMBER_ID + " = ? AND " + ADDRESS_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, ma.getMember().getMemberId());
        stmt.setInt(2, ma.getAddress().getAddressId());

        return stmt;
    }
}
