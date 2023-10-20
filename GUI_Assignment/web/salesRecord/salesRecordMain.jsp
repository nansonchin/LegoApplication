<%-- 
    Document   : salesRecordMain
    Created on : May 1, 2023, 12:59:33 AM
    Author     : erika
--%>

<%@page import="java.util.List"%>
<%@page import="Model.Product"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/GUI_Assignment/salesRecord/salesRecordMain.css" rel="stylesheet" />
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>
    <br><br>
    <body>
            <div class="container">
                <h2>Sales Record Main Page</h2>
                <br>
                <ul class="responsive-table">
                    <li class="table-header">
                        <div class="col col-1">Product ID</div>
                        <div class="col col-2">Product Name</div>
                        <div class="col col-4">View</div>
                    </li>
                    <% 
                        List<Product> salesRecord = (List<Product>) request.getAttribute("SalesRecord");
                        if (salesRecord == null || salesRecord.isEmpty()) {
                    %>
                        <li class="table-row">
                            <div class="col col-12">No Record Found</div>
                        </li>
                    <% } else {
                        for (Product product : salesRecord) {
                    %>
                    <li class="table-row">
                        <div class="col col-1" data-label="productID"><%= product.getProductId()%></div>
                        <div class="col col-2" data-label="productName"><%= product.getProductName()%></div>
                        <div class="col col-4" data-label="View">
                            <a href="/GUI_Assignment/salesRecord?productId=<%=product.getProductId()%>" target=""><button class="view-button">View</button></a>
                        </div>
                    </li>
                    <% } } %>
                </ul>

            </div>
    </body>
</html>
