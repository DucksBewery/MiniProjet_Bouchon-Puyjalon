$(document).ready(// Exécuté à la fin du chargement de la page
        function () {
            fillFreightCompanies();
            fillUpdatablePurchase();
            $("#purchaseList").change(fillSelectors);
            $("#quantityUpdated").change(showPurchaseDetails);
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
                                var productId = result[key]["productId"];
                                var quantity = result[key]["quantity"];
                                var cost = result[key]["cost"];
                                var option = new Option(product + ",    x" + quantity + ",    " + cost + "€", purchase);
                                select.append($(option));
                                $('#productId').val(productId);
                            }
                    );
                    fillSelectors();
                }
    });
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

function fillSelectors() {
    var selected = $("#purchaseList").val();
    $.ajax({
        url: "SelectorsToJSON",
        data: {"select": selected},
        dataType: "json",
        error: showError,
        success: // La fonction qui traite les résultats
                function (result) {
                    $("#product").empty();
                    $("#product").append(result[0]);
                    $("#quantityUpdated").val(result[1]);
                    $("#purchaseQuantity").val(result[1]);
                    $("#deliveryList").val(result[2]);
                    showPurchaseDetails();
                }
    });
}

function showPurchaseDetails() {
    var selectedProduct = $("#productId").val();
    $.ajax({
        url: "productInfoToJSON",
        data: {"prod": selectedProduct},
        dataType: "json",
        success: // La fonction qui traite les résultats
                function (result) {
                    $("#infoOrder").empty();
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