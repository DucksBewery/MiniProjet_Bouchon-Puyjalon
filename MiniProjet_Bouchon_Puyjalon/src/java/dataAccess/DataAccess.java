package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import models.Customer;
import models.Product;
import models.PurchaseOrder;

public class DataAccess {

    private final DataSource myDataSource;

    /**
     * Constructeur
     *
     * @param dataSource
     */
    public DataAccess(DataSource dataSource) {
        this.myDataSource = dataSource;

    }

    // -----------------------------------------------------------------------//
    // ------------------------ CONNECTION -----------------------------------//
    // -----------------------------------------------------------------------//
    
    /**
     * Cherche un client dans la base de donnees
     *
     * @param login de connection du client
     * @param mdp de connection du client
     * @return un objet Customer avec toutes les informations du client si
     * existant dans la base de donnees, null sinon
     * @throws SQLException
     */
    public Customer verifAuthentification(String login, String mdp) throws SQLException {

        Customer utilisateur = null;

        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL = ? AND CUSTOMER_ID = CAST( ? AS INTEGER)";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, mdp);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // si le client existe dans la base de donnees complete utilisateur
                    String name = rs.getString("NAME");
                    String address = rs.getString("ADDRESSLINE1");
                    utilisateur = new Customer(Integer.valueOf(mdp), name, address);
                }
            }
        }

        return utilisateur;
    }

    // -----------------------------------------------------------------------//
    // ------------------------ AJOUT COMMANDE -------------------------------//
    // -----------------------------------------------------------------------//
    /**
     * Ajoute un bon de cmmande a la base de donnees et met a jour les produits
     * disponibles
     *
     * @param customerId
     * @param productId
     * @param quantity
     * @param totalCost
     * @param freightCompany
     * @param quantityMax
     * @return true si tout s'est bien passé, false sinon
     * @throws SQLException
     */
    public boolean addPurchaseOrder(int customerId, int productId, int quantity, float totalCost, String freightCompany, int quantityMax) throws SQLException {

        boolean result = false;

        String sql2;// requete de mise a jour du produit
        String sql = "INSERT INTO PURCHASE_ORDER "
                + "VALUES ((SELECT MAX(ORDER_NUM) FROM PURCHASE_ORDER)+1,?,?,?,?,CURRENT_DATE,NULL,?)"; // requete d'ajout de la commande
        if (quantityMax - quantity == 0) {
            sql2 = "UPDATE PRODUCT SET QUANTITY_ON_HAND = ?, AVAILABLE = 'FALSE' WHERE PRODUCT_ID = ?";
        } else {
            sql2 = "UPDATE PRODUCT SET QUANTITY_ON_HAND = ? WHERE PRODUCT_ID = ?";
        }

        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql);
                PreparedStatement stmt2 = connection.prepareStatement(sql2)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setFloat(4, totalCost);
            stmt.setString(5, freightCompany);

            stmt2.setInt(1, quantityMax - quantity);
            stmt2.setInt(2, productId);

            // retourne le nombre de ligne modifiees 
            int rs = stmt.executeUpdate();
            int rs2 = stmt2.executeUpdate();
            if (rs != 0 && rs2 != 0) {
                result = true;
            }

            stmt.close();
            stmt2.close();
            connection.close();
        }
        return result;
    }

    /**
     * Recupere la liste des produits dont la quantite est superieur a 0
     *
     * @return la liste des produits disponibles
     * @throws SQLException
     */
    public List<Product> availableProductsList() throws SQLException {

        List<Product> produits = new LinkedList<>();

        String sql = "SELECT PRODUCT_ID,DESCRIPTION FROM APP.PRODUCT WHERE AVAILABLE = 'TRUE' ORDER BY DESCRIPTION";

        try (// Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                Statement stmt = connection.createStatement()) {

            // Un ResultSet pour parcourir les enregistrements du résultat
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Product produit = new Product(rs.getInt("PRODUCT_ID"));
                    produit.setDescription(rs.getString("DESCRIPTION"));
                    produits.add(produit);
                }
                rs.close();
            }
            stmt.close();
            connection.close();
        }
        return produits;
    }

    /**
     * Recupere la liste des differentes compagnies de livraison
     *
     * @return le liste des compagnies de livraison
     * @throws SQLException
     */
    public List<String> getFreightCompanies() throws SQLException {

        List<String> companies = new LinkedList<>();

        String sql = "SELECT DISTINCT FREIGHT_COMPANY FROM PURCHASE_ORDER ORDER BY FREIGHT_COMPANY";

        try (// Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                Statement stmt = connection.createStatement()) {

            // Un ResultSet pour parcourir les enregistrements du résultat
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    companies.add(rs.getString("FREIGHT_COMPANY"));
                }
                rs.close();
            }
            stmt.close();
            connection.close();
        }
        return companies;
    }

    /**
     * Recupere les informations liees a un produit selectionne
     * 
     * @param idProduct
     * @return un objet Product contenant toutes les informations du produit 
     * selectionne si tout s'est bien passe, null sinon
     * @throws SQLException 
     */
    public Product productInformations(int idProduct) throws SQLException {
        
        Product produit = new Product(idProduct);
        
        String sql = "SELECT PRODUCT_ID,PURCHASE_COST,QUANTITY_ON_HAND,p.DESCRIPTION,m.NAME AS MANUFACTURER_NAME,d.RATE,c.DESCRIPTION AS PROD_TYPE "
                + "FROM PRODUCT p "
                + "INNER JOIN MANUFACTURER m ON (p.MANUFACTURER_ID = m.MANUFACTURER_ID) "
                + "INNER JOIN PRODUCT_CODE c ON (p.PRODUCT_CODE = c.PROD_CODE) "
                + "INNER JOIN DISCOUNT_CODE d ON (c.DISCOUNT_CODE = d.DISCOUNT_CODE) "
                + "WHERE p.PRODUCT_ID = ?";
        
        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduct);
            
            // Un ResultSet pour parcourir les enregistrements du résultat
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produit.setManufacturer(rs.getString("MANUFACTURER_NAME"));
                    produit.setDescription(rs.getString("DESCRIPTION"));
                    produit.setProductCost(rs.getFloat("PURCHASE_COST"));
                    produit.setProductRate(rs.getFloat("RATE"));
                    produit.setProductType(rs.getString("PROD_TYPE"));
                    produit.setQuantityInStock(rs.getInt("QUANTITY_ON_HAND"));
                }
                rs.close();
                
            } catch (SQLException e) {
                System.out.println("PROBLEME : " + e);
            }
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("PROBLEME : " + e);
        }

        return produit;
    }

    // -----------------------------------------------------------------------//
    // ------------------------ MODIF COMMANDE -------------------------------//
    // -----------------------------------------------------------------------//
    
    /**
     * Met a jour un bon de cmmande a la base de donnees et met a jour les
     * produits disponibles
     *
     * @param purchaseId
     * @param productId
     * @param quantity
     * @param totalCost
     * @param freightCompany
     * @param quantityMax
     * @return true si tout s'est bien passé, false sinon
     * @throws SQLException
     */
    public boolean updatePurchaseOrder(int purchaseId, int productId, int quantity, float totalCost, String freightCompany, int quantityMax) throws SQLException {

        boolean result = false;

        // les requetes de mise a jour
        String sql2;
        String sql = "UPDATE PURCHASE_ORDER "
                + "SET PRODUCT_ID = ?, QUANTITY = ?, SHIPPING_COST = ?, SALES_DATE = CURRENT_DATE, FREIGHT_COMPANY = ?"
                + "WHERE ORDER_NUM = ?";
        if (quantityMax - quantity == 0) {
            sql2 = "UPDATE PRODUCT SET QUANTITY_ON_HAND = ?, AVAILABLE = 'FALSE' WHERE PRODUCT_ID = ?";
        } else {
            sql2 = "UPDATE PRODUCT SET QUANTITY_ON_HAND = ? WHERE PRODUCT_ID = ?";
        }

        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter chaque requete
                PreparedStatement stmt = connection.prepareStatement(sql);
                PreparedStatement stmt2 = connection.prepareStatement(sql2)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);
            stmt.setFloat(3, totalCost);
            stmt.setString(4, freightCompany);
            stmt.setInt(5, purchaseId);

            stmt2.setInt(1, quantityMax - quantity);
            stmt2.setInt(2, productId);

            // retourne le nombre de ligne modifiees 
            int rs = stmt.executeUpdate();
            int rs2 = stmt2.executeUpdate();
            if (rs != 0 && rs2 != 0) {
                result = true;
            }

            stmt.close();
            stmt2.close();
            connection.close();
        }
        return result;
    }

    /**
     * Recupere la liste des commandes pour un utilsateur dont la date
     * d'expedition n'est pas encore renseignée
     *
     * @param customer identifiant du client
     * @return la liste des commandes modifiables
     * @throws SQLException
     */
    public List<PurchaseOrder> updatablePurchaseList(int customer) throws SQLException {

        List<PurchaseOrder> purchases = new LinkedList<>();

        String sql = "SELECT o.ORDER_NUM, p.PRODUCT_ID, p.DESCRIPTION AS PRODUIT, o.QUANTITY, o.SHIPPING_COST, o.FREIGHT_COMPANY "
                + "FROM PURCHASE_ORDER o "
                + "INNER JOIN PRODUCT p ON (p.PRODUCT_ID = o.PRODUCT_ID)"
                + "WHERE o.CUSTOMER_ID = ? AND o.SHIPPING_DATE IS NULL "
                + "ORDER BY o.SALES_DATE DESC";

        try (// Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer);

            // Un ResultSet pour parcourir les enregistrements du résultat
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PurchaseOrder purchase = new PurchaseOrder(customer);
                    purchase.setPurchaseId(rs.getInt("ORDER_NUM"));
                    purchase.setProduct(rs.getString("PRODUIT"));
                    purchase.setProductId(rs.getInt("PRODUCT_ID"));
                    purchase.setQuantity(rs.getInt("QUANTITY"));
                    purchase.setCost(rs.getFloat("SHIPPING_COST"));
                    purchase.setFreightCompany(rs.getString("FREIGHT_COMPANY"));
                    purchases.add(purchase);
                }
                rs.close();

            } catch (SQLException e) {
                System.out.println("PROBLEME : " + e);
            }
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("PROBLEME : " + e);
        }
        return purchases;
    }
    
    /**
     * Recupere les informations d'une commande donnee
     * 
     * @param idPurchase
     * @return la liste des informations de la commande
     * @throws SQLException 
     */
    public ArrayList<String> selectorsInformations(int idPurchase) throws SQLException {
        
        ArrayList<String> selectors = new ArrayList<>();
        
        String sql = "SELECT o.PRODUCT_ID, p.DESCRIPTION, QUANTITY, FREIGHT_COMPANY FROM PURCHASE_ORDER o INNER JOIN PRODUCT p ON (p.PRODUCT_ID=o.PRODUCT_ID) WHERE ORDER_NUM = ?";
        
        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPurchase);
            
            // Un ResultSet pour parcourir les enregistrements du résultat
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    selectors.add(rs.getString("DESCRIPTION"));
                    selectors.add(rs.getString("QUANTITY"));
                    selectors.add(rs.getString("FREIGHT_COMPANY"));
                    selectors.add(rs.getString("PRODUCT_ID"));
                }
                rs.close();
                
            } catch (SQLException e) {
                System.out.println("PROBLEME : " + e);
            }
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("PROBLEME : " + e);
        }

        return selectors;
    }

    // -----------------------------------------------------------------------//
    // ------------------------ SUPPR COMMANDE -------------------------------//
    // -----------------------------------------------------------------------//
    
    /**
     * Supprime un bon de commande de la base de donnees
     *
     * @param purchaseId
     * @return true si tout s'est bien passé, false sinon
     * @throws SQLException
     */
    public boolean deletePurchaseOrder(int purchaseId) throws SQLException {

        boolean result = false;

        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";

        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, purchaseId);

            // retourne le nombre de ligne modifiees 
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                result = true;
            }

            stmt.close();
            connection.close();
        }

        return result;
    }

    /**
     * Met a jour les produits disponibles
     *
     * @param productId
     * @param quantity
     * @return true si tout s'est bien passé, false sinon
     * @throws SQLException
     */
    public boolean refillProduct(int productId, int quantity) throws SQLException {

        boolean result = false;

        String sql = "UPDATE PRODUCT SET QUANTITY_ON_HAND = QUANTITY_ON_HAND + ?, AVAILABLE = 'TRUE' WHERE PRODUCT_ID = ?";

        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);

            // retourne le nombre de ligne modifiees 
            int rs = stmt.executeUpdate();
            if (rs != 0) {
                result = true;
            }

            stmt.close();
            connection.close();
        }

        return result;
    }

    // -----------------------------------------------------------------------//
    // ------------------------ GOOGLE CHARTS --------------------------------//
    // -----------------------------------------------------------------------//
    
    /**
     * Recupere les produits achetes par un client
     *
     * @param customerId
     * @return une Map associant le nom du produit à sa quantite achetee
     * @throws SQLException
     */
    public Map<String, Double> salesByOneCustomer(int customerId) throws SQLException {
        
        Map<String, Double> result = new HashMap<>();

        String sql = "SELECT SUM(po.QUANTITY) AS SALES, p.DESCRIPTION"
                + "	      FROM PURCHASE_ORDER po"
                + "	      INNER JOIN PRODUCT p ON (po.PRODUCT_ID = p.PRODUCT_ID)"
                + "	      WHERE po.CUSTOMER_ID = ? GROUP BY p.DESCRIPTION";
        
        try (// Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            
            // Un ResultSet pour parcourir les enregistrements du résultat
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("DESCRIPTION");
                    double sales = rs.getDouble("SALES");
                    result.put(name, sales);
                }
            } catch (SQLException e) {
                System.out.println("PROBLEME : " + e);
            }
        }
        return result;
    }
}
