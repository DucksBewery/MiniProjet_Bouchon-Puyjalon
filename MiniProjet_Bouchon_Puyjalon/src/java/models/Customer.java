package models;

/**
 * Correspond à un enregistrement de la table Customer
 * @author puyjalon bouchon
 */
public class Customer {
	// TODO : ajouter les autres propriétés
	private int customerId;
	private String name;
	private String addressLine1;

	public Customer(int customerId, String name, String addressLine1) {
		this.customerId = customerId;
		this.name = name;
		this.addressLine1 = addressLine1;
	}

	/**
	 * Get the value of customerId
	 *
	 * @return the value of customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public String getName() {
		return name;
	}
        
	/**
	 * Get the value of addressLine1
	 *
	 * @return the value of addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

        
        public void setCustomerId(int id){
            this.customerId = id;
        }
        
        public void setName(String nom){
            this.name = nom;
        }
        
        public void setAddressLine1(String address){
           this.addressLine1 = address;
        }
}
