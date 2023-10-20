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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Utility.*;

/**
 *
 * @author guoc7
 */
public class updateCartListQuantityServlet extends HttpServlet {

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
            out.println("<title>Servlet updateCartListQuantityServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateCartListQuantityServlet at " + request.getContextPath() + "</h1>");
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
            try {
                DBTable db = new DBTable();
                Cartlist cartQuantityUpdate = new Cartlist();
                HttpSession session = request.getSession();
                Member member = (Member) session.getAttribute("member");
                if (session == null || session.getAttribute("member") == null) {
                    response.sendRedirect("/GUI_Assignment/login/login.jsp"); // Redirect to login.jsp if session is null or memberRole is null
                    return; // Return to prevent further execution of the code
                }

                int memberId = member.getMemberId();

                int cartId = -1;
//            getmemberId from Session
                String sql = "SELECT * FROM CART Where MEMBER_ID=?";
                ArrayList<Object> list = new ArrayList();
                list.add(memberId);
                List<Cart> cart = db.Cart.getData(new CartMapper(), list, sql);
                for (int i = 0; i < cart.size(); i++) {
                    Cart cartSearch = cart.get(i);
                    if (cartSearch.getMember().getMemberId() == memberId) {
                        cartId = cartSearch.getCartId();
                    }
                }

                cartQuantityUpdate.setCart(new Cart(cartId));
                cartQuantityUpdate.setProduct(new Product(Integer.parseInt(request.getParameter("productId"))));
                cartQuantityUpdate.setCartQuantity(Integer.parseInt(request.getParameter("quantity")));

                boolean updateTrue = db.Cartlist.Update(new CartlistMapper(), cartQuantityUpdate);
                if (updateTrue) {
                    response.sendRedirect("/GUI_Assignment/cartListServlet");
                }
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
