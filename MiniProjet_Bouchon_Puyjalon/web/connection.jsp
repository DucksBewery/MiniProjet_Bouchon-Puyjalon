<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Connection</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="color:red">${messageErreur}</div>
        <h1>AUTHENTIFICATION</h1>
        <form method="POST">
            <label for="email">Email : </label><input id="email" type="text" name="login" value="" placeholder="Email" accept-charset=utf-8><br>
            <label for="mdp">Mot de passe : </label><input id="mdp" type="password" name="id_client" value="" placeholder="Mot de passe" accept-charset=utf-8><br>
            <input type="submit" name="action" value="login">
        </form>
        <p>Il y a actuellement ${applicationScope.numberConnected} utilisateurs de cette application</p>
    </body>
</html>
