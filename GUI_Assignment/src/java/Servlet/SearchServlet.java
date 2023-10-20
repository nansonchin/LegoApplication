package Servlet;

/**
 * @author LOH XIN JIE
 */
import Controller.HomeController;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import DataAccess.*;
import DataAccess.Mapper.ProductMapper;
import Model.*;
import java.util.*;
import java.sql.*;
import Utility.*;

public class SearchServlet extends HttpServlet {

    private DBTable db;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //get user search data
            String search = (String) request.getParameter("search");

            //generate sql
            String query = "SELECT * FROM PRODUCT WHERE product_name LIKE '%" + search + "%'";

            //get data
            ArrayList<Product> plist = db.Product.getData(new ProductMapper(), new ArrayList<Object>(), query);

            request.setAttribute("searchResult", plist);

            //loop plist to get the rating value
            HashMap<Integer, Double> ratingList = new HashMap<>();
            for (Product p : plist) {
                double rate = HomeController.getProductRate(HomeController.getProductRatingList(db, p.getProductId()));
                ratingList.put(p.getProductId(), rate);
            }

            request.setAttribute("ratingList", ratingList);

            request.getRequestDispatcher("/Home/view/SearchResult.jsp").forward(request, response);
        } catch (SQLException ex) {
            //turn error page
            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
            request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
            request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!CheckPermission.permissionStaff(request)) {
            processRequest(request, response);
        } else {
            //turn to error page , reason - premission denied
            response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!CheckPermission.permissionStaff(request)) {
            processRequest(request, response);
        } else {
            //turn to error page , reason - premission denied
            response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
        }
    }

    @Override
    public void init() throws ServletException {
        this.db = new DBTable();
    }

}
