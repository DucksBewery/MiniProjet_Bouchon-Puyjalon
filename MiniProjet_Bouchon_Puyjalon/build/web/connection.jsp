<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Notre application web</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%--
            La servlet fait : request.setAttribute("errorMessage", "Login/Password incorrect");
            La JSP récupère cette valeur dans ${errorMessage}
        --%>
        <div style="color:red">${messageErreur}</div>
        <h1>AUTHENTIFICATION</h1>
        <form method="POST">
            <input type="text" name="login" value="" placeholder="Email">
            <input type="password" name="id_client" value="" placeholder="Mot de passe">
            <input type="submit" name="action" value="login">
        </form>
        <h3>Il y a actuellement ${applicationScope.numberConnected} utilisateurs de cette application</h3>
    </body>
</html>
