<%-- 
    Document   : login
    Created on : Nov 29, 2016, 5:47:07 PM
    Author     : user
--%>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <div>
             <form method="post" action="UserServlet.do"/>
            <pre>
       USER NAME     <input type="text" name="username" placeholder="username" class="text"/>

       PASSWORD      <input type="password" name="password" placeholder="password" class="text"/> 
                
                    <button type="submit"  name="tbl" value="action" class="btn">Login</button>
                    
                     <jsp:include page="foot2.jsp"/>
           </form>
            </pre>         
        </div>
    </body>
</html>
