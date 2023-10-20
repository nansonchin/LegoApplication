/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DataAccess.DBTable;
import DataAccess.Mapper.StaffMapper;
import Model.Staff;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author erika
 */
public class staffLogin extends HttpServlet {

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
        try {
            request.getRequestDispatcher("login/staffLogin").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            DBTable data = new DBTable();

            String id = request.getParameter("id");
            String password = request.getParameter("password");
            HttpSession session = request.getSession();

            //Check username and password match with db
            String sql = "SELECT * FROM STAFF WHERE staff_id = ?";
            ArrayList<Object> list = new ArrayList<>();
            list.add(id);
            ArrayList<Staff> staffs = null;

            if (id.equals("admin") && password.equals("admin0")) {
                session.setAttribute("staffLogin", "admin");
                response.sendRedirect("admin/view/home.jsp");
            } else {
                try {
                    int staffId = Integer.parseInt(id);
                    staffs = data.Staff.getData(new StaffMapper(), list, sql);

                    if (staffs.size() == 1) {
                        Staff staff = staffs.get(0);
                        if (staff.getStaffPass().equals(password)) {
                            session.setAttribute("staffLogin", staff.getStaffName());
                            session.setAttribute("staffs", staff);
                            response.sendRedirect("admin/view/home.jsp");
                        } else {
                            session.setAttribute("message", "Invalid password, please try again.");
                            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
                        }
                    } else {
                        session.setAttribute("message", "Invalid Staff ID, please try again.");
                        request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    session.setAttribute("message", "Invalid Staff ID, please try again.");
                    request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
                }
            }
        } catch (SQLException ex) {
            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
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
