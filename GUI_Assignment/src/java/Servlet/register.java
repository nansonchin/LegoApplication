package Servlet;

import DataAccess.DBTable;
import DataAccess.Mapper.CartMapper;
import DataAccess.Mapper.MemberMapper;
import Model.Cart;
import Model.Member;
import java.io.IOException;
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
 * Author: MaoKa
 */
public class register extends HttpServlet {

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
            request.getRequestDispatcher("login/StaffRegister.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
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
        String rePassword = request.getParameter("rePassword");
        HttpSession session = request.getSession();

        // check username duplicate
        String sql = "SELECT * FROM MEMBER WHERE member_name = ?";
        ArrayList<Object> list = new ArrayList<>();
        list.add(username);
        ArrayList<Member> members = null;

        try {
            members = data.Member.getData(new MemberMapper(), list, sql);

            if (members.size() > 0) {
                session.setAttribute("message", "Username already exists, please enter a again.");
                request.getRequestDispatcher("login/register.jsp").forward(request, response);
            } else {
                // check password format and match whater pass and repass same or not
                if (password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
                    if (password.equals(rePassword)) {
                        Member member = new Member(username, password);

                        // insert member into database if true
                        boolean success = true;
                        try {
                            success = data.Member.Add(new MemberMapper(), member);
                        } catch (SQLException ex) {
                            Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (success) {
                            //set pass
                            member.setMemberPass("");

                            members = data.Member.getData(new MemberMapper(), list, sql);
                            //get member id
                            Member memberId = members.get(0);
                            //create a new cart for the member
                            data.Cart.Add(new CartMapper(), new Cart(memberId));
                            //set member id into member
                            member.setMemberId(memberId.getMemberId());
                            request.getSession().setAttribute("member", member);
                            request.setAttribute("message", "Register successful");
                            response.sendRedirect("/GUI_Assignment/HomeServlet");

                        } else {
                            request.setAttribute("message", "Register Failed");
                            request.getRequestDispatcher("login/register.jsp").forward(request, response);
                        }
                    } else {
                        session.setAttribute("message", "Passwords do not match, please try again");
                        request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    }
                } else {
                    session.setAttribute("message", "Password invalid, you must have at least one special character, one uppercase letter, one lowercase letter, and one number with a minimum of eight characters");
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                }
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
