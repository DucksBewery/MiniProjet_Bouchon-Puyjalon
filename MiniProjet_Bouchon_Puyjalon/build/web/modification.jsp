<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>

<body>

    <h1>Modification d'un bon de commande</h1>

    <form method="POST" action="AddPurchaseController">
        <p>Sélectionnez le bon de commande à modifier : <br><select id='purchaseList' name="purchase"></select></p>
        <div id='infoToUpdate'>
            <p><label for="productUpdated">Changer le produit : </label><br><select id='productList' name="product"></select></p>
            <p><label for="quantityUpdated">Changer la quantité : </label><br><input type="number" name="qtt" value="1" min="1" max="" id="quantityUpdated"></p>
            <p><label for="deliveryList">Changer la compagnie de livraison : </label><br><select id='deliveryList' name="delivery"></select></p>
            <input type="hidden" name="userId" id="userId" value="${utilisateur.customerId}">
            <input type="hidden" id="productName" name="productName" value="">
            <input type="hidden" id="manufacturer" name="manufacturer" value="">
            <input type="hidden" id="productType" name="productType" value="">
            <input type="hidden" id="productCost" name="productCost" value="">
            <input type="hidden" id="quantityMax" name="quantityMax" value="">
            <input type="hidden" id="productRate" name="productRate" value="">
            <div id='infoOrder'></div>
        </div>
        <input type="submit" name='action' value="confirmModif">
        <a href="accueil.jsp" name='action' value='accueil'>Accueil</a>
    </form>

    <p id="erreur"></p>

    <script type="text/javascript" src="js/updateOrder.js"></script>

</body>

</html>
