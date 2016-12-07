<%-- 
    Document   : entete
    Created on : 17 nov. 2016, 16:11:07
    Author     : Margot Puyjalon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <link rel="stylesheet" media="screen" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" media="screen" type="text/css" href="css/style.css"/>
        <title>V/M Master</title>
    </head>

    <body>
        <div id="entete" class="container-fluid">
            <div class="col-sm-5" id="nomDuSIte">V/M Master BDC</div>
            <div class="col-sm-5"><p id="messageInfo"> ${message}</p></div>
            <div class="col-sm-2">

                <form class="form-inline" method="POST" action="AuthentificationController"> <!-- l'action par dÃ©faut est l'URL courant, qui va rappeler la servlet -->
                    <a href="accueil.jsp" name='action' value='accueil'><button type="button" class="btn btn-info">Accueil</button></a>
                    <button type="submit" class="btn btn-primary" name='action' value='logout'>Deconnexion</button> 
                </form>
            </div>
        </div>