/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DataAccess.DBTable;
import DataAccess.Mapper.*;
import Model.AddressBook;
import Model.Member;
import Model.Orderlist;
import Model.Orders;
import Model.PageModel.ViewSaleRecordModel;
import Model.Product;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utility.*;

/**
 *
 * @author erika
 */
public class salesRecord extends HttpServlet {

    private DBTable data;

    @Override
    public void init() throws ServletException {
        this.data = new DBTable();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request) || CheckPermission.permissionAdmin(request)) {
            String productId = request.getParameter("productId");

            //open a model to handle data
            ViewSaleRecordModel saleRecord = new ViewSaleRecordModel();
            int itemSold = 0;
            double ttlPrice = 0;

            try {
                if (productId != null && !productId.isEmpty()) {
                    int pId = Integer.parseInt(productId);
                    Product p = data.Product.getData(new ProductMapper(), pId).get(0);
                    saleRecord.setProduct(p);

                    //get filter list
                    ArrayList<Orders> olist = filterList(request);

                    if (olist != null && olist.size() > 0) {
                        //have ppl buy then loop the list
                        for (Orders o : olist) {
                            ViewSaleRecordModel.MemberDetail md = saleRecord.new MemberDetail();

                            //get member
                            Member m = data.Member.getData(new MemberMapper(), o.getMember().getMemberId()).get(0);

                            md.setMember(m);

                            //get orderlist
                            String sqlQuery1 = "SELECT * "
                                    + "FROM ORDERLIST "
                                    + "WHERE ORDERS_ID = ? AND PRODUCT_ID = ?";
                            ArrayList<Object> params = new ArrayList<>();
                            params.add(new Integer(o.getOrdersId()));
                            params.add(new Integer(pId));

                            Orderlist ol = data.Orderlist.getData(new OrderlistMapper(), params, sqlQuery1).get(0);

                            md.setOrderlist(ol);
                            itemSold += ol.getOrdersQuantity();
                            ttlPrice += ol.getOrdersSubprice();

                            //get address
                            AddressBook ab = data.AddressBook.getData(new AddressBookMapper(), o.getAddress().getAddressId()).get(0);

                            md.setAddress(ab);

                            saleRecord.addMdList(md);
                        }

                        saleRecord.setItemSold(itemSold);
                        saleRecord.setTtlPrice(ttlPrice);

                    }
                    request.setAttribute("salesRecord", saleRecord);
                    request.getRequestDispatcher("salesRecord/salesRecord.jsp").forward(request, response);

                } else {
                    request.getSession().setAttribute("UnexceptableError", "The Product ID in url is missing, please passing parameter in correct format");
                    request.getSession().setAttribute("UnexceptableErrorDesc", "Product ID is missing");
                    request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (NumberFormatException ex) {
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Invalid product ID");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            } catch (Exception ex) {
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Unexcepted exception");
                request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            }
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected ArrayList<Orders> filterList(HttpServletRequest request) throws SQLException {

        String memberID = request.getParameter("memberID") == null ? "" : request.getParameter("memberID");
        String city = request.getParameter("city") == null ? "" : request.getParameter("city");
        String postcode = request.getParameter("postcode") == null ? "" : request.getParameter("postcode");
        String state = request.getParameter("state") == null ? "" : request.getParameter("state");
        String productId = request.getParameter("productId") == null ? "" : request.getParameter("productId");

        String sqlQuery = "SELECT DISTINCT MEMBER.*, ORDERS.* "
                + "FROM PRODUCT "
                + "INNER JOIN ORDERLIST ON PRODUCT.PRODUCT_ID = ORDERLIST.PRODUCT_ID "
                + "INNER JOIN ORDERS ON ORDERLIST.ORDERS_ID = ORDERS.ORDERS_ID "
                + "INNER JOIN MEMBER ON ORDERS.MEMBER_ID = MEMBER.MEMBER_ID "
                + "INNER JOIN ADDRESSBOOK ON ORDERS.ADDRESS_ID = ADDRESSBOOK.ADDRESS_ID "
                + "WHERE PRODUCT.PRODUCT_ID = ? AND PRODUCT.PRODUCT_ACTIVE = ? ";

        ArrayList<Object> condition = new ArrayList<>();
        condition.add(Integer.parseInt(productId));
        condition.add(new Character('1'));

        if (!memberID.isEmpty()) {
            sqlQuery += "AND MEMBER.MEMBER_ID = ? ";
            condition.add(Integer.parseInt(memberID));
        }

        if (!city.isEmpty()) {
            sqlQuery += "AND ADDRESSBOOK.ADDRESS_CITY LIKE '%" + city + "%' ";
        }

        if (!postcode.isEmpty()) {
            sqlQuery += "AND ADDRESSBOOK.ADDRESS_POSTCODE LIKE '%" + postcode + "%' ";
        }

        if (!state.isEmpty()) {
            sqlQuery += "AND ADDRESSBOOK.ADDRESS_STATE LIKE '%" + state + "%' ";
        }

        return data.Orders.getData(new OrdersMapper(), condition, sqlQuery);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
