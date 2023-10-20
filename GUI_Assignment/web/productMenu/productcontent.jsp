<%-- 
    Document   : productcontent
    Created on : Mar 17, 2023, 7:41:06 PM
    Author     : guoc7
--%>

<%@page import="Controller.DiscountController"%>
<%@page import="Model.Discount"%>
<%@page import="java.util.HashMap"%>
<%@page import="Model.Member"%>
<%@page import="Model.RateReview"%>
<%@page import="Model.Cart"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/Home/view/Header.jsp"%>
<jsp:useBean id="discountController" class="Controller.DiscountController" scope="application" />
<jsp:useBean id="db" class="DataAccess.DBTable" scope="page" />
<!DOCTYPE html>
<html>
    <head>
        <title>Lego</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="css/productcontent.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"" />
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
        <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-element-bundle.min.js"></script>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="product-content-container">
            <div class="container">
                <div class="row">
                    <div class="col-12 col-lg-12 col-md-12 col-xl-12">
                        <%Product view = (Product) request.getAttribute("productDetail");%>
                        <div class="product-content-image-container">
                            <!--<swiper-container class="mySwiper" pagination="true" pagination-clickable="true" navigation="true" space-between="30"
                            centered-slides="true" autoplay-delay="2500" autoplay-disable-on-interaction="false">-->
                            <img src="RetrieveImageServlet?imageID=<%=view.getImageTable().getImageId()%>" class="product-content-img"/>
                            <!-- </swiper-container>-->
                            <div class="product-content-desc-container">

                                <div class="container">
                                    <h3 class="product-content-text product-content-text-center"><span class="product-content-span"><%=view.getProductName()%></span> </h3>
                                    <h4 class="product-content-text mt-3"><span class="product-content-span">Desc:</span> <%=view.getProductDesc()%></h4>

                                    <%Discount discount = discountController.getDiscount(db, view.getProductId());%>
                                    <%if (discount != null) {
                                            int discountPercent = discount.getDiscountPercentage();
                                            double discountPrice = DiscountController.getPrice(view.getProductPrice(), discountPercent);
                                    %>                                    
                                    <h3 class="product-content-discount product-content-text  "><span class="product-content-span">RM<%=view.getProductPrice()%> </span> </h3>
                                    <h3 class="product-content-text "><span class="product-content-span">RM<%=discountPrice%> </span> </h3>

                                    <%} else {%>
                                    <h3 class="product-content-text "><span class="product-content-span">RM<%=view.getProductPrice()%> </span> </h3>
                                    <%}%>
                                    <% double avgRating = Double.parseDouble(request.getParameter("avgRating"));
                                        if (avgRating == 0.0) {%>
                                    <p><span class="product-content-rating product-content-span"><i class="bi bi-star"></i>  RATING:</span> <span class="product-content-span product-content-rating-number-size"> No Rate Review Yet</span>/10</p>
                                    <%} else {%>
                                    <p><span class="product-content-rating product-content-span"><i class="bi bi-star"></i>  RATING:</span> <span class="product-content-span product-content-rating-number-size"> <%=request.getParameter("avgRating")%></span>/10</p>
                                    <%}%>
                                    <form action="addToCartPerMore" method="POST">
                                        <input type="hidden" name="hrefId" value="<%=view.getProductId()%>"/>
                                        <input type="hidden" name="hrefRating" value="<%=request.getParameter("avgRating")%>"/>
                                        <div class="product-content-quantityfield-container">
                                            <i class="product-content-btn bi bi-dash-square-fill" id="decrement-product" ></i>
                                            <input type="number" class="product-content-form-field" name="quantity" id="quantity" value="1" readonly>
                                            <i class="product-content-btn bi bi-plus-square-fill" id="increment-product"></i>

                                        </div>
                                        <div class="product-content-addcart-btn-container">
                                            <button type="submit" name="productId" value="<%=view.getProductId()%>" class="product-content-btn-cart btn btn-warning white-text"><i class="bi bi-bag-check"></i> To Cart</button>
                                        </div>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-12 col-lg-12 col-xl-12 col-md-12">
                    <div class="product-content-display-comment-container">
                        <div class="container">
                            <div class="product-content-customer-comment-container">
                                <h3 class="product-content-comment-title">COMMENT</h3>
                            </div>
                            <div class="product-content-customer-detail-container">
                                <% ArrayList<RateReview> rateList = (ArrayList<RateReview>) request.getAttribute("customerReviewProductDetails");%>
                                <%ArrayList<Member> memberList = (ArrayList<Member>) request.getAttribute("memberNameFound");%>
                                <%if (rateList == null || rateList.size() <= 0) {%>
                                <h4 class="p-5 m-5"><i class="bi bi-clipboard2-pulse"></i>Theres are no any comments yet</h4>
                                <%} else {%>
                                <%for (int h = 0; h < rateList.size(); h++) {%>
                                <div class="row m-3">
                                    <div class="col-12 col-lg-2 col-xl-2 col-md-2">
                                        
                                    </div>
                                    <div class="col-12 col-lg-8 col-md-8 col-xl-8">
                                        <%if (memberList == null || memberList.size() <= 0) {%>
                                        Member is Empty
                                        <%} else {%>
                                        <h5 class="product-content-customer-name"><%=memberList.get(h).getMemberName()%></h5>
                                        <%}%>
                                        <p class="white-text"><i class="bi bi-star"></i> RATING <%=rateList.get(h).getReviewRating()%> <span class="menu-list-ratingTotal">/10</span></p>
                                        <div class="product-content-review-container">
                                            <p class="product-content-comment"> <%=rateList.get(h).getReviewText()%></p>
                                        </div>
                                    </div>  
                                </div>
                                <%}
                                    }%>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="product-content-something-like-container mt-5">
                <h3 class="product-content-something-like-title">SOMETHING YOU MAY LIKE</h3>
                <div class="row">
                    <div class="col-12 col-xl-12 col-md-12 col-lg-12">
                        <div class="row">
                            <%  ArrayList<Product> banner = (ArrayList<Product>) request.getAttribute("contentBanner");
                                HashMap<Integer, Double> bannerRate = (HashMap<Integer, Double>) request.getAttribute("productContentHashBanner");
                                Collections.shuffle(banner);
                                int bannerminimunIndex = Math.min(banner.size(), 4);
                                if (banner == null || banner.size() <= 0) {
                            %><p>Banner is null</p>
                            <%} else {%>
                            <%    for (int y = 0; y < bannerminimunIndex; y++) {%>
                            <div class="col-3 col-xl-3 col-md-3 col-lg-3">
                                <div class="product-content-something-recommend-container">
                                    <img src="RetrieveImageServlet?imageID=<%=banner.get(y).getImageTable().getImageId()%>" class="product-content-something-recommend-image"/>
                                    <p><span class="product-content-span product-content-recomment-span-size"><%=banner.get(y).getProductName()%></span> </p>
                                    <%Discount discountSomething = discountController.getDiscount(db, banner.get(y).getProductId());%>
                                    <%if (discountSomething != null) {
                                            int discountPercent = discountSomething.getDiscountPercentage();
                                            double discountPrice = DiscountController.getPrice( banner.get(y).getProductPrice(), discountPercent);
                                    %>   
                                    <p>RM : <span class="product-content-span product-content-recomment-span-size product-content-discount"><%=banner.get(y).getProductPrice()%></span> </label>
                                    <label>RM : <span class="product-content-span product-content-recomment-span-size"><%=discountPrice%></span> </p>
                                    <%} else {%>
                                    <p>RM : <span class="product-content-span product-content-recomment-span-size"><%=banner.get(y).getProductPrice()%></span> </p>
                                    <%}%>
                                    <%if (bannerRate.get(banner.get(y).getProductId()) * 1.0 == 0) {%>
                                    <p>Rating : <span class="product-content-span product-content-recomment-span-size"> No Rate Review Yet</span> </p>
                                    <%} else {%>
                                    <p>Rating : <span class="product-content-span product-content-recomment-span-size"> <%=bannerRate.get(banner.get(y).getProductId()) * 1.0%></span> </p>                                    
                                    <%}%>
                                    <div class="product-content-addcart-btn-container ">  
                                        <a href="viewProductServlet?id=<%=banner.get(y).getProductId()%>&avgRating=<%=bannerRate.get(banner.get(y).getProductId()) * 1.0 <= 0 ? 0 : bannerRate.get(banner.get(y).getProductId()) * 1.0%>" class="px-3"><button type="submit" class="btn btn-primary white-text"><i class="bi bi-eye-fill"></i> View</button></a>
                                        <form method="POST" action="addToCartPerOne" >
                                            <button type="submit" name="menu-list-one" value="<%=banner.get(y).getProductId()%>" class="px-3 btn btn-warning white-text"><i class="bi bi-bag-check"></i> To Cart</button>
                                        </form>
                                    </div>

                                </div>

                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
        </div>

    </div>
</body>
</html>
<%@include file="/Home/view/Footer.jsp"%>
<script>
    var increase = document.getElementById("increment-product");
    var decrease = document.getElementById("decrement-product");
    var totalQuantityProduct = document.getElementById("quantity");

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
</script>