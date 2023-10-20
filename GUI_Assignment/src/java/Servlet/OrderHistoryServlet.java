package Servlet;

/**
 * @author LOH XIN JIE
 */
import DataAccess.DBTable;
import DataAccess.Mapper.OrderlistMapper;
import DataAccess.Mapper.OrdersMapper;
import DataAccess.Mapper.ProductMapper;
import DataAccess.Mapper.RateReviewMapper;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.SQLException;
import Model.*;
import Model.PageModel.*;
import java.io.*;
import Utility.*;
import java.text.ParseException;

public class OrderHistoryServlet extends HttpServlet {

    private DBTable db;
    private final String STATUS_NORATE = "HAVENT RATE";
    private final String STATUS_RATE = "RATE";

    @Override
    public void init() throws ServletException {
        this.db = new DBTable();
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //check permission only member can come in
        if (CheckPermission.permissionUser(request)) {
            try {
                //create model to hold data
                ArrayList<OrderHistoryModel> ohmList = new ArrayList<>();

                //get list
                ArrayList<Orders> olist = filterList(request);

                String rateStatus = request.getParameter("rateStatus") == null ? "" : request.getParameter("rateStatus");
                if (olist != null && olist.size() > 0) {
                    for (Orders o : olist) {
                        int noMatch = 0;
                        OrderHistoryModel ohm = new OrderHistoryModel();

                        ohm.setOrders(o);

                        ArrayList<Orderlist> olList = db.Orderlist.getData(new OrderlistMapper(), o.getOrdersId());

                        for (Orderlist ol : olList) {
                            OrderHistoryModel.ProductOrders po = ohm.new ProductOrders();
                            po.setOrderlist(ol);

                            po.setProduct(db.Product.getData(new ProductMapper(), ol.getProduct().getProductId()).get(0));

                            //get rate status
                            String sqlQuery = "SELECT * "
                                    + "FROM RATEREVIEW "
                                    + "WHERE ORDERS_ID = ? "
                                    + "AND PRODUCT_ID = ? ";

                            ArrayList<Object> condition = new ArrayList<>();
                            condition.add(new Integer(ol.getOrder().getOrdersId()));
                            condition.add(new Integer(ol.getProduct().getProductId()));

                            ArrayList<RateReview> rnr = db.RateReview.getData(new RateReviewMapper(), condition, sqlQuery);

                            if (rnr != null && rnr.size() > 0) {
                                po.setRnrStatus(STATUS_RATE);
                            } else {
                                po.setRnrStatus(STATUS_NORATE);
                            }

                            //add into Order History Model
                            ohm.addPolist(po);
                            if (!rateStatus.equals(po.getRnrStatus()) && !rateStatus.isEmpty()) {
                                noMatch++;
                            }
                        }

                        //add into Order History Model List
                        if (noMatch < ohm.getPolist().size()) {
                            ohmList.add(ohm);
                        }
                    }
                }

                request.setAttribute("ohmList", ohmList);
                request.getRequestDispatcher("orderHistory/view/History.jsp").forward(request, response);

            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
            } catch (ParseException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Error Occurs When Change Format Of Date");
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * this method is use to prepare sql query and return a arraylist back when
     * no filter will just return back target user order
     *
     * @param request
     * @return
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     * @throws java.lang.NumberFormatException
     */
    protected ArrayList<Orders> filterList(HttpServletRequest request)
            throws SQLException, ParseException, NumberFormatException {
        String ordersID = request.getParameter("ordersID") == null ? "" : request.getParameter("ordersID");
        String productName = request.getParameter("productName") == null ? "" : request.getParameter("productName");
        String ordersDate = request.getParameter("ordersDate") == null ? "" : request.getParameter("ordersDate");
        //String rateStatus = request.getParameter("rateStatus") == null ? "" : request.getParameter("rateStatus");

        int memberID = ((Member) request.getSession().getAttribute("member")).getMemberId();

        String sqlQuery = "SELECT DISTINCT ORDERS.* "
                + "FROM ORDERS "
                + "INNER JOIN ORDERLIST ON ORDERS.ORDERS_ID = ORDERLIST.ORDERS_ID "
                + "INNER JOIN PRODUCT ON ORDERLIST.PRODUCT_ID = PRODUCT.PRODUCT_ID "
                + "WHERE ORDERS.MEMBER_ID = ? ";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(memberID));

        if (!ordersID.isEmpty()) {
            sqlQuery += "AND ORDERS.ORDERS_ID = ? ";
            condition.add(new Integer(Integer.parseInt(ordersID)));
        }

        if (!productName.isEmpty()) {
            sqlQuery += "AND PRODUCT.PRODUCT_NAME LIKE '%" + productName + "%' ";
        }

        if (!ordersDate.isEmpty()) {
            sqlQuery += "AND ORDERS.ORDERS_DATE = ? ";
            condition.add(Converter.convertHTMLFormatToUtilDate(ordersDate));
        }

        return db.Orders.getData(new OrdersMapper(), condition, sqlQuery);
    }
}
