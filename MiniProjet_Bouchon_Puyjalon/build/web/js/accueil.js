$(document).ready(// Exécuté à la fin du chargement de la page
        function () {
            fillUpdatablePurchase();
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
                    $("#myPurchaseList").empty();
                    $("#myPurchaseList").append("<tr>" +
                            "<th class='colonnesTableauAccueil'>Produit</td>" +
                            "<th class='colonnesTableauAccueil'>Quantit&eacute;</td>" +
                            "<th class='colonnesTableauAccueil'>Co&ucirc;t total</td>" +
                            "<th colspan='2' class='colonnesTableauAccueil'></td>" +
                            "</tr>");
                    $.each(result,
                            function (key) {
                                // On ajoute une option dans le select
                                var purchaseId = result[key]["purchaseId"];
                                var product = result[key]["product"];
                                var productId = result[key]["productId"];
                                var quantity = result[key]["quantity"];
                                var cost = result[key]["cost"];
                                var purchases = "<tr>" +
                                        "<td class='colonnesTableauAccueil'>" + product + "</td>" +
                                        "<td class='colonnesTableauAccueil'>" + quantity + "</td>" +
                                        "<td class='colonnesTableauAccueil'>" + cost + "</td>" +
                                        "<td><form action='UpdateOrderController' method='POST'>" +
                                        "<input type='hidden' name='purchaseId' value='" + purchaseId + "'>" +
                                        "<input type='hidden' name='productId' value='" + productId + "'>" +
                                        "<button type='submit' name='action' value='Modifier' class='btn btn-warning'>Modifier</button>" +
                                        "</form></td>" +
                                        "<td><form action='DeletePurchaseController' method='POST'>" +
                                        "<input type='hidden' name='purchaseId' value='" + purchaseId + "'>" +
                                        "<input type='hidden' name='productId' value='" + productId + "'>" +
                                        "<input type='hidden' name='quantity' value='" + quantity + "'>" +
                                        "<button type='submit' name='action' value='Supprimer' class='btn btn-danger'>Supprimer</button>" +
                                        "</form></td>" +
                                        "</tr>";
                                $("#myPurchaseList").append(purchases);
                            }
                    );
                }
    });
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    $("#erreur").html("Erreur: " + status + " : " + message);
}