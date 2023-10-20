<%-- 
    Document   : ErrorPage
    Created on : Apr 22, 2023, 2:33:57 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Header.jsp" %>
<!DOCTYPE html>
<html>
    <body>
        <br><br><br>
        <div style="display: flex; justify-content: center; align-items: center">
            <div class="alert alert-dismissible alert-danger" style="width: 70%">
                <h4 class="text-center">Opps ~ Unexcepted Error Occurs</h4>
                <table class="table">
                    <tr class="table-danger">
                        <th class="text-start w-25 text-black">Error Cause From : </th>
                        <td class="text-center"><%=session.getAttribute("UnexceptableErrorDesc")%></td>
                    </tr>
                    <tr class="table-danger">
                        <th class="text-start w-25 text-black">For More Details Infomation : </th>
                        <td class="text-center"><%=session.getAttribute("UnexceptableError")%></td>
                    </tr>
                </table>
                <p class="text-center text-sm-center"><i class="bi bi-info-circle"></i> &nbsp; Please contact to ${adminEmail} to fixed problem</p>
            </div>
        </div>
        <div style="display: flex; justify-content: center; align-items: center">
            <a class="btn btn-warning" href="/GUI_Assignment/HomeServlet"><i class="bi bi-house"></i> &nbsp; BACK TO HOME</a>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
