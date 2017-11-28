<%-- 
    Document   : admin_approvedmember_result
    Created on : Nov 29, 2016, 2:02:56 AM
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
        <h1><%= (String) request.getParameter("eligibleMember") + "'s Membership Application is APPROVED and is Upgraded to Member"%></h1>
        <br>
        <%="All Members"%>
        <%=(String) (request.getAttribute("membertable"))%>
        
        <br>

        <jsp:include page="foot.jsp"/>
    </body>
</html>
