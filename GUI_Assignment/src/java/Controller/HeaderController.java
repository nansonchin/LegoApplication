package Controller;

/**
 * @author LOH XIN JIE
 */
import DataAccess.*;
import DataAccess.Mapper.*;
import java.sql.*;
import java.util.*;

public class HeaderController {

    public int getUserCartlistQty(int memberid) {
        try {
            DBTable db = new DBTable();

            //set sql require
            String query = "SELECT cl.*, c.*, m.* FROM Member m, Cart c, Cartlist cl WHERE m.member_id = ? AND m.member_id = c.member_id AND c.cart_id = cl.cart_id";
            ArrayList<Object> condition = new ArrayList<>();
            condition.add(new Integer(memberid));

            //get data from db
            return db.Cartlist.getData(new CartlistMapper(), condition, query).size();
        } catch (SQLException ex) {
            //move user to error page
            ex.printStackTrace();
            return -1;
        }
    }
}
