<%-- 
    Document   : login
    Created on : Apr 20, 2023, 2:59:00 AM
    Author     : erika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8" />
        <link href="/GUI_Assignment/login/login.css" rel="stylesheet" />
        <link href="/GUI_Assignment/css/bootstrap.css" rel="stylesheet" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>

    <body>
        <header>
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
        </header>

        <div class="container" style="max-width:100% !important;" >
            <div class="login_box">
                <form action="/GUI_Assignment/login" method="POST">
                    <h1>Login</h1>
                    <div class="input_box">
                        <input type="text" name="username" required="">
                        <label for="">User Name</label>
                        <ion-icon class="icon " name="person-outline"></ion-icon>
                    </div>
                    <div class="input_box">
                        <input type="password" name="password" required="">
                        <label for="">Password</label>
                        <ion-icon class="icon" name="lock-closed-outline"></ion-icon>
                    </div>
                    <div class="login">
                        <button>Log in</button>
                    </div>
                    <div>
                        <div class="reg">
                            <p>Don't have an account?</p>
                            <a href="/GUI_Assignment/login/register.jsp">Registration</a>
                        </div>
                        <div class="reg" style="margin-top: 0px !important;">
                            <p>Login as Staff?</p>
                            <a href="/GUI_Assignment/login/staffLogin.jsp">Staff Login</a>
                        </div>
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
