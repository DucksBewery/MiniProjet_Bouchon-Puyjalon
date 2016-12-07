package servletController;

import java.io.IOException;
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
import dataAccess.DataAccess;

@WebServlet(name = "UpdateOrderController", urlPatterns = {"/UpdateOrderController"})
public class UpdatePurchaseController extends HttpServlet {

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
        
        String jspView = "accueil.jsp";
        
        String action = request.getParameter("action");
        if (null != action) {
            switch (action) {
                
                case "Modifier":
                    request.setAttribute("purchaseId",request.getParameter("purchaseId"));
                    request.setAttribute("productId",request.getParameter("productId"));
                    jspView = "modification.jsp";
                    break;
                    
                case "confirmModif":
                    if (updatePurchaseOrderController(request)) {
                        request.setAttribute("message", "La modification de votre bon de commande a bien été pris en compte.");
                    } else {
                        request.setAttribute("message", "Le bon de commande ne s'est pas enregistré correctement.");
                    }
                    break;
            }
        }
        
        request.getRequestDispatcher(jspView).forward(request, response);

    }

    /**
     * Fait le lien avec le dao
     * @param request
     * @return 
     */
    private boolean updatePurchaseOrderController(HttpServletRequest request) {
        
        boolean test = false;
        
        try {
            // Créér le DAO avec sa source de données
            DataAccess dao = new DataAccess(getDataSource());
            
            // On récupère les paramètres de la requête
            String purchaseId = request.getParameter("purchaseId");
            String productID = request.getParameter("productId");
            String quantity = request.getParameter("qtt");
            String productCost = request.getParameter("productCost");
            String rate = request.getParameter("productRate");
            String freightCompany = request.getParameter("delivery"); 
            String quantityMax = request.getParameter("quantityMax");
            
            float costWithoutRate = parseFloat(productCost)*parseFloat(quantity);
            float totalCost = costWithoutRate - ((costWithoutRate * parseFloat(rate))/100);

            test = dao.updatePurchaseOrder(Integer.parseInt(purchaseId), Integer.parseInt(productID), Integer.parseInt(quantity), totalCost, freightCompany, Integer.parseInt(quantityMax));
        
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