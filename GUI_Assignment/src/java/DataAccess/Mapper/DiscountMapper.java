package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in discount table have 5 col
 */
import Model.*;
import Utility.Converter;
import java.sql.*;

public class DiscountMapper extends RowMapper<Discount> {

    public DiscountMapper() {
        super("discount_id");
    }

    @Override
    public Discount mapRow(ResultSet result) throws SQLException {
        return new Discount(result.getInt(DISCOUNT_ID),
                result.getInt(DISCOUNT_PERCENTAGE),
                Converter.convertSQLDateToUtilDate(result.getDate(DISCOUNT_START_DATE)),
                Converter.convertSQLDateToUtilDate(result.getDate(DISCOUNT_END_DATE)),
                new Product(result.getInt(PRODUCT_ID)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Discount discount) throws SQLException {
        String sqlQuery = "INSERT INTO " + discount.TABLENAME
                + " (" + DISCOUNT_PERCENTAGE + ", "
                + DISCOUNT_START_DATE + ", "
                + DISCOUNT_END_DATE + ", "
                + PRODUCT_ID + ") "
                + "VALUES(?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, discount.getDiscountId());
        //because auto create so the id will no need to track
        stmt.setInt(1, discount.getDiscountPercentage());
        stmt.setDate(2, Converter.convertUtilDateToSQLDate(discount.getDiscountStartDate()));
        stmt.setDate(3, Converter.convertUtilDateToSQLDate(discount.getDiscountEndDate()));
        stmt.setInt(4, discount.getProduct().getProductId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Discount discount) throws SQLException {
        String sqlQuery = "Update " + discount.TABLENAME + " SET "
                + DISCOUNT_PERCENTAGE + " = ?, "
                + DISCOUNT_START_DATE + " = ?, "
                + DISCOUNT_END_DATE + " = ?, "
                + PRODUCT_ID + " = ? WHERE " + DISCOUNT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(5, discount.getDiscountId());
        stmt.setInt(1, discount.getDiscountPercentage());
        stmt.setDate(2, Converter.convertUtilDateToSQLDate(discount.getDiscountStartDate()));
        stmt.setDate(3, Converter.convertUtilDateToSQLDate(discount.getDiscountEndDate()));
        stmt.setInt(4, discount.getProduct().getProductId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Discount discount) throws SQLException {
        String sqlQuery = "DELETE FROM " + discount.TABLENAME + " WHERE " + DISCOUNT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, discount.getDiscountId());

        return stmt;
    }
}
