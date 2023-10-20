/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DataAccess.DBTable;
import DataAccess.DbSet;
import DataAccess.Mapper.ProductMapper;
import DataAccess.Mapper.RateReviewMapper;
import Model.Product;
import Model.RateReview;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utility.*;

/**
 *
 * @author guoc7
 */
public class productMenuServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet productMenuServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet productMenuServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!CheckPermission.permissionStaff(request)) {
            try {
                DBTable db = new DBTable();
                String sql = "Select * From Product Where PRODUCT_ACTIVE = ?";
                ArrayList<Object> list = new ArrayList();
                list.add(new Integer(1));
                List<Product> productList = db.Product.getData(new ProductMapper(), list, sql);
                request.setAttribute("productList", productList);

                List<RateReview> rateCountList = db.RateReview.getData(new RateReviewMapper());
                List<Double> ratingList = new ArrayList<>();
                HashMap<Integer, Double> hash = new HashMap<Integer, Double>();

                //find RateAndReview Format
                for (int i = 0; i < productList.size(); i++) {
                    double totalRating = 0;
                    double ratingCount = 0;
                    for (int y = 0; y < rateCountList.size(); y++) {
                        if (rateCountList.get(y).getProduct().getProductId() == productList.get(i).getProductId()) {
                            totalRating += rateCountList.get(y).getReviewRating();
                            ratingCount++;
                        }
                    }
                    if (ratingCount > 0) {
                        double avgRating = Math.round(totalRating / ratingCount *100)/100.0;
                        //ratingList.add(avgRating);
                        hash.put(productList.get(i).getProductId(), avgRating);
                    } else {
                        hash.put(productList.get(i).getProductId(), 0.00);
//                    ratingList.add(0.00);
                    }
                }

                request.setAttribute("ratingList", hash);
                request.setAttribute("bannerList", productList);
                request.getRequestDispatcher("/productMenu/menu-list.jsp").forward(request, response);
            } catch (SQLException ex) {
                ex.printStackTrace();
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
            }
        } else {
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlei dont t description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
