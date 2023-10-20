<%-- 
    Document   : menu-list
    Created on : Mar 17, 2023, 6:42:00 PM
    Author     : guoc7
--%>

<%@page import="Controller.DiscountController"%>
<%@page import="Model.Discount"%>
<%@page import="java.util.*"%>
<%@page import="Model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="discountController" class="Controller.DiscountController" scope="application" />
<jsp:useBean id="db" class="DataAccess.DBTable" scope="page" />
<%@include file="/Home/view/Header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Lego</title>
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
        <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-element-bundle.min.js"></script>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <meta charset="UTF-8">
        <link href="css/productcontent.css" rel="stylesheet" type="text/css"/>
        <link href="css/menulist.css" rel="stylesheet" type="text/css"/>
        <link href="css/itemcart.css" rel="stylesheet" type="text/css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>

    <body>
        <div class="menu-list-container bg-color">
            <div class="menu-list-slider">
                <swiper-container class="mySwiper" navigation="true">
                    <swiper-slide>
                        <img src="https://cdn.wallpapersafari.com/9/9/Lnacs4.jpg" class="menu-list-slider-image"/>
                    </swiper-slide>
                    <swiper-slide>
                        <img src="https://cdn.wallpapersafari.com/2/77/UuQnks.jpg" class="menu-list-slider-image"/>
                    </swiper-slide>

                </swiper-container>
            </div>
            <div class="bg-colortest">

            </div>
            <div class="container mt-5">      
                <div class="row">
                    <div class="col col-lg-8 col-xl-8 col-md-8">
                        <% ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("productList");
                            HashMap<Integer, Double> ratingLists = (HashMap<Integer, Double>) request.getAttribute("ratingList");

                            if (productList != null && !productList.isEmpty()) {

                        %>
                        <div class="menu-list-item-gridcontainer">
                            <%for (int i = 0; i < productList.size(); i++) {
                                    Product getProd = (Product) productList.get(i);
                                    //search through discount model to get discount datas
                                    Discount discount = discountController.getDiscount(db, productList.get(i).getProductId());
                            %>

                            <div id="product-list" class="menu-list-item-container">
                                <%--<%=productList.get(i).getImageTable().getImageId()%>--%>
                                <img src="RetrieveImageServlet?imageID=<%=productList.get(i).getImageTable().getImageId()%>" class="menu-list-item-picture"/>
                                <h3 class="white-text"><span class="pink-lego-text"><%=productList.get(i).getProductName()%></span> </h3>
                                <%if (ratingLists.get(productList.get(i).getProductId()) * 1.0 == 0) {%>
                                <p class="white-text"><i class="bi bi-star"></i> RATING No Review Yet/ <span class="menu-list-ratingTotal">10</span></p>
                                <%} else {%>
                                <p class="white-text"><i class="bi bi-star"></i> RATING <%=ratingLists.get(productList.get(i).getProductId()) * 1.0%>/ <span class="menu-list-ratingTotal">10</span></p>
                                <%}%>
                                <%if (discount != null) {
                                        int discountPercent = discount.getDiscountPercentage();
                                        double discountPrice = DiscountController.getPrice(productList.get(i).getProductPrice(), discountPercent);
                                %>

                                <p class="white-text discount">RM  <span class="menu-list-ratingTotal"><%=productList.get(i).getProductPrice()%></span></p>
                                <p class="white-text ">RM  <span class="menu-list-ratingTotal afterDiscount"><%=discountPrice%></span></p>
                                    <%} else {%>
                                <p class="white-tex ">RM  <span class="menu-list-ratingTotal "><%=productList.get(i).getProductPrice()%></span></p>
                                    <%}%>
                                <div class="menu-list-button-container d-flex justify-content-end">

                                    <a class="px-3"href="viewProductServlet?id=<%=productList.get(i).getProductId()%>&avgRating=<%=ratingLists.get(productList.get(i).getProductId()) * 1.0 <= 0 ? 0 : ratingLists.get(productList.get(i).getProductId())%>">
                                        <button type="submit" class="btn btn-primary white-text"><i class="bi bi-eye-fill"></i> View</button>  
                                    </a>
                                    <form method="POST" action="addToCartPerOne" >
                                        <button type="submit" name="menu-list-one" value="<%=getProd.getProductId()%>" class=" px-3 btn btn-warning white-text"><i class="bi bi-bag-check"></i> To Cart</button>
                                    </form>

                                </div>
                            </div>
                            <%
                                }%>


                            <%} else {
                            %> 
                            <div class ="nProduct-menu-list ">
                                <h3>Database Currently Under Maintainace. Therefore No Product is Showing Right Now
                                    Please Try Again Later</h3>
                            </div>
                            <%}%>

                        </div>

                    </div>

                    <div class="col col-lg-4 col-xl-4 col-md-4 ">
                        <div class="menu-list-side-position">

                            <div class="menu-list-side-container">
                                <p class="menu-list-hotporpular white-text"><i class="bi bi-fire"></i>Something You May Like</p>
                            </div>
                            <div class="menu-list-side-porpular-item-container ">

                                <div class="menu-list-side-each-item-container my-3">
                                    <%  ArrayList<Product> banner = (ArrayList<Product>) request.getAttribute("bannerList");
                                        HashMap<Integer, Double> bannerRate = (HashMap<Integer, Double>) request.getAttribute("ratingList");
                                        Collections.shuffle(banner);
