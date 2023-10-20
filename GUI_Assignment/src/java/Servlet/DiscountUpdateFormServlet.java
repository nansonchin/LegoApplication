package Servlet;

/**
 * @author LOH XIN JIE
 */
import Controller.DiscountController;
import javax.servlet.http.*;
import javax.servlet.*;
import Model.*;
import DataAccess.*;
import DataAccess.Mapper.DiscountMapper;
import java.io.*;
import java.util.*;
import Utility.*;
import java.text.ParseException;
import java.sql.SQLException;

public class DiscountUpdateFormServlet extends HttpServlet {

    private DBTable db;

    @Override
    public void init() throws ServletException {
        db = new DBTable();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request) || CheckPermission.permissionAdmin(request)) {
            //clear request attribute discount
            request.removeAttribute("discount");
            //error map
            HashMap<String, String> errorMap = new HashMap<>();
            try {
                //get data
                String discountID = request.getParameter("discountID");
                String productID = request.getParameter("pdtDiscount");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String percentage = request.getParameter("percentage");
                Discount discount = new Discount();

                if (discountID == null || discountID.trim().isEmpty()) {
                    //turn error page
                    request.getSession().setAttribute("UnexceptableError", "Discount ID Missing Problem");
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Unable to Get Discount ID");
                    request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                } else {
                    //discount id
                    discount.setDiscountId(Integer.parseInt(discountID));

                    //check empty productID
                    if (productID == null || productID.trim().isEmpty()) {
                        errorMap.put("productIdError", "Product Discount Cannot Be Empty");
                    } else {
                        discount.setProduct(new Product(Integer.parseInt(productID)));
                    }

                    //check empty startDate
                    if (startDate == null || startDate.trim().isEmpty()) {
                        errorMap.put("startDate", "Discount Start Date Cannot Be Empty");
                    } else {
                        discount.setDiscountStartDate(Converter.convertHTMLFormatToUtilDate(startDate));
                    }

                    //check empty endDate
                    if (endDate == null || endDate.trim().isEmpty()) {
                        errorMap.put("endDate", "Discount End Date Cannot Be Empty");
                    } else {
                        discount.setDiscountEndDate(Converter.convertHTMLFormatToUtilDate(endDate));
                    }

                    //if not null then will be no error , make another check
                    if (discount.getDiscountStartDate() != null && discount.getDiscountEndDate() != null && discount.getProduct() != null) {
                        if (!DiscountController.dateValidateLogic(discount.getDiscountStartDate(), discount.getDiscountEndDate(), errorMap)) {
                            //if discount controller also no error then continue matching check
                            DiscountController.dateValidationMatchWithOutSelf(db, discount.getProduct().getProductId(), discount.getDiscountStartDate(), discount.getDiscountEndDate(), request, discount.getDiscountId());
                        }
                    }

                    //check empty percentage
                    if (percentage == null || percentage.trim().isEmpty()) {
                        errorMap.put("percentageError", "Discount Percentage Cannot Be Empty");
                    } else {
                        //check range
                        discount.setDiscountPercentage(Integer.parseInt(percentage));
                        if (discount.getDiscountPercentage() < 1 && discount.getDiscountPercentage() > 99) {
                            errorMap.put("percentageError", "Discount Percentage Out Of Range, Cannot Be More Than 99 Or Smaller Than 1");
                        }
                    }

                    //timeMatchError is set in dateValidationMatch method
                    if (errorMap.size() == 0 && ((ArrayList<String>) request.getAttribute("timeMatchError")) == null) {
                        //no error found
                        if (db.Discount.Update(new DiscountMapper(), discount)) {
                            //get discount id for filter use
                            request.getSession().setAttribute("DiscountSuccess", "Update Successfull");
                            response.sendRedirect("/GUI_Assignment/DiscountDisplayServlet?discountID=" + DiscountController.getTargetDiscount(db, discount.getProduct().getProductId(), discount.getDiscountStartDate(), discount.getDiscountEndDate()).getDiscountId());
                        } else {
                            //add unsuccessful
                            //turn error page
                            request.getSession().setAttribute("UnexceptableError", "Data cannot be added into Database");
                            request.getSession().setAttribute("UnexceptableErrorDesc", "Data Added Unsuccessful");
                            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                        }
                    } else {
                        //to hold the data inside request
                        request.setAttribute("errorList", errorMap);
                        //response.sendRedirect("/GUI_Assignment/DiscountCreateServlet");
                        request.getRequestDispatcher("/DiscountUpdateServlet").forward(request, response);
                    }
                }

            } catch (ParseException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Error Occurs When Change Format Of Date");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (Exception ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexceptable Exception");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            response.sendRedirect("/GUI_Assignment/login/staffLogin.jsp");
        } else {
            //turn to error page , reason - premission denied
            response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
        }
    }
}
