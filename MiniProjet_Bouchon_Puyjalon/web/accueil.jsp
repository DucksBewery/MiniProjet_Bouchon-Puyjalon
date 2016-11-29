<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
	<head>
		<title>COURS JAVA</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" media="screen" type="text/css" title="stylepage" href="style.css">
	</head>
	<body>
            <h1>Bonjour : ${utilisateur.name}</h1>
            <p> ${message}</p>
		<ul>
			<li>
				<a href="ajout.jsp" name="action" value="ajout">ajout BDC</a>
			</li>
			<li>
				<a href="modification.jsp">mmodif BDC</a>
			</li>
			<li>
				<a href="suppr.jsp">suppression BDC</a>
			</li>
		</ul>
	</body>
</html>
