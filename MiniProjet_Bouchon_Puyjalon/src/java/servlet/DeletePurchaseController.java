/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Float.parseFloat;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import models.DataAccess;

/**
 *
 * @author Margot
 */
@WebServlet(name = "DeletePurchaseController", urlPatterns = {"/DeletePurchaseController"})
public class DeletePurchaseController extends HttpServlet {

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
        String jspView = "accueil.jsp";
        if (null != action) {
            switch (action) {
                case "Supprimer":
                    request.setAttribute("purchaseId",request.getParameter("purchaseId"));
                    request.setAttribute("productId",request.getParameter("productId"));
                    request.setAttribute("quantity",request.getParameter("quantity"));
                    jspView = "suppr.jsp";
                    break;
                case "confirmSuppr":
                    if (deletePurchaseOrderController(request)) {
                        request.setAttribute("message", "Votre commande a bien été suprimée.");
                    } else {
                        request.setAttribute("message", "La suppression ne s'est pas effectuée correctement.");
                    }
                    break;
            }
        }
        request.getRequestDispatcher(jspView).forward(request, response);

    }

    private boolean deletePurchaseOrderController(HttpServletRequest request) {
        boolean test = false;
        try {
            // Créér le DAO avec sa source de données
            DataAccess dao = new DataAccess(getDataSource());
            // On récupère les paramètres de la requête
            String purchaseId = request.getParameter("purchaseId");
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");
            
            boolean testFirst = dao.deletePurchaseOrder(Integer.parseInt(purchaseId));
            boolean testScnd = dao.refillProduct(Integer.parseInt(productId),Integer.parseInt(quantity));
            
            if(testFirst && testScnd){
                test = true;
            }

        } catch (Exception ex) {
            Logger.getLogger("MiniProjet_Bouchon_Puyjalon").log(Level.SEVERE, "SQL Exception", ex);
        }
        return test;
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
