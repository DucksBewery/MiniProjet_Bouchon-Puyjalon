<%-- 
    Document   : ajout
    Created on : 17 nov. 2016, 16:09:47
    Author     : マルゴ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
    <body>
        <h1>Suppression d'un bon de commande</h1>
        <form action="PurchaseOrderController" method="POST">
            <select>${listeBonCommande}</select>
            <input type="submit" name="submit" value="Supprimer">
        </form>
    </body>
</html>
