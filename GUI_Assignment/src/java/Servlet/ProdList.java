/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controller.prodController;
import Model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Utility.*;

/**
 *
 * @author Yeet
 */
public class ProdList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String search = request.getParameter("search") == null ? null : request.getParameter("search");

            HttpSession session = request.getSession();

            if (search == null) {
                search = session.getAttribute("search") != null ? (String) session.getAttribute("search") : "";
            } else {
                session.setAttribute("search", search);
            }

            int status = request.getParameter("status") == null ? 1 : Integer.parseInt(request.getParameter("status"));

            if (request.getParameter("status") == null) {
                Integer history = (Integer) session.getAttribute("status");
                if (history != null) {
                    if (status != history) {
                        status = history;
                    }
                }
            } else {
                session.setAttribute("status", status);
            }

            ArrayList<Product> products = new prodController().getProds(search, status);
            if (products == null) {
                request.getSession().setAttribute("UnexceptableError", "products is null");
                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } else if (products.isEmpty()) {
                out.print("<td colspan=6>No Record.</td>");
            }
            for (Product product : products) {
                out.print("<tr>");
                out.print("<td>" + product.getProductId() + "</td>");
                out.print("<td>" + product.getProductName() + "</td>");
                out.print("<td>" + product.getProductDesc() + "</td>");
                out.print("<td>" + product.getProductPrice() + "</td>");
                out.print("<td><input type=\"checkbox\" " + (product.getProductActive() == '1' ? "checked" : "") + " disabled></td>");
                out.print("<td><a href=\"../../ProdMaint?id=" + product.getProductId() + "&isNew=false\" style=\"font-size:20px;color:grey\" class=\"fa\"><i class=\"edit fa fa-pencil\"></i></a></td>");
                out.print("</tr>");
            }
        } catch (SQLException ex) {
            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request)) {
            processRequest(request, response);
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }
}
