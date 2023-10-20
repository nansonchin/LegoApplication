/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controller.MemController;
import Model.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utility.*;

/**
 *
 * @author Yeet
 */
public class MemMaint extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String search = request.getParameter("search") != null ? request.getParameter("search") : "";
        ArrayList<Member> members = new MemController().getMems(search);
        if (members == null) {
            request.getSession().setAttribute("UnexceptableError", "Members is null");
            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            return;
        } else if (members.isEmpty()) {
            out.println("<td colspan=3>No Record.</td>");
        }
        for (Member member : members) {
            out.println("<tr>");
            out.println("<td>" + member.getMemberId() + "</td>");
            out.println("<td>" + member.getMemberName() + "</td>");
            out.println("<td><a href=\"/GUI_Assignment/MemMaint?id=" + member.getMemberId() + "&delete=1\" onclick=\"return confirm('Are you sure?');\" style=\"font-size:20px;color:grey\" class=\"fa\"><i class=\"delete fa fa-trash-o\"></i></a></td>");
            out.println("</tr>");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (CheckPermission.permissionStaff(request)) {
            String id = request.getParameter("id");
            String delete = request.getParameter("delete");

            if (id == null && delete == null) {
                try {
                    processRequest(request, response);
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                    request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                }
            } else {
                String search = request.getParameter("search") != null ? request.getParameter("search") : "";
                try {
                    if (delete != null && id != null) {
                        try {
                            if (new MemController().dltMem(id) == false) {
                                response.sendRedirect("/GUI_Assignment/admin/view/mem_list.jsp?search=" + search + "&notDelete=true");
                                return;
                            }
                        } catch (SQLException ex) {
                            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    response.sendRedirect("/GUI_Assignment/admin/view/mem_list.jsp?search=" + search + "&notDelete=true");
                }
                response.sendRedirect("/GUI_Assignment/admin/view/mem_list.jsp?search=" + search);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

}
