<%-- 
    Document   : ajout
    Created on : 17 nov. 2016, 16:09:47
    Author     : マルゴ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
    <body>
        <h1>Modification d'un bon de commande</h1>
        <form action="PurchaseOrderController" method="POST">
            <select>${listeProduit}</select>
            <p>Quantité : <input type="number" name="qtt" value=""></p>
            <p>${infoOrder}</p>
            <input type="submit" name="submit" value="Confirmer">
        </form>
    </body>
</html>
