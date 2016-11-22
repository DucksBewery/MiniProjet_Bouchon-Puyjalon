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
}
