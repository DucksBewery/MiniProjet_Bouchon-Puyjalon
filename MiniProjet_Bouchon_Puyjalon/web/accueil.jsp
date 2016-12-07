<%@include file="entete.jsp" %>

<div class="container-fluid">

    <h1>Bonjour : ${utilisateur.name}</h1>
    <input type="hidden" name="userId" id="userId" value="${utilisateur.customerId}">

    <div id="btnAjout" class="col-sm-12"><a href="ajout.jsp" name="action" value="ajout"><button id="boutonAjout" type="button" class="btn btn-success"><i class="fa fa-plus fa-5x" aria-hidden="true"></i></button></a></div>

    <table id="myPurchaseList" class="table table-striped"></table>

    <div class="container centered">
        <a href="googlePieChartCustomer.jsp">Afficher les statistiques de l'utilisateur</a>
    </div>

    <p id="erreur"></p>

    <script type="text/javascript" src="js/accueil.js"></script>

</div>

</body>
</html>
