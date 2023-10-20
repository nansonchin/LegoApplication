package DataAccess.Mapper;

/**
 * @author LOH XIN JIE<br/>
 * in image table have 4 col<br/>
 * in image table trans_id is long type of date
 */
import Model.ImageTable;
import java.sql.*;

public class ImageTableMapper extends RowMapper<ImageTable> {

    public ImageTableMapper() {
        super("image_id");
    }

    @Override
    public ImageTable mapRow(ResultSet result) throws SQLException {
        //get output in byte[]
        return new ImageTable(result.getInt(IMAGE_ID),
                result.getString(IMAGE_NAME),
                result.getString(IMAGE_CONTENT),
                result.getString(TRANS_ID),
                result.getBytes(IMAGE));
    }

    @Override
    public PreparedStatement prepareAdd(Connection conn, ImageTable imageTable) throws SQLException {
        String sqlQuery = "INSERT INTO " + imageTable.TABLENAME
                + " (" + IMAGE_NAME + ", "
                + IMAGE_CONTENT + ", "
                + TRANS_ID + ", "
                + IMAGE + ") "
                + "VALUES(?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        //stmt.setInt(1, imageTable.getImageId());
        //auto generate id
        stmt.setString(1, imageTable.getImageName());
        stmt.setString(2, imageTable.getImageContentType());
        stmt.setString(3, imageTable.getTransID());
        stmt.setBlob(4, imageTable.getInputImage());
        return stmt;
    }

    @Override
    public PreparedStatement prepareUpdate(Connection conn, ImageTable imageTable) throws SQLException {
        String sqlQuery = "Update " + imageTable.TABLENAME + " SET "
                + IMAGE_NAME + " = ?, "
                + IMAGE_CONTENT + " = ?, "
                + TRANS_ID + " = ?, "
                + IMAGE + " = ? WHERE " + IMAGE_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(5, imageTable.getImageId());
        stmt.setString(1, imageTable.getImageName());
        stmt.setString(2, imageTable.getImageContentType());
        stmt.setString(3, imageTable.getTransID());
        stmt.setBlob(4, imageTable.getInputImage());
        return stmt;
    }

    @Override
    public PreparedStatement prepareDelete(Connection conn, ImageTable imageTable) throws SQLException {
        String sqlQuery = "DELETE FROM " + imageTable.TABLENAME + " WHERE " + IMAGE_ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, imageTable.getImageId());

        return stmt;
    }
}
