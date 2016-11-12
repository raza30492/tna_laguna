<%-- 
    Document   : test
    Created on : 17 Mar, 2016, 6:45:12 PM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function fun1(){
                
                var name = [];
                
                for( i = 0; i < 4; i++){
                    name[i] = document.forms["form1"]["name" + (i+1)].value;
                }
                var str = "";
                for(i = 0; i < 4; i++){
                    str += name[i];
                }
                alert(str);
                return false;
                
            }
        </script>
    </head>
    <body>
        <form name="form1" action="xyz" onsubmit="return fun1()" >
            <input type="text" name="name1" /><br>
            <input type="text" name="name2" /><br>
            <input type="text" name="name3" /><br>
            <input type="text" name="name4" /><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
