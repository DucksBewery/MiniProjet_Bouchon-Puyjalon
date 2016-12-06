
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>

    <body>
        
        <h1>Ajout d'un bon de commande</h1>
        
        <form method="POST" action="AddPurchaseController">
            <p>Sélectionnez un produit : <br><select id='productList' name="product"></select></p>
            <p><label for="quantity">Sélectionnez une quantité : </label><br><input type="number" name="qtt" value="1" min="1" max="" id="quantity"></p>
            <p><label for="delivery">Entrez votre compagnie de livraison : </label><br><select id='deliveryList' name="delivery"></select></p>
            <input type="hidden" name="userId" value="${utilisateur.customerId}">
            <h2>Visualisation du bon de commande</h2>
            <input type="hidden" id="productName" name="productName" value="">
            <input type="hidden" id="manufacturer" name="manufacturer" value="">
            <input type="hidden" id="productType" name="productType" value="">
            <input type="hidden" id="productCost" name="productCost" value="">
            <input type="hidden" id="quantityMax" name="quantityMax" value="">
            <input type="hidden" id="productRate" name="productRate" value="">
            <div id='infoOrder'></div>
            <input type="submit" name='action' value="confirmAjout">
            <a href="accueil.jsp" name='action' value='accueil'>Accueil</a>
        </form>
        
        <p id="erreur"></p>
        
        <script type="text/javascript" src="js/addOrder.js"></script>
        
    </body>
    
</html>
