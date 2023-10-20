package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in member table have 3 col
 */
import Model.*;
import java.sql.*;

public class CartMapper extends RowMapper<Cart> {

    public CartMapper() {
        super("cart_id");
    }

    @Override
    public Cart mapRow(ResultSet result) throws SQLException {
        return new Cart(result.getInt(CART_ID),
                new Member(result.getInt(MEMBER_ID)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Cart cart) throws SQLException {
        String sqlQuery = "INSERT INTO " + cart.TABLENAME
                + " (" + MEMBER_ID + ") "
                + "VALUES(?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, cart.getCartId());
        //because auto create so the id will no need to track
        stmt.setInt(1, cart.getMember().getMemberId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Cart cart) throws SQLException {
        String sqlQuery = "Update " + cart.TABLENAME + " SET "
                + MEMBER_ID + " = ? WHERE " + CART_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(2, cart.getCartId());
        stmt.setInt(1, cart.getMember().getMemberId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Cart cart) throws SQLException {
        String sqlQuery = "DELETE FROM " + cart.TABLENAME + " WHERE " + CART_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, cart.getCartId());

        return stmt;
    }
}
