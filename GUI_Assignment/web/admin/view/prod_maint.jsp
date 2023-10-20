<%boolean isAdmin = session.getAttribute("staffLogin") == null ? false : ((String) session.getAttribute("staffLogin")).equals("admin");%>
<%@page import="Model.Product"%>
<% boolean isNew = request.getParameter("isNew").equals("true") ? true : false;
    boolean isSaved = request.getParameter("isSaved") != null ? Boolean.parseBoolean(request.getParameter("isSaved")) : false;
    int action = request.getParameter("action") == null ? 0 : Integer.parseInt(request.getParameter("action"));
    Product product = (Product) session.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="https://bootswatch.com/5/darkly/bootstrap.min.css">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
        <style>
            .form_wid{
                max-width: 850px;
            }
            .error-message {
                color: red;
                font-size:12px;
            }
            .table td{
                width:50%;
                min-width:400px;
            }
            #display_image{
                width: 100%;
                height: 300px;
                background-position: center;
                background-size: cover;
                overflow: hidden;
            }
            #img{
                width: 100%;
                height: 100%;
            }
        </style>
    </head>
    <body>
        <div class="form_wid container mt-5">
            <h2 class="text-center mb-3"><%= isNew ? "Add New" : "Edit"%> Product</h2>
            <form id="form" method="POST" action="../../ProdMaint" onsubmit="return validateForm()" enctype="multipart/form-data">
                <table class="table table-striped table-dark">
                    <thead>
                        <tr>
                            <th scope="col">Field</th>
                            <th scope="col">Value</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="row">ID</th>
                            <td><input readonly style="color:grey;" type="text" id="id" name="id" class="form-control" placeholder="ID will be auto generated." value="<%=isNew ? "" : product.getProductId()%>"></td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td><span id="name_error" class="error-message"></span><input onblur="isInputFieldEmpty(this);" type="text" id="name" name="name" class="error-border form-control" value="<%=isNew ? "" : product.getProductName()%>"></td>
                        </tr>
                        <tr>
                            <th scope="row">Description</th>
                            <td><textarea id="description" name="description" class="error-border form-control" style="height: 180px;"><%=isNew ? "" : product.getProductDesc()%></textarea></td>
                        </tr>
                        <tr>
                            <th scope="row">Price</th>
                            <td><span id="price_error" class="error-message"></span><input onkeypress="return isPriceKey(event)" onchange="formatPrice(this)" onblur="isValidPrice()" type="text" id="price" name="price" class="error-border form-control" value="<%=isNew ? "" : product.getProductPrice()%>"></td>
                        </tr>
                        <%if(isAdmin){%><tr>
                            <th scope="row">Active</th>
                            <td><%}%><input <%= isAdmin ? "" : "hidden"%> value = "1" type="checkbox" id="active" name="active" class="form-check-input" <%=isNew ? "checked" : product.getProductActive() == '1' ? "checked" : ""%>><%if(isAdmin){%></td>
                        </tr><%}%>
                        <tr>
                            <th scope="row">Image</th>
                            <td>
                                <span id="image_error" class="error-message"></span>
                                <input hidden id="image_input" type="file" name="image" accept=".jpg, .jpeg, .jfif, .pjpeg, .pjp, .png">
                                <input hidden id="imgID" name="imgID" value="<%=isNew ? 0 : product.getImageTable().getImageId()%>">
                                <%= isNew ? "" : "<input hidden id=\"imgEdited\" name=\"imgEdited\" value=\"false\">"%>
                                <div class="error-border form-control" id="display_image"><img id="img" src="../../RetrieveImageServlet?imageID=<%=isNew ? 0 : product.getImageTable().getImageId()%>" alt="No Image"></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="d-flex justify-content-end">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="action" id="inlineRadio1" value="1" checked>
                        <label class="form-check-label" for="inlineRadio1">Return to listing page</label>
                    </div>
                    <% if (isNew) {%>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="action" id="inlineRadio2" value="2" <%=action == 2 ? "checked" : ""%>>
                        <label class="form-check-label" for="inlineRadio2">Insert another record</label>
                    </div>
                    <%}%>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="action" id="inlineRadio3" value="3" <%=action == 3 ? "checked" : ""%>>
                        <label class="form-check-label" for="inlineRadio3">Preview record</label>
                    </div>
                    <input hidden name="submit" value="<%=isNew ? "1" : "0"%>">
                    <button type="button" onclick="cancel()" class="btn btn-danger ms-3">Cancel</button>
                    <% if (!isNew) {%><%if(isAdmin){%><button type="button" onclick="remove()" class="btn btn-danger ms-3">Delete</button><%}%><%}%>
                    <button type="reset" onclick="resets()" class="btn btn-secondary ms-3">Reset</button>
                    <button id="submit" type="submit" class="btn btn-primary ms-3">Submit</button>
                </div>
            </form>
        </div>
    </body>
    <%@include file="/Home/view/Footer.jsp"%>
    <%if (isSaved) {%><script>alert('Record Saved.');</script> <%}%>
    <script src="../js/maint_page_util.js" type="text/javascript"></script>
    <script src="../js/maint_page_prod.js" type="text/javascript"></script>
    <script>
        var displayImage = document.getElementById("display_image");
        displayImage.onclick = function () {
            var imageInput = document.getElementById("image_input");
            imageInput.click();
        };

        function remove() {
            const form = document.getElementById("form");
            const check = document.getElementById("active");
            const submitBtn = document.getElementById("submit");

            form.reset();

            check.checked = false;

            submitBtn.click();
        }
    </script>
</html>
