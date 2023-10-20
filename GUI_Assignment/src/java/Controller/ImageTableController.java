package Controller;

/**
 * @author LOH XIN JIE
 */
import DataAccess.*;
import DataAccess.Mapper.*;
import Model.*;
import Utility.Converter;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class ImageTableController {

    private DBTable db;

    public ImageTableController() {
        db = new DBTable();
    }

    /**
     * when image added successful will return Trans_id<br/>
     *
     * @param request
     * @return 
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @throws java.sql.SQLException
     * @MultipartConfig(<br/>
     * fileSizeThreshold = 1024 * 1024 * 1, // 1 MB<br/>
     * maxFileSize = 1024 * 1024 * 10, // 10 MB<br/>
     * maxRequestSize = 1024 * 1024 * 15, // 15 MB<br/>
     * location = "C:/tmp"<br/>
     * }<br/>
     * <p>
     * When using this function please added <b>@MultiConfig</b> above to your
     * <b>Servlet</b>
     * </p>
     * <ul>
     * <li>max file size upload up to 10MB</li>
     * <li>please import javax.servlet.annotation.MultipartConfig;</li>
     * </ul>
     */
    public int UpdateImage(HttpServletRequest request) throws IOException, ServletException, SQLException {
        ImageTable itable = new ImageTable();
        String transID;

        //name of the image form must be image
        Part imagePart = request.getPart("image");

        if (imagePart != null) {
            //set table value
            itable.setImageName(imagePart.getSubmittedFileName());
            itable.setImageContentType(imagePart.getContentType());

            //get image
            itable.setInputImage(imagePart.getInputStream());

            //get trans id
            transID = Converter.convertDateToFormatString(new Date());
            itable.setTransID(transID);

            //save to imagetable
            if (db.ImageTable.Add(new ImageTableMapper(), itable)) {
                //update success -> get image id
                String sqlQuery = "SELECT * FROM IMAGETABLE WHERE TRANS_ID = ?";
                ArrayList<Object> clist = new ArrayList<>();
                clist.add(new String(transID));

                return db.ImageTable.getData(new ImageTableMapper(), clist, sqlQuery).get(0).getImageId();
            }
        }

        return -1;//invalid
    }
    
    public int uploadImage(Part imagePart) throws IOException, ServletException, SQLException{
        ImageTable itable = new ImageTable();
        String transID;
        if (imagePart != null) {
            //set table value
            itable.setImageName(imagePart.getSubmittedFileName());
            itable.setImageContentType(imagePart.getContentType());

            //get image
            itable.setInputImage(imagePart.getInputStream());

            //get trans id
            transID = Converter.convertDateToFormatString(new Date());
            itable.setTransID(transID);

            //save to imagetable
            if (db.ImageTable.Add(new ImageTableMapper(), itable)) {
                //update success -> get image id
                String sqlQuery = "SELECT * FROM IMAGETABLE WHERE TRANS_ID = ?";
                ArrayList<Object> clist = new ArrayList<>();
                clist.add(new String(transID));

                return db.ImageTable.getData(new ImageTableMapper(), clist, sqlQuery).get(0).getImageId();
            }
        }

        return -1;//invalid
    }
    
    public boolean updateImage(Part imagePart, int imgID) throws IOException, ServletException, SQLException{
        ImageTable itable = new ImageTable();
        String transID = Converter.convertDateToFormatString(new Date());
        if (imagePart != null) {
            itable.setImageName(imagePart.getSubmittedFileName());
            itable.setImageContentType(imagePart.getContentType());
            itable.setInputImage(imagePart.getInputStream());
            itable.setImageId(imgID);
            itable.setTransID(transID);

            if (db.ImageTable.Update(new ImageTableMapper(), itable)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean updateImage(Part imagePart, String imgID) throws IOException, ServletException, SQLException{
        int id = Integer.parseInt(imgID);
        return updateImage(imagePart, id);
    }
}
