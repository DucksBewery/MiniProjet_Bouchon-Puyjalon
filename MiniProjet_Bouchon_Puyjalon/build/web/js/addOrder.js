$(document).ready(// Exécuté à la fin du chargement de la page
        function () {
            fillAvailableProducts();
            fillFreightCompanies();
            $("#productList").change(showPurchaseDetails);
            $("#quantity").change(changeDetails);
        }
);

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

function showPurchaseDetails() {
    var selectedProduct = $("#productList").val();
    $.ajax({
        url: "productInfoToJSON",
        data: {"prod": selectedProduct},
        dataType: "json",
        success: // La fonction qui traite les résultats
                function (result) {
                    $("#infoOrder").empty();
                    $("#quantity").val(1);
                    var productCost = result["productCost"];
                    var rate = result["productRate"];
                    var total = productCost - ((productCost * rate)/100);
                    var info = "<p>Produit sélectionné : " + result["description"] + "</p>" +
                            "<p>Type : " + result["productType"] + "</p>" +
                            "<p>Constructeur : " + result["manufacturer"] + "</p>" +
                            "<p id='quantiteProd'>Quantité : 1</p>" +
                            "<p id='prixProd'>Prix du produit : " + productCost.toFixed(2) + "€</p>" +
                            "<p id='remise'>Remise : " + rate.toFixed(0) + "%</p>" +
                            "<p id='total'>Total à payer : " + total.toFixed(2) + "€</p>";
                    $("#infoOrder").append(info);
                    $("#productName").val(result["description"]);
                    $("#manufacturer").val(result["manufacturer"]);
                    $("#productType").val(result["productType"]);
                    $("#productCost").val(result["productCost"]);
                    $("#quantityMax").val(result["quantityInStock"]);
                    $("#productRate").val(result["productRate"]);
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
    var rate = parseFloat($("#productRate").val());
    var total = (productCost*qtt) - (((productCost*qtt) * rate)/100);
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
                "<p id='prixProd'> Prix du produit : " + productCost.toFixed(2) + "€</p>" +
                "<p id='remise'>Remise : " + rate.toFixed(0) + "%</p>" +
                "<p id='total'>Total à payer : " + total.toFixed(2) + "€</p>";
    }
    $("#infoOrder").append(info);
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    $("#erreur").html("Erreur: " + status + " : " + message);
}


