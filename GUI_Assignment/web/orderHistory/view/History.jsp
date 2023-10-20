<%-- 
    Document   : History
    Created on : Apr 30, 2023, 8:46:04 AM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../Home/view/Header.jsp" %>
<%@page import="java.util.*, Model.*, Model.PageModel.*, Utility.*" %>
<%
    ArrayList<OrderHistoryModel> ohmList = (ArrayList<OrderHistoryModel>) request.getAttribute("ohmList");
    final String STATUS_NORATE = "HAVENT RATE";
    final String STATUS_RATE = "RATE";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="w-100 p-5">
            <div class="text-center">
                <h3>Order History</h3>
            </div>
            <br>
            <form method="get" action="/GUI_Assignment/OrderHistoryServlet">
                <table class="table table-borderless">
                    <tr>
                        <td>Order ID </td>
                        <td><input type="number" class="form-control" name="ordersID" value="<%=request.getParameter("ordersID")%>"></td>
                        <td>Product Name </td>
                        <td><input type="text" name="productName" class="form-control" value="<%=request.getParameter("productName") == null ? "" : request.getParameter("productName")%>"></td>
                    </tr>
                    <tr>
                        <td>Order Date </td>
                        <td><input type="date" class="form-control" name="ordersDate" value="<%=request.getParameter("ordersDate")%>"></td>
                        <td>Product Rate Status </td>
                        <td>
                            <select name="rateStatus" class="form-control">
                                <option value="">Select Rating Status</option>
                                <option value="<%=STATUS_NORATE%>" <%=request.getParameter("rateStatus") == null ? "" : request.getParameter("rateStatus").equals(STATUS_NORATE) == true ? "selected" : ""%>><%=STATUS_NORATE%></option>
                                <option value="<%=STATUS_RATE%>" <%=request.getParameter("rateStatus") == null ? "" : request.getParameter("rateStatus").equals(STATUS_RATE) == true ? "selected" : ""%>><%=STATUS_RATE%></option>
                            </select>
                        </td>
                    </tr>
                    <tr class="text-end">
                        <td colspan="4">
                            <a class="btn btn-secondary mx-2" href="/GUI_Assignment/OrderHistoryServlet">Clear Search</a>
                            <input type="submit" value="Search" class="btn btn-primary">
                        </td>
                    </tr>
                </table>
            </form>
            <table class="table table-hover table-borderless">
                <tbody>
                    <%if(ohmList != null && ohmList.size() > 0){%>
                        <%for(OrderHistoryModel ohm : ohmList){%>
                            <tr>
                                <td>
                                    <div class="w-100 p-5">
                                        <table class="table align-middle">
                                            <tbody>
                                                <tr>
                                                    <td colspan="3">
                                                        <div class="mt-3 mb-3">
                                                            Order ID : &nbsp; <%=ohm.getOrders().getOrdersId()%><br>
                                                            Order Date : &nbsp; <%=Converter.convertDateToSimpleFormat(ohm.getOrders().getOrdersDate())%><br>
                                                            Order Total Price : &nbsp; RM <%=ohm.getOrders().getOrdersTtlPrice()%><br>
                                                        </div>
                                                    </td>
                                                    <td style="width: 150px;" class="text-end">
                                                        <a href="/GUI_Assignment/HistoryDetailServlet?orderID=<%=ohm.getOrders().getOrdersId()%>" class="btn btn-warning">VIEW MORE</a>
                                                    </td>
                                                </tr>
                                                <tr class="table-active">
                                                    <th>Name</th>
                                                    <th class="text-center">Qty</th>
                                                    <th class="text-center">Price</th>
                                                    <th>Rating Status</th>
                                                </tr>
                                                <!--product in orders-->
                                                <%for(OrderHistoryModel.ProductOrders po : ohm.getPolist()){%>
                                                    <tr onclick="turnLocation()">
                                                        <td>
                                                            <%=po.getProduct().getProductName()%>
                                                        </td>
                                                        <td class="text-center">
                                                            <%=po.getOrderlist().getOrdersQuantity()%>
                                                        </td>
                                                        <td class="text-center">
                                                            RM <%=po.getOrderlist().getOrdersSubprice()%>
                                                        </td>
                                                        <%if(po.getRnrStatus().equals(STATUS_NORATE)){%>
                                                            <td class="text-end">
                                                                <span class="badge bg-danger"><%=STATUS_NORATE%></span>
                                                            </td>
                                                        <%}else{%>
                                                            <td class="text-end">
                                                                <span class="badge bg-success"><%=STATUS_RATE%></span>
                                                            </td>
                                                        <%}%>
                                                    </tr>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        <%}%>
                    <%}else{%>
                        <tr class="text-center">
                            <td>No Match Record Found ~</td>
                        </tr>
                        <tr class="text-center">
                            <td><a class="btn btn-primary" href="/GUI_Assignment/productMenuServlet">CONTINUE SHOPPING</a></td>
                        </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
        <script>
            $(function(){
                $("#history").addClass("active");
            });
        </script>
    </body>
</html>