//                                        Collections.shuffle(bannerRate);
                                        int bannerminimunIndex = Math.min(banner.size(), 3);
                                        if (banner != null && !banner.isEmpty()) {
                                            for (int y = 0; y < bannerminimunIndex; y++) {
                                                //search through discount model to get discount datas
                                                Discount discountBanner = discountController.getDiscount(db, productList.get(y).getProductId());

                                    %>
                                    <div class="row mb-3">
                                        <div class="col col-lg-4 col-xl-4 col-md-4"> 
                                            <img src="RetrieveImageServlet?imageID=<%=banner.get(y).getImageTable().getImageId()%>" class="menu-list-each-item-img"/>
                                        </div>
                                        <div class="col col-lg-8 col-xl-8 col-md-8">
                                            <div class="menu-list-side-text-container">
                                                <p class="text-white"><span class="pink-lego-text"><%=banner.get(y).getProductName()%></span></p>
                                                    <%if (discountBanner != null) {
                                                            int discountPercent = discountBanner.getDiscountPercentage();
                                                            double discountPrice = DiscountController.getPrice(banner.get(y).getProductPrice(), discountPercent);
                                                    %>
                                                <p class="text-white discount "><span class="">RM <span class="menu-list-ratingTotal pink-lego-text "><%=banner.get(y).getProductPrice()%></span></p>
                                                <p class="white-text ">RM  <span class="menu-list-ratingTotal afterDiscount"><%=discountPrice%></span></p>

                                                <%} else {%>
                                                <p class="text-white"><span class="">RM <span class="menu-list-ratingTotal pink-lego-text"><%=banner.get(y).getProductPrice()%></span></p>
                                                    <%}%>
                                                    <%
                                                        if (bannerRate.get(productList.get(y).getProductId()) * 1.0 == 0) {
                                                    %>
                                                <p class="white-text"><i class="bi bi-star"></i> RATING No Review Yet / <span class="menu-list-ratingTotal">10</span></p>
                                                <%} else {%>
                                                <p class="white-text"><i class="bi bi-star"></i> RATING : <%=bannerRate.get(productList.get(y).getProductId()) * 1.0%> / <span class="menu-list-ratingTotal">10</span></p>

                                                <%}%>
                                                <div class="d-flex justify-content-end">

                                                    <a class="px-3" href="viewProductServlet?id=<%=banner.get(y).getProductId()%>&avgRating=<%=bannerRate.get(productList.get(y).getProductId()) * 1.0 <= 0 ? 0 : bannerRate.get(productList.get(y).getProductId()) * 1.0%>">
                                                        <button type="submit" class="btn btn-primary white-text"><i class="bi bi-eye-fill"></i> View</button>   </a>


                                                    <form method="POST" action="addToCartPerOne" >
                                                        <button type="submit" name="menu-list-one" value="<%=banner.get(y).getProductId()%>" class=" px-3 btn btn-warning white-text"><i class="bi bi-bag-check"></i> To Cart</button>
                                                    </form>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <%}
                                    } else {%>
                                    <div> Banner Currently is Unavailable</div>
                                    <%}%>
                                </div>    
                            </div>


                            <div class="menu-list-side-container mt-5">
                                <p class="menu-list-hotporpular white-text"><i class="bi bi-badge-ad-fill"></i> BANNER</p>
                            </div>
                            <%  Collections.shuffle(banner);
                                int bannerPicture = Math.min(banner.size(), 6);%>
                            <div class="menu-list-side-banner">
                                <%if (banner
                                            != null && !banner.isEmpty()) {%>
                                <swiper-container class="mySwiper" pagination="true" pagination-clickable="true" navigation="true" space-between="30"
                                                  centered-slides="true" autoplay-delay="2500" autoplay-disable-on-interaction="false">

                                    <%for (int z = 0; z < bannerPicture; z++) {%>
                                    <swiper-slide>
                                        <div class="menu-list-banner-container">
                                            <a href="viewProductServlet?id=<%=banner.get(z).getProductId()%>&avgRating=<%=bannerRate.get(banner.get(z).getProductId()) * 1.0%>">
                                                <img class="menu-list-banner-img" src="RetrieveImageServlet?imageID=<%=banner.get(z).getImageTable().getImageId()%>" />                        
                                            </a>
                                        </div>
                                    </swiper-slide>
                                    <%}%>


                                </swiper-container>
                                <%} else {%>
                                <div>Slider Currently is not available</div>
                                <%}%>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>    
    </div>
    <%@include file="/Home/view/Footer.jsp"%>
    <script>
            $(function(){
                $("#menu").addClass("active");
            });
    </script>
</body>
</html>
