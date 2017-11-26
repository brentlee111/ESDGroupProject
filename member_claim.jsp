<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>All available claims</h1>
        
        <%="All Claims"%>
        <%=(String) (request.getAttribute("currentclaimtable"))%>
        
        <form method="post" action="MemberSubmitClaim.do">
            <table>
       
            <th>Request a claim</th>
                <tr>
                    <td>Insert a rationale : </td>
                    <td><input name="rationale" type="text"</td>
                </tr>
                <tr>
                    <td>Insert claim amount : </td>
                    <td><input name="amount" type="text"</td>
                </tr>
                <br>    
            </table>
          <input type="submit" value="Submit" name="submit">
        </form>
         <jsp:include page="memberfoot.jsp"/>
    </body>
</html>
