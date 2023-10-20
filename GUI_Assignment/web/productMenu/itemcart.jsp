<%-- 
    Document   : itemcart
    Created on : Mar 20, 2023, 10:22:00 PM
    Author     : guoc7
--%>

<%@page import="Controller.DiscountController"%>
<%@page import="Model.Discount"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Product"%>
<%@page import="Model.Cart"%>
<%@page import="Model.Cartlist"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/Home/view/Header.jsp"%>
<jsp:useBean id="discountController" class="Controller.DiscountController" scope="application" />
<jsp:useBean id="db" class="DataAccess.DBTable" scope="page" />
<!DOCTYPE html>
<html>
    <head>
        <link href="css/menulist.css" rel="stylesheet" type="text/css"/>
        <link href="css/productcontent.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="css/itemcart.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"" />
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
        <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-element-bundle.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Item Cart</title>
    </head>
    <body>
        <div class="cart-item-container">
            <div class="container">
                <div class="cart-item-table">
                    <div class="row item-cart-line pb-5">
                        <div class="col col-lg-5 col-xl-5 col-md-5">
                            <h5 class="pink-lego-text item-cart-desc-text">Description</h5>
                        </div>
                        <div class="col col-lg-2 col-xl-2 col-md-2 cart-item-lego-item-container">
                            <h5 class="pink-lego-text">Price(RM/Unit)</h5>
                        </div>
                        <div class="col col-lg-3 col-xl-3 col-md-3 cart-item-lego-item-container">
                            <h5 class="pink-lego-text">Quantity</h5>
                        </div>
                        <div class="col col-lg-2 col-xl-2 col-md-2 cart-item-lego-item-container">
                            <h5 class="pink-lego-text">Total (RM)</h5>
                        </div>
                    </div>
                    <%
                        ArrayList<Cartlist> cartList = (ArrayList<Cartlist>) request.getAttribute("cartList");
                        ArrayList<Product> product = (ArrayList<Product>) request.getAttribute("productList");
                        double subTotal = 0;
                        double shippingTax = 0.00;
                        boolean disable = false;
                    %>
                    <% if (product == null || product.size() == 0) {
                            shippingTax = 0.00;
                    %>
                    <h3 class="p-3"><i class="bi bi-basket3-fill"></i> No Product Currently in Your Cart List Yet</h3> 
                    <%} else {
                        //haveproduct
                        shippingTax = 25.00;
                    %>

                    <%
                        for (int i = 0; i < product.size(); i++) {
                    %> 
                    <form action="/GUI_Assignment/updateCartListQuantityServlet" method="POST">
                        <!--//item window-->
                        <div class="item-window hidden" id="<%=product.get(i).getProductId()%>">
                            <div class="item-window-container">
                                <div class="container">
                                    <div class="itme-window-field">
                                        <h3 class="item-window-title pink-lego-text">LEGO</h3>
                                        <p class="item-window-text">Update the Quantity For your Product</p>
                                        <p class="item-window-productName item-window-text" >Name: <%=product.get(i).getProductName()%></p>
                                        <div class="item-window-productQuantity">
                                            <i class="product-content-btn bi bi-dash-square-fill" id="decrement-product-<%=product.get(i).getProductId()%>"></i>
                                            <%for (Cartlist cl : cartList) {%>
                                            <%if (cl.getProduct().getProductId() == product.get(i).getProductId()) {%>
                                            <input type="text" id="cart-quantity-window-<%=product.get(i).getProductId()%>" name="quantity" maxlength="4" size="4" value="<%=cl.getCartQuantity()%>"><br><br>
                                            <%}%>
                                            <%}%>

                                            <i class="product-content-btn bi bi-plus-square-fill" id="increment-product-<%=product.get(i).getProductId()%>" ></i>
                                        </div>

                                        <div class="cart-item-purchasenow-container">
                                            <div class="cart-item-purchase m-3">
                                                <button type="button" class=" p-3 btn btn-light back-item-cart" onclick="closeWindow('<%=product.get(i).getProductId()%>')"><i class="bi bi-arrow-left-square-fill"></i> Back</button>
                                                <button type="submit" class=" p-3 btn btn-danger" name="productId" value="<%=product.get(i).getProductId()%>"><i class="bi bi-coin"></i> Update Now</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!--//deleteCross Button-->

                    <form action="/GUI_Assignment/cartListServlet" method="POST">
                        <div class="cart-cross-container">
                            <div class="cart-cross">
                                <button type="submit" class=" cart-cross-icon btn btn-danger"  name="deleteProuctId" value="<%=product.get(i).getProductId()%>" ><i class=" bi bi-bag-x-fill"></i></button>
                            </div>
                        </div>
                        <%if (product.get(i).getProductActive() == '0') {
                                disable = true;
                        %>
                        <!--//ProductActive == false -->
                        <div class="row mt-5 py-3 item-cart-line">
                            <div style="color:red; font-size:20px;">The Product is Currently Unavailable for Now. Sorry For the Inconvenience</div>
                            <div class="col col-lg-5 col-xl-5 col-md-5  product-cartList-false">
                                <div class="cart-item-lego-item">
                                    <div class="row ">
                                        <div class="col col-lg-6 col-xl-6 col-md-6">
                                            <img src="/GUI_Assignment/RetrieveImageServlet?imageID=<%=product.get(i).getImageTable().getImageId()%>" class="cart-item-lego-img"/>
                                        </div>
                                        <div class="col col-lg-6 col-xl-6 col-md-6 cart-item-lego-item-container">
                                            <div class="item-cart-center">
                                                <h5 class="white-text"><span class="pink-lego-text"><%=product.get(i).getProductName()%></span> </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col col-lg-2 col-xl-2 col-md-2 cart-item-lego-item-container product-cartList-false">
                                <h3 class="pink-lego-text"><%=product.get(i).getProductPrice()%></h3>
                            </div>
                            <div class="col col-lg-3 col-xl-3 col-md-3 cart-item-lego-item-container product-cartList-false">
                                <div class=" d-flex">
                                    <i class="product-content-btn bi bi-dash-square-fill" disabled></i>
                                    <%double quantityAmountPrice = 0;%>
                                    <%for (Cartlist cl : cartList) {%>
                                    <%
                                        if (cl.getProduct().getProductId() == product.get(i).getProductId()) {
                                            quantityAmountPrice = Math.round(cl.getCartQuantity() * product.get(i).getProductPrice() * 100) / 100.0;
                                    %>
                                    <input type="text" id="cart-quantity" name="pin" maxlength="4" size="4" value="<%=cl.getCartQuantity()%>" readonly><br><br>
                                    <%}%>
                                    <%}%>
                                    <i class="product-content-btn bi bi-plus-square-fill" disabled></i>
                                </div>
                            </div>
                            <%
                                //double quantityAmountPrice = cartList.get(i).getCartQuantity() * product.get(i).getProductPrice();
%>
                            <div class="col col-lg-2 col-xl-2 col-md-2 cart-item-lego-item-container product-cartList-false">
                                <h3 class="pink-lego-text"><%=quantityAmountPrice%></h3>
                            </div>
                        </div>

                        <%} else {%>

                        <!--//ItemCartList--> 
                        <div class="row mt-5 py-3 item-cart-line ">
                            <div class="col col-lg-5 col-xl-5 col-md-5">
                                <div class="cart-item-lego-item">
                                    <div class="row ">
                                        <div class="col col-lg-6 col-xl-6 col-md-6">
                                            <img src="RetrieveImageServlet?imageID=<%=product.get(i).getImageTable().getImageId()%>" class="cart-item-lego-img"/>
                                        </div>
                                        <div class="col col-lg-6 col-xl-6 col-md-6 cart-item-lego-item-container">
                                            <div class="item-cart-center">
                                                <h5 class="white-text"><span class="pink-lego-text"><%=product.get(i).getProductName()%></span> </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col col-lg-2 col-xl-2 col-md-2 cart-item-lego-item-container">
                                <%Discount discount = discountController.getDiscount(db, product.get(i).getProductId());%>
                                <%if (discount != null) {
                                        int discountPercent = discount.getDiscountPercentage();
                                        double discountPrice = DiscountController.getPrice(product.get(i).getProductPrice(), discountPercent);
                                %>    
                                <h5 class="pink-lego-text cart-discount p-2"><%=product.get(i).getProductPrice()%></h5>
                                <%product.get(i).setProductPrice(discountPrice);%>
                                <div></div>
                                <h3 class="pink-lego-text"><%=product.get(i).getProductPrice()%></h3>

                                <%} else {%>
                                <h3 class="pink-lego-text"><%=product.get(i).getProductPrice()%></h3>
                                <%}%>
                            </div>
                            <div class="col col-lg-3 col-xl-3 col-md-3 cart-item-lego-item-container">
                                <div class=" d-flex">
                                    <i class="product-content-btn bi bi-dash-square-fill" onclick="openWindow('<%=product.get(i).getProductId()%>')"></i>
                                    <%double quantityAmountPrice = 0;%>
                                    <%for (Cartlist cl : cartList) {%>
                                    <%
                                        if (cl.getProduct().getProductId() == product.get(i).getProductId()) {
                                            quantityAmountPrice = Math.round(cl.getCartQuantity() * product.get(i).getProductPrice() * 100) / 100.0;
                                    %>
                                    <input type="text" id="cart-quantity" name="pin" maxlength="4" size="4" value="<%=cl.getCartQuantity()%>" readonly><br><br>
                                    <%}%>
                                    <%}%>

                                    <i class="product-content-btn bi bi-plus-square-fill" onclick="openWindow('<%=product.get(i).getProductId()%>')"></i>
                                </div>
                            </div>
                            <% //double quantityAmountPrice = Math.round(cartList.get(i).getCartQuantity() * product.get(i).getProductPrice() * 100) / 100.0;
                                subTotal += quantityAmountPrice;
                            %>
                            <div class="col col-lg-2 col-xl-2 col-md-2 cart-item-lego-item-container">
                                <h3 class="pink-lego-text"><%=quantityAmountPrice%></h3>
                            </div>
                        </div>

                    </form>
                    <!--******NoproductClosetag & ProductActive Close Tag && ForLoop Close Tag*****-->
                    <%}
                            }
                        }%>
                </div>
                <div class="cart-item-total-container mt-5">
                    <div class="cart-item-total mt-3">
                        <h5 class="cart-item-total-sub-line p-2"><span class="pink-lego-text">Sub Total :</span> RM <%=subTotal%></h3>
                            <%if (subTotal == 0.00) {%>
                            <h5 class="cart-item-total-sub-line p-2"><span class="pink-lego-text">Shipping :</span> RM 0.00</h3>
                                <h3 class="cart-item-total-sub-line p-2"><span class="pink-lego-text">Total :</span> RM 0.00</h3>

                                <%} else {%>
                                <h5 class="cart-item-total-sub-line p-2"><span class="pink-lego-text">Shipping :</span> RM <%=subTotal >= 200 ? "0.00" : shippingTax%></h3>
                                    <h3 class="cart-item-total-sub-line p-2"><span class="pink-lego-text">Total :</span> RM <%=subTotal >= 200 ? subTotal : (subTotal + shippingTax)%></h3>
                                    <%}%>    
                                    </div>
                                    </div>
                                    <div class="cart-item-purchasenow-container">
                                        <div class="cart-item-purchase m-3">
                                            <a href="/GUI_Assignment/HomeServlet" class=" p-3 btn btn-light"><i class="bi bi-arrow-left-square-fill"></i> Back</a>
                                            <%if (disable == true) {%>
                                            <a href="/GUI_Assignment/PaymentServlet" class=" p-3 btn btn-danger disabled"><i class="bi bi-coin"></i> Purchase Now</a>
                                            <%} else {%>
                                            <%if (subTotal == 0.0) {%>
                                            <a href="/GUI_Assignment/PaymentServlet" class=" p-3 btn btn-danger disabled"  ><i class="bi bi-coin"></i> Purchase Now</a>
                                            <%} else {%>
                                            <a href="/GUI_Assignment/PaymentServlet" class=" p-3 btn btn-danger" ><i class="bi bi-coin"></i> Purchase Now</a>

                                            <%}%>
                                            <%}%>                                        </div>
                                    </div>
                                    </div>
                                    </div>
                                    </body>
                                    <%@include file="/Home/view/Footer.jsp"%>
                                    <script>
                                        function openWindow(id) {
                                            var target = document.getElementById(id);
                                            target.classList.remove('hidden');
                                            target.classList.add('show');

                                            var increase = document.getElementById("increment-product-" + id);
                                            var decrease = document.getElementById("decrement-product-" + id);
                                            var quantity = document.getElementById("cart-quantity-window-" + id);

                                            increase.addEventListener("click", function () {
                                                quantity.value = parseInt(quantity.value) + 1;
                                                if (quantity.value > 1) {
                                                    decrease.disabled = false;
                                                } else {
                                                    decrease.disabled = true;
                                                }
                                            });

                                            decrease.addEventListener("click", function () {
                                                if (quantity.value > 1) {
                                                    quantity.value = parseInt(quantity.value) - 1;
                                                }
                                                if (quantity.value === 1) {
                                                    decrease.disabled = true;
                                                }
                                            });
                                        }

                                        function closeWindow(id) {
                                            var target = document.getElementById(id);
                                            target.classList.remove('show');
                                            target.classList.add('hidden');
                                        }

                                    </script>
                                    </html>

