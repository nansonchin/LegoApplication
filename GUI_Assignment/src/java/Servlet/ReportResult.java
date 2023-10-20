/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controller.ReportController;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Utility.*;

/**
 *
 * @author Yeet
 */
public class ReportResult extends HttpServlet {

    protected void processReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reportName = request.getParameter("reportName") != null ? request.getParameter("reportName") : "";
        String submit = request.getParameter("submit") != null ? request.getParameter("submit") : "";
        
        if (submit.equals("") || reportName.equals("")) {
            request.getSession().setAttribute("UnexceptableError", "some error");
            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            return;
        }
        
        ReportController contorl = null;
        List<HashMap<String, Object>> rows = null;
        String conditions = null;
        
        if (submit.equals("1")) {//sales
            String dateFrom = request.getParameter("dateFrom") != null ? request.getParameter("dateFrom") : "";
            String dateTo = request.getParameter("dateTo") != null ? request.getParameter("dateTo") : "";

            conditions = "from " + dateFrom +" to "+ dateTo;

            contorl = new ReportController(dateFrom, dateTo);


            rows = contorl.salesReport();

            request.setAttribute("reportName", reportName);
            request.setAttribute("contorl", contorl);
            request.setAttribute("rows", rows);
            request.setAttribute("conditions", conditions);

            RequestDispatcher rd = request.getRequestDispatcher("/admin/view/report_result.jsp");
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("UnexceptableError", "report error");
            request.getSession().setAttribute("UnexceptableErrorDesc", "Unexpected Error");
            request.getRequestDispatcher("admin/view/unexpected_error.jsp").forward(request, response);
            return;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request)) {
            processReport(request, response);
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CheckPermission.permissionStaff(request)) {
            processReport(request, response);
        } else if (CheckPermission.permissionNoLogin(request)) {
            request.getRequestDispatcher("login/staffLogin.jsp").forward(request, response);
        } else {
            //turn to error page , reason - premission denied
            request.getRequestDispatcher("Home/view/PermissionDenied.jsp").forward(request, response);
        }
    }

}
