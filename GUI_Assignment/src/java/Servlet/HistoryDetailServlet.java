package Servlet;

/**
 * @author LOH XIN JIE
 */
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.SQLException;
import Model.*;
import Model.PageModel.*;
import DataAccess.*;
import DataAccess.Mapper.*;
import java.util.*;
import Utility.*;

public class HistoryDetailServlet extends HttpServlet {

    private DBTable db;
    private final String STATUS_NORATE = "HAVENT RATE";
    private final String STATUS_RATE = "RATE";

    @Override
    public void init() throws ServletException {
        this.db = new DBTable();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //check permission for user can access only
        if (CheckPermission.permissionUser(request)) {
            try {
                int orderID = request.getParameter("orderID") == null ? -1 : Integer.parseInt(request.getParameter("orderID"));
                if (orderID > 0) {
                    //create model to hold data
                    OrderHistoryModel ohm = new OrderHistoryModel();

                    //get order
                    ohm.setOrders(db.Orders.getData(new OrdersMapper(), orderID).get(0));

                    //use order to get address
                    ohm.setAddress(db.AddressBook.getData(new AddressBookMapper(), ohm.getOrders().getAddress().getAddressId()).get(0));

                    //get orderlist
                    ArrayList<Orderlist> olList = db.Orderlist.getData(new OrderlistMapper(), orderID);

                    //loop orderlist to get product data
                    for (Orderlist ol : olList) {
                        OrderHistoryModel.ProductOrders po = ohm.new ProductOrders();

                        po.setOrderlist(ol);

                        //get product
                        po.setProduct(db.Product.getData(new ProductMapper(), ol.getProduct().getProductId()).get(0));

                        //get rnrStatus
                        String sqlQuery = "SELECT * "
                                + "FROM RATEREVIEW "
                                + "WHERE ORDERS_ID = ? AND PRODUCT_ID = ?";

                        ArrayList<Object> condition = new ArrayList<>();
                        condition.add(new Integer(orderID));
                        condition.add(new Integer(ol.getProduct().getProductId()));

                        ArrayList<RateReview> rnr = db.RateReview.getData(new RateReviewMapper(), condition, sqlQuery);

                        if (rnr != null && rnr.size() > 0) {
                            po.setRnrStatus(STATUS_RATE);
                        } else {
                            po.setRnrStatus(STATUS_NORATE);
                        }

                        ohm.addPolist(po);
                    }

                    request.setAttribute("ohm", ohm);
                    request.getRequestDispatcher("orderHistory/view/HistoryDetail.jsp").forward(request, response);
                } else {
                    //order id missing
                    request.getSession().setAttribute("UnexceptableError", "Order ID is empty");
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Order ID Missing Problem");
                    request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Order ID Format Invalid");
                request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
            } catch (Exception ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexcepted Exception Occurs");
                request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            response.sendRedirect("/GUI_Assignment/login/login.jsp");
        } else {
            //turn to error page , reason - premission denied
            response.sendRedirect("/GUI_Assignment/Home/view/PermissionDenied.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
