package Servlet;

import java.util.regex.Pattern;
import Controller.StaffController;
import Model.Staff;
import java.io.IOException;
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
public class StaffMaint extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (CheckPermission.permissionAdmin(request)) {
            String id = request.getParameter("id");
            String delete = request.getParameter("delete");

            if (delete != null && id != null) {
                try {
                    if (new StaffController().dltStaff(Integer.parseInt(id))) {
                        response.sendRedirect("/GUI_Assignment/admin/view/staff_list.jsp?delete=1");
                        return;
                    } else {
                        request.getSession().setAttribute("UnexceptableError", "staff delete failed");
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        return;
                    }
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                    request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                }
            }

            boolean isNew = request.getParameter("isNew").equals("true");
            if (!isNew) {
                try {
                    ArrayList<Staff> staffs = new StaffController().getStaff(id);
                    if (!staffs.isEmpty()) {
                        Staff staff;
                        staff = staffs.get(0);
                        HttpSession session = request.getSession();
                        session.setAttribute("staff", staff);
                        response.sendRedirect("/GUI_Assignment/admin/view/staff_maint.jsp?id=" + staff.getStaffId() + "&isNew=false");
                    } else {
                        request.getSession().setAttribute("UnexceptableError", "staffs empty");
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                    }
                } catch (SQLException ex) {
                    request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                    request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                }
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (CheckPermission.permissionAdmin(request)) {
            String id = request.getParameter("id") != null ? request.getParameter("id") : "";

            int action = request.getParameter("action") == null ? 0 : Integer.parseInt(request.getParameter("action"));
            if (request.getParameter("submit") != null) {
                int submit = Integer.parseInt(request.getParameter("submit"));
                if (submit == 1) {
                    try {
                        String name = request.getParameter("name");
                        String phoneNum = request.getParameter("phoneNum");
                        String email = request.getParameter("email");
                        String birthday = request.getParameter("birthday");
                        String ic = request.getParameter("ic");
                        String password = request.getParameter("password");
                        action = Integer.parseInt(request.getParameter("action"));

                        if (validateData(name, phoneNum, email, birthday, ic, password) == false) {
                            request.getSession().setAttribute("UnexceptableError", "invalid data - please contact support");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "invalid data - please contact support");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                            return;
                        }

                        if (new StaffController().addStaff(name, password, ic, phoneNum, email, birthday)) {
                            if (action == 1) {
                                response.sendRedirect("/GUI_Assignment/admin/view/staff_list.jsp");
                                return;
                            }
                            if (action == 2) {
                                response.sendRedirect("/GUI_Assignment/admin/view/staff_maint.jsp?isNew=true&action=" + action + "");
                                return;
                            }
                            if (action == 3) {
                                Staff staff = new StaffController().getLatestStaff();
                                HttpSession session = request.getSession();
                                session.setAttribute("staff", staff);

                                response.sendRedirect("/GUI_Assignment/admin/view/staff_maint.jsp?isNew=false&action=" + action + "&isSaved=true&id=" + staff.getStaffId() + "");
                            }
                        } else {
                            request.getSession().setAttribute("UnexceptableError", "staff insert failed");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                    }

                } else if (submit == 0) {

                    try {
                        String name = request.getParameter("name");
                        String phoneNum = request.getParameter("phoneNum");
                        String email = request.getParameter("email");
                        String birthday = request.getParameter("birthday");
                        String ic = request.getParameter("ic");
                        String password = request.getParameter("password");
                        action = Integer.parseInt(request.getParameter("action"));

                        if (validateData(name, phoneNum, email, birthday, ic, password) == false) {
                            request.getSession().setAttribute("UnexceptableError", "invalid data - please contact support");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "invalid data - please contact support");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                            return;
                        }

                        if (new StaffController().updateStaff(id, name, password, ic, phoneNum, email, birthday)) {
                            if (action == 1) {
                                response.sendRedirect("/GUI_Assignment/admin/view/staff_list.jsp");
                                return;
                            }
                            if (action == 2) {
                                response.sendRedirect("/GUI_Assignment/admin/view/staff_maint.jsp?isNew=true&action=" + action + "");
                                return;
                            }
                            if (action == 3) {
                                ArrayList<Staff> staffs = new StaffController().getStaff(id);
                                if (!staffs.isEmpty()) {
                                    Staff staff;
                                    staff = staffs.get(0);
                                    HttpSession session = request.getSession();
                                    session.setAttribute("staff", staff);
                                }
                                response.sendRedirect("/GUI_Assignment/admin/view/staff_maint.jsp?isNew=false&action=" + action + "&isSaved=true&id=" + id + "");
                            }
                        } else {
                            request.getSession().setAttribute("UnexceptableError", "staff update failed");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                    }

                }

            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    private boolean validateData(String name, String phoneNum, String email, String birthday, String ic, String password) {

        if (name.isEmpty() || phoneNum.isEmpty() || email.isEmpty() || birthday.isEmpty() || ic.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (!Pattern.matches("^[0-9]{10}$", phoneNum)) {
            return false;
        }

        if (!Pattern.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", email)) {
            return false;
        }

        if (!Pattern.matches("\\d{6}[01][0-4]\\d{4}$", ic)) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                hasLetter = true;
            } else if (c >= '0' && c <= '9') {
                hasNumber = true;
            }
            if (hasLetter && hasNumber) {
                break;
            }
        }
        if (!hasLetter || !hasNumber) {
            return false;
        }

        try {
            java.sql.Date.valueOf(birthday);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

}
