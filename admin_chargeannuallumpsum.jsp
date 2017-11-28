<%-- 
    Document   : admin_chargeannuallumpsum
    Created on : Nov 30, 2016, 2:20:31 PM
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
        <h1>Charge Annual Lumpsum</h1>
        <br>
        <%="All Approved Claims"%>
        <%=(String) (request.getAttribute("approvedclaimtable"))%>
        <br>
        <%="All Approved Members"%>
        <%=(String) (request.getAttribute("approvedmembertable"))%>
        <br>
        <%="Number of approved members = " + request.getAttribute("memberCount")%>
        <br>
        <%="Total approved claims = " + request.getAttribute("totalClaim")%>
        <br>
        <b><%="Annual Lumpsum Charge per Member = " + request.getAttribute("annualLumpsum")%></b>
        <br><br>
        
        
        <%="Charge Every Members"%>
        <br>
        <form method="post" action="AdminChargeLumpsum.do">
            <input type="submit" value="Charge">
        </form>
        <br>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
