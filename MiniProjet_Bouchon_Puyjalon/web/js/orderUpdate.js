$(document).ready(// execute apres le chargement de la page
        function () {
            fillFreightCompanies(); // cree la liste des compagnies de livraison
            fillSelectors(); // complete les champs de moification avec les informations de la commande
            $("#quantityUpdated").change(showPurchaseDetails); // affiche les details de la commande en fonction de la quantite
        }
);

/*
 * Remplie la liste deroulante #deliveryList avec les compagnies de livraison
 * de la base de donnees
 */
function fillFreightCompanies() {
    $.ajax({// appel ajax pour recuperer les informations des compagnies de livraison
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
 * Insere les valeurs de la commande en cours de modification dans les champs de
 * selection de la quantite et de la compagnie de livraison
 */
function fillSelectors() {
    var selected = $("#purchaseId").val(); // la commande en cours de modification
    $.ajax({// appel ajax pour recuperer les informations de la commande
        url: "SelectorsToJSON",
        data: {"select": selected},
        dataType: "json",
        error: showError,
        success:
                function (result) {
                    $("#product").empty(); // vide le champs produit
                    $("#product").append(result[0]); // le rempli avec une valeur non modifiable
                    // remplie les champs de selection de la quantite et de la compagnie de livraison
                    $("#quantityUpdated").val(result[1]);
                    $("#purchaseQuantity").val(result[1]);
                    $("#deliveryList").val(result[2]);
                    showPurchaseDetails(); // actualise les details de la commande
                }
    });
}

/*
 * Affiche les details de la commande pour un produit selectionne
 */
function showPurchaseDetails() {
    var selectedProduct = $("#productId").val();// le produit selectionne
    $.ajax({ // appel ajax pour recuperer les informations du produit
        url: "productInfoToJSON",
        data: {"prod": selectedProduct},
        dataType: "json",
        success:
                function (result) {
                    // vide l'emplacement des informations
                    $("#infoOrder").empty();
                    // remplie les informations
                    var qtt = parseInt($("#quantityUpdated").val());
                    var max = result["quantityInStock"] + parseInt($("#purchaseQuantity").val());
                    var productCost = result["productCost"];
                    var rate = result["productRate"];
                    var total = (productCost * qtt) - (((productCost * qtt) * rate) / 100);
                    if (qtt > max) {
                        var info = "<p>Produit sélectionné : " + result["description"] + "</p>" +
                                "<p>Type : " + result["productType"] + "</p>" +
                                "<p>Constructeur : " + result["manufacturer"] + "</p>" +
                                "<p id='quantiteProd' style='color:red;'>Quantité : Veuillez sélectionner une valeur inférieure ou égale à " + max + "</p>" +
                                "<p id='prixProd'> Prix du produit :  €</p>" +
                                "<p id='remise'>Remise : %</p>" +
                                "<p id='total'>Total à payer : €</p>";
                    } else {
                        var info = "<p>Produit sélectionné : " + result["description"] + "</p>" +
                                "<p>Type : " + result["productType"] + "</p>" +
                                "<p>Constructeur : " + result["manufacturer"] + "</p>" +
                                "<p id='quantiteProd'>Quantité : " + qtt + "</p>" +
                                "<p id='prixProd'>Prix du produit : " + productCost.toFixed(2) + "€</p>" +
                                "<p id='remise'>Remise : " + rate.toFixed(0) + "%</p>" +
                                "<p id='total'>Total à payer : " + total.toFixed(2) + "€</p>";
                    }
                    $("#infoOrder").append(info);
                    // ajoute des informations cachees pour le formulaire
                    $("#productName").val(result["description"]);
                    $("#manufacturer").val(result["manufacturer"]);
                    $("#productType").val(result["productType"]);
                    $("#productCost").val(productCost);
                    $("#quantityMax").val(max);
                    $("#productRate").val(rate);
                    $("#quantity").attr({"max": max});
                },
        error: showError
    });
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    $("#erreur").html("Erreur: " + status + " : " + message);
}