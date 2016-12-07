$(document).ready(// Exécuté à la fin du chargement de la page
        function () {
            fillDeletablePurchase();
            $("#purchaseList").change(fillProductAndQuantity);
        }
);

function fillDeletablePurchase() {
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
                            }
                    );
                    fillProductAndQuantity();

                }
    });
}

function fillProductAndQuantity() {
    var selected = $("#purchaseList").val();
    $.ajax({
        url: "SelectorsToJSON",
        data: {"select": selected},
        dataType: "json",
        error: showError,
        success: // La fonction qui traite les résultats
                function (result) {
                    $('#productId').val(result[3]);
                    $('#quantity').val(result[1]);
                }
    });
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    $("#erreur").html("Erreur: " + status + " : " + message);
}
