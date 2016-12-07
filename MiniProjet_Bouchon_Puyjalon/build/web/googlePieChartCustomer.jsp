<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<!DOCTYPE html>
<head>
    <title>Visualisation des ventes par client</title>
    <!-- On charge JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- On charge l'API Google -->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">

        $(document).ready();
        google.load("visualization", "1", {packages: ["corechart"]});

        // Après le chargement de la page, on fait l'appel AJAX
        google.setOnLoadCallback(doAjax);

        function drawChart(dataArray) {
            var data = google.visualization.arrayToDataTable(dataArray);
            var options = {
                title: 'Détail produits achetés',
                is3D: true
            };
            var chart = new google.visualization.PieChart(document.getElementById('piechart'));
            chart.draw(data, options);
        }

        // Afficher les ventes par client
        function doAjax() {
            var customer = parseInt($("#idCustomer").val());
            $.ajax({
                url: "salesByOneCustomer",
                data: {"customerId": customer},
                dataType: "json",
                success: // La fonction qui traite les résultats
                        function (result) {
                            // On reformate le résultat comme un tableau
                            var chartData = [];
                            // On met le descriptif des données
                            chartData.push(["Produit", "Achats"]);
                            for (var produit in result) {
                                chartData.push([produit, result[produit]]);
                            }
                            // On dessine le graphique
                            drawChart(chartData);
                        },
                error: showError
            });
        }

        // Fonction qui traite les erreurs de la requête
        function showError(xhr, status, message) {
            alert("Erreur: " + status + " : " + message);
        }

    </script>
</head>
<body>
    <div class="container-fluid">
        <h1>Statistiques de ${utilisateur.name}</h1>
        <input type="hidden" id="idCustomer" value="${utilisateur.customerId}">
        <a href='salesByOneCustomer?customerId=${utilisateur.customerId}' target="_blank">Voir les données brutes</a><br>
        <!-- Le graphique apparaît ici -->
        <div id="piechart" style="width: 900px; height: 500px;"></div>
    </div>
</body>