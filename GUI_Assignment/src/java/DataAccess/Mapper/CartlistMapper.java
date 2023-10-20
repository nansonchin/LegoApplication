package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in cartlist table have 3 col
 */
import Model.*;
import java.sql.*;

public class CartlistMapper extends RowMapper<Cartlist> {

    public CartlistMapper() {
        super("cart_id");
    }

    @Override
    public Cartlist mapRow(ResultSet result) throws SQLException {
        return new Cartlist(new Cart(result.getInt(CART_ID)),
                new Product(result.getInt(PRODUCT_ID)),
                result.getInt(CART_QTY));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Cartlist cl) throws SQLException {
        String sqlQuery = "INSERT INTO " + cl.TABLENAME + " VALUES(?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, cl.getCart().getCartId());
        stmt.setInt(2, cl.getProduct().getProductId());
        stmt.setInt(3, cl.getCartQuantity());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Cartlist cl) throws SQLException {
        String sqlQuery = "Update " + cl.TABLENAME + " SET "
                + CART_QTY + " = ? "
                + "WHERE " + CART_ID + " = ? AND " + PRODUCT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(2, cl.getCart().getCartId());
        stmt.setInt(3, cl.getProduct().getProductId());
        stmt.setInt(1, cl.getCartQuantity());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Cartlist cl) throws SQLException {
        String sqlQuery = "DELETE FROM " + cl.TABLENAME
                + " WHERE " + CART_ID + " = ? AND " + PRODUCT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, cl.getCart().getCartId());
        stmt.setInt(2, cl.getProduct().getProductId());

        return stmt;
    }
}
