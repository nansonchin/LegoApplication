/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controller.ImageTableController;
import Controller.prodController;
import Model.ImageTable;
import Model.Product;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import Utility.*;

/**
 *
 * @author Yeet
 */
@MultipartConfig
public class ProdMaint extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (CheckPermission.permissionStaff(request)) {
            boolean isNew = request.getParameter("isNew").equals("true");
            if (!isNew) {
                try {
                    Product product;
                    String id = request.getParameter("id");
                    product = new prodController().getProd(id);
                    if (product != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("product", product);
                        response.sendRedirect("/GUI_Assignment/admin/view/prod_maint.jsp?id=" + product.getProductId() + "&isNew=false");
                    } else {
                        request.getSession().setAttribute("UnexceptableError", "product is null");
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

        if (CheckPermission.permissionStaff(request)) {
            Product product = null;

            int action = request.getParameter("action") == null ? 0 : Integer.parseInt(request.getParameter("action"));

            if (request.getParameter("submit") != null) {

                int submit = Integer.parseInt(request.getParameter("submit"));
                if (submit == 1) {
                    int imageID;
                    try {
                        Part image = request.getPart("image");
                        imageID = new ImageTableController().uploadImage(image);
                    } catch (SQLException | IOException | ServletException ex) {
                        request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        return;
                    }

                    if (imageID == -1) {
                        request.getSession().setAttribute("UnexceptableError", "image not found");
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        return;
                    }

                    try {
                        String name = request.getParameter("name");
                        String desc = request.getParameter("description");
                        double price = Double.parseDouble(request.getParameter("price"));
                        char active = request.getParameter("active").charAt(0);
                        
                        
                    if(name.equals("")){
                        request.getSession().setAttribute("UnexceptableError", "Invalid data : name must not be empty");
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Invalid data : name must not be empty");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        return;
                    }

                        if (new prodController().addProd(name, desc, price, active, new ImageTable(imageID))) {
                            if (action == 1) {
                                response.sendRedirect("/GUI_Assignment/admin/view/prod_list.jsp");
                                return;
                            } else if (action == 2) {
                                response.sendRedirect("/GUI_Assignment/admin/view/prod_maint.jsp?isNew=true&action=" + action + "");
                                return;
                            } else if (action == 3) {
                                String id = request.getParameter("id");
                                product = new prodController().getLatestProd();
                                if (product != null) {
                                    HttpSession session = request.getSession();
                                    session.setAttribute("product", product);
                                }
                                response.sendRedirect("/GUI_Assignment/admin/view/prod_maint.jsp?isNew=false&action=" + action + "&isSaved=true&id=" + product.getProductId() + "");
                            } else {
                                request.getSession().setAttribute("UnexceptableError", "action not found");
                                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                            }
                        } else {
                            request.getSession().setAttribute("UnexceptableError", "product insert failed");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                    }
                } else if (submit == 0) {

                    try{
                    int imageID = Integer.parseInt(request.getParameter("imgID"));
                    boolean imgEdited = request.getParameter("imgEdited") != null ? request.getParameter("imgEdited").equals("true") : false;
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    String desc = request.getParameter("description");
                    double price = Double.parseDouble(request.getParameter("price"));
                    char active = request.getParameter("active") != null ? request.getParameter("active").charAt(0) : '0';
                    
                    if(id.equals("") || name.equals("")){
                        request.getSession().setAttribute("UnexceptableError", "Invalid data : id or name must not be empty");
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Invalid data : id or name must not be empty");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        return;
                    }

                    if (imgEdited) {
                        try {
                            Part image = request.getPart("image");
                            boolean imgUpdated = new ImageTableController().updateImage(image, imageID);
                            if (imgUpdated == false) {
                                request.getSession().setAttribute("UnexceptableError", "image update failed");
                                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                                return;
                            }
                        } catch (SQLException | IOException | ServletException ex) {
                            request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                            return;
                        }
                    }

                    try {
                        if (new prodController().updateProd(id, name, desc, price, active, new ImageTable(imageID))) {
                            if (action == 1) {
                                response.sendRedirect("/GUI_Assignment/admin/view/prod_list.jsp");
                                return;
                            }
                            if (action == 2) {
                                response.sendRedirect("/GUI_Assignment/admin/view/prod_maint.jsp?isNew=true&action=" + action + "");
                                return;
                            }
                            if (action == 3) {
                                product = new prodController().getProd(id);
                                if (product != null) {
                                    HttpSession session = request.getSession();
                                    session.setAttribute("product", product);
                                }
                                response.sendRedirect("/GUI_Assignment/admin/view/prod_maint.jsp?isNew=false&&action=" + action + "&isSaved=true&id=" + id + "");
                            }
                        } else {
                            request.getSession().setAttribute("UnexceptableError", "product update failed");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        }
                    } catch (SQLException ex) {
                        request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
                        request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                    }
                    }catch(Exception ex){
                        request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                        request.getSession().setAttribute("UnexceptableErrorDesc", "Invalid data");
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
}
