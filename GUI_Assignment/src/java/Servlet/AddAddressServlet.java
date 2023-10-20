/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controller.PaymentController;
import static Servlet.PaymentServlet.retrieveMemberAddressesAndBooks;
import static Servlet.PaymentServlet.returnPaymentDetails;
import DataAccess.DBTable;
import DataAccess.Mapper.AddressBookMapper;
import Utility.*;
import Model.AddressBook;
import Model.Member;
import Model.MemberAddress;
import Model.PageModel.PaymentModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "AddAddressServlet", urlPatterns = {"/AddAddressServlet"})
public class AddAddressServlet extends HttpServlet {

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
            out.println("<title>Servlet addAddress</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addAddress at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);

        if (CheckPermission.permissionUser(request)) {
            String newAddressName = request.getParameter("newAddressName");
            String newAddressPhone = request.getParameter("newAddressPhone");
            String newAddressNo = request.getParameter("newAddressNo");
            String newAddressStreet = request.getParameter("newAddressStreet");
            String newAddressCity = request.getParameter("newAddressCity");
            String newAddressState = request.getParameter("newAddressState");
            String newAddressPostcode = request.getParameter("newAddressPostcode");

            String errorMessage = null;

            // Validation checks
            if (newAddressName == null) {
                errorMessage = "Please enter a valid address name.";
            } else if (!newAddressName.matches("^[a-zA-Z ]+$")) {
                errorMessage = "Address name can only contain letters and spaces.";
            } else if (newAddressPhone == null) {
                errorMessage = "Please enter a valid phone number.";
            } else if (!newAddressPhone.matches("\\d{10}")) {
                errorMessage = "Phone number must be a 10-digit number.";
            } else if (newAddressNo == null) {
                errorMessage = "Please enter a valid address number.";
            } else if (newAddressStreet == null) {
                errorMessage = "Please enter a valid address street.";
            } else if (newAddressCity == null) {
                errorMessage = "Please enter a valid address city.";
            } else if (newAddressState == null) {
                errorMessage = "Please enter a valid address state.";
            } else if (newAddressPostcode == null) {
                errorMessage = "Please enter a valid postcode.";
            } else if (!newAddressPostcode.matches("\\d{5}")) {
                errorMessage = "Postcode must be a 5-digit number.";
            }

            try {
                HttpSession session = request.getSession();
//            int memberid = 2000;
                Member member = (Member) session.getAttribute("member");
                int memberId = member.getMemberId();
                if (memberId != 0) {
                    // Search for member's addresses
                    ArrayList<Object> lists = PaymentController.getAddressLists(memberId);
                    ArrayList<MemberAddress> mAddress = (ArrayList<MemberAddress>) lists.get(0);
                    ArrayList<AddressBook> addressBook = (ArrayList<AddressBook>) lists.get(1);

                    // Combine member addresses and address book entries
                    ArrayList<PaymentModel> addressItems = PaymentController.getAddressItems(mAddress, addressBook);

                    boolean addressExists = false;

                    for (PaymentModel addressItem : addressItems) {
                        AddressBook address = addressItem.getAddressBook();
                        if (newAddressName.equalsIgnoreCase(address.getAddressName())
                                && newAddressPhone.equalsIgnoreCase(address.getAddressPhone())
                                && newAddressNo.equalsIgnoreCase(address.getAddressNo())
                                && newAddressStreet.equalsIgnoreCase(address.getAddressStreet())
                                && newAddressCity.equalsIgnoreCase(address.getAddressCity())
                                && newAddressState.equalsIgnoreCase(address.getAddressState())
                                && newAddressPostcode.equalsIgnoreCase(address.getAddressPostcode())) {
                            addressExists = true;
                            break;
                        }
                    }

                    if (session == null || session.getAttribute("member") == null) {
                        response.sendRedirect("/GUI_Assignment/PaymentServlet");
                        return;
                    }

                    if (addressExists) {
                        errorMessage = "This address already exists";
                    }

                    // Set attribute for JSP
                    session.setAttribute("addressItems", addressItems);
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

            // If there is an error, set an attribute and redirect to p.jsp
            if (errorMessage != null) {
                HttpSession session = request.getSession();
                Member member = (Member) session.getAttribute("member");
                int memberId = member.getMemberId();

                try {
                    retrieveMemberAddressesAndBooks(memberId, request);
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                }

                try {
                    returnPaymentDetails(request, session, memberId);
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                }

                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("Payment/Payment.jsp").forward(request, response);
            } else {
//            int memberid = 2000;
                HttpSession session = request.getSession();
                Member member = (Member) session.getAttribute("member");
                int memberId = member.getMemberId();
                try {
                    retrieveMemberAddressesAndBooks(memberId, request);
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                    response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
                }

                PaymentController p = new PaymentController();
                try {
                    p.addAddress(newAddressName, newAddressPhone, newAddressNo, newAddressStreet, newAddressCity, newAddressState, newAddressPostcode);
                    ArrayList<Object> condition = new ArrayList<>();
                    condition.add(new String(newAddressName));
                    condition.add(new String(newAddressPhone));
                    condition.add(new String(newAddressNo));
                    condition.add(new String(newAddressStreet));
                    condition.add(new String(newAddressCity));
                    condition.add(new String(newAddressState));
                    condition.add(new String(newAddressPostcode));

                    ArrayList<AddressBook> a = new DBTable().AddressBook.getData(new AddressBookMapper(), condition,
                            "SELECT * FROM ADDRESSBOOK WHERE address_name = ? AND "
                            + "address_phone = ? AND address_no = ? AND address_street = ? AND address_city = ? AND"
                            + " address_state = ? AND address_postcode = ?");

                    if (a != null && a.size() > 0) {
                        p.addMemberAddress(a.get(0).getAddressId(), memberId);
                        response.sendRedirect("/GUI_Assignment/PaymentServlet");
                    }

                } catch (Exception ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Unexcepted Exception");
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
