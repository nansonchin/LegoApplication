<%-- 
    Document   : ThankYou
    Created on : Apr 26, 2023, 8:36:08 PM
    Author     : Thong Sau Wei
--%>

<%-- Document : thankYou Created on : Apr 19, 2023, 11:12:32 PM Author : Acer --%>

<%@page import="Utility.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js">

        <link rel="stylesheet" href="/GUI_Assignment/css/bootstrap.css">
        <link rel="stylesheet" href="/GUI_Assignment/css/ThankYou.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    </head>

    <body>

        <%@include file="../Home/view/Header.jsp"%>

        <div class=content>
            <div class="wrapper-1">
                <img class="product__image" src="/GUI_Assignment/CheckOut/blackpink.png">
                <div class="wrapper-2">
                    <h1>Thank you !</h1>

                    <%
                        if (session.getAttribute("orderId") != null) {
                            int orderId = (Integer)session.getAttribute("orderId");
                    %>

                    <p>Thank you for your order, <strong>Order id - <span
                                style="color: rgb(164, 94, 94);">${orderId}</span></strong></p>
                                <br><p><strong>Order Date - <span style="color: rgb(164, 94, 94);"><%=Converter.convertDateToSimpleFormat((java.util.Date)session.getAttribute("orderDate"))%></span></strong></p>
                            <%
                                }
                            %>


                    <p>~ Have a Nice Day ~ </p>
                    <button type="button" class="btn btn-primary" onclick="location.href = '/GUI_Assignment/HomeServlet'">Back Home</button>
                    <button type="button" class="btn btn-primary" style="margin-left: 10px;" onclick="location.href = '/GUI_Assignment/OrderHistoryServlet'">View History</button>

                </div>
            </div>
        </div>



        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Source+Sans+Pro" rel="stylesheet">

    </body>

</html>
