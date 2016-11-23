
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>

    <body>
        
        <h1>Ajout d'un bon de commande</h1>
        
        <form method="POST">
            <p>Sélectionnez un produit : <br><select id='listeProduits' name="product"></select></p>
            <p><label for="quantite">Sélectionner une quantité : </label><input type="number" name="qtt" value="1" min="1" id="quantite"></p>
            <p><label for="delivery">Entrez votre compagnie de livraison : </label><input type="text" name="freightCompany" placeholder="Slow Snail" id="delivery"></p>
            <div id='infoOrder'></div>
            <input type="submit" name='action' value="confirmAjout">
            <a href="accueil.jsp" name='action' value='accueil'>Accueil</a>
        </form>
        
        <p id="erreur"></p>
        
        <script type="text/javascript" src="js/purchaseOrder.js"></script>
        
    </body>
    
</html>
