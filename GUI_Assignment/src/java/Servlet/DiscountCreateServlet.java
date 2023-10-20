package Servlet;

/**
 * @author LOH XIN JIE
 */
import javax.servlet.http.*;
import javax.servlet.*;
import Model.*;
import DataAccess.*;
import DataAccess.Mapper.ProductMapper;
import java.io.*;
import java.util.*;
import java.sql.SQLException;
import Utility.*;

public class DiscountCreateServlet extends HttpServlet {

    private DBTable db = new DBTable();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request) || CheckPermission.permissionAdmin(request)) {
            //clear success message
            request.getSession().removeAttribute("DiscountSuccess");
            try {
                //get dropdown list
                String plistQuery = "SELECT * FROM PRODUCT WHERE PRODUCT_ACTIVE = ?";
                ArrayList<Object> condition = new ArrayList<>();
                condition.add(new Character('1'));

                ArrayList<Product> plist = db.Product.getData(new ProductMapper(), condition, plistQuery);

                request.setAttribute("plist", plist);
                request.getRequestDispatcher("Discount/view/Create.jsp").forward(request, response);
            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            response.sendRedirect("/GUI_Assignment/login/staffLogin.jsp");
        } else {
            //turn to error page , reason - premission denied
            response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void init() throws ServletException {
        this.db = new DBTable();
    }
}
