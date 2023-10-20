<%-- 
    Document   : Header
    Created on : Apr 19, 2023, 11:04:03 AM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="Model.Member" %>
<jsp:useBean id="header" class="Controller.HeaderController" scope="application"></jsp:useBean>
<jsp:useBean id="member" class="Model.Member" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--add link for bootstrap, icon and -->
        <link rel="stylesheet" href="/GUI_Assignment/css/bootstrap.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>
    <body>
        <%--header--%>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">${companyName}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarColor01">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="/GUI_Assignment/HomeServlet" id="home">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/GUI_Assignment/productMenuServlet" id="menu">Menu</a>
                        </li>
                        <%if(member != null && member.getMemberName() != null){%>
                            <li class="nav-item">
                                <a class="nav-link" href="/GUI_Assignment/OrderHistoryServlet" id="history">Order History</a>
                            </li>
                        <%}%>
                    </ul>
                    <!--search bar-->
                    <form class="d-flex" action="/GUI_Assignment/SearchServlet" method="get">
                        <input class="form-control me-sm-2" type="search" name="search" placeholder="Search Product" value="<%=request.getParameter("search") ==  null ? "" : request.getParameter("search")%>">
                        <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
                    </form>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <ul class="nav navbar-nav d-flex">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                                <i class="bi bi-person-circle size-adjust" style="font-size: 25px"></i>
                                <%if (member != null && member.getMemberName() != null) {%>
                                &nbsp; <h4 style="display: inline; font-size: 17px">Welcome <%=member.getMemberName()%> !</h4>
                                <%}%>
                            </a>
                            <div class="dropdown-menu">
                                <!-- check user login and display corresponding content -->
                                <%if(member != null && member.getMemberName() != null){%>
                                    <a class="dropdown-item" href="/GUI_Assignment/logout">Logout</a>
                                <%}%>
                                <%if(member == null || member.getMemberName() == null){%>
                                    <!-- no login -->
                                    <a class="dropdown-item" href="/GUI_Assignment/login/login.jsp">Login</a>
                                    <a class="dropdown-item" href="/GUI_Assignment/login/register.jsp">Register</a>
                                    <a class="dropdown-item" href="/GUI_Assignment/login/staffLogin.jsp">Staff Login</a>
                                <%}%>
                            </div>
                        </li>
                        <%if(member != null && member.getMemberName() != null){%>
                            <li class="nav-item d-flex">
                                <a class="nav-link" href="/GUI_Assignment/cartListServlet">
                                    <i class="bi bi-cart" style="font-size: 25px"></i>
                                    <span class="badge bg-danger rounded-pill"><%=header.getUserCartlistQty(member.getMemberId())%></span>
                                </a>
                            </li>
                        <%}%>
                    </ul>
                </div>
            </div>
        </nav>
        <br><br>
    </body>
</html>
