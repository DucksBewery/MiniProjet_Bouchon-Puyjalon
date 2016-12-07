<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Connexion</title>
        <!-- On charge JQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <link rel="stylesheet" media="screen" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" media="screen" type="text/css" href="css/style.css"/>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    </head>
    <body>
        
        <div id="entete" class="container-fluid">
           <div class="col-sm-5" id="nomDuSIte">V/M Master BDC</div>
        </div>
        
        <div style="color:red">${messageErreur}</div>
        <div class="container-fluid">
            <h1>AUTHENTIFICATION</h1>

            <form class="form-horizontal">
                <div class="form-group">
                    <label for="email" class="col-sm-1 centered">Email</label>
                    <div class="col-sm-11">
                        <input id="email" type="text" name="login" value="" placeholder="Email" accept-charset=utf-8>
                    </div>
                </div>
                <div class="form-group">
                    <label for="mdp" class="col-sm-1 centered">Password</label>
                    <div class="col-sm-11">
                        <input id="mdp" type="password" name="id_client" value="" placeholder="Mot de passe" accept-charset=utf-8>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-11">
                        <button type="submit" name="action" value="login" class="btn btn-default">Connexion</button>
                    </div>
                </div>
            </form>
            <p>Il y a actuellement ${applicationScope.numberConnected} utilisateurs de cette application</p>
        </div>
    </body>
</html>
