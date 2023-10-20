/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import DataAccess.DBTable;
import DataAccess.Mapper.MemberMapper;
import DataAccess.Mapper.ProductMapper;
import DataAccess.Mapper.RateReviewMapper;
import Model.Member;
import Model.Product;
import Model.RateReview;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utility.*;

/**
 *
 * @author guoc7
 */
public class viewProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!CheckPermission.permissionStaff(request)) {
            try {
                int productId = Integer.parseInt(request.getParameter("id"));

                //Show the Product Details
                DBTable db = new DBTable();
                List<Product> viewProd = db.Product.getData(new ProductMapper(), productId);
                request.setAttribute("productDetail", viewProd.get(0));

                //Show Product Avg rating
                List<RateReview> rateCountList = db.RateReview.getData(new RateReviewMapper());
                List<Double> ratingList = new ArrayList<>();

                //Show Product at Banner in ProductDetails Page
                String sql = "Select * From Product Where PRODUCT_ACTIVE = ?";
                ArrayList<Object> list = new ArrayList();
                list.add(new Integer(1));
                List<Product> productList = db.Product.getData(new ProductMapper(), list, sql);
                request.setAttribute("contentBanner", productList);
                List<RateReview> productContentBanner = db.RateReview.getData(new RateReviewMapper());
                HashMap<Integer, Double> productContentHashBanner = new HashMap<Integer, Double>();

                //find RateAndReview Format
                for (int i = 0; i < productList.size(); i++) {
                    double totalRating = 0;
                    double ratingCount = 0;
                    for (int y = 0; y < rateCountList.size(); y++) {
                        if (rateCountList.get(y).getProduct().getProductId() == productList.get(i).getProductId()) {
                            totalRating += rateCountList.get(y).getReviewRating();
                            ratingCount++;
                        }
                    }
                    if (ratingCount > 0) {
                        double avgRating = Math.round(totalRating / ratingCount);
                        //ratingList.add(avgRating);
                        productContentHashBanner.put(productList.get(i).getProductId(), avgRating);
                    } else {
                        productContentHashBanner.put(productList.get(i).getProductId(), 0.00);
//                    ratingList.add(0.00);
                    }
                }
                request.setAttribute("productContentHashBanner", productContentHashBanner);

                //Customer Comment
                String commentSql = "Select * FROM RATEREVIEW Where product_id=? ";
                ArrayList<Object> params = new ArrayList<Object>();
                params.add(productId);
                ArrayList<RateReview> review = db.RateReview.getData(new RateReviewMapper(), params, commentSql);
                request.setAttribute("customerReviewProductDetails", review);

                //Show Customer Comment Name
                List<RateReview> rateReviewList = db.RateReview.getData(new RateReviewMapper());
                List<Member> memberList = db.Member.getData(new MemberMapper());
                List<Member> storeMemberId = new ArrayList<>();

                List<Member> memberNameFound = new ArrayList<>();
                //search through the customer and ratereview if found then store in arraylist so i can output the product details

                for (int i = 0; i < rateReviewList.size(); i++) {
                    for (int j = 0; j < memberList.size(); j++) {
                        if (rateReviewList.get(i).getMember().getMemberId() == memberList.get(j).getMemberId()) {
                            if (rateReviewList.get(i).getProduct().getProductId() == productId) {
                                memberNameFound.add(memberList.get(j));
                            }
                        }
                    }
                }
                request.setAttribute("memberNameFound", memberNameFound);

                request.getRequestDispatcher("/productMenu/productcontent.jsp").forward(request, response);

            } catch (SQLException ex) {
                ex.printStackTrace();
                request.getSession().setAttribute("UnexceptableError", ex.getMessage());
                request.getSession().setAttribute("UnexceptableErrorDesc", "Database Server Exception");
                response.sendRedirect("/GUI_Assignment/Home/view/ErrorPage.jsp");
            }
        } else {
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
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
        //processRequest(request, response);
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
