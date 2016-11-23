/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import models.DataAccess;

/**
 *
 * @author マルゴ
 */
@WebServlet(name = "AddPurchaseController", urlPatterns = {"/AddPurchaseController"})
public class AddPurchaseController extends HttpServlet {
    
    public DataSource getDataSource() throws SQLException {
		org.apache.derby.jdbc.ClientDataSource ds = new org.apache.derby.jdbc.ClientDataSource();
		ds.setDatabaseName("sample");
		ds.setUser("app");
		ds.setPassword("app");
		// The host on which Network Server is running
		ds.setServerName("localhost");
		// port on which Network Server is listening
		ds.setPortNumber(1527);
		return ds;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String jspView;
		if (null != action) {
                    jspView = "authentifie.jsp";
			switch (action) {
				case "confirmAjout":
					jspView = "test.jsp";
					break;
				case "ajout":
					jspView = "ajout.jsp";
					break;
                                case "accueil":
					jspView = "accueil.jsp";
					break;
			}
		}
                else{jspView = "authentifie.jsp";}
                request.getRequestDispatcher(jspView).forward(request, response);
        /*try {
            // Créér le DAO avec sa source de données
            DataAccess dao = new DataAccess(getDataSource());
            // On récupère les paramètres de la requête
            String productID = request.getParameter("product");
            String quantity = request.getParameter("qtt");
            String freightCompany = request.getParameter("freightCompany");
            int productIdInt = Integer.parseInt(productID);
            int quantityInt = Integer.parseInt(quantity);
            // En fonction des paramètres, on initialise les variables utilisées dans les JSP
            // Et on choisit la vue (page JSP) à afficher
            boolean test = dao.addPurchaseOrder(1,productIdInt,quantityInt,freightCompany);
            if (test){
                request.setAttribute("message","L'ajout du bon de commande a bien été pris en compte.");
                jspView = "authentifie.jsp";
            }
            else{
                request.setAttribute("messageErreur","Le bon de commande ne s'est pas enregistré correctement.");
                jspView = "erreur.jsp";
            }
            // On continue vers la page JSP sélectionnée
            request.getRequestDispatcher(jspView).forward(request, response);
             
        } catch (Exception ex) {
			// on stocke le message d'erreur pour utilisation dans la JSP
			request.setAttribute("exception", ex.getMessage());
			// On va vers la page d'affichage
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
        }*/
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
