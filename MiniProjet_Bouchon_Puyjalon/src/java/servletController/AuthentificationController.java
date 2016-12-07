package servletController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import models.Customer;
import dataAccess.DataAccess;

@WebServlet(name = "AuthentificationController", urlPatterns = {"/AuthentificationController"})
public class AuthentificationController extends HttpServlet {

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

        request.getSession(true);

        String jspView = "accueil.jsp";

        String action = request.getParameter("action");
        if (null != action) {
            switch (action) {

                case "login":
                    if (checkLogin(request)) {
                        jspView = "authentifie.jsp";
                    }
                    break;

                case "logout":
                    doLogout(request);
                    break;

                case "accueil":
                    jspView = "accueil.jsp";
                    break;
            }
        }

        // cherche utilisateur dans la session
        Object utilisateur = findCustomerInSession(request);

        if (null == utilisateur) { // L'utilisateur n'est pas connecté
            jspView = "connection.jsp";
        }

        request.getRequestDispatcher(jspView).forward(request, response);
    }

    /**
     * Fait le lien avec le dao
     * @param request
     * @return
     */
    private boolean checkLogin(HttpServletRequest request) {

        boolean test = false;

        String login = request.getParameter("login");
        String pswd = request.getParameter("id_client");

        try {
            // Créér le DAO avec sa source de données
            DataAccess dao = new DataAccess(getDataSource());
            
            Customer utilisateur = dao.verifAuthentification(login, pswd);
            
            if (utilisateur != null) { // On a trouvé la combinaison login / password
                // Stoke l'utilisateur dans la session
                HttpSession session = request.getSession(true); // démarre la session
                session.setAttribute("utilisateur", utilisateur);
                test = true;
            } else {
                request.setAttribute("messageErreur", "Identifiant ou mot de passe incorrect");
            }
        } catch (SQLException ex) {
            Logger.getLogger("MiniProjet_Bouchon_Puyjalon").log(Level.SEVERE, "SQL Exception", ex);
        }
        
        return test;
    }

    /**
     * Ferme la session
     * @param request 
     */
    private void doLogout(HttpServletRequest request) {
        // On termine la session
        request.getSession(false).invalidate();
    }

    /**
     * Cherche un utilisateur dans la session
     * @param request
     * @return un objet Object avec les informations de la session en cours, 
     * null si la session est vide
     */
    private Object findCustomerInSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session == null) ? null : session.getAttribute("utilisateur");
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
