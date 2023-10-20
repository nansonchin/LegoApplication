<%-- 
    Document   : HistoryDetail
    Created on : Apr 30, 2023, 10:09:22 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../Home/view/Header.jsp" %>
<%@page import="Model.PageModel.*, Utility.*"%>
<jsp:useBean id="ohm" class="Model.PageModel.OrderHistoryModel" scope="request"></jsp:useBean>
<!DOCTYPE html>
<html>
    <body>
        <div class="text-center">
            <h3>Order History Details - <span style="color: rgba(242, 150, 147)">Orders ID <%=ohm.getOrders() == null ? "" : ohm.getOrders().getOrdersId()%><span></h3>
        </div>
        <div class="w-100 p-5 d-flex justify-content-center align-middle">
            <%if(ohm != null){%>
                <table class="table table-hover m-3 w-75 align-middle">
                    <tbody>
                        <tr>
                            <td colspan="3">
                                <table class="table table-borderless">
                                    <tr>
                                        <td>Receiver : </td>
                                        <td>
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressName()%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Phone No : </td>
                                        <td>
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressPhone()%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Address : </td>
                                        <td>
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressNo()%> 
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressStreet()%> 
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressPostcode()%> 
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressState()%> 
                                            <%=ohm == null ? "" : ohm.getAddress().getAddressCity()%> 
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <!--product details-->
                        <%if(ohm.getPolist() != null && ohm.getPolist().size() > 0){%>
                            <%for(OrderHistoryModel.ProductOrders po : ohm.getPolist()){%>
                                <tr>
                                    <td rowspan="2">
                                        <img src="/GUI_Assignment/RetrieveImageServlet?imageID=<%=po.getProduct().getImageTable().getImageId()%>" alt="Image Not Found" width="100px" height="100px">
                                    </td>
                                    <td>
                                        <%=po.getProduct().getProductName()%> &nbsp; <span class="badge <%=po.getRnrStatus().equals("HAVENT RATE") == true ? "bg-danger" : "bg-success"%>"><%=po.getRnrStatus()%></span>
                                    </td>
                                    <td style="width: 150px;">
                                        <%if(po.getRnrStatus().equals("HAVENT RATE")){%>
                                        <a href="/GUI_Assignment/RateReviewServlet?productId=<%=po.getProduct().getProductId()%>&orderId=<%=ohm.getOrders().getOrdersId()%>" class="btn btn-primary">RATE NOW</a>
                                        <%}%>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Qty : <%=po.getOrderlist().getOrdersQuantity()%></td>
                                    <td>RM <%=po.getOrderlist().getOrdersSubprice()%></td>
                                </tr>
                            <%}%>
                        <%}%>
                        <!--order details-->
                        <tr>
                            <td colspan="3">
                                <table class="table  table-borderless">
                                    <tr>
                                        <td colspan="2" class="text-end">Order ID : </td>
                                        <td style="width: 150px;" class="px-2"><%=ohm.getOrders().getOrdersId()%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="text-end">Order Date : </td>
                                        <td><%=Converter.convertDateToSimpleFormat(ohm.getOrders().getOrdersDate())%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="text-end">Payment Method : </td>
                                        <td><%=ohm.getOrders().getOrdersPaymentType()%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="text-end">Delivery Fee : </td>
                                        <td>RM <%=ohm.getOrders().getOrdersDeliveryFee()%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="text-end">Express Shipping : </td>
                                        <td>RM <%=ohm.getOrders().getOrdersExpressShipping()%></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="text-end">Total Price : </td>
                                        <td>RM <%=ohm.getOrders().getOrdersTtlPrice()%></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>
            <%}else{%>
                <div class="text-center w-100">
                    <p>Opps ~ There Have Some Error Occurs ~</p>
                    <a class="btn btn-primary" href="/GUI_Assignment/HomeServlet">Back To Home</a>
                </div>
            <%}%>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
