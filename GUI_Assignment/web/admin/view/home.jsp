<%boolean isAdmin = session.getAttribute("staffLogin") == null ? false : ((String) session.getAttribute("staffLogin")).equals("admin");%>
<%session.removeAttribute("search");%><%session.removeAttribute("status");%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="https://bootswatch.com/5/darkly/bootstrap.min.css">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>
    <body style="width: 100vw;height: 100vh;">
        <div class="h-100 d-flex align-items-center justify-content-center">
            <div class="container-fluid d-flex flex-column justify-content-center align-items-center h-100">
                <h1 class="text-center mb-5">Welcome, <%= session.getAttribute("staffLogin") %></span>.</h1>
                <div class="row">
                    <%if (isAdmin) {%>
                    <div class="col-md-6 col-lg-6 mb-4 d-flex justify-content-center">
                        <button onclick="window.location.href = 'staff_list.jsp'" class="btn btn-primary btn-lg rounded-lg w-100 h-100">Staff</button>
                    </div>
                    <%}%>
                    <div class="col-md-6 col-lg-6 mb-4 d-flex justify-content-center">
                        <button onclick="window.location.href = 'prod_list.jsp'" class="btn btn-primary btn-lg rounded-lg w-100 h-100">Product</button>
                    </div>
                    <%if (isAdmin) {%>
                    <div class="col-md-6 col-lg-6 mb-4 d-flex justify-content-center">
                        <button onclick="window.location.href = 'mem_list.jsp'" class="btn btn-primary btn-lg rounded-lg w-100 h-100">Member</button>
                    </div>
                    <%}%>
                    <div class="col-md-6 col-lg-6 mb-4 d-flex justify-content-center">
                        <button onclick="window.location.href = 'report.jsp'" class="btn btn-primary btn-lg rounded-lg w-100 h-100">Report</button>
                    </div>
                    <div class="col-md-6 col-lg-6 mb-4 d-flex justify-content-center">
                        <button onclick="window.location.href = '/GUI_Assignment/DiscountDisplayServlet'" class="btn btn-primary btn-lg rounded-lg w-100 h-100">Discount</button>
                    </div>
                    <div class="col-md-6 col-lg-6 mb-4 d-flex justify-content-center">
                        <button onclick="window.location.href = '/GUI_Assignment/salesRecordMain'" class="btn btn-primary btn-lg rounded-lg w-100 h-100">Sales Record</button>
                    </div>
                    <div class="col-md-4 col-lg-4 mb-4 d-flex justify-content-center" style="margin:auto;">
                        <button onclick="window.location.href = '/GUI_Assignment/logout'" class="btn btn-danger btn-lg rounded-lg w-100 h-100">Logout</button>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
