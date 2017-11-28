<%-- 
    Document   : admin_charge_result
    Created on : Nov 30, 2016, 4:21:32 PM
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
        <h1>Updated Lumpsum Charges</h1>
        <br>
        <%=(String) (request.getAttribute("updatedMemTable"))%>
        <br>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
