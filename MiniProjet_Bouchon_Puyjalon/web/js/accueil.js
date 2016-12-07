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
                            "<td>Produit</td>" +
                            "<td>Quantit&eacute;</td>" +
                            "<td>Co&ucirc;t total</td>" +
                            "<td></td>" +
                            "<td></td>" +
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
                                        "<td>" + product + "</td>" +
                                        "<td>" + quantity + "</td>" +
                                        "<td>" + cost + "</td>" +
                                        "<td>" +
                                            "<form action='UpdateOrderController' method='POST'>" +
                                                "<input type='hidden' name='purchaseId' value='" + purchaseId + "'>" +
                                                "<input type='hidden' name='productId' value='" + productId + "'>" +
                                                "<button type='submit' name='action' value='Modifier' class='btn btn-warning'>Modifier</button>" +
                                            "</form>" +
                                            "<form action='DeletePurchaseController' method='POST'>" +
                                                "<input type='hidden' name='purchaseId' value='" + purchaseId + "'>" +
                                                "<input type='hidden' name='productId' value='" + productId + "'>" +
                                                "<input type='hidden' name='quantity' value='" + quantity + "'>" +
                                                "<button type='submit' name='action' value='Supprimer' class='btn btn-danger'>Supprimer</button>" +
                                            "</form>"+
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