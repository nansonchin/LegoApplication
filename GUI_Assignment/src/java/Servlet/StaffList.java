package Servlet;

import Controller.StaffController;
import Model.Staff;
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
public class StaffList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionAdmin(request)) {
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

                ArrayList<Staff> staffs = new StaffController().getStaff(search);
                if (staffs == null) {
                    request.getSession().setAttribute("UnexceptableError", "staffs is null");
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                    request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                } else if (staffs.isEmpty()) {
                    out.print("<td colspan=8>No Record.</td>");
                }
                for (Staff staff : staffs) {
                    out.println("<tr>");
                    out.println("<td>" + staff.getStaffId() + "</td>");
                    out.println(" <td>" + staff.getStaffName() + "</td>");
                    out.println("<td>" + staff.getStaffPhNo() + "</td>");
                    out.println("<td>" + staff.getStaffEmail() + "</td>");
                    out.println(" <td>" + staff.getDisplayFormatBirthdate() + "</td>");
                    out.println("<td>" + staff.getStaffIc() + "</td>");
                    out.println(" <td>" + staff.getStaffPass() + "</td>");
                    out.println("  <td><a href=\"../../StaffMaint?id=" + staff.getStaffId() + "&isNew=false\" style=\"font-size:20px;color:grey\" class=\"fa\"><i class=\"edit fa fa-pencil\"></i></a></td>");
                    out.println("</tr>");
                }
            } catch (SQLException ex) {
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
