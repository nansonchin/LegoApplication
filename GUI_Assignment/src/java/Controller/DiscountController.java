package Controller;

/**
 * @author LOH XIN JIE
 */
import Model.*;
import DataAccess.*;
import DataAccess.Mapper.*;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.http.*;
import Utility.Converter;

/*One product will only have one discount in time*/
public class DiscountController {

    //if have discount will return
    public static Discount getDiscount(DBTable db, int productId) throws SQLException {
        String sqlQuery = "SELECT * FROM DISCOUNT "
                + "WHERE PRODUCT_ID = ? AND "
                + "DISCOUNT_START_DATE <= ? AND "
                + "DISCOUNT_END_DATE >= ?";

        Date currdate = new Date();

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(productId));
        condition.add(currdate);
        condition.add(currdate);

        ArrayList<Discount> dlist = db.Discount.getData(new DiscountMapper(), condition, sqlQuery);

        return dlist.size() > 0 ? dlist.get(0) : null;
    }

    public static double getPrice(double productPrice, int discountPercentage) {
        return Math.round((productPrice * (1.0 - (discountPercentage * 1.0 / 100))) * 10) / 10.0;
    }

    /**
     * <h3>Main Checking : to validate the date user choose cannot touch the
     * date already exist</h3>
     * <p>
     * check follow the SQL statement
     * </p>
     *
     * @param db
     * @param productID
     * @param startDate
     * @param endDate
     * @param request
     * @return true when match, false when no match
     * @throws java.sql.SQLException
     */
    public static boolean dateValidationMatch(DBTable db, int productID, Date startDate, Date endDate, HttpServletRequest request) throws SQLException {
        String sqlQuery = "SELECT * "
                + "FROM DISCOUNT "
                + "WHERE PRODUCT_ID = ? AND "
                + "((DISCOUNT_START_DATE <= ? AND DISCOUNT_END_DATE >= ?) OR "
                + "(DISCOUNT_START_DATE >= ? AND DISCOUNT_END_DATE <= ?) OR "
                + "(DISCOUNT_START_DATE <= ? AND DISCOUNT_END_DATE >= ?))";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(productID));
        condition.add(startDate);
        condition.add(startDate);
        condition.add(startDate);
        condition.add(endDate);
        condition.add(endDate);
        condition.add(endDate);

        return dateValidationMatchErrorMessage(condition, sqlQuery, db, request);
    }

    /**
     * <h3>To Match the requirement</h3>
     * <ul>
     * <li>startDate >= currDate</li>
     * <li>endDate >= currDate</li>
     * <li>endDate > startDate</li>
     * </ul>
     *
     * @param startDate
     * @param endDate
     * @param errorMap
     * @return return true when have error, return false when no error
     */
    public static boolean dateValidateLogic(Date startDate, Date endDate, HashMap<String, String> errorMap) {
        Date currDate = new Date();
        currDate.setHours(0);
        currDate.setMinutes(0);
        currDate.setSeconds(0);

        //startDate >= currDate
        if (startDate.before(currDate)) {
            errorMap.put("startDate", "Discount Start Date Must After Today");
        }

        //endDate >= currDate
        if (endDate.before(currDate)) {
            errorMap.put("endDate", "Discount End Date Must After Today");
        }

        //endDate >= startDate
        if (endDate.before(startDate)) {
            errorMap.put("endDate", "Discount End Date Cannot Before Start Date");
        }

        return errorMap.get("endDate") != null || errorMap.get("startDate") != null;//have error
    }

    public static Discount getTargetDiscount(DBTable db, int productID, Date startDate, Date endDate) throws SQLException {
        String sqlQuery = "SELECT * "
                + "FROM DISCOUNT "
                + "WHERE PRODUCT_ID = ? "
                + "AND DISCOUNT_START_DATE = ? "
                + "AND DISCOUNT_END_DATE = ?";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(productID));
        condition.add(startDate);
        condition.add(endDate);

        return db.Discount.getData(new DiscountMapper(), condition, sqlQuery).get(0);
    }

    /**
     * <h3>Main Checking : to validate the date user choose cannot touch the
     * date already exist</h3>
     * <h2>This matching checking will without the target discount id</h2>
     * <p>
     * check follow the SQL statement
     * </p>
     *
     * @param db
     * @param productID
     * @param startDate
     * @param endDate
     * @param request
     * @param discountID
     * @return true when match, false when no match
     * @throws java.sql.SQLException
     */
    public static boolean dateValidationMatchWithOutSelf(DBTable db, int productID, Date startDate, Date endDate, HttpServletRequest request, int discountID) throws SQLException {
        String sqlQuery = "SELECT * "
                + "FROM DISCOUNT "
                + "WHERE DISCOUNT_ID != ? AND "
                + "PRODUCT_ID = ? AND "
                + "((DISCOUNT_START_DATE <= ? AND DISCOUNT_END_DATE >= ?) OR "
                + "(DISCOUNT_START_DATE >= ? AND DISCOUNT_END_DATE <= ?) OR "
                + "(DISCOUNT_START_DATE <= ? AND DISCOUNT_END_DATE >= ?))";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(new Integer(discountID));
        condition.add(new Integer(productID));
        condition.add(startDate);
        condition.add(startDate);
        condition.add(startDate);
        condition.add(endDate);
        condition.add(endDate);
        condition.add(endDate);

        return dateValidationMatchErrorMessage(condition, sqlQuery, db, request);
    }

    private static boolean dateValidationMatchErrorMessage(ArrayList<Object> condition, String sqlQuery, DBTable db, HttpServletRequest request) throws SQLException {
        ArrayList<Discount> dlist = db.Discount.getData(new DiscountMapper(), condition, sqlQuery);

        if (dlist != null && !dlist.isEmpty()) {
            //have touch to another discount time
            ArrayList<String> timeMatchList = new ArrayList<>();
            for (Discount d : dlist) {
                String time = "DISCOUNT ID : " + d.getDiscountId() + " : "
                        + "FROM " + Converter.convertDateToSimpleFormat(d.getDiscountStartDate())
                        + " TO " + Converter.convertDateToSimpleFormat(d.getDiscountStartDate());

                timeMatchList.add(time);
            }
            request.setAttribute("timeMatchError", timeMatchList);
            return true;//have match
        } else {
            return false;//no match
        }
    }
}
