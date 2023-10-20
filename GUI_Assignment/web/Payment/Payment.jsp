<%-- 
    Document   : Payment
    Created on : Apr 23, 2023, 5:59:05 AM
    Author     : Thong Sau Wei
--%>

<%@page import="Model.PageModel.PaymentModel"%>
<%@page import="Controller.PaymentController"%>
<%@page import="Model.*, java.util.*"%>
<%@page import="java.util.ArrayList"%>
<!-- header -->
<%@include file="../Home/view/Header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" %>

<%
    ArrayList<MemberAddress> memberA = (ArrayList<MemberAddress>) request.getAttribute("mlist");
    ArrayList<AddressBook> addressB = (ArrayList<AddressBook>) request.getAttribute("alist");

    ArrayList<Cartlist> cart = (ArrayList<Cartlist>) request.getAttribute("clist");
    ArrayList<Product> product = (ArrayList<Product>) request.getAttribute("plist");

    ArrayList<Discount> discount1 = (ArrayList<Discount>) request.getAttribute("dlist");

    ArrayList<MemberAddress> mAdd = (ArrayList<MemberAddress>) request.getAttribute("memberAddress");
    ArrayList<AddressBook> aBook = (ArrayList<AddressBook>) request.getAttribute("addressBook");

    ArrayList<PaymentModel> cartItems = PaymentController.getCartItem(cart, product);
    double deliveryFee = (Double) session.getAttribute("deliveryFee") == null ? 0.00 : (Double) session.getAttribute("deliveryFee");

    int totalProducts = (Integer) session.getAttribute("totalProducts");
    HashMap<Integer, Double> dlist = (HashMap<Integer, Double>) session.getAttribute("productPrice");
%>

