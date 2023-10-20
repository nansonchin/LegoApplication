package Servlet;

/**
 * @author LOH XIN JIE
 */
import javax.servlet.*;
import javax.servlet.http.*;
import DataAccess.*;
import DataAccess.Mapper.DiscountMapper;
import DataAccess.Mapper.ProductMapper;
import java.io.*;
import java.sql.SQLException;
import Model.*;
import java.util.*;
import Utility.*;

public class DiscountUpdateServlet extends HttpServlet {

    private DBTable db = new DBTable();

    @Override
    public void init() throws ServletException {
        db = new DBTable();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request) || CheckPermission.permissionAdmin(request)) {
            //clear success message
            request.getSession().removeAttribute("DiscountSuccess");
            try {
                int discountID = Integer.parseInt(request.getParameter("discountID"));

                //get data and display at front
                Discount discount = db.Discount.getData(new DiscountMapper(), discountID).get(0);

                Product product = db.Product.getData(new ProductMapper(), discount.getProduct().getProductId()).get(0);

                discount.setProduct(product);

                request.setAttribute("discount", discount);
                request.getRequestDispatcher("Discount/view/Update.jsp").forward(request, response);
            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Discount ID Missing Problem");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (Exception ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexcepted Exception Occurs");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            response.sendRedirect("/GUI_Assignment/login/staffLogin.jsp");
        } else {
            //turn to error page , reason - premission denied
            response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
