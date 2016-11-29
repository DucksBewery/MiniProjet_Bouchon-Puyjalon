/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(// Exécuté à la fin du chargement de la page
            function () {
                    // On remplit le <select> avec les états existants
                    fillAvailableProducts();
                    fillFreightCompanies();
                    // Quand on sélectionnne un nouvel état, on affiche les clients de cet état
                    $("#productList").change(showPurchaseDetails);
                    $("#quantite").change(changeDetails);
            }
);
			
function fillAvailableProducts() {
        // On fait un appel AJAX pour chercher les états existants
        $.ajax({
                url: "availableProducts",
                dataType: "json",
                error: showError,
                success: // La fonction qui traite les résultats
                        function(result) {
                                var select = $('#productList');
                                // Pour chaque état dans le résultat
                                $.each(result, 
                                        function(key) {
                                                // On ajoute une option dans le select
                                                var productCode = result[key]["productId"];
                                                var productDesc = result[key]["description"];
                                                var option = new Option(productDesc, productCode);
                                                select.append($(option));
                                        }
                                );
                                // On initialise l'affichage 
                                showPurchaseDetails();		
                        }
        });								
}

function fillFreightCompanies(){
        $.ajax({
                url: "freightCompanies",
                dataType: "json",
                error: showError,
                success: // La fonction qui traite les résultats
                        function(result) {
                                var select = $('#deliveryList');
                                // Pour chaque état dans le résultat
                                $.each(result, 
                                        function(key) {
                                                // On ajoute une option dans le select
                                                var freightCompanyName = result[key];
                                                var option = new Option(freightCompanyName, freightCompanyName);
                                                select.append($(option));
                                        }
                                );		
                        }
        });		
}

			
// Afficher les clients dans l'état sélectionné
function showPurchaseDetails() {
        // Quel est le produit sélectionné ?
        var selectedProduct = $("#productList").val();
        // On fait un appel AJAX pour chercher les informations liées à ce produit
        $.ajax({
                url: "productInfoToJSON",
                data: { "prod" : selectedProduct },
                dataType: "json",
                success: // La fonction qui traite les résultats
                        function(result) {
                                $("#infoOrder").empty();
                                $("#quantite").val(1);
                                var info = "<p>Produit sélectionné : "+result["description"]+"</p>"+
                                        "<p>Type : "+result["productType"]+"</p>"+
                                        "<p>Constructeur : "+result["manufacturer"]+"</p>"+
                                        "<p id='quantiteProd'>Quantité : 1</p>"+
                                        "<p id='prixProd'> Prix : "+result["productCost"]+"€</p>";
                                $("#infoOrder").append(info);
                                $("#productName").val(result["description"]);
                                $("#manufacturer").val(result["manufacturer"]);
                                $("#productType").val(result["productType"]);
                                $("#productCost").val(result["productCost"]);
                                $("#quantityMax").val(result["quantityInStock"]);
                                $("#quantite").attr({"max" : result["quantityInStock"]});
                        },
                error: showError
        });				
}

function changeDetails(){
        $("#infoOrder").empty();
        var qtt = $("#quantite").val();
        var max = $("#quantite").attr("max");
        if(qtt>max){
            var info = "<p>Produit sélectionné : "+$("#productName").val()+"</p>"+
                "<p>Type : "+$("#productType").val()+"</p>"+
                "<p>Constructeur : "+$("#manufacturer").val()+"</p>"+
                "<p id='quantiteProd' style='color:red;'>Quantité : Veuillez sélectionner une valeur inférieure ou égale à "+max+"</p>"+
                "<p id='prixProd'> Prix :  €</p>";
        }
        else{
            var info = "<p>Produit sélectionné : "+$("#productName").val()+"</p>"+
                "<p>Type : "+$("#productType").val()+"</p>"+
                "<p>Constructeur : "+$("#manufacturer").val()+"</p>"+
                "<p id='quantiteProd'>Quantité : "+qtt+"</p>"+
                "<p id='prixProd'> Prix : "+$("#productCost").val()*qtt+"€</p>";
        }
        $("#infoOrder").append(info);
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        $("#erreur").html("Erreur: " + status + " : " + message);
}


