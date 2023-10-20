package Servlet;

/**
 * @author LOH XIN JIE
 */
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import DataAccess.*;
import Model.*;
import Model.PageModel.*;
import Controller.*;
import DataAccess.Mapper.OrdersMapper;
import DataAccess.Mapper.ProductMapper;
import Utility.CheckPermission;
import java.sql.*;
import java.util.*;

public class HomeServlet extends HttpServlet {

    private DBTable db;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!CheckPermission.permissionStaff(request)) {
            HomeModel hm = new HomeModel();
            try {
                //region HOTSALES
                //get hot sales data
                ArrayList<Product> hotProduct = HomeController.getHotSales(db);

                //will only display the top 10 hot product
                //because already arrange in db so directly loop only
                int size = hotProduct.size() > 10 ? 10 : hotProduct.size();

                for (int i = 0; i < size; i++) {
                    HomeModel.HotSales hs = hm.new HotSales();

                    //cal each product rate and number sold
                    hs.setProduct(hotProduct.get(i));

                    //rating = -1 = no rating yet
                    hs.setRating(HomeController.getProductRate(HomeController.getProductRatingList(db, hotProduct.get(i).getProductId())));
                    hs.setProductSold(HomeController.getNumOfProductSold(db, hotProduct.get(i).getProductId()));

                    //get discount (optional)
                    Discount discount = DiscountController.getDiscount(db, hotProduct.get(i).getProductId());
                    if (discount != null) {
                        hs.setDiscount(discount);
                    }

                    hm.addHotSales(hs);
                }
                //endregion HOTSALES

                //region DISCOUNT
                ArrayList<Discount> dlist = HomeController.getDiscountList(db);

                if (dlist != null && dlist.size() > 0) {
                    for (Discount d : dlist) {
                        HomeModel.DiscountProduct dp = hm.new DiscountProduct();
                        //because PRODUCT_ACTIVE already check in getDiscountList method so can directly take
                        dp.setProduct(db.Product.getData(new ProductMapper(), d.getProduct().getProductId()).get(0));
                        dp.setRating(HomeController.getProductRate(HomeController.getProductRatingList(db, d.getProduct().getProductId())));
                        dp.setDiscount(d);

                        hm.addDiscountList(dp);
                    }
                }
                //endregion DISCOUNT

                //region GET PRODUCT HAVENT RATE
                //get orderlist of target user
                if (((Member) request.getSession().getAttribute("member")) != null && ((Member) request.getSession().getAttribute("member")).getMemberName() != null) {
                    ArrayList<Orderlist> olist = HomeController.getTargetUserOrderlist(db, ((Member) request.getSession().getAttribute("member")).getMemberId());

                    if (olist != null && olist.size() > 0) {
                        //loop the olist to get which havent be rate
                        for (Orderlist ol : olist) {
                            if (!HomeController.foundHaventRate(db, ol.getOrder().getOrdersId(), ol.getProduct().getProductId())) {
                                //false doesnt have data inside
                                HomeModel.ProductNoRate pnr = hm.new ProductNoRate();

                                //get orders date
                                pnr.setOrdersDate(db.Orders.getData(new OrdersMapper(), ol.getOrder().getOrdersId()).get(0).getOrdersDate());
                                pnr.setQuantityOrders(ol.getOrdersQuantity());
                                pnr.setPrice(ol.getOrdersSubprice());
                                pnr.setProduct(db.Product.getData(new ProductMapper(), ol.getProduct().getProductId()).get(0));
                                pnr.setOrdersId(ol.getOrder().getOrdersId());

                                hm.addProductHaventRate(pnr);
                            }
                        }
                    }
                }
                //endregion GET PRODUCT HAVENT RATE

                request.setAttribute("homeModel", hm);
                request.getRequestDispatcher("index.jsp").forward(request, response);

            } catch (SQLException ex) {
                //turn error page
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("Home/view/ErrorPage.jsp").forward(request, response);
            }
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

    @Override
    public void init() throws ServletException {
        db = new DBTable();
    }
}
