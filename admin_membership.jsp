<%-- 
    Document   : admin_membership
    Created on : Nov 28, 2016, 9:58:57 PM
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
        <h1>Process Membership Applications</h1>

        <%="All Provisional Member Applications"%>
        <%=(String) (request.getAttribute("provisionalmembertable"))%>

        <br>

        <%="All Payment Made"%>
        <%=(String) (request.getAttribute("paymenttable"))%>
        
        <br>
        
        <%="Provisional Members That Have Paid Membership FEE"%>
        <form method="get" action="AdminProcessMembership.do">
            <select name="eligibleMember">
                <%=(String) (request.getAttribute("approvelist"))%>
            </select>
            <br><br>
            <input type="submit" value="Approve">
        </form>
            
        <br>

        <jsp:include page="foot.jsp"/>
    </body>
</html>
