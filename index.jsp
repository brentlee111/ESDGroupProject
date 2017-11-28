<%-- 
    Document   : index
    Created on : Nov 29, 2016, 3:00:44 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
    </head>
    <body>
        <h1>Main Page</h1>
        <form method="POST" action="Login.do">
        <p />
            View a table <br />
            <input type="radio" name="tbl" value="Login">Login<br />
            <input type="radio" name="tbl" value="NewUser">New User<br />
            <input type="radio" name="tbl" value="Update">Password Change<br/>
            <input type=submit name="tbl" value="Action"> <br/>
        </form> 
    </body>
</html>
