package Utility;

/**
 * @author LOH XIN JIE
 */
import javax.servlet.http.*;
import Model.*;

public class CheckPermission {

    /**
     * <h3>The page Member can access</h3>
     * <ul>
     * <li>Home</li>
     * <li>Search on Header</li>
     * <li>Order History</li>
     * <li>Menu</li>
     * <li>Product Content</li>
     * <li>Add to cart</li>
     * <li>Register Member</li>
     * <li>Payment</li>
     * <li>Thank You</li>
     * <li>Rate and Review</li>
     * <li>Check Out</li>
     * </ul>
     *
     * @param request
     * @return true when permission match
     */
    public static boolean permissionUser(HttpServletRequest request) {
        return ((Member) request.getSession().getAttribute("member")) != null && ((Member) request.getSession().getAttribute("member")).getMemberName() != null;
    }

    /**
     * <h3>The page Staff can access</h3>
     * <ul>
     * <li>Home (staff) </li>
     * <li>Member CRUD</li>
     * <li>Product CRU (Cannot delete)</li>
     * <li>Sales Record</li>
     * <li>Discount CRUD</li>
     * <li>Report</li>
     * </ul>
     *
     * @param request
     * @return
     */
    public static boolean permissionStaff(HttpServletRequest request) {
        return ((String) request.getSession().getAttribute("staffLogin")) != null;
    }

    /**
     * <h3>The page Admin can access</h3>
     * <ul>
     * <li>Home (staff) </li>
     * <li>Member CRUD</li>
     * <li>Product CRUD</li>
     * <li>Sales Record</li>
     * <li>Discount CRUD</li>
     * <li>Staff CRUD</li>
     * <li>Report</li>
     * </ul>
     *
     * @param request
     * @return
     */
    public static boolean permissionAdmin(HttpServletRequest request) {
        return ((String) request.getSession().getAttribute("staffLogin")) == null ? false : ((String) request.getSession().getAttribute("staffLogin")).equals("admin");
    }

    /**
     * <h3>No login person will turn them to login page (Member login page &
     * staff login page)</h3>
     * <h3>The page Anonymous can access</h3>
     * <ul>
     * <li>Home</li>
     * <li>Search on Header</li>
     * <li>Menu</li>
     * <li>Product Content</li>
     * <li>Register Member</li>
     * <li>Login Member</li>
     * <li>Login Staff</li>
     * </ul>
     *
     * @param request
     * @return
     */
    public static boolean permissionNoLogin(HttpServletRequest request) {
        return (((Member) request.getSession().getAttribute("member")) == null || ((Member) request.getSession().getAttribute("member")).getMemberName() == null) && ((String) request.getSession().getAttribute("staffLogin")) == null;
    }

}
