<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="entete.jsp" %>
<!DOCTYPE html>
<head>
	<title>Visualisation des ventes par client</title>
	<!-- On charge JQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<!-- On charge l'API Google -->
        <link rel="stylesheet" media="screen" type="text/css" href="css/style.css"/>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
        
		google.load("visualization", "1", {packages: ["corechart"]});

		// Après le chargement de la page, on fait l'appel AJAX
		google.setOnLoadCallback(doAjax);
		
		function drawChart(dataArray) {
			var data = google.visualization.arrayToDataTable(dataArray);
			var options = {
				title: 'Ventes par client',
				is3D: true
			};
			var chart = new google.visualization.PieChart(document.getElementById('piechart'));
			chart.draw(data, options);
		}

		// Afficher les ventes par client
		function doAjax() {
			$.ajax({
				url: "salesByCustomer",
				dataType: "json",
				success: // La fonction qui traite les résultats
					function (result) {
						// On reformate le résultat comme un tableau
						var chartData = [];
						// On met le descriptif des données
						chartData.push(["Client", "Ventes"]);
						for(var client in result) {
							chartData.push([client, result[client]]);
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
	<h1>Statistiques : ventes par client</h1>
	<a href='salesByCustomer' target="_blank">Voir les données brutes</a><br>
	<!-- Le graphique apparaît ici -->
	<div id="piechart" style="width: 900px; height: 500px;"></div>
</body>