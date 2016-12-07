<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<body>
    <div class="container-fluid">
    <h1 class="centered">Voulez vous vraiment supprimer votre commande ?</h1>
    <form action="DeletePurchaseController" method="POST">
        <input type="hidden" name="userId" id="userId" value="${utilisateur.customerId}">
        <input type="hidden" name="purchaseId" id="purchaseId" value="${purchaseId}">
        <input type="hidden" name="productId" id="productId" value="${productId}">
        <input type="hidden" name="quantity" id="quantity" value="${quantity}">
        <div class="container centered">
        <a href="ajout.jsp"><button type="submit" name="action" value="confirmSuppr" class="btn btn-danger">Valider la suppression</button></a>
        <a href="accueil.jsp"><button type="button" class="btn">Annuler</button></a>
        </div>
    </form>
        </div>
</body>
</html>
