package DataAccess.Mapper;

/**
 * @author LOH XIN JIE <br/>
 * in product table have 6 col
 */
import Model.*;
import Utility.Converter;
import java.sql.*;

public class ProductMapper extends RowMapper<Product> {

    public ProductMapper() {
        super("product_id");
    }

    @Override
    public Product mapRow(ResultSet result) throws SQLException {
        return new Product(result.getInt(PRODUCT_ID),
                result.getString(PRODUCT_NAME),
                result.getString(PRODUCT_DESC),
                result.getDouble(PRODUCT_PRICE),
                result.getString(PRODUCT_ACTIVE).charAt(0),
                new ImageTable(result.getInt(IMAGE_ID)));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, Product product) throws SQLException {
        String sqlQuery = "INSERT INTO " + product.TABLENAME
                + " (" + PRODUCT_NAME + ", "
                + PRODUCT_DESC + ", "
                + PRODUCT_PRICE + ", "
                + PRODUCT_ACTIVE + ", "
                + IMAGE_ID + ") "
                + "VALUES(?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, product.getProductId());
        //because auto create so the id will no need to track
        stmt.setString(1, product.getProductName());
        stmt.setString(2, product.getProductDesc());
        stmt.setDouble(3, product.getProductPrice());
        stmt.setString(4, Converter.convertToString(product.getProductActive()));
        stmt.setInt(5, product.getImageTable().getImageId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, Product product) throws SQLException {
        String sqlQuery = "Update " + product.TABLENAME + " SET "
                + PRODUCT_NAME + " = ?, "
                + PRODUCT_DESC + " = ?, "
                + PRODUCT_PRICE + " = ?, "
                + PRODUCT_ACTIVE + " = ?, "
                + IMAGE_ID + " = ? WHERE " + PRODUCT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(6, product.getProductId());
        stmt.setString(1, product.getProductName());
        stmt.setString(2, product.getProductDesc());
        stmt.setDouble(3, product.getProductPrice());
        stmt.setString(4, Converter.convertToString(product.getProductActive()));
        stmt.setInt(5, product.getImageTable().getImageId());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, Product product) throws SQLException {
        String sqlQuery = "DELETE FROM " + product.TABLENAME + " WHERE " + PRODUCT_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, product.getProductId());

        return stmt;
    }
}
