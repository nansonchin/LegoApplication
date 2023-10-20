<%-- 
    Document   : Display
    Created on : Apr 26, 2023, 11:20:36 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, Model.*, Model.PageModel.*, Utility.Converter" %>
<%
    ArrayList<Product> plist = (ArrayList<Product>) request.getAttribute("plist");
    ArrayList<DiscountDisplayModel> ddmList = (ArrayList<DiscountDisplayModel>) request.getAttribute("ddmList");
    final String VALID = "VALID";
    final String INVALID = "INVALID";
    final String COMING_SOON = "COMING SOON";
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
        <link rel="stylesheet" href="/GUI_Assignment/Discount/css/DisplayStyle.css"/>
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
        <style>
            #searchForm table tr td > input, #searchForm table tr td > select{
                width: 100%;
                display: inline-block; 
            }
            
            #searchForm table tr td > input , #searchForm table tr td > label, #searchForm table tr td > select{
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <%if ((String)session.getAttribute("DiscountSuccess") != null) {%>
        <div class="w-100 d-flex justify-content-center align-middle">
            <div class="alert alert-dismissible alert-success w-100">
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                <h4><%=(String) session.getAttribute("DiscountSuccess")%></h4>
            </div>
        </div>
        <%}%>
        <br><br>
        <div class="text-center">
            <h3><i class="bi bi-tags-fill"></i> &nbsp; DISCOUNT</h3>
        </div>
        <form action="/GUI_Assignment/DiscountDisplayServlet" id="searchForm" class="p-5" method="get">
            <table class="table table-borderless w-100">
                <tr>
                    <td>
                        <label>DISCOUNT ID : </label>
                    </td>
                    <td>
                        <input type="number" class="form-control" name="discountID" value="<%=request.getParameter("discountID")%>">
                    </td>
                    <td>
                        <label>PRODUCT : </label>
                    </td>
                    <td>
                        <select name="product" class="form-control" value="<%=request.getParameter("product")%>">
                            <option value="">Select Product Search</option>
                            <%if (plist != null && plist.size() > 0) {%>
                                <%for (Product p : plist) {%>
                                    <option value="<%=p.getProductId()%>" <%=String.valueOf(p.getProductId()).equals(request.getParameter("product")) == true ? "selected" : ""%>><%=p.getProductName()%></option>
                                <%}%>
                            <%}%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>STATUS : </label>
                    </td>
                    <td>
                        <select name="status" class="form-control">
                            <option value="">Choose Status</option>
                            <option value="<%=VALID%>" <%=VALID.equals(request.getParameter("status")) == true ? "selected" : ""%>><%=VALID%></option>
                            <option value="<%=INVALID%>" <%=INVALID.equals(request.getParameter("status")) == true ? "selected" : ""%>><%=INVALID%></option>
                            <option value="<%=COMING_SOON%>" <%=COMING_SOON.equals(request.getParameter("status")) == true ? "selected" : ""%>><%=COMING_SOON%></option>
                        </select>
                    </td>
                    <td>
                        <label>DISCOUNT PERCENTAGE : </label>
                    </td>
                    <td>
                        <input type="number" class="form-control" max="100" min="1" name="percentage" value="<%=request.getParameter("percentage")%>">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>DATE : </label> 
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>START DATE </label> 
                    </td>
                    <td>
                        <input type="date" class="form-control" name="startDate" value="<%=request.getParameter("startDate")%>">
                    </td>
                    <td>
                        <label>END DATE </label> 
                    </td>
                    <td>
                        <input type="date" class="form-control" name="endDate" value="<%=request.getParameter("endDate")%>">
                    </td>
                </tr>
            </table>
            
            <div class="text-end">
                <a href="/GUI_Assignment/DiscountDisplayServlet" class="btn btn-secondary">CLEAR SEARCH</a>
                <input type="submit" value="SEARCH" class="btn btn-primary">
            </div>
        </form>
        
        <div class="w-100 p-5">
            <div class="text-end w-100 p-3">
                <a class="btn btn-success mx-2" href="/GUI_Assignment/admin/view/home.jsp">BACK TO HOME</a>
                <a class="btn btn-warning" href="/GUI_Assignment/DiscountCreateServlet"><i class="bi bi-plus-square"></i> &nbsp; CREATE</a>  
            </div><br>
            <table class="table table-hover text-center align-middle">
                <thead>
                    <tr>
                        <th scope="col">NO</th>
                        <th scope="col">DISCOUNT ID</th>
                        <th scope="col">DATE</th>
                        <th scope="col">PERCENTAGE DISCOUNT</th>
                        <th scope="col">PRODUCT NAME</th>
                        <th scope="col">STATUS</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <%int i = 1;%>
                    <%if(ddmList != null && ddmList.size() > 0){%>
                        <%for(DiscountDisplayModel ddm : ddmList){%>
                            <tr>
                                <th><%=i%></th>
                                <td><%=ddm.getDiscount().getDiscountId()%></td>
                                <td>
                                    <%=Converter.convertDateToSimpleFormat(ddm.getDiscount().getDiscountStartDate())%>
                                    <br>
                                    ~
                                    <br>
                                    <%=Converter.convertDateToSimpleFormat(ddm.getDiscount().getDiscountEndDate())%>
                                </td>
                                <td><%=ddm.getDiscount().getDiscountPercentage()%> %</td>
                                <td><%=ddm.getProduct().getProductName()%></td>
                                <td class="<%=ddm.getStatus().equals("VALID") == true ?"text-success" : ddm.getStatus().equals("INVALID") == true ? "text-danger" : "text-info"%>"><%=ddm.getStatus()%></td>
                                <td>
                                    <%--if invalid will not display Update button--%>
                                    <%if(!ddm.getStatus().equals("INVALID")){%>
                                        <a href="/GUI_Assignment/DiscountUpdateServlet?discountID=<%=ddm.getDiscount().getDiscountId()%>" class="btn btn-warning m-1">
                                            <i class="bi bi-arrow-up-square"></i> &nbsp; UPDATE
                                        </a>
                                        <br>
                                    <%}%>
                                    <a href="/GUI_Assignment/DiscountDeleteServlet?discountID=<%=ddm.getDiscount().getDiscountId()%>" class="btn btn-danger m-1">
                                        <i class="bi bi-x-square"></i> &nbsp; DELETE
                                    </a>
                                </td>
                            </tr>
                            <%i++;%>
                        <%}%>
                    <%}else{%>
                        <tr>
                            <td colspan="7" class="text-center text-danger">NO RECORD FOUND</td>
                        </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
        <%@include file="../../Home/view/Footer.jsp" %>
    </body>
</html>
