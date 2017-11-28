<%-- 
    Document   : admin_rejectedclaim_result
    Created on : Nov 30, 2016, 11:59:46 AM
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
        <h1><%= "Claim " + (String)request.getParameter("fclaimuser") + " is REJECTED"%></h1>
        
        <br>
        
        <%=(String) (request.getAttribute("rejectedClaim"))%>
        
        <br>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
