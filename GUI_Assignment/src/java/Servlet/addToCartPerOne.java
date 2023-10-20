/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DataAccess.DBTable;
import DataAccess.Mapper.CartMapper;
import DataAccess.Mapper.CartlistMapper;
import Model.Cart;
import Model.Cartlist;
import Model.Member;
import Model.Product;
import Utility.CheckPermission;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guoc7
 */
public class addToCartPerOne extends HttpServlet {

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
            out.println("<title>Servlet addToCartPerOne</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addToCartPerOne at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
            if (!CheckPermission.permissionStaff(request) && !CheckPermission.permissionNoLogin(request)) {
                try {
                    DBTable db = new DBTable();
                    int cartId = -1;
                    HttpSession session = request.getSession();

                    Member member = (Member) session.getAttribute("member");
                    String memberName = member.getMemberName();
//        getMemberId
                    //String sqlstmt = "SELECT * FROM CART Where MEMBER_ID=?";
                    String sqlstmt = "SELECT * "
                            + "FROM CART "
                            + "INNER JOIN MEMBER ON CART.MEMBER_ID = MEMBER.MEMBER_ID "
                            + "WHERE MEMBER.MEMBER_NAME = ?";
                    ArrayList<Object> list = new ArrayList();
                    list.add(memberName);
                    Cart cart = db.Cart.getData(new CartMapper(), list, sqlstmt).get(0);
                    cartId = cart.getCartId();

                    int productId = Integer.parseInt(request.getParameter("menu-list-one"));
                    int cartQuantity = 1;

                    //set sql item values;
                    ArrayList<Object> parameters = new ArrayList<Object>();
                    String sql = "Select * From Cartlist WHERE CART_ID=? and PRODUCT_ID=?";
                    parameters.add(cartId);
                    parameters.add(productId);
                    String previousUrl = request.getHeader("Referer");

                    List<Cartlist> cartListChecking = db.Cartlist.getData(new CartlistMapper(), parameters, sql);
                    if (cartListChecking != null && cartListChecking.size() > 0) {
                        Cartlist cartList = cartListChecking.get(0);
                        int newQuantity = cartList.getCartQuantity() + 1;
                        cartList.setCartQuantity(newQuantity);
                        db.Cartlist.Update(new CartlistMapper(), cartList);
                        response.sendRedirect(previousUrl);
                    } else {
                        Cartlist cartlist = new Cartlist(new Cart(cartId), new Product(productId), cartQuantity);
                        db.Cartlist.Add(new CartlistMapper(), cartlist);
                        response.sendRedirect(previousUrl);
                    }

                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                }
            } else if (CheckPermission.permissionNoLogin(request)) {
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
            } else {
                //turn to error page , reason - premission denied
                response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
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
