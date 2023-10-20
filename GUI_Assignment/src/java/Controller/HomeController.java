package Controller;

/**
 * @author LOH XIN JIE
 */
import java.util.*;
import Model.*;
import DataAccess.*;
import DataAccess.Mapper.*;
import java.sql.SQLException;

public class HomeController {

    //get hot sales with join table
    public static ArrayList<Product> getHotSales(DBTable db) throws SQLException {
        String sqlQuery = "SELECT p.*, SUM(ol.orders_quantity) AS ttlQuantity "
                + "FROM orders o, product p, orderlist ol "
                + "WHERE ol.orders_id = o.orders_id AND ol.product_id = p.product_id AND p.product_active = ?"
                + "GROUP BY p.product_id, p.product_name, p.product_desc, p.product_price, p.product_active, p.image_id "
                + "ORDER BY ttlQuantity DESC";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Character('1'));
        return db.Product.getData(new ProductMapper(), condition, sqlQuery);
    }

    //get the target product rating list
    public static ArrayList<RateReview> getProductRatingList(DBTable db, int productId) throws SQLException {
        String sqlQuery = "SELECT * FROM RATEREVIEW WHERE PRODUCT_ID = ?";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(productId));

        return db.RateReview.getData(new RateReviewMapper(), condition, sqlQuery);
    }

    //do a calculation in rate add all rate and divide by the number ppl rate
    public static double getProductRate(ArrayList<RateReview> rlist) {
        int size = rlist.size();

        if (size > 0) {
            int ttl = 0;

            for (RateReview rr : rlist) {
                ttl += rr.getReviewRating();
            }

            return Math.round(ttl / (size * 1.0));
        } else {
            //no rate or review yet
            return -1;
        }
    }

    public static int getNumOfProductSold(DBTable db, int productId) throws SQLException {
        String sqlQuery = "SELECT * FROM ORDERLIST WHERE PRODUCT_ID = ?";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(productId));

        ArrayList<Orderlist> targetlist = db.Orderlist.getData(new OrderlistMapper(), condition, sqlQuery);

        //get ttl sold out
        int ttlSoldOut = 0;
        for (Orderlist ol : targetlist) {
            ttlSoldOut += ol.getOrdersQuantity();
        }

        return ttlSoldOut;
    }

    public static ArrayList<Discount> getDiscountList(DBTable db) throws SQLException {
        String sqlQuery = "SELECT DISCOUNT.* FROM DISCOUNT "
                + "INNER JOIN PRODUCT ON DISCOUNT.PRODUCT_ID = PRODUCT.PRODUCT_ID "
                + "WHERE PRODUCT.PRODUCT_ACTIVE = ? AND DISCOUNT.DISCOUNT_END_DATE >= ? "
                + "ORDER BY DISCOUNT.DISCOUNT_START_DATE";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Character('1'));
        Date date = new Date();
        condition.add(date);

        return db.Discount.getData(new DiscountMapper(), condition, sqlQuery);
    }

    public static ArrayList<Orderlist> getTargetUserOrderlist(DBTable db, int memberId) throws SQLException {
        String sqlQuery = "SELECT ORDERLIST.* "
                + "FROM MEMBER "
                + "INNER JOIN ORDERS ON MEMBER.MEMBER_ID = ORDERS.MEMBER_ID "
                + "INNER JOIN ORDERLIST ON ORDERS.ORDERS_ID = ORDERLIST.ORDERS_ID "
                + "INNER JOIN PRODUCT ON ORDERLIST.PRODUCT_ID = PRODUCT.PRODUCT_ID "
                + "WHERE MEMBER.MEMBER_ID = ?";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(memberId));

        return db.Orderlist.getData(new OrderlistMapper(), condition, sqlQuery);
    }

    public static boolean foundHaventRate(DBTable db, int ordersId, int productId) throws SQLException {
        String sqlQuery = "SELECT * FROM RATEREVIEW WHERE ORDERS_ID = ? AND PRODUCT_ID = ?";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(ordersId));
        condition.add(new Integer(productId));

        return db.RateReview.getData(new RateReviewMapper(), condition, sqlQuery).size() > 0;//biggest than 0 mean have rate
    }
}
