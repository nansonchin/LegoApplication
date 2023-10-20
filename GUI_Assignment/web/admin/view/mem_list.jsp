<% String search = request.getParameter("search") != null ? (String) request.getParameter("search") : "";%>
<% Boolean notDelete = request.getParameter("notDelete") != null ? Boolean.parseBoolean(request.getParameter("notDelete")) : false;%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="../css/css.css" rel="stylesheet" type="text/css"/>
        <script src="../js/list_page_util.js" type="text/javascript"></script>
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
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
    <body>
        
        <%=notDelete? "<script>alert('Member have orders, unable to delete.');</script>" : ""%>

        <a href="home.jsp" class=" mb-1 btn btn-primary fixed-bottom-center  rounded-pill home-btn">Return</a>
        
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class=" rounded-lg shadow-sm">
                        <div class="card-body">
                            <form action="mem_list.jsp">
                                <div class="input-group mb-3">
                                    <div class="input-group-addon">
                                    </div>
                                    <input id="search" name="search" type="text" class="form-control form-control-lg rounded-pill" placeholder="ID or Name..." value="<%=search%>">
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
                    <table class="table table-hover table-striped">
                        <thead>
                            <tr>
                                <th style="width: 20%">ID</th>
                                <th style="width: 70%">Name</th>
                                <th style="width: 10%">Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <jsp:include page="../../MemMaint" />
                        </tbody>
                    </table>
                </div>
            </div>
            <%@include file="/Home/view/Footer.jsp"%> 
    </body>
</html>
