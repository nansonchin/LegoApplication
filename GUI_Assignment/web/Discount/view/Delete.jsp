<%-- 
    Document   : Delete
    Created on : Apr 27, 2023, 7:45:23 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Utility.*" %>
<jsp:useBean id="discount" class="Model.Discount" scope="request"></jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--add link for bootstrap, icon and -->
        <link rel="stylesheet" href="/GUI_Assignment/css/bootstrap.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">
        <link rel="stylesheet" href="/GUI_Assignment/Discount/css/CreateStyle.css"/>
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>
    <body>
        <br><br>
        <div class="mybox">
            <div>
                <form class="discountForm" method="post" action="/GUI_Assignment/DiscountDeleteServlet">
                    <input type="hidden" value="<%=discount.getDiscountId()%>" name="discountID">
                    <table class="table table-borderless w-100">
                        <tr>
                            <td colspan="2" class="text-center">
                                <h3><i class="bi bi-tags-fill"></i> &nbsp; Delete New Discount</h3>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label">
                                Product Discount : 
                            </td>
                            <td>
                                <input type="text" value="<%=discount.getProduct().getProductName()%>" disabled class="form-control">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                Discount Date : 
                            </td>
                        </tr>
                        <tr>
                            <td>FROM</td>
                            <td>
                                <input type="date" name="startDate" class="form-control" style="display: inline-block" disabled value="<%=Converter.convertDateToHTMLFormat(discount.getDiscountStartDate())%>">
                            </td>
                        </tr>
                        <tr>
                            <td>TO</td>
                            <td>
                                <input type="date" name="endDate" class="form-control" style="display: inline-block" disabled value="<%=Converter.convertDateToHTMLFormat(discount.getDiscountEndDate())%>">
                            </td>
                        </tr>
                        <tr>
                            <td class="w-25">
                                Discount Percentage : 
                            </td>
                            <td>
                                <input type="number" name="percentage" class="form-control" max="100" min="1" disabled value="<%=discount.getDiscountPercentage()%>">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text-end">
                                <a class="btn btn-danger w-25" href = '/GUI_Assignment/DiscountDisplayServlet'><i class="bi bi-arrow-left-square"></i> &nbsp; CANCEL </a>
                                <button type="submit" class="btn btn-primary w-25" id="submitbtn"><i class="bi bi-x-square"></i> &nbsp; DELETE </button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <%@include file="../../Home/view/Footer.jsp" %>
    </body>
</html>
