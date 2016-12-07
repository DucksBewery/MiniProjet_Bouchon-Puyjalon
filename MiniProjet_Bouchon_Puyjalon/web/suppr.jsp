<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<<<<<<< HEAD
        <h1>Suppression d'un bon de commande</h1>
        <form action="PurchaseOrderController" method="POST">
            <select>${listeBonCommande}</select>
            <input type="submit" name="submit" value="Supprimer">
        </form>
    </body>
=======
<body>
    <h1>Suppression d'un bon de commande</h1>
    <form action="DeletePurchaseController" method="POST">
        <select id="purchaseList" name="purchase"></select>
        <input type="hidden" name="userId" id="userId" value="${utilisateur.customerId}">
        <input type="hidden" id="productId" name="productId" value="">
        <input type="hidden" id="quantity" name="quantity" value="">
        <input type="submit" name="action" value="Supprimer">  
    </form>
        <script type="text/javascript" src="js/deleteOrder.js"></script>
</body>
>>>>>>> origin/master
</html>
