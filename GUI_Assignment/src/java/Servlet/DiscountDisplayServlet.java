package Servlet;

/**
 * @author LOH XIN JIE
 */
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;
import DataAccess.*;
import DataAccess.Mapper.DiscountMapper;
import DataAccess.Mapper.ProductMapper;
import java.io.IOException;
import java.util.*;
import java.sql.SQLException;
import Model.PageModel.*;
import Utility.*;
import java.text.ParseException;

public class DiscountDisplayServlet extends HttpServlet {

    private DBTable db;
    private final String STATUS_VALID = "VALID";
    private final String STATUS_INVALID = "INVALID";
    private final String STATUS_COMING_SOON = "COMING SOON";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request) || CheckPermission.permissionAdmin(request)) {
            try {
                //get product list for dropdown
                ArrayList<Product> plist = getActiveProductList();

                //get discount list for display (need product name)
                ArrayList<Discount> dlist = filterList(request);
                String status = request.getParameter("status") == null ? "" : request.getParameter("status").trim();

                ArrayList<DiscountDisplayModel> ddmList = new ArrayList<>();
                Date date = new Date();
                Date currDate = new Date(date.getYear(), date.getMonth(), date.getDate());
                currDate.setHours(0);
                currDate.setMinutes(0);
                currDate.setSeconds(0);

                for (Discount d : dlist) {
                    DiscountDisplayModel ddm = new DiscountDisplayModel();
                    ddm.setDiscount(d);

                    //find status
                    if (d.getDiscountEndDate().before(currDate) && !d.getDiscountEndDate().equals(currDate)) {
                        //if end date smaller than now then will be inactive
                        ddm.setStatus(STATUS_INVALID);
                    } else if (currDate.before(d.getDiscountStartDate())) {
                        //if start date bigger than now set coming soon
                        ddm.setStatus(STATUS_COMING_SOON);
                    } else {
                        //start date >= now <= end date
                        ddm.setStatus(STATUS_VALID);
                    }

                    Product p = checkProductActive(d.getProduct().getProductId());
                    if (p.getProductActive() != '1') {
                        ddm.setStatus(STATUS_INVALID);
                    }

                    ddm.setProduct(p);

                    if (status.equals(ddm.getStatus()) || status.isEmpty()) {
                        ddmList.add(ddm);
                    }

                }

                request.setAttribute("plist", plist);
                request.setAttribute("ddmList", ddmList);
                request.getRequestDispatcher("Discount/view/Display.jsp").forward(request, response);

            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (ParseException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Error Occurs When Change Format Of Date");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
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

    protected ArrayList<Product> getActiveProductList() throws SQLException {
        String sqlQuery = "SELECT * FROM PRODUCT WHERE PRODUCT_ACTIVE = ?";
        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(1));

        return db.Product.getData(new ProductMapper(), condition, sqlQuery);
    }

    protected Product checkProductActive(int productID) throws SQLException {
        return db.Product.getData(new ProductMapper(), productID).get(0);
    }

    protected ArrayList<Discount> filterList(HttpServletRequest request) throws SQLException, ParseException {
        String discountID = request.getParameter("discountID") == null ? "" : request.getParameter("discountID").trim();
        String productID = request.getParameter("product") == null ? "" : request.getParameter("product").trim();

        String startDate = request.getParameter("startDate") == null ? "" : request.getParameter("startDate").trim();
        String endDate = request.getParameter("endDate") == null ? "" : request.getParameter("endDate").trim();
        String percentage = request.getParameter("percentage") == null ? "" : request.getParameter("percentage").trim();

        String sqlQuery = "SELECT * FROM DISCOUNT";
        ArrayList<Object> condition = new ArrayList<>();

        int check = 0;
        if (discountID.isEmpty() && productID.isEmpty() && startDate.isEmpty() && endDate.isEmpty() && percentage.isEmpty()) {
            return db.Discount.getData(new DiscountMapper());
        } else {
            sqlQuery += " WHERE";
        }

        if (!discountID.isEmpty()) {
            sqlQuery += " DISCOUNT_ID = ?";
            condition.add(Integer.parseInt(discountID));
            check++;
        }

        if (!productID.isEmpty()) {
            if (check > 0) {
                sqlQuery += " AND";
            }
            sqlQuery += " PRODUCT_ID = ?";
            condition.add(Integer.parseInt(productID));
            check++;
        }

        if (!startDate.isEmpty()) {
            if (check > 0) {
                sqlQuery += " AND";
            }
            sqlQuery += " DISCOUNT_START_DATE >= ?";
            condition.add(Converter.convertHTMLFormatToUtilDate(startDate));
            check++;
        }

        if (!endDate.isEmpty()) {
            if (check > 0) {
                sqlQuery += " AND";
            }
            sqlQuery += " DISCOUNT_END_DATE <= ?";
            condition.add(Converter.convertHTMLFormatToUtilDate(endDate));
            check++;
        }

        if (!percentage.isEmpty()) {
            if (check > 0) {
                sqlQuery += " AND";
            }
            sqlQuery += " DISCOUNT_PERCENTAGE = ?";
            condition.add(Integer.parseInt(percentage));
            check++;
        }

        return db.Discount.getData(new DiscountMapper(), condition, sqlQuery);
    }

    @Override
    public void init() throws ServletException {
        db = new DBTable();
    }
}
