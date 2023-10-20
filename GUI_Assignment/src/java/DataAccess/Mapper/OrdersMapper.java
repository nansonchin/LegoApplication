package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in orders table have 9 col
 */
import Model.*;
import Utility.Converter;
import java.sql.*;

public class OrdersMapper extends RowMapper<Orders> {

    public OrdersMapper() {
        super("orders_id");
    }

    @Override
    public Orders mapRow(ResultSet result) throws SQLException {
        return new Orders(result.getInt(ORDERS_ID),
                Converter.convertSQLDateToUtilDate(result.getDate(ORDERS_DATE)),
                result.getString(ORDERS_PAYMENT_TYPE),
                result.getDouble(ORDERS_TTL_PRICE),
                result.getDouble(ORDERS_TAX),
                result.getDouble(ORDERS_DELIVERY_FEE),
                result.getDouble(ORDERS_EXPRESS_SHIP),
                new Member(result.getInt(MEMBER_ID)),
                new AddressBook(result.getInt(ADDRESS_ID)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Orders order) throws SQLException {
        String sqlQuery = "INSERT INTO " + order.TABLENAME
                + " (" + ORDERS_DATE + ", "
                + ORDERS_PAYMENT_TYPE + ", "
                + ORDERS_TTL_PRICE + ", "
                + ORDERS_TAX + ", "
                + ORDERS_DELIVERY_FEE + ", "
                + ORDERS_EXPRESS_SHIP + ", "
                + MEMBER_ID + ", "
                + ADDRESS_ID + ") "
                + "VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, order.getOrdersId());
        stmt.setDate(1, Converter.convertUtilDateToSQLDate(order.getOrdersDate()));
        stmt.setString(2, order.getOrdersPaymentType());
        stmt.setDouble(3, order.getOrdersTtlPrice());
        stmt.setDouble(4, order.getOrdersTax());
        stmt.setDouble(5, order.getOrdersDeliveryFee());
        stmt.setDouble(6, order.getOrdersExpressShipping());
        stmt.setInt(7, order.getMember().getMemberId());
        stmt.setInt(8, order.getAddress().getAddressId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Orders order) throws SQLException {
        String sqlQuery = "Update " + order.TABLENAME + " SET "
                + ORDERS_DATE + " = ?, "
                + ORDERS_PAYMENT_TYPE + " = ?, "
                + ORDERS_TTL_PRICE + " = ?, "
                + ORDERS_TAX + " = ?, "
                + ORDERS_DELIVERY_FEE + " = ?, "
                + ORDERS_EXPRESS_SHIP + " = ?, "
                + MEMBER_ID + " = ?, "
                + ADDRESS_ID + " = ? WHERE " + ORDERS_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(9, order.getOrdersId());
        stmt.setDate(1, Converter.convertUtilDateToSQLDate(order.getOrdersDate()));
        stmt.setString(2, order.getOrdersPaymentType());
        stmt.setDouble(3, order.getOrdersTtlPrice());
        stmt.setDouble(4, order.getOrdersTax());
        stmt.setDouble(5, order.getOrdersDeliveryFee());
        stmt.setDouble(6, order.getOrdersExpressShipping());
        stmt.setInt(7, order.getMember().getMemberId());
        stmt.setInt(8, order.getAddress().getAddressId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Orders order) throws SQLException {
        String sqlQuery = "DELETE FROM " + order.TABLENAME + " WHERE " + ORDERS_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, order.getOrdersId());

        return stmt;
    }
}
