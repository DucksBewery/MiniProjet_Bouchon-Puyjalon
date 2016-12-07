<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<body>
    <h1>Voulez vous vraiment supprimer votre commande ?</h1>
    <form action="DeletePurchaseController" method="POST">
        <input type="hidden" name="userId" id="userId" value="${utilisateur.customerId}">
        <input type="hidden" name="purchaseId" id="purchaseId" value="${purchaseId}">
        <input type="hidden" name="productId" id="productId" value="${productId}">
        <input type="hidden" name="quantity" id="quantity" value="${quantity}">
        <input type="submit" name="action" value="confirmSuppr">
        <a href="accueil.jsp"><button type="button" class="btn">Annuler</button></a>
    </form>
        <script type="text/javascript" src="js/deleteOrder.js"></script>
</body>
</html>
