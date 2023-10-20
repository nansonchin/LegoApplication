/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DataAccess.DBaccess;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Yeet
 */
public class OrderListingController {

    public static List<HashMap<String, Object>> getOrdersForList(String search) throws SQLException {
        String query = ""
                + "SELECT o.orders_id, o.orders_date, p.product_id, p.product_name, ol.orders_quantity, m.member_id, m.member_name "
                + "FROM orders o"
                + " INNER JOIN OrderList ol ON o.orders_id = ol.orders_id"
                + " INNER JOIN Product p ON p.product_id = ol.product_id"
                + " INNER JOIN Member m ON m.member_id = o.member_id "
                + " ORDER BY o.orders_date DESC";
        return DBaccess.customizeSqlSelect(query);

    }
}
