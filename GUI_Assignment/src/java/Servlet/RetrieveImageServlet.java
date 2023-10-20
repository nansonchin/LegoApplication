package Servlet;

/**
 * @author LOH XIN JIE
 */
import Controller.ImageTableController;
import DataAccess.*;
import DataAccess.Mapper.ImageTableMapper;
import Model.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15, // 15 MB
        location = "C:/tmp"
)//max file size upload up to 10MB
public class RetrieveImageServlet extends HttpServlet {

    private DBTable db;

    /**
     * for GET and POST method use
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse respone) throws ServletException, IOException {

        String imageID = request.getParameter("imageID");

        try {
            if (imageID != null) {
                //use trans id to get image
                int id = Integer.parseInt(imageID);
                ImageTable itable = db.ImageTable.getData(new ImageTableMapper(), id).get(0);

                //set return info
                respone.setContentType(itable.getImageContentType());
                OutputStream os = respone.getOutputStream();

                //convert byte to input stream
                InputStream imageStream = new ByteArrayInputStream(itable.getOutputImage());
                byte[] buffer = new byte[2048];
                int length = 0;
                while ((length = imageStream.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
            }
        } catch (SQLException ex) {
            respone.getOutputStream().flush();
            respone.getOutputStream().close();
            //turn error page
            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
            request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
            request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, respone);
        } catch (Exception ex) {
            respone.getOutputStream().flush();
            respone.getOutputStream().close();
            //turn error page
            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexcepted error occurs");
            request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, respone);
        }
    }

    @Override
    public void init() throws ServletException {
        db = new DBTable();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
//        try {
//            ImageTableController iControl = new ImageTableController();
//            iControl.UpdateImage(request);
//
//        } catch (SQLException ex) {
//            //turn error page
//            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
//            request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
//            request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
//        }
//        response.sendRedirect("ExampleForImage/index.jsp");
        //request.getRequestDispatcher("ExampleForImage/index.jsp").forward(request, response);
        //got bug the location is different when using requestDispatcher
        //requestdispatcher http://localhost:8080/GUI_Assignment/RetrieveImageServlet
        //will using the old location when passing
    }

}
