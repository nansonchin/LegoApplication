package Servlet;

import Controller.DiscountController;
import Controller.PaymentController;
import DataAccess.DBTable;
import DataAccess.Mapper.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Utility.*;

@WebServlet(name = "PaymentMethodServlet", urlPatterns = {"/PaymentMethodServlet"})
public class PaymentMethodServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payment Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Payment Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionUser(request)) {
            HttpSession session = request.getSession();

            try {

                Member member = (Member) session.getAttribute("member");
                if (session == null || session.getAttribute("member") == null) {
                    response.sendRedirect("HomeServlet");
                    return;
                }
                int memberId = member.getMemberId();
                if (memberId != 0) {
                    //return cartlist and productlist data(if they same - memberid)
                    Map<String, List<?>> cartAndProductLists = PaymentController.getCartAndProductLists(memberId);
                    List<Cartlist> cartList = (List<Cartlist>) cartAndProductLists.get("cartList");
                    List<Product> productList = (List<Product>) cartAndProductLists.get("productList");
                    request.setAttribute("clist", cartList);
                    request.setAttribute("plist", productList);

                    int totalProducts = 0;
                    for (Cartlist cartItem : cartList) {
                        totalProducts += cartItem.getCartQuantity();
                    }

                    request.setAttribute("totalProducts", totalProducts);

                    String shippingMethod = (String) session.getAttribute("shippingMethod");

                    //return memberAddress & addressBook data
                    ArrayList<Object> lists = PaymentController.getAddressLists(memberId);
                    ArrayList<MemberAddress> mAddress = (ArrayList<MemberAddress>) lists.get(0);
                    ArrayList<AddressBook> addressBook = (ArrayList<AddressBook>) lists.get(1);

                    request.setAttribute("memberAddress", mAddress);
                    request.setAttribute("addressBook", addressBook);

                    //calculate total something
                    ArrayList<Cartlist> cart = (ArrayList<Cartlist>) request.getAttribute("clist");
                    ArrayList<Product> product = (ArrayList<Product>) request.getAttribute("plist");

                    HashMap<Integer, Double> dlist = new HashMap<>();
                    for (Product p : product) {
                        DBTable db = new DBTable();
                        double originalPrice = p.getProductPrice();

                        Discount discount = DiscountController.getDiscount(db, p.getProductId()); // get the discount for the product

                        if (discount != null) {
                            double discountedPrice = DiscountController.getPrice(originalPrice, discount.getDiscountPercentage());
                            dlist.put(p.getProductId(), discountedPrice);

                        }
                    }
                    session.setAttribute("productPrice", dlist);

                    for (Cartlist cartItem : cartList) {
                        if (cartItem != null) {
                            totalProducts += cartItem.getCartQuantity();
                            ArrayList<Object> pcondition = new ArrayList<>();
                            pcondition.add(cartItem.getProduct().getProductId());

                            DBTable db = new DBTable();
                            // Check if product has discount
                            List<Discount> discountList = db.Discount.getData(new DiscountMapper(), pcondition, "SELECT * FROM DISCOUNT WHERE product_id = ?");

                            if (discountList != null && discountList.size() > 0) {
                                request.setAttribute("dlist", discountList);
                            }
                        }
                    }

                    DBTable db = new DBTable();
                    // calculate grand total
                    double grandT = PaymentController.calculateGrandTotal(cart, product, db);

                    // calculate tax
                    double tax = PaymentController.calculateTax(grandT);

                    // calculate shipping charge
                    double shippingCharge = PaymentController.calculateShippingCharge(shippingMethod);

                    // calculate delivery fee
                    double deliveryFee = PaymentController.calculateDeliveryFee(grandT);

                    // calculate final total
                    double finalTotal = PaymentController.calculateFinalTotal(grandT, tax, shippingCharge, deliveryFee);

                    if (grandT != 0.0 && tax != 0.0 && finalTotal != 0.0) {
                        double subTotal = grandT + tax + deliveryFee;
                        // set attributes for displaying in JSP
                        session.setAttribute("grandTotal", subTotal);
                        session.setAttribute("tax", tax);
                        session.setAttribute("shippingCharge", shippingCharge);
                        session.setAttribute("deliveryFee", deliveryFee);
                        session.setAttribute("finalTotal", finalTotal);
                    }
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

            String paymentType = (String) session.getAttribute("paymentMethod");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/Payment/" + paymentType + ".jsp");
            dispatcher.forward(request, response);
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionUser(request)) {
            response.setContentType("text/html");
            HttpSession session = request.getSession();

            // Determine which page the form was submitted from
            String paymentType = (String) session.getAttribute("paymentMethod");

            // Validate the form input
            String errorMessage = null;

            if (paymentType.equals("creditCard")) {
                String cardNumber = request.getParameter("cardNumber");
                String expirationDate = request.getParameter("expirationDate");
                String cvv = request.getParameter("cvv");
                String typeCard = request.getParameter("typeCard");
                String cardName = request.getParameter("cardName");

                if (cardNumber == null) {
                    errorMessage = "Please enter a valid card number.";
                } else if (!cardNumber.matches("\\d{16}")) {
                    errorMessage = "Card number should be a 16-digit number.";
                } else if (expirationDate == null) {
                    errorMessage = "Please enter a valid expiration date.";
                } else if (typeCard == null) {
                    errorMessage = "Please enter a typeCard.";
                } else if (cvv == null) {
                    errorMessage = "Please enter a valid CVV.";
                }

                // Store input data in session
                session.setAttribute("cardNumber", cardNumber);
                session.setAttribute("expirationDate", expirationDate);
                session.setAttribute("cvv", cvv);
                session.setAttribute("typeCard", typeCard);
                session.setAttribute("cardName", cardName);

            } else if (paymentType.equals("cash")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String email = request.getParameter("email");

                if (firstName == null) {
                    errorMessage = "Please enter a valid first name.";
                } else if (lastName == null) {
                    errorMessage = "Please enter a valid last name.";
                } else if (email == null) {
                    errorMessage = "Please enter a valid email.";
                } else if (!email.contains("@")) {
                    errorMessage = "Please enter a valid email address that contains '@'.";
                }

                // Store input data in session
                session.setAttribute("cashfirstName", firstName);
                session.setAttribute("cashlastName", lastName);
                session.setAttribute("cashemail", email);

            } else {
                errorMessage = "Invalid page.";
            }

            // If there is an error, set an attribute and redirect back to the page
            if (errorMessage != null) {

                try {
//                int memberid = 2000;
                    Member member = (Member) session.getAttribute("member");
                    if (session == null || session.getAttribute("member") == null) {
                        response.sendRedirect("/GUI_Assignment/HomeServlet");
                        return;
                    }

                    int memberId = member.getMemberId();

                    if (memberId != 0) {
                        //return cartlist and productlist data(if they same - memberid)
                        Map<String, List<?>> cartAndProductLists = PaymentController.getCartAndProductLists(memberId);
                        List<Cartlist> cartList = (List<Cartlist>) cartAndProductLists.get("cartList");
                        List<Product> productList = (List<Product>) cartAndProductLists.get("productList");
                        request.setAttribute("clist", cartList);
                        request.setAttribute("plist", productList);

                        int totalProducts = 0;
                        for (Cartlist cartItem : cartList) {
                            if (cartItem != null) {
                                totalProducts += cartItem.getCartQuantity();
                            }
                        }

                        request.setAttribute("totalProducts", totalProducts);

                        String shippingMethod = (String) session.getAttribute("shippingMethod");

                        //return memberAddress & addressBook data
                        ArrayList<Object> lists = PaymentController.getAddressLists(memberId);
                        ArrayList<MemberAddress> mAddress = (ArrayList<MemberAddress>) lists.get(0);
                        ArrayList<AddressBook> addressBook = (ArrayList<AddressBook>) lists.get(1);

                        request.setAttribute("memberAddress", mAddress);
                        request.setAttribute("addressBook", addressBook);

                        //calculate total something
                        ArrayList<Cartlist> cart = (ArrayList<Cartlist>) request.getAttribute("clist");
                        ArrayList<Product> product = (ArrayList<Product>) request.getAttribute("plist");

                        for (Cartlist cartItem : cartList) {
                            if (cartItem != null) {
                                totalProducts += cartItem.getCartQuantity();
                                ArrayList<Object> pcondition = new ArrayList<>();
                                pcondition.add(cartItem.getProduct().getProductId());

                                DBTable db = new DBTable();
                                // Check if product has discount
                                List<Discount> discountList = db.Discount.getData(new DiscountMapper(), pcondition, "SELECT * FROM DISCOUNT WHERE product_id = ?");

                                if (discountList.size() > 0) {
                                    request.setAttribute("dlist", discountList);
                                }
                            }
                        }

                        DBTable db = new DBTable();
                        // calculate grand total
                        double grandT = PaymentController.calculateGrandTotal(cart, product, db);

                        // calculate tax
                        double tax = PaymentController.calculateTax(grandT);

                        // calculate shipping charge
                        double shippingCharge = PaymentController.calculateShippingCharge(shippingMethod);

                        // calculate delivery fee
                        double deliveryFee = PaymentController.calculateDeliveryFee(grandT);

                        double subTotal = grandT + tax + deliveryFee;

                        // calculate final total
                        double finalTotal = PaymentController.calculateFinalTotal(grandT, tax, shippingCharge, deliveryFee);

                        if (grandT != 0.0 && tax != 0.0 && finalTotal != 0.0) {
                            session.setAttribute("grandTotal", subTotal);
                            session.setAttribute("tax", tax);
                            session.setAttribute("shippingCharge", shippingCharge);
                            session.setAttribute("deliveryFee", deliveryFee);
                            session.setAttribute("finalTotal", finalTotal);
                        }
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

                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/Payment/" + paymentType + ".jsp").forward(request, response);
            } else {
                // No errors, redirect to appropriate page
                if (paymentType.equals("creditCard")) {
                    response.sendRedirect("CheckOutReviewServlet");
                } else {
                    response.sendRedirect("CheckOutReviewServlet");
                }
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
