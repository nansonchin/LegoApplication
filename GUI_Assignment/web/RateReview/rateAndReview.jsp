<%-- 
    Document   : rateAndReview
    Created on : Apr 26, 2023, 4:36:42 PM
    Author     : Acer
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("product");
    ArrayList<Orderlist> orderlist = (ArrayList<Orderlist>) request.getAttribute("orderlist");
    ArrayList<Discount> discount1 = (ArrayList<Discount>) request.getAttribute("dlist");
    HashMap<Integer, Double> dlist = (HashMap<Integer, Double>) session.getAttribute("productPrice");
%>
<jsp:useBean id="discount" class="Controller.DiscountController" scope="application"></jsp:useBean>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js">

        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/rateAndReview.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    </head>

    <body>

        <%@include file="/Home/view/Header.jsp"%>

        <div id="container" style="box-shadow: 8px 10px 20px rgba(242, 150, 147); margin-top: 10px;">

            <!-- Start  Product details -->
            <div class="product-details" style="margin-left: 30px;">

                <%-- Display error message if there is one --%>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>

                <!--  Product Name -->
                <% for (Product p : productList) {%>
                <% if (p != null) {%>
                <h1 style="color: rgba(242, 150, 147);"><%= p.getProductName()%></h1>
                <% }%>
                <% }%>

                <form action="RateReviewServlet" method="post">

                    <input type="hidden" name="productId" value="${requestScope.productID}">

                    <input type="hidden" name="orderId" value="${requestScope.orderID}">

                    <div class="stars">
                        <input class="star star-5" id="star-5" type="radio" name="rating" value="10" />
                        <label class="star star-5" for="star-5"></label>
                        <input class="star star-4" id="star-4" type="radio" name="rating" value="8" />
                        <label class="star star-4" for="star-4"></label>
                        <input class="star star-3" id="star-3" type="radio" name="rating" value="6" />
                        <label class="star star-3" for="star-3"></label>
                        <input class="star star-2" id="star-2" type="radio" name="rating" value="4" />
                        <label class="star star-2" for="star-2"></label>
                        <input class="star star-1" id="star-1" type="radio" name="rating" value="2" />
                        <label class="star star-1" for="star-1"></label>
                    </div>

                    <div class="form-row">
                        <div class="form-group col">
                            <label for="reviewText">Review</label>
                            <textarea id="reviewText" name="reviewText" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px"></textarea>
                        </div>

                    </div>


                    <button class="btn" type="submit" style="background-color: rgba(242, 150, 147);">submit</button>

                </form>

            </div>
            <div class="product-image">
                <% for (Product p : productList) {%>
                <% if (p != null) {%>

                <img src="RetrieveImageServlet?imageID=<%= p.getProductId()%>">

                <div class="info">
                    <h2 style="margin-top: 70px;">DESCRIPTION</h2>
                    <ul style="margin-left: 20px;">
                        <li><strong>Name : </strong><%= p.getProductName()%></li>
                        <li><strong>Description : </strong><%= p.getProductDesc()%></li>


                        <% double originalPrice = p.getProductPrice(); %>

                        <!-- possible cause error -->

                        <% //double price = (Double) session.getAttribute("productPrice");
                                        if (dlist != null && dlist.size() > 0) {%>
                        <%if (dlist.get(p.getProductId()) != null) {%>
                        
                        <li><strong>Price: </strong><del>RM <%= originalPrice%></del>RM <%=dlist.get(p.getProductId())%></li>
                        
                        <%} else {%>
                        <li><strong>Price: </strong>RM <%=originalPrice%></li>
                        <%}%>
                        <%
                        } else {
                        %>

                        <li><strong>Price: </strong>RM <%=originalPrice%></li>

                        <%
                            }
                        %>

                        
                    </ul>
                </div>
                <% }%>
                <% }%>
            </div>
        </div>

    </body>

</html>
