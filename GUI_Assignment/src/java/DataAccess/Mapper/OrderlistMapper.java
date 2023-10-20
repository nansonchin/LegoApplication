package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in orderlist table have 4 col
 */
import Model.*;
import java.sql.*;

public class OrderlistMapper extends RowMapper<Orderlist> {

    public OrderlistMapper() {
        super("orders_id");
    }

    @Override
    public Orderlist mapRow(ResultSet result) throws SQLException {
        return new Orderlist(new Orders(result.getInt(ORDERS_ID)),
                new Product(result.getInt(PRODUCT_ID)),
                result.getInt(ORDERS_QTY),
                result.getDouble(ORDERS_SUBPRICE));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Orderlist ol) throws SQLException {
        String sqlQuery = "INSERT INTO " + ol.TABLENAME + " VALUES(?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, ol.getOrder().getOrdersId());
        stmt.setInt(2, ol.getProduct().getProductId());
        stmt.setInt(3, ol.getOrdersQuantity());
        stmt.setDouble(4, ol.getOrdersSubprice());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Orderlist ol) throws SQLException {
        String sqlQuery = "Update " + ol.TABLENAME + " SET "
                + ORDERS_QTY + " = ?, "
                + ORDERS_SUBPRICE + " = ? "
                + "WHERE " + ORDERS_ID + " = ? AND " + PRODUCT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(3, ol.getOrder().getOrdersId());
        stmt.setInt(4, ol.getProduct().getProductId());
        stmt.setInt(1, ol.getOrdersQuantity());
        stmt.setDouble(2, ol.getOrdersSubprice());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Orderlist ol) throws SQLException {
        String sqlQuery = "DELETE FROM " + ol.TABLENAME
                + " WHERE " + ORDERS_ID + " = ? AND " + PRODUCT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, ol.getOrder().getOrdersId());
        stmt.setInt(2, ol.getProduct().getProductId());

        return stmt;
    }
}
