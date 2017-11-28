<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Outstanding balance and Payment</h1>
        
        <%="Balance remaining"%>
        <%=(String) (request.getAttribute("outstandingbalancetable"))%>
        
        <form method="post" action="MemberPayment.do">
            <table>
                <tr>
                    <td>Make a payment : </td>
                    <td><input name="balance" type="text"></td>
                </tr> 
               
                <select name="type_of_payment">
                <option value="lumpsum">Lump sum </option>
                <option value="membership">Membership</option>
                </select> 
            </table>      
            <input type="submit">
            <br>
        </form>
        <jsp:include page="memberfoot.jsp"/>
    </body>
</html>