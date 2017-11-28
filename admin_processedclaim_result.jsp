<%-- 
    Document   : admin_processedclaim_result
    Created on : Nov 30, 2016, 12:23:31 PM
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
        <h1><%= "Claim " + (String)session.getAttribute("claimid") + " is " + request.getParameter("claimChoice")%></h1>
        
        <br>
        
        <%=(String) (request.getAttribute("processedClaim"))%>
        
        <br>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
