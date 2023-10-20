<%@page import="Controller.ReportController"%>
<!DOCTYPE html>
<html>
    <head>
        <!--change title and favicon-->
        <title>${companyName}</title>
        <link rel="icon" href="/GUI_Assignment/Home/image/LEGOlogo.png" type="image/x-icon"/>
        <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.min.css">
        <style>
            .container {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .sections{
                display: none;
                width: 100%;
                height: 100vh;
            }
            #section1{
                display: block;
            }

            .fixed-bottom-center {
                position: fixed;
                bottom: 0;
                left: 50%;
                transform: translateX(-50%);
            }
            .submit{
                position: fixed;
                right: 25%;
                transform: translateX(-50%);
                bottom: 50%;
                font-size:30px;
                width:80px;
                height: 80px;
            }
            #back-btn{
                position: fixed;
                left: 25%;
                transform: translateX(-50%);
                bottom: 50%;
                font-size:30px;
                width:80px;
                height: 80px;
            }
            select[multiple] {
                overflow: hidden;
                scrollbar-width: none;
                -ms-overflow-style: none;
                ::-webkit-scrollbar {
                    display: none;
                }
            }
        </style>




        <script>
            window.onload = function () {
                // Add click event listeners to section 1 links
                document.getElementById('sales-link').addEventListener('click', function () {
                    // Hide section 1 and show section 2
                    document.getElementById('section1').style.display = 'none';
                    document.getElementById('section2').style.display = 'block';
                    document.getElementById('back-btn').style.display = 'block';
                    document.getElementById('home-btn').style.display = 'none';
                });
                document.getElementById('popularity-link').addEventListener('click', function () {
                    // Hide section 1 and show section 3
                    document.getElementById('section1').style.display = 'none';
                    document.getElementById('section3').style.display = 'block';
                    document.getElementById('back-btn').style.display = 'block';
                    document.getElementById('home-btn').style.display = 'none';
                });
            };
            function back() {
                document.getElementById('section1').style.display = 'block';
                const elements = document.querySelectorAll('.sections');
                for (let i = 0; i < elements.length; i++) {
                    elements[i].style.display = 'none';
                }
                document.getElementById('back-btn').style.display = 'none';
                document.getElementById('home-btn').style.display = 'block';
            }
            ;
        </script>
    </head>
    <body>
        <div class="container">
            <div id="section1" class=" btn-group-vertical">
                <h1 class="text-center mb-5">Report</h1>
                <a id="sales-link" class="mt-1 btn btn-primary btn-lg rounded-lg ">Sales</a>
                <!-- <a id="popularity-link" class="mt-1  btn btn-primary btn-lg rounded-lg ">Popularity</a> -->
            </div>
            <%String[] opt = null;%>
            <div class="sections" id="section2">
                <h1 class="text-center mt-5">Sales Report</h1>
                <form action="../../ReportResult" method="POST" onsubmit="return validateForm()">
                    <table class="table table-striped table-dark mt-5" style="width: 550px;margin:auto;">
                        <thead class="bg-primary">
                            <tr>
                                <th>Field</th>
                                <th>Value</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>Order Date</th>
                                <td><label style="width:25px;">From</label><input class="ml-3" name="dateFrom" type="date"><br><label style="width:25px;">To</label><input class="ml-3" name="dateTo" type="date"></td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td>
                                    <button onclick="back()" class="btn btn-primary ms-3 ">&lt;</button>
                                </td>
                                <td style="text-align:right;">
                                    <input hidden name="reportName" value="Sales">
                                    <input hidden name="submit" value="1">
                                    <button type="submit" class="btn btn-primary ms-3 ">&gt;</button></td>
                            </tr>
                        </tfoot>
                    </table>
                </form>
            </div>
            <div class="sections" id="section3">
                <h1 class="text-center mt-5">Popularity Report</h1>
                <p class="text-center"">Under develop</p>
                <form action="report_result.jsp">
                    <table class="table table-striped table-dark mt-5">
                        <thead>
                        <th>Condition</th>
                        <th>Value</th>
                        </thead>
                        <tbody>
                        <th></th>
                        <td></td>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>

        <a id="home-btn" href="home.jsp" class=" mb-1 btn btn-primary fixed-bottom-center  rounded-pill">Return</a>
        <button onclick="back()" class="btn btn-primary ms-3 rounded-pill " style="display:none;">&lt;</button>
        <script src="../js/report.js" type="text/javascript"></script>
        <script>
            function validateForm() {
                const dateFrom = document.getElementsByName('dateFrom')[0];
                const dateTo = document.getElementsByName('dateTo')[0];

                if (!dateFrom.value || !dateTo.value) {
                    if (!dateFrom.value) {
                        dateFrom.style.border = '1px solid red';
                    }
                    if (!dateTo.value) {
                        dateTo.style.border = '1px solid red';
                    }
                    return false;
                }

                return true;
            }



        </script>
        <%@include file="/Home/view/Footer.jsp"%>
    </body>
</html>
