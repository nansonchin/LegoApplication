<%

    String variableValue = (String) request.getAttribute("variableName");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://bootswatch.com/5/darkly/bootstrap.min.css">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>
    <body>
        <div class="container my-5">
            <div class="alert alert-danger" role="alert">
                <h4 class="alert-heading">Unexpected Error Occurred</h4>
                <p>Please contact support for assistance.</p>
                <br>
                <h6>Error Cause From : </h6>
                <p><%=session.getAttribute("UnexceptableErrorDesc")%></p>
                <br>
                <h6>Details : </h6>
                <p><%=session.getAttribute("UnexceptableError")%></p>
            </div>
            <div class="my-5 text-center">
                <a href="/GUI_Assignment/admin/view/home.jsp" class="btn btn-primary">Go to Home Page</a>
            </div>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
