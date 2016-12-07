package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class DataAccess {

    private final DataSource myDataSource;

    /**
     *
     * @param dataSource la source de données à utiliser
     */
    public DataAccess(DataSource dataSource) {
        this.myDataSource = dataSource;

    }

    /**
     *
     * @param email
     * @return l'identifiant correspondant au mail saisi
     * @throws SQLException
     */
    public Customer verifAuthentification(String login, String mdp) throws SQLException {

        Customer utilisateur = null;

        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL = ? AND CUSTOMER_ID = CAST( ? AS INTEGER)";
        // try-with-resources, cf. https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, mdp);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    String address = rs.getString("ADDRESSLINE1");
                    utilisateur = new Customer(Integer.valueOf(mdp), name, address);
                }
            }
        }
        // On renvoie l'utilisateur ainsi connecté
        return utilisateur;
    }

    public boolean addPurchaseOrder(int customerId, int productId, int quantity, float totalCost, String freightCompany, int quantityMax) throws SQLException {
        boolean result = false;
        String sql2;
        String sql = "INSERT INTO PURCHASE_ORDER "
                + "VALUES ((SELECT MAX(ORDER_NUM) FROM PURCHASE_ORDER)+1,?,?,?,?,CURRENT_DATE,NULL,?)";
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

    public List<Product> availableProductsList() throws SQLException {
        List<Product> produits = new LinkedList<>();
        String sql = "SELECT PRODUCT_ID,DESCRIPTION FROM APP.PRODUCT WHERE AVAILABLE = 'TRUE' ORDER BY DESCRIPTION";
        // Ouvrir une connexion
        try (Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                Statement stmt = connection.createStatement()) {
            // Un ResultSet pour parcourir les enregistrements du résultat
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    // On récupère le champ DESCRIPTION de l'enregistrement courant
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

    public List<PurchaseOrder> updatablePurchaseList(int customer) throws SQLException {
        List<PurchaseOrder> purchases = new LinkedList<>();
        String sql = "SELECT o.ORDER_NUM, p.DESCRIPTION AS PRODUIT, o.QUANTITY, o.SHIPING_COST, o.FREIGHT_COMPANY "
                + "FROM PURCHASE_ORDER o "
                + "INNER JOIN PRODUCT p ON (p.PRODUCT_ID = o.PRODUCT_ID)"
                + "WHERE o.CUSTOMER_ID = ?"
                + "ORDER BY o.SALES_DATE DESC";
        // Ouvrir une connexion
        try (Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // On récupère le champ DESCRIPTION de l'enregistrement courant
                    PurchaseOrder purchase = new PurchaseOrder(customer);
                    purchase.setPurchaseId(rs.getInt("ORDER_NUM"));
                    purchase.setProduct(rs.getString("PRODUIT"));
                    purchase.setQuantity(rs.getInt("QUANTITY"));
                    purchase.setCost(rs.getFloat("SHIPPING_COST"));
                    purchase.setFreightCompany(rs.getString("FREIGHT_COMPANY"));
                    purchases.add(purchase);
                }
                rs.close();
            }
            stmt.close();
            connection.close();
        }
        return purchases;
    }

    public List<String> getFreightCompanies() throws SQLException {
        List<String> companies = new LinkedList<>();
        String sql = "SELECT DISTINCT FREIGHT_COMPANY FROM PURCHASE_ORDER ORDER BY FREIGHT_COMPANY";
        // Ouvrir une connexion
        try (Connection connection = myDataSource.getConnection();
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
            try ( // Un ResultSet pour parcourir les enregistrements du résultat
                    ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produit.setManufacturer(rs.getString("MANUFACTURER_NAME"));
                    produit.setDescription(rs.getString("DESCRIPTION"));
                    produit.setProductCost(rs.getFloat("PURCHASE_COST"));
                    produit.setProductRate(rs.getFloat("RATE"));
                    produit.setProductType(rs.getString("PROD_TYPE"));
                    produit.setQuantityInStock(rs.getInt("QUANTITY_ON_HAND"));
                }
                rs.close();
            }
            stmt.close();
            connection.close();
        }

        return produit;
    }
    
    public Product purchaseInformations(int idProduct) throws SQLException {
        Product produit = new Product(idProduct);
        String sql = "SELECT o.ORDER_NUM, p.DESCRIPTION AS PRODUIT, o.QUANTITY, m.\"NAME\" AS MANUFACTURER, pc.DESCRIPTION AS TYPE, p.PURCHASE_COST, p.QUANTITY_ON_HAND, d.RATE "
                + "FROM PURCHASE_ORDER o "
                + "INNER JOIN PRODUCT p ON (p.PRODUCT_ID = o.PRODUCT_ID)"
                + "INNER JOIN MANUFACTURER m ON (p.MANUFACTURER_ID = m.MANUFACTURER_ID)"
                + "INNER JOIN PRODUCT_CODE pc ON (p.PRODUCT_CODE = pc.PROD_CODE)"
                + "INNER JOIN DISCOUNT_CODE d ON (pc.DISCOUNT_CODE = d.DISCOUNT_CODE)"
                + "WHERE o.CUSTOMER_ID = ?"
                + "ORDER BY o.SALES_DATE DESC";
        try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduct);
            try ( // Un ResultSet pour parcourir les enregistrements du résultat
                    ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produit.setManufacturer(rs.getString("MANUFACTURER_NAME"));
                    produit.setDescription(rs.getString("DESCRIPTION"));
                    produit.setProductCost(rs.getFloat("PURCHASE_COST"));
                    produit.setProductRate(rs.getFloat("RATE"));
                    produit.setProductType(rs.getString("PROD_TYPE"));
                    produit.setQuantityInStock(rs.getInt("QUANTITY_ON_HAND"));
                }
                rs.close();
            }
            stmt.close();
            connection.close();
        }

        return produit;
    }
    
    /**
	 * ventes par client
	 *
	 * @return Une Map associant le nom du client à son chiffre d'affaires
	 * @throws SQLException
	 */
	public Map<String, Double> salesByCustomer() throws SQLException {
		Map<String, Double> result = new HashMap<>();
		String sql = "SELECT NAME, SUM(PURCHASE_COST * QUANTITY) AS SALES" +
		"	      FROM CUSTOMER c" +
		"	      INNER JOIN PURCHASE_ORDER o ON (c.CUSTOMER_ID = o.CUSTOMER_ID)" +
		"	      INNER JOIN PRODUCT p ON (o.PRODUCT_ID = p.PRODUCT_ID)" +
		"	      GROUP BY NAME";
		try (Connection connection = myDataSource.getConnection(); 
		     Statement stmt = connection.createStatement(); 
		     ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				// On récupère les champs nécessaires de l'enregistrement courant
				String name = rs.getString("NAME");
				double sales = rs.getDouble("SALES");
				// On l'ajoute à la liste des résultats
				result.put(name, sales);
			}
		}
		return result;
	}
        
        /**
	 * ventes par client
	 *
     * @param customerId
	 * @return Une Map associant le nom du client à son chiffre d'affaires
	 * @throws SQLException
	 */
	public Map<String, Double> salesByOneCustomer(int customerId) throws SQLException {
		Map<String, Double> result = new HashMap<>();
                    

		String sql = "SELECT SUM(po.QUANTITY) AS SALES, p.DESCRIPTION" +
		"	      FROM PURCHASE_ORDER po" +
		"	      INNER JOIN PRODUCT p ON (po.PRODUCT_ID = p.PRODUCT_ID)" +
		"	      WHERE po.CUSTOMER_ID = ? GROUP BY p.DESCRIPTION";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)){ 
		     stmt.setInt(1, customerId);
                        try ( // Un ResultSet pour parcourir les enregistrements du résultat
                        ResultSet rs = stmt.executeQuery()) {
                                while(rs.next()) {
                                    // On récupère les champs nécessaires de l'enregistrement courant
                                    String name = rs.getString("DESCRIPTION");
                                    double sales = rs.getDouble("SALES");
                                    // On l'ajoute à la liste des résultats
                                    result.put(name, sales);
                                }
                        }
                        catch(SQLException e){System.out.println("PROBLEME : "+e);}
		}
		return result;
	}
    
}
