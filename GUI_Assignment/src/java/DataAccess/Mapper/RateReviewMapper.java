package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in ratereview table have 6 col
 */
import Model.*;
import Utility.Converter;
import java.sql.*;

public class RateReviewMapper extends RowMapper<RateReview> {

    public RateReviewMapper() {
        super("review_id");
    }

    @Override
    public RateReview mapRow(ResultSet result) throws SQLException {
        return new RateReview(result.getInt(REVIEW_ID),
                result.getString(REVIEW_TEXT),
                result.getInt(REVIEW_RATING),
                Converter.convertSQLDateToUtilDate(result.getDate(REVIEW_DATE)),
                new Product(result.getInt(PRODUCT_ID)),
                new Member(result.getInt(MEMBER_ID)),
                new Orders(result.getInt(ORDERS_ID)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, RateReview rate) throws SQLException {
        String sqlQuery = "INSERT INTO " + rate.TABLENAME
                + " (" + REVIEW_TEXT + ", "
                + REVIEW_RATING + ", "
                + REVIEW_DATE + ", "
                + PRODUCT_ID + ", "
                + MEMBER_ID + ", "
                + ORDERS_ID + ") "
                + "VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, rate.getReviewId());
        //because auto create so the id will no need to track
        stmt.setString(1, rate.getReviewText());
        stmt.setInt(2, rate.getReviewRating());
        stmt.setDate(3, Converter.convertUtilDateToSQLDate(rate.getReviewDate()));
        stmt.setInt(4, rate.getProduct().getProductId());
        stmt.setInt(5, rate.getMember().getMemberId());
        stmt.setInt(6, rate.getOrders().getOrdersId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, RateReview rate) throws SQLException {
        String sqlQuery = "Update " + rate.TABLENAME + " SET "
                + REVIEW_TEXT + " = ?, "
                + REVIEW_RATING + " = ?, "
                + REVIEW_DATE + " = ?, "
                + PRODUCT_ID + " = ?, "
                + MEMBER_ID + " = ?, "
                + ORDERS_ID + " = ? WHERE " + REVIEW_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(7, rate.getReviewId());
        stmt.setString(1, rate.getReviewText());
        stmt.setInt(2, rate.getReviewRating());
        stmt.setDate(3, Converter.convertUtilDateToSQLDate(rate.getReviewDate()));
        stmt.setInt(4, rate.getProduct().getProductId());
        stmt.setInt(5, rate.getMember().getMemberId());
        stmt.setInt(6, rate.getOrders().getOrdersId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, RateReview rate) throws SQLException {
        String sqlQuery = "DELETE FROM " + rate.TABLENAME + " WHERE " + REVIEW_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, rate.getReviewId());

        return stmt;
    }
}
