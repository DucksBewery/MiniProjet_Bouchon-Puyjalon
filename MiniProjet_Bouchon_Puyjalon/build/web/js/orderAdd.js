$(document).ready(// execute apres le chargement de la page
        function () {
            fillAvailableProducts(); // cree la liste des produits disponibles
            fillFreightCompanies(); // cree la liste des companies de livraison disponibles
            $("#productList").change(showPurchaseDetails); // affiche les details a la selection d'un produit
            $("#quantity").change(changeDetails); // change les details au changement d'une quantite
        }
);

/*
 * Remplie la liste deroulante #productList avec les produits disponibles de la 
 * base de donnees
 */
function fillAvailableProducts() {
    $.ajax({ // appel ajax pour recuperer les informations des produits disponibles
        url: "availableProducts",
        dataType: "json",
        error: showError,
        success: // La fonction qui traite les résultats
                function (result) {
                    var select = $('#productList');
                    $.each(result, // pour chaque produit
                            function (key) {
                                // ajout d'une option dans le select
                                var productCode = result[key]["productId"];
                                var productDesc = result[key]["description"];
                                var option = new Option(productDesc, productCode);
                                select.append($(option));
                            }
                    );
                    // affiche les details de la commande
                    showPurchaseDetails();
                }
    });
}

/*
 * Remplie la liste deroulante #deliveryList avec les compagnies de livraison
 * de la base de donnees
 */
function fillFreightCompanies() {
    $.ajax({ // appel ajax pour recuperer les informations des compagnies de livraison
        url: "freightCompanies",
        dataType: "json",
        error: showError,
        success: 
                function (result) {
                    var select = $('#deliveryList');
                    // Pour chaque état dans le résultat
                    $.each(result, // pour chaque compagnie
                            function (key) {
                                // ajout d'une option dans le select
                                var freightCompanyName = result[key];
                                var option = new Option(freightCompanyName, freightCompanyName);
                                select.append($(option));
                            }
                    );
                }
    });
}

/*
 * Affiche les details de la commande pour un produit selectionne
 */
function showPurchaseDetails() {
    var selectedProduct = $("#productList").val(); // le produit selectionne
    $.ajax({ // appel ajax pour recuperer les informations du produit
        url: "productInfoToJSON",
        data: {"prod": selectedProduct},
        dataType: "json",
        success:
                function (result) {
                    // vide l'emplacement des details
                    $("#infoOrder").empty();
                    // remplie les informations
                    $("#quantity").val(1);
                    var productCost = result["productCost"];
                    var rate = result["productRate"];
                    var total = productCost - ((productCost * rate) / 100);
                    var info = "<p>Produit sélectionné : " + result["description"] + "</p>" +
                            "<p>Type : " + result["productType"] + "</p>" +
                            "<p>Constructeur : " + result["manufacturer"] + "</p>" +
                            "<p id='quantiteProd'>Quantité : 1</p>" +
                            "<p id='prixProd'>Prix du produit : " + productCost.toFixed(2) + "€</p>" +
                            "<p id='remise'>Remise : " + rate.toFixed(0) + "%</p>" +
                            "<p id='total'>Total à payer : " + total.toFixed(2) + "€</p>";
                    $("#infoOrder").append(info);
                    // ajoute des informations cachees pour le formulaire
                    $("#manufacturer").val(result["manufacturer"]);
                    $("#productName").val(result["description"]);
                    $("#productType").val(result["productType"]);
                    $("#productCost").val(result["productCost"]);
                    $("#productRate").val(result["productRate"]);
                    $("#quantityMax").val(result["quantityInStock"]);
                    $("#quantity").attr({"max": result["quantityInStock"]});
                },
        error: showError
    });
}

/*
 * Change les details avec la quantite
 */
function changeDetails() {
    // vide l'emplacement des details
    $("#infoOrder").empty();
    // le remplie avec les informations recalculees
    var qtt = parseInt($("#quantity").val());
    var max = parseInt($("#quantity").attr("max"));
    var cost = parseFloat($("#productCost").val());
    var rate = parseFloat($("#productRate").val());
    var total = (cost * qtt) - (((cost * qtt) * rate) / 100);
    if (qtt > max) {
        var info = "<p>Produit sélectionné : " + $("#productName").val() + "</p>" +
                "<p>Type : " + $("#productType").val() + "</p>" +
                "<p>Constructeur : " + $("#manufacturer").val() + "</p>" +
                "<p id='quantiteProd' style='color:red;'>Quantité : Veuillez sélectionner une valeur inférieure ou égale à " + max + "</p>" +
                "<p id='prixProd'> Prix du produit :  €</p>" +
                "<p id='remise'>Remise : %</p>" +
                "<p id='total'>Total à payer : €</p>";
    } else {
        var info = "<p>Produit sélectionné : " + $("#productName").val() + "</p>" +
                "<p>Type : " + $("#productType").val() + "</p>" +
                "<p>Constructeur : " + $("#manufacturer").val() + "</p>" +
                "<p id='quantiteProd'>Quantité : " + qtt + "</p>" +
                "<p id='prixProd'> Prix du produit : " + cost.toFixed(2) + "€</p>" +
                "<p id='remise'>Remise : " + rate.toFixed(0) + "%</p>" +
                "<p id='total'>Total à payer : " + total.toFixed(2) + "€</p>";
    }
    $("#infoOrder").append(info);
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    $("#erreur").html("Erreur: " + status + " : " + message);
}


