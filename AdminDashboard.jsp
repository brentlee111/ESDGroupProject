<%-- 
    Document   : AdminDashboard
    Created on : Nov 13, 2016, 5:34:52 PM
    Author     : Eric
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Admin Dashboard</h1>

        <form method="post" action="AdminDashboard.do">
            <select name="action">
                <option value="processClaims">Process individual claims</option>
                <option value="processMembershipApplication">Process membership application</option>
                <option value="chargeAnnualLumpsum">Charge Annual Lumpsum</option>
            </select>
            <br><br>
            <input type="submit">
        </form>

        <%="All Members"%>
        <%=(String) (request.getAttribute("membertable"))%>
        <%="All Claims"%>
        <%=(String) (request.getAttribute("claimtable"))%>
        <%="All Outstanding Balances"%>
        <%=(String) (request.getAttribute("outstandingbalancetable"))%>
        <%="All Provisional Member Application"%>
        <%=(String) (request.getAttribute("provisionalmembertable"))%>
        <br>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
