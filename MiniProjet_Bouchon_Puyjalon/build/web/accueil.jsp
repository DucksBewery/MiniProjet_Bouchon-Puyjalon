 <%@include file="entete.jsp" %>
            <h1>Bonjour : ${utilisateur.name}</h1>
            <p> ${message}</p>
		<ul>
			<li>
				<a href="ajout.jsp" name="action" value="ajout">Ajouter un bon de commande</a>
			</li>
			<li>
				<a href="modification.jsp">Modifier un bon de commande</a>
			</li>
			<li>
                            <a href="suppr.jsp">suppression BDC</a>
			</li>
                        <li>
                            <a href="googlePieChart.jsp">Afficher les statistiques du site</a>
			</li>
                        <li>
                            <a href="googlePieChartCustomer.jsp">Afficher les statistiques de l'utilisateur</a>
			</li>
		</ul>
            
	</body>
</html>
