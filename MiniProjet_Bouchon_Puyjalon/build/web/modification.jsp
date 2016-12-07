<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
    <h1>Modification d'un bon de commande</h1>

    <form method="POST" action="UpdateOrderController">
            
            <input type="hidden" id="userId" name="userId" value="${utilisateur.customerId}">
            <input type="hidden" id="purchaseId" name="purchaseId" value="${purchaseId}">
            <input type="hidden" id="productId" name="productId" value="${productId}">
            
            <p>Votre produit : <strong id="product"></strong></p>
            <p><label for="quantityUpdated">Changer la quantité : </label><br><input type="number" name="qtt" value="1" min="1" max="" id="quantityUpdated"></p>
            <input type="hidden" id="purchaseQuantity" name="purchaseQuantity" value="">
            <p><label for="deliveryList">Changer la compagnie de livraison : </label><br><select id='deliveryList' name="delivery"></select></p>
            
            <input type="hidden" id="productCost" name="productCost" value="">
            <input type="hidden" id="productRate" name="productRate" value="">
            <input type="hidden" id="quantityMax" name="quantityMax" value="">
            
            <div id='infoOrder'></div>
            
        <input type="submit" name='action' value="confirmModif">
    </form>

    <p id="erreur"></p>

    <script type="text/javascript" src="js/updateOrder.js"></script>

</body>

</html>
