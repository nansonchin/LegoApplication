<%-- 
    Document   : salesRecord
    Created on : Apr 28, 2023, 2:56:23 AM
    Author     : erika
--%>

<%@page import="java.util.*, Model.*, Model.PageModel.ViewSaleRecordModel, Utility.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    ViewSaleRecordModel salesRecord = (ViewSaleRecordModel) request.getAttribute("salesRecord");

%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/GUI_Assignment/salesRecord/salesRecord.css" rel="stylesheet" />
    <link href="/GUI_Assignment/css/bootstrap.css" rel="stylesheet" />
    <!--change title and favicon-->
    <title>${companyName}</title>
    <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    <style>
        #searchForm table tr td > input, #searchForm table tr td > select{
            width: 100%;
            display: inline-block;
        }

        #searchForm table tr td > input , #searchForm table tr td > label, #searchForm table tr td > select{
            padding: 10px;
        }
    </style>
</head>

<br><br>
<form action="/GUI_Assignment/salesRecord" method="POST">
    <div class="container">
        <h2>Sales Record</h2>
        <br><br>
        <form method="GET" action="/GUI_Assignment/salesRecord">
            <input type="hidden" name="productId" value="<%=request.getParameter("productId")%>" readonly>
            <table class="table table-borderless">
                <tr>
                    <td>Member ID </td>
                    <td><input type="number" class="form-control" name="memberID" value="<%=request.getParameter("memberID")%>"></td>
                    <td>State </td>
                    <td><input type="text" name="state" class="form-control" value="<%=request.getParameter("state") == null ? "" : request.getParameter("state")%>"></td>
                </tr>
                <tr>
                    <td>City </td>
                    <td><input type="text" name="city" class="form-control" value="<%=request.getParameter("city") == null ? "" : request.getParameter("city")%>"></td>
                    <td>Postcode </td>
                    <td><input type="text" name="postcode" class="form-control" value="<%=request.getParameter("postcode") == null ? "" : request.getParameter("postcode")%>"></td>
                </tr>
                <tr class="text-end">
                    <td colspan="4">
                        <br>
                        <a class="btn btn-secondary mx-2" href="/GUI_Assignment/salesRecordMain">Back To Main</a>
                        <a class="btn btn-secondary mx-2" href="/GUI_Assignment/salesRecord?productId=<%=salesRecord.getProduct().getProductId()%>">Clear Search</a>
                        <input type="submit" value="Search" class="btn btn-primary">
                    </td>
                </tr>
            </table>
        </form>
        <br>
        <ul class="responsive-table">
            <li class="table-header">
                <div class="col col-1">Product Name</div>
                <div class="col col-2">Member ID</div>
                <div class="col col-3">Quantity</div>
                <div class="col col-4">Total Price</div>
                <div class="col col-5">Member Address</div>
            </li>
            <% if (salesRecord != null) {%>
                <%ArrayList<ViewSaleRecordModel.MemberDetail> mdList = salesRecord.getMdList();%>
                <%if (mdList != null && mdList.size() > 0) {%>
                    <% for (ViewSaleRecordModel.MemberDetail md : mdList) {%>
                        <li class="table-row">
                            <div class="col col-1" data-label="productName"><%=salesRecord.getProduct().getProductName()%></div>
                            <div class="col col-2" data-label="MemberID"><%=md.getMember().getMemberId()%></div>
                            <div class="col col-3" data-label="quantity"><%=md.getOrderlist().getOrdersQuantity()%></div>
                            <div class="col col-4" data-label="subprice"><%=md.getOrderlist().getOrdersSubprice()%></div>
                            <div class="col col-5" data-label="MemAddress"><%=md == null ? "" : md.getAddress().getAddressNo()%> 
                                <%=md == null ? "" : md.getAddress().getAddressStreet()%> 
                                <%=md == null ? "" : md.getAddress().getAddressPostcode()%> 
                                <%=md == null ? "" : md.getAddress().getAddressState()%> 
                                <%=md == null ? "" : md.getAddress().getAddressCity()%></div>
                        </li>
                    <% }%>
                <% }else{%>
                    <li class="table-row">
                        <div class="col col-12">No Record Found</div>
                    </li>
                <%}%>
            <% } else {%>
            <li class="table-row">
                <div class="col col-12">No Record Found</div>
            </li>
            <%}%>
        </ul>
    </div>
</form>