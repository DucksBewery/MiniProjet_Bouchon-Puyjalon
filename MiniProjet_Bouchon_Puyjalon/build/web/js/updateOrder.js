$(document).ready(// Exécuté à la fin du chargement de la page
        function () {
            fillUpdatablePurchase();
            fillFreightCompanies();
            $("#purchaseList").change(fillSelectors);
            $("#quantityUpdated").change(changeDetails);
        }
);

function fillUpdatablePurchase() {
    var customerId = parseInt($("#userId").val());
    $.ajax({
        url: "purchaseToUpdate",
        data: {"customer": customerId},
        dataType: "json",
        error: showError,
        success: // La fonction qui traite les résultats
                function (result) {
                    var select = $('#purchaseList');
                    // Pour chaque état dans le résultat
                    $.each(result,
                            function (key) {
                                // On ajoute une option dans le select
                                var purchase = result[key]["purchaseId"];
                                var product = result[key]["product"];
                                var quantity = result[key]["quantity"];
                                var cost = result[key]["cost"];
                                var option = new Option(product + ",    x" + quantity + ",    " + cost + "€", purchase);
                                select.append($(option));
                            }
                    );
                    fillSelectors(result[0]["product"],result[0]["quantity"],result[0]["freightCompany"]);
                }
    });
}

function fillSelectors(product,quantity,company){
    $("#productList").val(product);
    $("#quantityUpdated").val(quantity);
    $("#deliveryList").val(company);
}

function fillFreightCompanies() {
    $.ajax({
        url: "freightCompanies",
        dataType: "json",
        error: showError,
        success: // La fonction qui traite les résultats
                function (result) {
                    var select = $('#deliveryList');
                    // Pour chaque état dans le résultat
                    $.each(result,
                            function (key) {
                                // On ajoute une option dans le select
                                var freightCompanyName = result[key];
                                var option = new Option(freightCompanyName, freightCompanyName);
                                select.append($(option));
                            }
                    );
                }
    });
}

function fillAvailableProducts() {
    $.ajax({
        url: "availableProducts",
        dataType: "json",
        error: showError,
        success: // La fonction qui traite les résultats
                function (result) {
                    var select = $('#productList');
                    $.each(result,
                            function (key) {
                                // On ajoute une option dans le select
                                var productCode = result[key]["productId"];
                                var productDesc = result[key]["description"];
                                var option = new Option(productDesc, productCode);
                                select.append($(option));
                            }
                    );
                    showPurchaseDetails();
                }
    });
}

function showPurchaseDetails() {
    var selectedProduct = $("#purchaseList").val();
    $.ajax({
        url: "productInfoToJSON",
        data: {"prod": selectedProduct},
        dataType: "json",
        success: // La fonction qui traite les résultats
                function (result) {
                    $("#infoOrder").empty();
                    $("#quantite").val(1);
                    var productCost = result["productCost"];
                    var purchaseCost = result["purchaseCost"];
                    var rate = result["productRate"];
                    var total = (productCost + purchaseCost) - rate;
                    var info = "<p>Produit sélectionné : " + result["description"] + "</p>" +
                            "<p>Type : " + result["productType"] + "</p>" +
                            "<p>Constructeur : " + result["manufacturer"] + "</p>" +
                            "<p id='quantiteProd'>Quantité : 1</p>" +
                            "<p id='prixProd'>Prix du produit : " + productCost + "€</p>" +
                            "<p id='prixCommande'>Prix de la commande : " + purchaseCost + "€</p>" +
                            "<p id='remise'>Remise : " + rate + "€</p>" +
                            "<p id='total'>Total à payer : " + total + "€</p>";
                    $("#infoOrder").append(info);
                    $("#productName").val(result["description"]);
                    $("#manufacturer").val(result["manufacturer"]);
                    $("#productType").val(result["productType"]);
                    $("#productCost").val(result["productCost"]);
                    $("#quantityMax").val(result["quantityInStock"]);
                    $("#productRate").val(result["productRate"]);
                    $("#purchaseCost").val(result["purchaseCost"]);
                    $("#quantity").attr({"max": result["quantityInStock"]});
                },
        error: showError
    });
}

function changeDetails() {
    $("#infoOrder").empty();
    var qtt = parseInt($("#quantity").val());
    var max = parseInt($("#quantity").attr("max"));
    var productCost = parseFloat($("#productCost").val());
    var purchaseCost = parseFloat($("#purchaseCost").val());
    var rate = parseFloat($("#productRate").val());
    var total = (productCost * qtt + purchaseCost) - rate;
    if (qtt > max) {
        var info = "<p>Produit sélectionné : " + $("#productName").val() + "</p>" +
                "<p>Type : " + $("#productType").val() + "</p>" +
                "<p>Constructeur : " + $("#manufacturer").val() + "</p>" +
                "<p id='quantiteProd' style='color:red;'>Quantité : Veuillez sélectionner une valeur inférieure ou égale à " + max + "</p>" +
                "<p id='prixProd'> Prix du produit :  €</p>" +
                "<p id='prixProd'> Prix de la commande :  €</p>" +
                "<p id='remise'>Remise : €</p>" +
                "<p id='total'>Total à payer : €</p>";
    } else {
        var info = "<p>Produit sélectionné : " + $("#productName").val() + "</p>" +
                "<p>Type : " + $("#productType").val() + "</p>" +
                "<p>Constructeur : " + $("#manufacturer").val() + "</p>" +
                "<p id='quantiteProd'>Quantité : " + qtt + "</p>" +
                "<p id='prixProd'> Prix du produit : " + productCost + "€</p>" +
                "<p id='prixProd'> Prix de la commande : " + purchaseCost + "€</p>" +
                "<p id='remise'>Remise : " + rate + "€</p>" +
                "<p id='total'>Total à payer : " + total + "€</p>";
    }
    $("#infoOrder").append(info);
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    $("#erreur").html("Erreur: " + status + " : " + message);
}