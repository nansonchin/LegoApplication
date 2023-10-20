<%-- 
    Document   : CheckOut
    Created on : Apr 26, 2023, 1:01:39 AM
    Author     : Thong Sau Wei
--%>

<%@page import="java.util.HashMap"%>
<%@page import="Controller.DiscountController"%>
<%@page import="Model.*"%>
<%@page import="Model.PageModel.PaymentModel"%>
<%@page import="Controller.PaymentController"%>
<%@page import="Model.Product"%>
<%@page import="Model.Cartlist"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    ArrayList<Cartlist> cart = (ArrayList<Cartlist>) request.getAttribute("clist");
    ArrayList<Product> product = (ArrayList<Product>) request.getAttribute("plist");

    ArrayList<Discount> discount1 = (ArrayList<Discount>) request.getAttribute("dlist");

    ArrayList<MemberAddress> mAdd = (ArrayList<MemberAddress>) request.getAttribute("memberAddress");
    ArrayList<AddressBook> aBook = (ArrayList<AddressBook>) request.getAttribute("addressBook");

    ArrayList<PaymentModel> cartItems = PaymentController.getCartItem(cart, product);
    String shippingMethod = (String) session.getAttribute("shippingMethod");
    String paymentMethod = (String) session.getAttribute("paymentMethod");
    double dF = (Double) session.getAttribute("deliveryFee");

    //shipping address
    String sId = (String) session.getAttribute("sId");
    ArrayList<AddressBook> sA = (ArrayList<AddressBook>) request.getAttribute("slist");

    int totalProduct1 = (Integer) request.getAttribute("totalProducts");

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
            <!--<link rel="stylesheet" href="/GUI_Assignment/css/CheckOut.css">-->


            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        </head>

        <body>

        <%@include file="/Home/view/Header.jsp"%>
        <section class="pt-5 pb-5">
            <div class="container" style="width: 70%;">
                <div class="row w-100">
                    <div class="col-lg-12 col-md-12 col-12">
                        <h5 class="display-5 mb-2 text-center" style="color:rgba(242, 150, 147);margin-top: -38px;">Check Out Review</h5>

                        <table id="shoppingCart" class="table table-condensed table-responsive">
                            <thead>
                                <tr>
                                    <th style="width:60%;color:rgba(242, 150, 147);">Product</th>
                                    <th style="width:20%">Price</th>
                                    <th style="width:20%;color:rgba(242, 150, 147);">Quantity</th>
                                    <th style="width:16%"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (PaymentModel cartItem : cartItems) {%>
                                <% if (cartItem != null && cartItems.size() > 0) {%>
                                <tr>
                                    <td data-th="Product">
                                        <div class="row">
                                            <div class="col-md-3 text-left">
                                                <img src="RetrieveImageServlet?imageID=<%= cartItem.getProduct().getProductId()%>" width="160" height="120" alt=""
                                                     class="img-fluid d-none d-md-block rounded mb-2 shadow ">
                                            </div>
                                            <div class="col-md-9 text-left mt-sm-2">
                                                <h5><%= cartItem.getProduct().getProductName()%></h5>
                                            </div>
                                        </div>
                                    </td>
                                    <% double originalPrice = cartItem.getProduct().getProductPrice(); %>

                                    <%
                                        double price = originalPrice;
                                        if (dlist != null && dlist.size() > 0) {%>
                                    <%if (dlist.get(cartItem.getProduct().getProductId()) != null) {
                                            price = dlist.get(cartItem.getProduct().getProductId());
                                    %>

                                    <td data-th="Price"><del><%= originalPrice%></del>
                                        RM <%=dlist.get(cartItem.getProduct().getProductId())%></td>
                                        <%} else {
                                            price = originalPrice;%>
                                    <td data-th="Price">RM <%= originalPrice%></td>
                                    <%}%>
                                    <%
                                    } else {
                                        price = originalPrice;
                                    %>
                                    <td data-th="Price">RM <%= originalPrice%></td>
                                    <%
                                        }
                                    %>

                                    <td data-th="Quantity">
                                        <%= cartItem.getCartQuantity()%>
                                    </td>

                                </tr>

                                <% }%>
                                <% }%>

                            </tbody>
                        </table>

                    </div>
                </div>

                <div class="row py-5 p-4 bg-dark rounded shadow-sm">
                    <div class="col-lg-6">
                        <div class="px-4 py-3 text-uppercase font-weight-bold border-bottom">Payment Details</div>
                        <div class="p-4">

                            <form>
                                <%
                                    String adName, adPhone, adNo, adStreet, adCity, adState, adPostcode;
                                    for (AddressBook ad : sA) {
                                        adName = ad.getAddressName();
                                        adPhone = ad.getAddressPhone();
                                        adNo = ad.getAddressNo();
                                        adStreet = ad.getAddressStreet();
                                        adCity = ad.getAddressCity();
                                        adState = ad.getAddressState();
                                        adPostcode = ad.getAddressPostcode();

                                %>
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">Shipping Address</label>
                                    <textarea disabled style="width: 440px;height: 70px;"><%= adNo%>, <%= adStreet%>, <%= adState%>, <%= adPostcode%>, <%= adCity%></textarea>
                                </div>
                                <%  }%>

                                <%
                                    if (paymentMethod.equals("creditCard")) {
                                %>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">Type of card</label>
                                    <input type="text" class="form-control" placeholder="${typeCard}" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">Name</label>
                                    <input type="text" class="form-control" placeholder="${cardName}" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">Card Number</label>
                                    <input type="text" class="form-control" placeholder="${cardNumber}" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">Expiration Date</label>
                                    <input type="text" class="form-control" placeholder="${expirationDate}" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">CVV</label>
                                    <input type="text" class="form-control" placeholder="${cvv}" disabled>
                                </div>
                                <%
                                    }
                                    if (paymentMethod.equals("cash")) {
                                %>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">First Name</label>
                                    <input type="text" class="form-control" placeholder="${cashfirstName}" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">Last Name</label>
                                    <input type="text" class="form-control" placeholder="${cashlastName}" disabled>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPassword1" class="form-label">Email</label>
                                    <input type="text" class="form-control" placeholder="${cashemail}" disabled>
                                </div>
                                <%
                                    }
                                %>
                            </form>
                        </div>

                    </div>
                    <div class="col-lg-6">
                        <div class="px-4 py-3 text-uppercase font-weight-bold border-bottom">Order summary</div>
                        <!-- <hr style="margin-top: -10px; margin-left: 15px;"> -->
                        <div class="p-4">
                            <!-- <p class="font-italic mb-4">Shipping and additional costs are calculated based on values you have entered.</p> -->
                            <ul class="list-unstyled mb-4">
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">TAX </strong><strong>RM <%= session.getAttribute("tax")%></strong></li>

                                <%
                                    if (dF == 0.0) {
                                %>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">DELIVERY FEE</strong><strong>Free</strong></li>
                                    <%
                                    } else {
                                    %>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">DELIVERY FEE</strong><strong>RM <%= dF%></strong></li>
                                    <%
                                        }
                                    %>

                                <%
                                    // Get the grandTotal value from the session
                                    double grandTotal = (Double) session.getAttribute("grandTotal");

                                    // Round the grandTotal to 2 decimal places
                                    double roundedTotal = Math.round(grandTotal * 100.0) / 100.0;
                                %>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Subtotal</strong><strong>RM <%= roundedTotal%></strong></li>

                                <% if (shippingMethod.equals("expressShipping")) {%>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Shipping Charge</strong><strong>RM <%= session.getAttribute("shippingCharge")%></strong></li>
                                    <% } %>

                                <%
                                    Double finalT = (Double) session.getAttribute("finalTotal");
                                %>
                                <% if (finalT != null) {%>
                                <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Total</strong>
                                    <h5 class="font-weight-bold">RM <%= session.getAttribute("finalTotal")%></h5>
                                    <% }%>
                                </li>

                                <form method="post" action="CheckOutReviewServlet">
                                    <button type="submit" class="btn btn-primary" id="btn" style="margin-top: 15px;width: 50%">CONFIRM PAYMENT</button>
                                </form>

                                <!-- </ul><a href="#" class="btn btn-dark rounded-pill py-2 btn-block">Procceed to checkout</a> -->
                        </div>

                    </div>
                </div>
            </div>

        </section>

    </div>
</body>


</html>
