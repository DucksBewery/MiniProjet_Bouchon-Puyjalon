<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreur</title>
    </head>
    <body>
        <h1>Erreur !</h1>

        <!-- La variable "errorMessage" est initialisée dans la servlet -->
        <p>Erreur: ${messageErreur}</p>
	<a href="connection.html">Retour au formulaire</a><br>
    </body>
</html>
