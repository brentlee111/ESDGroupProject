<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Member Dashboard</h1>

        <form method="post" action="MemberDashboard.do">
            <select name="action">
                <option name="action" value="outstandingBalance">Outstanding balance or Make a payment </option>
                <option name="action" value="submitClaims">Submit a claim</option>
                <option name="action" value="listClaimsPayments">List all claims and payments to date</option>
            </select>
            <br><br>
            <input type="submit">
        </form>

        <%="Member details"%>
        <%=(String) (request.getAttribute("membertable"))%>
        <%="Claims request"%>
        <%=(String) (request.getAttribute("claimtable"))%>
        <%="Payment record"%>
        <%=(String) (request.getAttribute("payments"))%>
        <jsp:include page="memberfoot.jsp"/>
    </body>
</html>

