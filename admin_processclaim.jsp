<%-- 
    Document   : admin_processclaim
    Created on : Nov 29, 2016, 7:22:35 PM
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
        <h1>APPROVE OR REJECT Claims That Met Criteria</h1>
        
        <%="Claim " + (String)request.getParameter("sclaimuser")%>
        <%=(String) (request.getAttribute("processClaim"))%>
        
        <br>
        
        <%="APPROVE OR REJECT"%>
        <form method="get" action="AdminDecideClaim.do">
            <select name="claimChoice">
                <option value="APPROVED">APPROVE</option>
                <option value="REJECTED">REJECT</option>
            </select>
            <br><br>
            <input type="submit" value="Process">
        </form>
        
        <br>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
