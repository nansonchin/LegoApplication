<%
    String search = request.getParameter("search") == null ? session.getAttribute("search") != null ? (String) session.getAttribute("search") : "" : request.getParameter("search");
    String delete = request.getParameter("delete");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="../css/css.css" rel="stylesheet" type="text/css"/>

        <script src="../js/list_page_util.js" type="text/javascript"></script>

        <style>
            .fixed-bottom-center {
                position: fixed;
                bottom: 0;
                left: 50%;
                transform: translateX(-50%);
            }

            .home-btn{
                z-index: 1;
            }
        </style>
    </head>

    <% if (delete != null)
                if (delete.equals("1")) {%>
    <script>
        alert('Record has been deleted.');
    </script>                <%}%>
    <body>

        <a href="home.jsp" class=" mb-1 btn btn-primary fixed-bottom-center rounded-pill home-btn">Return</a>

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class=" rounded-lg shadow-sm">
                        <div class="card-body">
                            <form action="staff_list.jsp">
                                <div class="input-group mb-3">
                                    <div class="input-group-addon">
                                    </div>
                                    <input id="search" name="search" type="text" class="form-control form-control-lg rounded-pill" placeholder="ID, IC, phone num, email or name..." value="<%= search%>">
                                    <div class="input-group-addon">
                                        <button id="submit-button" type="submit" class="btn btn-primary btn-lg rounded-pill">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-md-12">

                    <a href="staff_maint.jsp?isNew=true" class="btn btn-primary btn-lg rounded-pill">
                        Add New
                    </a>
                    <table class="table table-hover table-striped">
                        <thead>
                            <tr>
                                <th style="width: 5%">ID</th>
                                <th style="width: 20%">Name</th>
                                <th style="width: 15%">Phone Num</th>
                                <th style="width: 20%">Email</th>
                                <th style="width: 10%">Birthday</th>
                                <th style="width: 15%">IC</th>
                                <th style="width: 15%">Password</th>
                                <th style="width: 5%">Edit</th>
                            </tr>
                        </thead>
                        <tbody>
                            <jsp:include page="../../StaffList" />
                        </tbody>
                    </table>
                </div>
            </div>
            <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
