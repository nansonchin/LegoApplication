<%-- 
    Document   : creditCard
    Created on : Apr 24, 2023, 2:18:15 AM
    Author     : Thong Sau Wei
--%>

<%@page import="java.util.HashMap"%>
<%@page import="Model.*"%>
<%@page import="Model.PageModel.PaymentModel"%>
<%@page import="Controller.PaymentController"%>
<%@page import="Model.Product"%>
<%@page import="Model.Cartlist"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    ArrayList<Cartlist> cart = (ArrayList<Cartlist>) request.getAttribute("clist");
    ArrayList<Product> product = (ArrayList<Product>) request.getAttribute("plist");

    ArrayList<MemberAddress> mAdd = (ArrayList<MemberAddress>) request.getAttribute("memberAddress");
    ArrayList<AddressBook> aBook = (ArrayList<AddressBook>) request.getAttribute("addressBook");

    ArrayList<Discount> discount1 = (ArrayList<Discount>) request.getAttribute("dlist");

    ArrayList<PaymentModel> cartItems = PaymentController.getCartItem(cart, product);

    String shippingAddress = (String) session.getAttribute("shippingAddress");
    int totalProducts = (Integer) session.getAttribute("totalProducts");

    String shippingMethod = (String) session.getAttribute("shippingMethod");
    double deliveryFee = (Double) session.getAttribute("deliveryFee");

    int totalProduct1 = (Integer) request.getAttribute("totalProducts");

    //shipping address
    String sId = (String) session.getAttribute("sId");
    ArrayList<AddressBook> sA = (ArrayList<AddressBook>) request.getAttribute("slist");
    HashMap<Integer, Double> dlist = (HashMap<Integer, Double>) session.getAttribute("productPrice");
%>

<jsp:useBean id="discount" class="Controller.DiscountController" scope="application"></jsp:useBean>


    <!DOCTYPE html>
    <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js">


            <link rel="stylesheet" href="css/bootstrap.css">
            <link rel="stylesheet" href="css/creditCard.css">

            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        </head>


        <body>

        <%@include file="/Home/view/Header.jsp"%>

        <div class="yy" style="margin-top: 100px;">
            <div class="content">
                <div class="card-container">


                    <div class="aa">
                        <div class="shoe-image-wrapper">
                            <div class="shoe-img" style="width: 20px;height: 20px;">
                                <img src="Payment/batman.png" alt="Nike shoe" class="shoe" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="checkOut">

                <%-- Display error message if there is one --%>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert" style="margin-bottom: 30px;margin-left: 38%">
                        ${errorMessage}
                    </div>
                </c:if>

                <h1>Checkout</h1>
                <h4>Payment Details</h4>

                <form method="post" action="PaymentMethodServlet" style="margin-bottom: 80px;">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="cardName">Card Holder Name</label>
                            <input type="text" class="form-control" id="cardName" name="cardName" placeholder="Testing Haha">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="cardNumber">Card Number</label>
                            <input type="password" class="form-control" id="cardNumber" name="cardNumber" placeholder="1234567890098765768">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="expirationDate">Expiration Date: Month Year</label>
                            <input type="month" class="form-control" id="expirationDate" name="expirationDate">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="typeCard" style="color: white;">Type of Card</label>
                            <select id="typeCard" name="typeCard" class="form-control">
                                <option value="Visa" selected>Visa</option>
                                <option value="American Express">American Express</option>
                                <option value="MasterCard">MasterCard</option>
                            </select>
                        </div>

                        <div class="form-group col-md-4">
                            <label for="cvv">CVV</label>
                            <input type="text" class="form-control" id="cvv" name="cvv" placeholder="123">
                        </div>
                    </div>

                    <button class="btn" style="background-color: rgba(242, 150, 147);">submit</button>

                </form>

                <div class="container2" style="margin-left: 0px;">
                    <div class="row">
                        <div class="col-md-offset-1 col-md-12">
                            <div class="panel">
                                <div class="panel-body table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>Product Name</th>
                                                <th style="color: rgba(242, 150, 147);">Price</th>
                                                <th>Quantity</th>
                                                <th style="color: rgba(242, 150, 147);">Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (PaymentModel cartItem : cartItems) {%>
                                            <% if (cartItem != null && cartItems.size() > 0) {%>


                                            <tr>
                                                <td></td>
                                                <td><%= cartItem.getProduct().getProductName()%></td>

                                                <% double originalPrice = cartItem.getProduct().getProductPrice(); %>

                                                <%
                                                    double price = originalPrice;
                                                    if (dlist != null && dlist.size() > 0) {%>
                                                <%if (dlist.get(cartItem.getProduct().getProductId()) != null) {
                                                price = dlist.get(cartItem.getProduct().getProductId());
                                                %>


                                                <td><s>RM <%= originalPrice%>  </s>
                                                    RM <%=dlist.get(cartItem.getProduct().getProductId())%></td>


                                                <%} else {
                                                    price = originalPrice;%>

                                                <td>RM <%=originalPrice%></td>

                                                <%}%>
                                                <%
                                                } else {
                                                    price = originalPrice;
                                                %>

                                                <td>RM <%=originalPrice%></td>
                                                </div>

                                                <%
                                                    }
                                                %>

                                                <td><%= cartItem.getCartQuantity()%></td>
                                                <% double ttlPrice = Math.round(price * cartItem.getCartQuantity() * 100) / 100.0;%>
                                                <td>RM <%= ttlPrice%></td>
                                            </tr>
                                            <% }%>
                                            <% }%>

                                        </tbody>
                                        <tr style="color: rgba(242, 150, 147);">
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Tax</td>
                                            <%
                                                Double tax = (Double) session.getAttribute("tax");
                                            %>
                                            <% if (tax != null) {%>
                                            <td>RM <%= session.getAttribute("tax")%></td>
                                            <% }%>
                                        </tr>

                                        <%
                                            if (deliveryFee == 0.0) {
                                        %>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Delivery Fee</td>
                                            <td>FREE</td>
                                        </tr>
                                        <%
                                        } else {
                                        %>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Delivery Fee</td>
                                            <td>RM <%= deliveryFee%></td>
                                        </tr>
                                        <%
                                            }
                                        %>

                                        <tr style="color: rgba(242, 150, 147);">
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Subtotal</td>
                                            <%
                                                Double grandTotal = (Double) session.getAttribute("grandTotal");
                                            %>
                                            <% if (grandTotal != null) {
                                            double roundedTotal = Math.round(grandTotal * 100.0) / 100.0;
                                            %>
                                            <td>RM <%= roundedTotal%></td>
                                            <% }%>
                                        </tr>


                                        <% if (shippingMethod.equals("expressShipping")) {%>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Shipping Charge</td>
                                            <%
                                                Double shippingC = (Double) session.getAttribute("shippingCharge");
                                            %>
                                            <% if (tax != null) {%>
                                            <td>RM <%= session.getAttribute("shippingCharge")%></td>
                                            <% }%>
                                        </tr>
                                        <% } %>




                                        <tr style="color: rgba(242, 150, 147);">
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td>Final Total</td>
                                            <%
                                                Double finalT = (Double) session.getAttribute("finalTotal");
                                            %>
                                            <% if (finalT != null) {%>
                                            <td>RM <%= session.getAttribute("finalTotal")%></td>
                                            <% }%>
                                        </tr>
                                    </table>
                                </div>
                                <div class="panel-footer">
                                    <div class="row">
                                        <% if (totalProduct1 != 0) {%>
                                        <div class="col col-sm-6 col-xs-6">total product - <b><%= totalProduct1%></b></div>
                                        <% }%>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </body>


</html>
