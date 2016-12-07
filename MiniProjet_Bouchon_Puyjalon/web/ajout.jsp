
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<div class="container-fluid">
    <h1 class="centered">Ajout d'un bon de commande</h1>
    <div class="container-fluid">
        <div class="col-sm-3">
            <form method="POST" action="AddPurchaseController" class="form-inline">
                <h2>Edition du bon</h2>
                <p>Sélectionnez un produit : <br><select id='productList' name="product"></select></p>
                <p><label for="quantity">Sélectionnez une quantité : </label><br><input type="number" name="qtt" value="1" min="1" max="" id="quantity"></p>
                <p><label for="delivery">Entrez votre compagnie de livraison : </label><br><select id='deliveryList' name="delivery"></select></p>
                <input type="hidden" name="userId" value="${utilisateur.customerId}">
                <input type="hidden" id="productName" name="productName" value="">
                <input type="hidden" id="manufacturer" name="manufacturer" value="">
                <input type="hidden" id="productType" name="productType" value="">
                <input type="hidden" id="productCost" name="productCost" value="">
                <input type="hidden" id="quantityMax" name="quantityMax" value="">
                <input type="hidden" id="productRate" name="productRate" value="">
                </div>
                <div class="col-sm-6">
                    <h2>Visualisation</h2>
                    <div id='infoOrder'></div>
                </div>
                <div class="col-sm-3" id="boutonValidAjout">
                    <a href="ajout.jsp"><button id="boutonAjout" type="submit" name="action" value="confirmAjout" class="btn btn-success">Valider ajout</button></a>
                </div>
            </form>
        </div>

        <p id="erreur"></p>

        <script type="text/javascript" src="js/addOrder.js"></script>

    </div>
</body>

</html>
