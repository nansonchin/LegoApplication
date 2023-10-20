<%-- 
    Document   : index.jsp
    Created on : Apr 20, 2023, 12:55:39 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/Home/view/Header.jsp"%>
<%@page import="Model.PageModel.*"%>
<%@page import="Model.PageModel.HomeModel.DiscountProduct"%>
<%@page import="Model.*"%>
<%@page import="Utility.*"%>
<%@page import="Controller.*"%>
<%@page import="java.util.*"%>
<%--<jsp:include page="/HomeServlet"></jsp:include>--%>
<jsp:useBean id="homeModel" class="Model.PageModel.HomeModel" scope="request"></jsp:useBean>
<%Date currDate = new Date();%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/GUI_Assignment/Home/css/HomeStyle.css"/>
    </head>
    <body>
        <%--discount/ topsale/ check rating/ view more--%>
        <div class="myrow">
            <div class="box box1">
                <div class="title-box">
                    <h4>HOT SALES</h4>
                </div>
                <button class="hover-box" data-bs-toggle="modal" data-bs-target="#HOT-SALES">
                    <h4>Click Me !!</h4>
                </button>
            </div>
            <div class="box box2">
                <div class="title-box">
                    <h4>DISCOUNT</h4>
                </div>
                <div class="hover-box" data-bs-toggle="modal" data-bs-target="#DISCOUNT">
                    <h4>Click Me !!</h4>
                </div>
            </div>
        </div>
        <div class="myrow">
            <%if(member != null && member.getMemberName() != null){%>
            <%
                if (homeModel.getProductHaventRate() != null && homeModel.getProductHaventRate().size() > 0) {
            %>
                <div class="box box3">
                    <div class="title-box">
                        <h4>ORDERS HAVEN'T RATE</h4>
                    </div>
                    <div class="hover-box" data-bs-toggle="modal" data-bs-target="#CHECK-RATING">
                        <h4>Click Me !!</h4>
                    </div>
                </div>
            <%}%>
            <%}%>
            <div class="box box4">
                <div class="title-box">
                    <h4>VIEW MORE PRODUCT &nbsp; <i class="bi bi-arrow-right-square"></i></h4>
                </div>
                <a class="hover-box" href="/GUI_Assignment/productMenuServlet">
                    <h4>VIEW MORE</h4>
                </a>
            </div>
        </div>
        <%--hot sales modal--%>
        <div class="modal fade" id="HOT-SALES" tabindex="-1" aria-labelledby="HOT-SALES" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><i class="bi bi-fire"></i> &nbsp; HOT SALES ~ TOP 10</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-hover">
                            <thead class="text-center">
                                <tr>
                                    <th scope="col"><i class="bi bi-trophy"></i> &nbsp; Rank</th>
                                    <th scope="col">Product Image</th>
                                    <th scope="col">Product Details</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if (homeModel != null && homeModel.getHotSalesList().size() > 0) {
                                        //if exist printout
                                        int i = 1;
                                        for (HomeModel.HotSales hs : homeModel.getHotSalesList()) {
                                %>
                                            <tr class="table-active">
                                                <th scope="row" class="text-center align-middle">
                                                    <h3><%=i%></h3>
                                                </th>
                                                <td class="align-middle">
                                                    <img src="/GUI_Assignment/RetrieveImageServlet?imageID=<%=hs.getProduct().getImageTable().getImageId()%>" alt="Image not found" width="120px" height="120px"/>
                                                </td>
                                                <td class="align-middle">
                                                    <div>
                                                        <h5>
                                                            <%=hs.getProduct().getProductName()%>
                                                            <%--must occurs before or equal currDate --%>
                                                            <%if(hs.getDiscount() != null && hs.getDiscount().getDiscountStartDate().compareTo(currDate) <= 0){%>
                                                             &nbsp; <small><span class="badge bg-danger align-baseline"><%=hs.getDiscount().getDiscountPercentage()%> %</span></small>
                                                            <%}%>
                                                        </h5>
                                                        <%--check discount--%>
                                                        <%if (hs.getDiscount() == null || hs.getDiscount().getDiscountStartDate().compareTo(currDate) > 0) {%>
                                                            RM <%=hs.getProduct().getProductPrice()%><br>
                                                        <%}else{%>
                                                            <span class="fs-5">RM</span> <small><span class="text-decoration-line-through fs-6"><%=hs.getProduct().getProductPrice()%></span></small> &nbsp; <span class="fs-5 text-success"><%=DiscountController.getPrice(hs.getProduct().getProductPrice(), hs.getDiscount().getDiscountPercentage())%></span><br>
                                                        <%}%>
                                                        <i class="bi bi-star-fill"></i> &nbsp; <%=hs.getRating() == -1 ? "NO RATING" : hs.getRating()%> <br>
                                                        <p>Already sold out &nbsp; <span class="badge rounded-pill bg-primary"><%=hs.getProductSold()%></span></p>
                                                        <div class="text-end w-100">
                                                            <a class="btn btn-warning" href="/GUI_Assignment/viewProductServlet?id=<%=hs.getProduct().getProductId()%>&avgRating=<%=hs.getRating() == -1 ? 0.0 : hs.getRating()%>"> BUY </a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                <%
                                            i++;
                                        }
                                    }else{
                                %>
                                        <tr>
                                            <th colspan="3" class="text-center text-info"><i class="bi bi-info-circle"></i> &nbsp; Ranking Havent Open Yet</th>
                                        </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <%--DISCOUNT modal--%>
        <div class="modal fade" id="DISCOUNT" tabindex="-1" aria-labelledby="DISCOUNT" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><i class="bi bi-tags-fill"></i> &nbsp; PRODUCT ON DISCOUNT</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-hover">
                            <thead class="text-center">
                                <tr>
                                    <th scope="col">Date</th>
                                    <th scope="col">Product Image</th>
                                    <th scope="col">Product Details</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if(homeModel != null && homeModel.getDiscountList().size() > 0){
                                        for(HomeModel.DiscountProduct dp : homeModel.getDiscountList()){
                                %>
                                            <tr class="table-active">
                                                <th scope="row" class="text-center align-middle">
                                                    <small><%=Converter.convertDateToSimpleFormat(dp.getDiscount().getDiscountStartDate())%></small><br>
                                                    ~<br>
                                                    <small><%=Converter.convertDateToSimpleFormat(dp.getDiscount().getDiscountEndDate())%></small>
                                                </th>
                                                <td class="align-middle">
                                                    <img src="/GUI_Assignment/RetrieveImageServlet?imageID=<%=dp.getProduct().getImageTable().getImageId()%>" alt="Image not found" width="120px" height="120px"/>
                                                </td>
                                                <td class="align-middle">
                                                    <div>
                                                        <h5><%=dp.getProduct().getProductName()%> &nbsp; <small><span class="badge bg-danger align-baseline"><%=dp.getDiscount().getDiscountPercentage()%> %</span></small></h5>
                                                        <span class="fs-5">RM</span> <small><span class="text-decoration-line-through fs-6"><%=dp.getProduct().getProductPrice()%></span></small> &nbsp; <span class="fs-5 text-success"><%=DiscountController.getPrice(dp.getProduct().getProductPrice(), dp.getDiscount().getDiscountPercentage())%></span><br>
                                                        <i class="bi bi-star-fill"></i> &nbsp; <%=dp.getRating() == -1 ? "NO RATING" : dp.getRating()%> <br>
                                                        <div class="text-end w-100 mt-2 mb-2">
                                                            <%--havent start yet--%>
                                                            <%if(dp.getDiscount().getDiscountStartDate().compareTo(currDate) > 0){%>
                                                                <button class="btn btn-info disabled">COMING SOON</button>
                                                            <%}else{%>
                                                                <a class="btn btn-warning" href="/GUI_Assignment/viewProductServlet?id=<%=dp.getProduct().getProductId()%>&avgRating=<%=dp.getRating() == -1 ? 0.0 : dp.getRating()%>"> BUY </a>
                                                            <%}%>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                <%
                                        }
                                    }else{
                                %>
                                        <tr>
                                            <th colspan="3" class="text-center text-info"><i class="bi bi-info-circle"></i> &nbsp; No Discount Yet</th>
                                        </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <%
            if(member != null && member.getMemberName() != null){
                if(homeModel.getProductHaventRate() != null && homeModel.getProductHaventRate().size() > 0){
        %>
                    <div class="modal fade" id="CHECK-RATING" tabindex="-1" aria-labelledby="CHECK-RATING" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="exampleModalLabel"><i class="bi bi-pencil-square"></i> &nbsp; ORDERS HAVENT RATE</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <table class="table table-hover text-center">
                                        <thead>
                                            <tr class="text-center">
                                                <th scope="col">No</th>
                                                <th scope="col">Column heading</th>
                                                <th scope="col">Column heading</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <%
                                            if(homeModel.getProductHaventRate() != null && homeModel.getProductHaventRate().size() > 0){
                                                int i = 1;
                                                for(HomeModel.ProductNoRate pnr : homeModel.getProductHaventRate()){
                                        %>
                                                <tr class="table-active">
                                                    <th scope="row" class="align-middle"><%=i%></th>
                                                    <td class="align-middle">
                                                        <img src="/GUI_Assignment/RetrieveImageServlet?imageID=<%=pnr.getProduct().getImageTable().getImageId()%>" alt="Image not found" width="120px" height="120px"/>
                                                    </td>
                                                    <td class="text-start">
                                                        <div class="align-middle">
                                                            <h5><%=pnr.getProduct().getProductName()%></h5>
                                                            Quantity Buying : <%=pnr.getQuantityOrders()%>
                                                            <br>
                                                            Total Price : RM <%=pnr.getPrice()%>
                                                            <br>
                                                            Orders Date : <%=Converter.convertDateToSimpleFormat(pnr.getOrdersDate())%>
                                                        </div>
                                                        <div class="text-end w-100 mt-2 mb-2">
                                                            <a class="btn btn-warning" href="/GUI_Assignment/RateReviewServlet?productId=<%=pnr.getProduct().getProductId()%>&orderId=<%=pnr.getOrdersId()%>"> RATE NOW </a>
                                                        </div>
                                                    </td>
                                                </tr>
                                        
                                        <%
                                                    i++;
                                                }
                                            }
                                        %>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
        <%
                }
            }
        %>
        <%@include file="/Home/view/Footer.jsp"%>
        <script>
            $(function(){
                $("#home").addClass("active");
            });
        </script>
    </body>
    </body>
</html>