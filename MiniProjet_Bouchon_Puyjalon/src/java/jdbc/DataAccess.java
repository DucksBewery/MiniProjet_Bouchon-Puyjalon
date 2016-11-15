package jdbc;

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
        public int verifAuthentification(String email) throws SQLException{
                
                int result = 0;
                String sql = "SELECT CUSTOMER_ID FROM APP.CUSTOMER WHERE EMAIL = ?";
                // Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, email);
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
			// On récupère le champ CUSTOMER_ID de l'enregistrement courant
			result = rs.getInt("CUSTOMER_ID");
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();
                
                return result;
        }
        
        /**
	 * Trouver un Customer à partir de sa clé
	 *
	 * @param customerID la clé du CUSTOMER à rechercher
	 * @return l'enregistrement correspondant dans la table CUSTOMER, ou null si pas trouvé
	 * @throws SQLException
	 */
	public String findNameOfCustomer(int customerID) throws SQLException {
		String result = null;

		// Une requête SQL paramétrée
		String sql = "SELECT NAME FROM CUSTOMER WHERE CUSTOMER_ID = ?";
		// Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, customerID);
		
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
			// On récupère les champs nécessaires de l'enregistrement courant
			result = rs.getString("NAME");
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}
        
        /**
	 *
	 * @return le nombre d'enregistrements dans la table CUSTOMER
	 * @throws SQLException
	 */
	public int numberOfCustomers() throws SQLException {
		int result = 0;

		String sql = "SELECT COUNT(*) AS NUMBER FROM CUSTOMER";
		// Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		Statement stmt = connection.createStatement();
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
			// On récupère le champ NUMBER de l'enregistrement courant
			result = rs.getInt("NUMBER");
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();

		return result;
	}
	
	/**
	 *
	 * @param customerId la clé du client à recherche
	 * @return le nombre de bons de commande pour ce client (table PURCHASE_ORDER)
	 * @throws SQLException
	 */
	public int numberOfOrdersForCustomer(int customerId) throws SQLException {
		int result = 0;

		// Une requête SQL paramétrée
		String sql = "SELECT COUNT(*) AS NUMBER FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ?";
		// Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, customerId);
		
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
			// On récupère le champ NUMBER de l'enregistrement courant
			result = rs.getInt("NUMBER");
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}

	/**
	 * Trouver un Customer à partir de sa clé
	 *
	 * @param customerID la clé du CUSTOMER à rechercher
	 * @return l'enregistrement correspondant dans la table CUSTOMER, ou null si pas trouvé
	 * @throws SQLException
	 */
	public Customer findCustomer(int customerID) throws SQLException {
		Customer result = null;

		// Une requête SQL paramétrée
		String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
		// Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, customerID);
		
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
			// On récupère les champs nécessaires de l'enregistrement courant
			String name = rs.getString("NAME");
			String address = rs.getString("ADDRESSLINE1");
			// On crée l'objet "entity"
			result = new Customer(customerID, name, address);
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();
		
		return result;
	}

	/**
	 * Liste des clients localisés dans un état des USA
	 *
	 * @param state l'état à rechercher (2 caractères)
	 * @return la liste des clients habitant dans cet état
	 * @throws SQLException
	 */
	public List<Customer> customersInState(String state) throws Exception {
		if (null == state)
			throw new Exception("state is null");
		
		List<Customer> result = new LinkedList<>();

		// Une requête SQL paramétrée
		String sql = "SELECT * FROM CUSTOMER WHERE STATE = ?";
		// Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, state);
		
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) { 
			// On récupère les champs nécessaires de l'enregistrement courant
			int id = rs.getInt("CUSTOMER_ID");
			String name = rs.getString("NAME");
			String address = rs.getString("ADDRESSLINE1");
			// On crée l'objet entité
			Customer c = new Customer(id, name, address);
			// On l'ajoute à la liste des résultats
			result.add(c);
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();
		
		return result;

	}

	/**
	 * Liste des états des USA présents dans la table CUSTOMER
	 *
	 * @return la liste des états
	 * @throws SQLException
	 */
	public List<String> existingStates() throws SQLException {
		List<String> result = new LinkedList<>();

		// Une requête SQL paramétrée
		String sql = "SELECT DISTINCT STATE FROM CUSTOMER";
		// Ouvrir une connexion
		Connection connection = myDataSource.getConnection();
		// On crée un statement pour exécuter une requête
		Statement stmt = connection.createStatement();
		
		// Un ResultSet pour parcourir les enregistrements du résultat
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) { 
			// On récupère les champs nécessaires de l'enregistrement courant
			String state = rs.getString("STATE");
			// On l'ajoute à la liste des résultats
			result.add(state);
		}
		// On ferme tout
		rs.close();
		stmt.close();
		connection.close();
		
		return result;

	}

}
