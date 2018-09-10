<%--
  Created by IntelliJ IDEA.
  User: sampa
  Date: 9/6/2018
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h1>Welcome</h1>
    You are logged in as <%=session.getAttribute("username")%>
    <form action="validate" method="POST">
        
        <label>Registration Number :</label>
        <input type="text" name="id" placeholder="ID"><br/>
        
        <label>Password</label>
        <input type="password" name="key" placeholder="password"><br/>

        <input type="hidden" name="tokentxt" id="tokentxt"/>

        <input type="submit" value="Submit"/>

    </form>
    <script src="./jquery.min.js"></script>
    <script>
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: '/SynchronizerTokenPattern/validate',
            success: function (data) {
                alert(data);
                $("#tokentxt").val(data.csrfToken);
            },
            error: function (xhr, status, error) {
                alert(status);
            }
        });
    </script>

</body>
</html>
