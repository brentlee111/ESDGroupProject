<%-- 
    Document   : admin_claim
    Created on : Nov 28, 2016, 2:44:21 AM
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
        <h1>Process Individual Claims</h1>

        <%="Claims History"%>
        <%=(String) (request.getAttribute("historyclaimtable"))%>
        
        <br>
        
        <%="Submitted Claims"%>
        <%=(String) (request.getAttribute("submittedclaimtable"))%>
        
        <br>

        <%="Claims that meet criteria"%>
        <form method="post" action="AdminProcessClaim.do">
            <select name="sclaimuser">
                <%=(String) (request.getAttribute("successclaimuserlist"))%>
            </select>
            <br><br>
            <input type="submit" value="PROCESS">
        </form>
            
        <br>
        
         <%="Claims that do not meet criteria"%>
        <form method="post" action="AdminRejectClaim.do">
            <select name="fclaimuser">
                <%=(String) (request.getAttribute("failedclaimuserlist"))%>
            </select>
            <br><br>
            <input type="submit" value="REJECT">
        </form>
            
        <br>

        <jsp:include page="foot.jsp"/>
    </body>
</html>
