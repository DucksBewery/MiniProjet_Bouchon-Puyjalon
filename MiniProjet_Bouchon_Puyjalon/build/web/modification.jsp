<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<div class="container-fluid">
    <h1 class="centered">Moification d'un bon de commande</h1>
    <div class="container-fluid">
        <div class="col-sm-3">
            <form method="POST" action="UpdateOrderController">
                <h2>Votre du bon</h2></br>
                <input type="hidden" id="userId" name="userId" value="${utilisateur.customerId}">
                <input type="hidden" id="purchaseId" name="purchaseId" value="${purchaseId}">
                <input type="hidden" id="productId" name="productId" value="${productId}">

                <p>Votre produit : <strong id="product"></strong></p>
                <p><label for="quantityUpdated">Changer la quantité : </label><br><input type="number" name="qtt" value="1" min="1" max="" id="quantityUpdated"></p>
                <input type="hidden" id="purchaseQuantity" name="purchaseQuantity" value="">
                <p><label for="deliveryList">Changer la compagnie de livraison : </label><br><select id='deliveryList' name="delivery"></select></p>
        </div>
        <input type="hidden" id="productCost" name="productCost" value="">
        <input type="hidden" id="productRate" name="productRate" value="">
        <input type="hidden" id="quantityMax" name="quantityMax" value="">

        <div class="col-sm-6">
            <h2>Visualisation</h2>
            <div id='infoOrder'></div>
        </div>
        <div class="col-sm-3" id="boutonValidAjout">
            <a href="ajout.jsp"><button id="boutonAjout" type="submit" name="action" value="confirmModif" class="btn btn-warning">Valider la modification</button></a>
        </div>
        </form>
    </div>
    <p id="erreur"></p>

    <script type="text/javascript" src="js/orderUpdate.js"></script>
</div>
</body>

</html>
