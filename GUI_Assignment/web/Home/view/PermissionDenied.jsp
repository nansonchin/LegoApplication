<%-- 
    Document   : PermissionDenied
    Created on : May 1, 2023, 12:52:32 PM
    Author     : LOH XIN JIE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/GUI_Assignment/css/bootstrap.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
    </head>
    <body>
        <br><br><br>
        <div style="display: flex; justify-content: center; align-items: center">
            <div class="alert alert-dismissible alert-danger" style="width: 70%">
                <h4 class="text-center">Opps ~ Permission Denied ~</h4>
                <table class="table">
                    <tr class="table-danger">
                        <th class="text-start w-25 text-black">Error Cause : </th>
                        <td class="text-center">You Have No Permission To Access This Page</td>
                    </tr>
                </table>
                <p class="text-center text-sm-center"><i class="bi bi-info-circle"></i> &nbsp; If any problem please contact to ${adminEmail} to fixed problem</p>
            </div>
        </div>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