<jsp:useBean id="discount" class="Controller.DiscountController" scope="application"></jsp:useBean>

    <!DOCTYPE html>
    <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">


            <link rel="stylesheet" href="css/bootstrap.css">
            <link rel="stylesheet" href="css/payment.css">


            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


        </head>

        <style>
            .card-1 {
                width: 400px;
                border-radius: 18px;
                border: none;
            }

            .card-2 {
                border-radius: 20px;
            }


            .card-child {

                border: 3px solid blue;
                border-radius: 16px;

            }

            .circle {
                background-image: url('Payment/mapping.png');
                height: 50px;
                width: 50px;
                border-radius: 50%;
                display: flex;
                color: #9553ea;
                justify-content: center;
                align-items: center;
                font-size: 20px;
                margin-top: 10px;
            }
        </style>

        <body>

            <!-- payment -->
            <main class="container">

                <h1 class="heading">
                    <img src="Payment/legocart.png" alt="money" width="55px" height="50px">
                    Payment
                </h1>


                <form method="post" action="PaymentServlet">
                    <div class="item">

                        <section class="left">

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert" style="margin-bottom: 30px;">
                                ${errorMessage}
                            </div>
                        </c:if>


                        <h2 class="section-heading">Shipping Address</h2>
                        <div class="line-1"></div>

                        <%if (memberA != null && memberA.size() > 0) {%>
                        <% for (MemberAddress m : memberA) {
                                for (AddressBook b : addressB) {
                                    if (m != null & b != null) {
                                        if (m.getAddress().getAddressId() == b.getAddressId()) {%>
                        <!-- shipping address -->
                        <div class="row">
                            <div class="col-xl-6 pb-5">
                                <input class="shipping-address" type="radio" name="shippingAddress"
                                       value="<%= b.getAddressId()%>" id="<%= b.getAddressId()%>">
                                <label class="address1" for="<%= b.getAddressId()%>" id="shippingA">
                                    <span class="circle">
                                        <i class="fa fa-map-marker" style="color: red;"></i>
                                    </span>

                                    <div class="d-flex flex-column ms-4" style="margin-top: 10px; margin-bottom: 10px;">
                                        <h6 class="fw-bold">
                                            <%= b.getAddressName()%> <span class="phoneAddress" style="margin-left: 20px;">
                                                <%= b.getAddressPhone()%>
                                            </span>
                                        </h6>
                                        <span style="color: #A9A9A9;">
                                            <%= b.getAddressNo()%>, <br>
                                            <%= b.getAddressStreet()%>, <br>
                                            <%= b.getAddressCity()%>,
                                            <br>
                                            <%= b.getAddressState()%>
                                            ,<br>
                                            <%= b.getAddressPostcode()%>
                                        </span>
                                    </div>
                                </label>
                            </div>
                        </div>

                        <% }
                                        }
                                    }
                                }
                            }%>

                        <button type="button" class="btn btn-outline-info" data-bs-toggle="modal"
                                data-bs-target="#addAddress" style="margin-left: 3px;">
                            <i class="fa fa-plus" aria-hidden="true"></i> Add Address
                        </button>


                        <!-- Payment method -->
                        <h2 class="section-heading" style="margin-top: 30px;">Payment Method</h2>
                        <div class="line-1"></div>

                        <div class="col-xl-10 pb-5">
                            <input class="checkbox-budget" type="radio" name="paymentMethod" value="creditCard"
                                   id="creditCard">
                            <label class="for-checkbox-budget" for="creditCard">
                                <span data-hover="Credit card">Credit card</span>
                            </label>

                            <input class="checkbox-budget" type="radio" name="paymentMethod" value="cash" id="cash">
                            <label class="for-checkbox-budget" for="cash">
                                <span data-hover="Cash">CASH</span>
                            </label>
                        </div>

                        <!-- Available Shipping Method -->
                        <h2 class="section-heading" style="margin-top: -17px;">Available Shipping Method</h2>
                        <div class="line-1"></div>

                        <div class="col-xl-10 pb-5">
                            <input class="checkbox-budget" type="radio" id="normalDelivery" name="shippingMethod" value="normalDelivery">
                            <label class="for-checkbox-budget" for="normalDelivery">
                                <span data-hover="Normal Delivery">Normal Delivery</span>
                            </label>

                            <input class="checkbox-budget" type="radio" id="expressShipping" name="shippingMethod" value="expressShipping">
                            <label class="for-checkbox-budget" for="expressShipping">
                                <span data-hover="Express Shipping">Express Shipping</span>
                            </label>

                        </div>

                        <!-- button -->
                        <div class="passPage">
                            <div class="center-center">
                                <button class="btn-glitch-fill" type="submit">
                                    <%
                                        // Get the grandTotal value from the session
                                        double grandTotal = (Double) session.getAttribute("grandTotal");

                                        // Round the grandTotal to 2 decimal places
                                        double roundedTotal = Math.round(grandTotal * 100.0) / 100.0;
                                    %>

                                    <span class="text">// Pay RM <%= roundedTotal%></span>
                                    <span class="text-decoration">_</span>
                                    <span class="decoration">&rArr;</span>
                                </button>

                                <a class="btn-glitch-fill" style="float: right;" href="/GUI_Assignment/productMenuServlet">
                                    <span class="text">// Continue
                                        Shopping</span><span class="text-decoration">_</span>
                                    <span class="decoration">&rArr;</span></button>
                                </a>
                            </div>
                        </div>

                    </section>
            </form>

            <!-- Shipping Address -->
            <div class="modal fade" id="addAddress" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Add Address</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="newAddressForm" method="post" action="/GUI_Assignment/AddAddressServlet" class="row g-3">
                                <div class="col-md-12">
                                    <label for="newAddressName" class="form-label">Name</label>
                                    <input type="text" class="form-control" id="newAddressName"
                                           name="newAddressName">
                                </div>
                                <div class="col-md-12">
                                    <label for="newAddressPhone" class="form-label">Phone</label>
                                    <input type="text" class="form-control" id="newAddressPhone"
                                           name="newAddressPhone">
                                </div>

                                <div class="col-md-6">
                                    <label for="newAddressNo" class="form-label">No.</label>
                                    <input type="text" class="form-control" id="newAddressNo"
                                           name="newAddressNo">
                                </div>
                                <div class="col-md-6">
                                    <label for="newAddressStreet" class="form-label">Street</label>
                                    <input type="text" class="form-control" id="newAddressStreet"
                                           name="newAddressStreet">
                                </div>

                                <div class="col-md-6">
                                    <label for="newAddressCity" class="form-label">City</label>
                                    <input type="text" class="form-control" id="newAddressCity"
                                           name="newAddressCity">
                                </div>

                                <div class="col-md-6">
                                    <label for="newAddressPostcode" class="form-label">Postcode</label>
                                    <input type="text" class="form-control" id="newAddressPostcode"
                                           name="newAddressPostcode">
                                </div>

                                <div class="col-md-12">
                                    <label for="newAddressState" class="form-label">State</label>
                                    <input type="text" class="form-control" id="newAddressState"
                                           name="newAddressState">
                                </div>

                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

            <section class="right">
                <div class="cart-item-box">

                    <h2 class="section-heading">Summary Order <span>(<%= totalProducts%>)</span></h2>
                    <div class="line-1"></div>

                    <div class="product-card">

                        <div id="displayCart" class="rounded">
                            <% if (cartItems != null && cartItems.size() > 0) {%>
                            <% for (PaymentModel cartItem : cartItems) {%>

                            <div class="row item">
                                <div class="col-4 align-self-center" style="width: 90px; height: 70px;margin-bottom: 15px;"><img class="img-fluid" src="RetrieveImageServlet?imageID=<%= cartItem.getProduct().getProductId()%>"></div>
                                <div class="col-8" style="margin-left: 10px;">
                                    <div class="row text-muted">
                                        <%= cartItem.getProduct().getProductName()%>
                                    </div>
                                    <div class="number" style="margin-left: 73px;">
                                        <%= cartItem.getCartQuantity()%>
                                    </div>

                                    <% double originalPrice = cartItem.getProduct().getProductPrice(); %>

                                    <!-- possible cause error -->

                                    <% //double price = (Double) session.getAttribute("productPrice");
                                        if (dlist != null && dlist.size() > 0) {%>
                                    <%if (dlist.get(cartItem.getProduct().getProductId()) != null) {%>
                                    <div class="row">
                                        <div class="col-6"><del style="margin-left: -10px;">RM <%= originalPrice%></del></div>
                                        <div class="col-6">RM <%=dlist.get(cartItem.getProduct().getProductId())%></div>
                                    </div>
                                    <%} else {%>
                                    <div class="row">RM <%=originalPrice%>
                                    </div>
                                    <%}%>
                                    <%
                                    } else {
                                    %>

                                    <div class="row">RM <%=originalPrice%>
                                    </div>

                                    <%
                                        }
                                    %>



                                </div>
                            </div>
                            <% }%>
                            <% }%>

                            <div class="amount" style="border-top: 1px solid hsl(0, 0%, 90%);">

                                <div class="tax" style="margin-top: 10px;">
                                    <span>Tax</span> <span>RM <span id="tax">
                                            <%= session.getAttribute("tax")%>
                                        </span></span>
                                </div>

                                <div class="delivery">
                                    <% if (deliveryFee == 0.0) { %>
                                    <span>Delivery Fee</span> <span id="delivery">Free</span>
                                    <% } else {%>
                                    <span>Delivery Fee</span> <span>RM <span id="delivery">
                                            <%= deliveryFee%>
                                        </span></span>
                                        <% }%>
                                </div>

                                <div class="total">
                                    

                                    <span>Total</span> <span>RM <span id="total"><%= roundedTotal%></span></span>


                                </div>

                            </div>

                        </div>
                    </div>

                </div>

            </section>
        </div>

    </main>

</body>

</html>