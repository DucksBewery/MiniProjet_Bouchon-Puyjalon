<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vous êtes authentifié</title>
    </head>
    <body>
        <h1>Bravo! Vous êtes connectés avec le compte de ${utilisateur.name} et le mot de passe ${utilisateur.customerId}</h1>
        <h3>Il y a actuellement ${applicationScope.numberConnected} utilisateurs de cette application</h3>
        <form method="POST"> <!-- l'action par défaut est l'URL courant, qui va rappeler la servlet -->
            <input type='submit' name='action' value='logout'>
            <a href="accueil.jsp" name='action' value='accueil'>Accueil</a>
        </form>
    </body>
</html>
