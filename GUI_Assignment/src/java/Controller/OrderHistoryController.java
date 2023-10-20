package Controller;

/**
 * @author LOH XIN JIE
 */
import Model.*;
import DataAccess.*;
import DataAccess.Mapper.MemberMapper;
import DataAccess.Mapper.OrdersMapper;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.http.*;

public class OrderHistoryController {

    DBTable db = new DBTable();

    protected ArrayList<Orders> filterList(HttpServletRequest request) throws SQLException {

        String memberID = request.getParameter("memberID") == null ? "" : request.getParameter("memberID");
        String city = request.getParameter("city") == null ? "" : request.getParameter("city");
        String postcode = request.getParameter("postcode") == null ? "" : request.getParameter("postcode");
        String state = request.getParameter("state") == null ? "" : request.getParameter("state");
        String productID = request.getParameter("productID") == null ? "" : request.getParameter("productID");

        String sqlQuery = "SELECT DISTINCT MEMBER.*, ORDERS.* "
                + "FROM PRODUCT "
                + "INNER JOIN ORDERLIST ON PRODUCT.PRODUCT_ID = ORDERLIST.PRODUCT_ID "
                + "INNER JOIN ORDERS ON ORDERLIST.ORDERS_ID = ORDERS.ORDERS_ID "
                + "INNER JOIN MEMBER ON ORDERS.MEMBER_ID = MEMBER.MEMBER_ID "
                + "INNER JOIN ADDRESSBOOK ON ORDERS.ADDRESS_ID = ADDRESSBOOK.ADDRESS_ID "
                + "WHERE PRODUCT.PRODUCT_ID = ? AND PRODUCT.PRODUCT_ACTIVE = ? ";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(Integer.parseInt(productID));
        condition.add(new Character('1'));

        if (!memberID.isEmpty()) {
            sqlQuery += "AND MEMBER.MEMBER_ID = ? ";
            condition.add(Integer.parseInt(memberID));
        }

        if (!city.isEmpty()) {
            sqlQuery += "AND ADDRESSBOOK.ADDRESS_CITY LIKE '%" + city + "%' ";
        }

        if (!postcode.isEmpty()) {
            sqlQuery += "AND ADDRESSBOOK.ADDRESS_CITY LIKE '%" + postcode + "%' ";
        }

        if (!state.isEmpty()) {
            sqlQuery += "AND ADDRESSBOOK.ADDRESS_CITY LIKE '%" + state + "%' ";
        }

        return db.Orders.getData(new OrdersMapper(), condition, sqlQuery);
    }
}
