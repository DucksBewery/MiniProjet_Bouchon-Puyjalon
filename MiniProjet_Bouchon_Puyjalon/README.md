# MiniProjet_Bouchon-Puyjalon
un 'tit truc vite fait pour les cours de Java

22/11/16 : Version 1-1 déployée !! Variables de session opératinelles et prêtes à l'emploi !
CONVENTION
Nom des variables : variableName
Nom des classes : ClassName
Nom des tables : TABLE_NAME



CONSIGNES

A r?aliser par groupe de 2 ou 3.

On se propose de r?aliser une application web J2EE, en respectant au mieux l'architecture MVC (DAO, JSP, Servlet).

On utilise la base de donn?es "sample" fournie avec NetBeans.

Sch?ma de la base de donn?es : les clients (CUSTOMER)passent des bons de commande (PURCHASE_ORDER), les bons de commandes r?f?rencent des produits(PRODUCT), les produits appartiennent a des cat?gories (PRODUCT_CODE), les cat?gories sont associ?es ? un taux de remise (DISCOUNT_CODE).

Fonctionnalit?s de l'application web ? d?velopper :

Etape 1 :

- Le client doit s'authentifier pour acc?der ? l'?dition des bons de commandes.

On utilisera les champs suivants de la table CUSTOMER pour l'authentification :

login : EMAIL
password : CUSTOMER_ID
Une fois connect?, l'application doit permettre au client l'?dition compl?te des bons de commande (ajout, modification, suppression).

Etape 2 :JavaScript / AJAX

Afficher sous forme graphique de statistiques pour le client connect?

par exemple: chiffre d'affaire par p?riode, par cat?gorie d'articles...

Pour ce faire, on utilisera la biblioth?que javascript Google Charts

Note : les groupes peuvent proposer leur propre sujet, de complexit? ?quivalente : base de donn?es non triviale, possibilit? de faire des ajouts/modifications dans la base, opportunit? d'avoir une repr?sentation graphique des donn?es.