<%-- 
    Document   : Create
    Created on : Apr 26, 2023, 1:10:32 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%
    ArrayList<Product> plist = (ArrayList<Product>) request.getAttribute("plist");
    HashMap<String,String> errorList = (HashMap<String,String>) request.getAttribute("errorList");
%>
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
        <%if(errorList != null && errorList.size() > 0){%>
            <div class="w-100 d-flex justify-content-center align-middle">
                <div class="alert alert-dismissible alert-danger w-100">
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    <h4>Error ! Unable To Create A New Discount</h4>
                    <ul>
                        <%for (String values : errorList.values()) {%>
                            <li><%=values%></li>
                        <%}%>
                    </ul>
                </div>
            </div>
        <%}%>
        <br><br>
        <div class="mybox">
            <div>
                <form class="discountForm" method="post" action="/GUI_Assignment/DiscountFormServlet">
                    <table class="table table-borderless w-100">
                        <tr>
                            <td colspan="2" class="text-center">
                                <h3><i class="bi bi-tags-fill"></i> &nbsp; Create New Discount</h3>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label">
                                Product Discount : 
                            </td>
                            <td>
                                <select name="pdtDiscount" class="form-control" id="pdtDiscount">
                                    <option value="">Select Product</option>
                                    <%
                                        if(plist != null && plist.size() > 0){
                                            for(Product p : plist){
                                    %>
                                                <option value="<%=p.getProductId()%>" <%=String.valueOf(p.getProductId()).equals(request.getParameter("pdtDiscount")) == true ? "selected" : ""%>><%=p.getProductName()%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                                <span class="text-danger" id="pdterror">
                                    <%=errorList == null ? "" : errorList.get("productIdError") == null ? "" : errorList.get("productIdError")%>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                Discount Date : 
                                <%
                                    ArrayList<String> timeMatch = (ArrayList<String>) request.getAttribute("timeMatchError");
                                    if(timeMatch != null && timeMatch.size() > 0){
                                %>
                                        <p class="text-danger">Time Slot Unavailable, This Product Have Discount On : </p>
                                        <ul>
                                            <%for(String time : timeMatch){%>
                                                <li class="text-danger"><%=time%></li>
                                            <%}%>
                                        </ul>
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                        <tr>
                            <td>FROM</td>
                            <td>
                                <input type="date" name="startDate" class="form-control" style="display: inline-block" value="<%=request.getParameter("startDate")%>" id="startDate">
                                <span class="text-danger" id="startDateErr">
                                    <%=errorList == null ? "" : errorList.get("startDate") == null ? "" : errorList.get("startDate")%>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td>TO</td>
                            <td>
                                <input type="date" name="endDate" class="form-control" style="display: inline-block" value="<%=request.getParameter("endDate")%>" id="endDate">
                                <span class="text-danger" id="endDateErr">
                                    <%=errorList == null ? "" : errorList.get("endDate") == null ? "" : errorList.get("endDate")%>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="w-25">
                                Discount Percentage : 
                            </td>
                            <td>
                                <input type="number" name="percentage" class="form-control" max="99" min="1" value="<%=request.getParameter("percentage")%>">
                                <span class="text-danger" id="percentageerror">
                                    <%=errorList == null ? "" : errorList.get("percentageError") == null ? "" : errorList.get("percentageError")%>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text-end">
                                <a class="btn btn-danger w-25" href = '/GUI_Assignment/DiscountDisplayServlet'><i class="bi bi-arrow-left-square"></i> &nbsp; CANCEL </a>
                                <button type="submit" class="btn btn-primary w-25" id="submitbtn"><i class="bi bi-pencil-square"></i> &nbsp; CREATE </button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <%@include file="../../Home/view/Footer.jsp" %>
        <script>
            $(function(){
                $(".discountForm").change(function(){
                    $("#submitbtn").attr("disabled", false);
                    
                    if($("#pdtDiscount").val() == ""){
                        $("#pdterror").removeClass("dis-none");
                        $("#pdterror").addClass("dis-inline");
                        $("#pdterror").html("Product Cannot Be Empty");
                        $("#submitbtn").attr("disabled", true);
                    }else{
                        $("#pdterror").addClass("dis-none");
                        $("#pdterror").removeClass("dis-inline");
                    }
                    
                    //check discount size
                    var percentageSize = $("input[type=number]").val();
                    if(parseInt(percentageSize) > 100 || parseInt(percentageSize) < 1){
                        //show error
                        $("#percentageerror").removeClass("dis-none");
                        $("#percentageerror").addClass("dis-inline");
                        $("#percentageerror").html("Discount Percentage Out Of Range, Cannot Be More Than 100 Or Smaller Than 1");
                        $("#submitbtn").attr("disabled", true);
                    }else if(percentageSize == ""){
                        //show error
                        $("#percentageerror").removeClass("dis-none");
                        $("#percentageerror").addClass("dis-inline");
                        $("#percentageerror").html("Discount Percentage Cannot Be Empty");
                        $("#submitbtn").attr("disabled", true);
                    }else{
                        $("#percentageerror").addClass("dis-none");
                        $("#percentageerror").removeClass("dis-inline");
                    }
                    
                    if ($("#startDate").val() == "") {
                        $("#startDateErr").removeClass("dis-none");
                        $("#startDateErr").addClass("dis-inline");
                        $("#startDateErr").html("Discount Start Date Cannot Be Empty");
                        $("#submitbtn").attr("disabled", true);
                    } else {
                        $("#startDateErr").addClass("dis-none");
                        $("#startDateErr").removeClass("dis-inline");
                    }

                    if ($("#endDate").val() == "") {
                        $("#endDateErr").removeClass("dis-none");
                        $("#endDateErr").addClass("dis-inline");
                        $("#endDateErr").html("Discount End Date Cannot Be Empty");
                        $("#submitbtn").attr("disabled", true);
                    } else {
                        $("#endDateErr").addClass("dis-none");
                        $("#endDateErr").removeClass("dis-inline");
                    }
                });
            });
        </script>
    </body>
</html>
