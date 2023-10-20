<%-- 
    Document   : SearchResult
    Created on : Apr 19, 2023, 9:05:34 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- add header -->
<%@include file="Header.jsp"%>
<%@page import="java.util.*"%> 
<%@page import="Model.*"%> 
<%
    ArrayList<Product> searchResult = (ArrayList<Product>)request.getAttribute("searchResult");
    HashMap<Integer, Double> ratingList = (HashMap<Integer, Double>)request.getAttribute("ratingList");
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/GUI_Assignment/Home/css/SearchStyle.css">
    </head>
    <body>
        <div class="p-5 m-4">
            <table class="table table-hover">
                <thead class="text-center">
                    <tr>
                        <th scope="col">No</th>
                        <th scope="col">Product Image</th>
                        <th scope="col">Product Details</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <%if (searchResult.size() > 0) {
                        int i = 1;
                        for (Product p : searchResult) {%>
                        <tr>
                            <th scope="row" class="text-center"><%=i%></th>
                            <td class="w-25">
                                <div class="text-center">
                                    <img src="/GUI_Assignment/RetrieveImageServlet?imageID=<%=p.getImageTable().getImageId()%>" class="rounded" alt="Image not found" width="150px" height="150px">
                                </div>
                            </td>
                            <td class="text-start align-middle">
                                <h5>Product Name : <%=p.getProductName()%></h5>
                                <p class="text-muted">Product Price  : <%=p.getProductPrice()%></p>
                                <p class="text-muted">Product Rating : <%=ratingList.get(p.getProductId()) == -1 ? "NO RATING" : ratingList.get(p.getProductId())%></p>
                            </td>
                            <td class="text-center align-middle">
                                <a href="/GUI_Assignment/viewProductServlet?id=<%=p.getProductId()%>&avgRating=<%=ratingList.get(p.getProductId()) == -1 ? 0 : ratingList.get(p.getProductId())%>">
                                    <button class="btn btn-custom">View More &nbsp; <i class="bi bi-arrow-right-circle"></i></button>
                                </a>
                            </td>
                        </tr>
                    <%      i++;
                        }
                    } else {%>
                        <tr>
                            <td colspan="4">
                                <h4 class="text-danger text-center">NO RECORD FOUND!!</h4>
                            </td>
                        </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
