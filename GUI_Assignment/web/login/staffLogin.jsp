<%-- 
    Document   : staffLogin
    Created on : Apr 18, 2023, 1:11:06 AM
    Author     : erika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link href="/GUI_Assignment/login/staffLogin.css" rel="stylesheet" />
        <link href="/GUI_Assignment/css/bootstrap.css" rel="stylesheet" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>

    <body>
        <header>
        </header>

        <div class="container" style="max-width: 100% !important;" >
            <%
                String message = (String) request.getSession().getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-danger text-center mx-auto mb-3" role="alert" style="background-color: rgba(242, 150, 147); position: absolute; top: 5px; left: 50%; transform: translate(-50%, 0); width: 60%;">
                <%= message%>
            </div>
            <%
                }
            %>
            <div class="login_box">
                <form action="/GUI_Assignment/staffLogin" method="POST">
                    <h1>Staff Login</h1><br/>
                    <div class="input_box">
                        <input type="text" name="id" required="">
                        <label for="">Staff ID</label>
                        <ion-icon class="icon " name="person-outline"></ion-icon>
                    </div>
                    <div class="input_box">
                        <input type="password" name="password" required="">
                        <label for="">Password</label>
                        <ion-icon class="icon" name="lock-closed-outline"></ion-icon>
                    </div>
                    <div class="login">
                        <button type="submit">Login</button>
                    </div>
                    <div>
                </form>
            </div>
            <footer>
                <%@include file="/Home/view/Footer.jsp"%> 
            </footer>
        </div>
    </body>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</html>
