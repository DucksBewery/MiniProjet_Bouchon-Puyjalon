<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@include file="entete.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="5; URL=accueil.jsp">
    </head>
    <body>
        <h1>Connection réussie!</h1>
        <h2>Vous êtes connectés avec le compte de ${utilisateur.name} et le mot de passe ${utilisateur.customerId}</h2>
        Redirection vers la page d'accueil dans 5 secondes...
        Ou cliquez sur <a href="accueil.jsp">Accueil</a>
    </body>
</html>
