/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controller.DiscountController;
import Controller.PaymentController;
import DataAccess.DBTable;
import DataAccess.Mapper.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import Utility.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Acer
 */
@WebServlet(name = "RateReviewServlet", urlPatterns = {"/RateReviewServlet"})
public class RateReviewServlet extends HttpServlet {

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
            out.println("<title>Servlet RateReviewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RateReviewServlet at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        if (CheckPermission.permissionUser(request)) {
            String productId = request.getParameter("productId");
            int pId = Integer.parseInt(productId);
            request.setAttribute("productID", pId);

            String orderId = request.getParameter("orderId");
            int oId = Integer.parseInt(orderId);

            request.setAttribute("orderID", oId);

            String sql = "SELECT * FROM PRODUCT WHERE product_id = ? ";

            ArrayList<Object> params = new ArrayList<>();
            params.add(pId);

            ArrayList<Product> product;
            try {
                product = new DBTable().Product.getData(new ProductMapper(), params, sql);

                if (product != null) {
                    request.setAttribute("product", product);
                }
                HttpSession session = request.getSession();
                HashMap<Integer, Double> dlist = new HashMap<>();
                for (Product p : product) {
                    DBTable db = new DBTable();
                    double originalPrice = p.getProductPrice();

                    Discount discount = DiscountController.getDiscount(db, p.getProductId()); // get the discount for the product

                    if (discount != null) {
                        double discountedPrice = DiscountController.getPrice(originalPrice, discount.getDiscountPercentage());
                        dlist.put(p.getProductId(), discountedPrice);

                    }
                }
                session.setAttribute("productPrice", dlist);

                ArrayList<Object> pcondition = new ArrayList<>();
                pcondition.add(pId);

                DBTable db = new DBTable();
                // Check if product has discount
                List<Discount> discountList = db.Discount.getData(new DiscountMapper(), pcondition, "SELECT * FROM DISCOUNT WHERE product_id = ?");

                if (discountList.size() > 0) {
                    request.setAttribute("dlist", discountList);
                }

                request.getRequestDispatcher("/RateReview/rateAndReview.jsp").forward(request, response);
            } catch (SQLException ex) {
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
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
        if (CheckPermission.permissionUser(request)) {
            HttpSession session = request.getSession();
//        processRequest(request, response);
            String reviewText = request.getParameter("reviewText");
            String rate = request.getParameter("rating");

            String errorMessage = null;
            if (reviewText == null) {
                errorMessage = "Please write review.";
            } else if (rate == null) {
                errorMessage = "Please give us a rating.";
            }

            //if got error return back
            if (errorMessage != null) {
                String productId = request.getParameter("productId");
                int pId = Integer.parseInt(productId);
                request.setAttribute("productID", pId);

                String orderId = request.getParameter("orderId");
                int oId = Integer.parseInt(orderId);

                request.setAttribute("orderID", oId);

                String sql = "SELECT * FROM PRODUCT WHERE product_id = ? ";

                ArrayList<Object> params = new ArrayList<>();
                params.add(pId);

                ArrayList<Product> product;
                try {
                    product = new DBTable().Product.getData(new ProductMapper(), params, sql);

                    if (product != null) {
                        request.setAttribute("product", product);
                    }
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                }

                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/RateReview/rateAndReview.jsp").forward(request, response);
            } else {
//            int memberId = 2000;
                Member member = (Member) session.getAttribute("member");
                if (session == null || session.getAttribute("member") == null) {
                    response.sendRedirect("/GUI_Assignment/HomeServlet");
                    return;
                }
                int memberId = member.getMemberId();
                Date rateDate = new Date();
                try {
                    if (memberId != 0) {
                        int rating = Integer.parseInt(rate);
                        PaymentController p = new PaymentController();

                        //productid
                        String productId = request.getParameter("productId");
                        int pId = Integer.parseInt(productId);

                        String orderId = request.getParameter("orderId");
                        int oId = Integer.parseInt(orderId);

                        boolean success = p.addRateReview(reviewText, rating, rateDate, pId, memberId, oId);

                        if (success != false) {
                            response.sendRedirect("/GUI_Assignment/HomeServlet");
                        }

                    }
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                } catch (Exception ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                }
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
