package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in member table have 3 col
 */
import Model.*;
import java.sql.*;

public class MemberMapper extends RowMapper<Member> {

    public MemberMapper() {
        super("member_id");
    }

    @Override
    public Member mapRow(ResultSet result) throws SQLException {
        return new Member(result.getInt(MEMBER_ID),
                result.getString(MEMBER_NAME),
                result.getString(MEMBER_PASS));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Member member) throws SQLException {
        String sqlQuery = "INSERT INTO " + member.TABLENAME
                + " (" + MEMBER_NAME + ", "
                + MEMBER_PASS + ") "
                + "VALUES(?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, member.getMemberId());
        //because auto create so the id will no need to track
        stmt.setString(1, member.getMemberName());
        stmt.setString(2, member.getMemberPass());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Member member) throws SQLException {
        String sqlQuery = "Update " + member.TABLENAME + " SET "
                + MEMBER_NAME + " = ?, "
                + MEMBER_PASS + " = ? WHERE " + MEMBER_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(3, member.getMemberId());
        stmt.setString(1, member.getMemberName());
        stmt.setString(2, member.getMemberPass());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Member member) throws SQLException {
        String sqlQuery = "DELETE FROM " + member.TABLENAME + " WHERE " + MEMBER_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, member.getMemberId());

        return stmt;
    }
}
