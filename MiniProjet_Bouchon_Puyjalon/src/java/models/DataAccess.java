package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
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
        public Customer verifAuthentification(String login, String mdp) throws SQLException{
                
            Customer utilisateur = null;
            
            String sql = "SELECT * FROM CUSTOMER WHERE EMAIL = ? AND CUSTOMER_ID = CAST( ? AS INTEGER)";
            // try-with-resources, cf. https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try ( Connection connection = myDataSource.getConnection(); 
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, login);
			stmt.setString(2, mdp);
			
			try ( ResultSet rs = stmt.executeQuery()) {
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
        
         public boolean addPurchaseOrder(int customerId, int productId, int quantity, int quantityMax, String freightCompany) throws SQLException{
                boolean result = false;
                String sql2;
                String sql = "INSERT INTO PURCHASE_ORDER " +
                    "VALUES ((SELECT MAX(ORDER_NUM) FROM PURCHASE_ORDER)+1,?,?,?,(SELECT PURCHASE_COST FROM PRODUCT WHERE PRODUCT_ID = ?)*?,CURRENT_DATE,CURRENT_DATE,?)";
                if(quantityMax-quantity == 0){
                        sql2 = "UPDATE PRODUCT SET QUANTITY_ON_HAND = ?, AVAILABLE = 'FALSE' WHERE PRODUCT_ID = ?";
                }
                else{
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
                        stmt.setInt(4, productId);
                        stmt.setInt(5, quantity);
                        stmt.setString(6, freightCompany);
                        
                        stmt2.setInt(1, quantityMax-quantity);
                        stmt2.setInt(2, productId);
                        
                        int rs = stmt.executeUpdate();
                        int rs2 = stmt2.executeUpdate();
                        if (rs != 0 && rs2 != 0){
                                result = true;
                        }
                        
                        stmt.close();
                        stmt2.close();
                        connection.close();
                }
                return result;
        }
        
        public List<Product> availableProductsList() throws SQLException{
                List<Product> produits = new LinkedList<>();
                String sql = "SELECT PRODUCT_ID,DESCRIPTION FROM APP.PRODUCT WHERE AVAILABLE = 'TRUE'";
                        // Ouvrir une connexion
                        try(Connection connection = myDataSource.getConnection();
                        // On crée un statement pour exécuter une requête
                        Statement stmt = connection.createStatement()){
                                // Un ResultSet pour parcourir les enregistrements du résultat
                                try(ResultSet rs = stmt.executeQuery(sql)){
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
        
        public List<String> getFreightCompanies() throws SQLException{
                List<String> companies = new LinkedList<>();
                String sql = "SELECT DISTINCT FREIGHT_COMPANY FROM PURCHASE_ORDER";
                        // Ouvrir une connexion
                        try(Connection connection = myDataSource.getConnection();
                        // On crée un statement pour exécuter une requête
                        Statement stmt = connection.createStatement()){
                                // Un ResultSet pour parcourir les enregistrements du résultat
                                try(ResultSet rs = stmt.executeQuery(sql)){
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
        

        
        public Product productInformations(int idProduct) throws SQLException{
                Product produit = new Product(idProduct);
                String sql = "SELECT PRODUCT_ID,PURCHASE_COST,QUANTITY_ON_HAND,p.DESCRIPTION,NAME AS MANUFACTURER_NAME,c.DESCRIPTION AS PROD_TYPE \n" +
                    "FROM PRODUCT p \n" +
                    "INNER JOIN MANUFACTURER m ON (p.MANUFACTURER_ID = m.MANUFACTURER_ID) \n" +
                    "INNER JOIN PRODUCT_CODE c ON (p.PRODUCT_CODE = c.PROD_CODE) \n" +
                    "WHERE p.PRODUCT_ID = ?";
                try ( // Ouvrir une connexion
                Connection connection = myDataSource.getConnection();
                // On crée un statement pour exécuter une requête
                PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setInt(1, idProduct);
                        try ( // Un ResultSet pour parcourir les enregistrements du résultat
                        ResultSet rs = stmt.executeQuery()) {
                                if(rs.next()) {
                                    produit.setManufacturer(rs.getString("MANUFACTURER_NAME"));
                                    produit.setDescription(rs.getString("DESCRIPTION"));
                                    produit.setProductCost(rs.getFloat("PURCHASE_COST"));
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
}
