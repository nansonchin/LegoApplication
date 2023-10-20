package Servlet;

import DataAccess.DBTable;
import DataAccess.Mapper.MemberMapper;
import Model.Member;
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
 * Author: MaoKa
 */
public class login extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBTable data = new DBTable();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // check username and password
        String sql = "SELECT * FROM MEMBER WHERE MEMBER_NAME = ?";
        ArrayList<Object> list = new ArrayList<>();
        list.add(username);
        ArrayList<Member> members = null;
        try {
            members = data.Member.getData(new MemberMapper(), list, sql);

            if (members.size() == 1) {
                Member member = members.get(0);
                if (member.getMemberPass().equals(password)) {
                    session.setAttribute("member", member);
                    response.sendRedirect("/GUI_Assignment/HomeServlet");
                } else {
                    session.setAttribute("message", "Invalid password, please try again.");
                    request.getRequestDispatcher("login/login.jsp").forward(request, response);
                }
            } else {
                session.setAttribute("message", "Invalid username, please try again.");
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
